package com.genesis.apps.comm.model.repo;

import android.text.TextUtils;

import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.room.DatabaseHolder;
import com.genesis.apps.room.GlobalData;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

import static com.genesis.apps.comm.model.vo.CardVO.CARD_STATUS_20;
import static com.genesis.apps.comm.model.vo.CardVO.CARD_STATUS_30;

public class CardRepository {
    private DatabaseHolder databaseHolder;

    public final MutableLiveData<NetUIResponse<List<CardVO>>> cardVoList = new MutableLiveData<>();

    @Inject
    public CardRepository(DatabaseHolder databaseHolder){
        this.databaseHolder = databaseHolder;
    }

//    /**
//     * @brief 전체 메뉴 리스트
//     * 전체 메뉴를 정의된 enum에서 로드
//     * @return
//     */
//    public MutableLiveData<NetUIResponse<String>> getMenuList(){
//        favoriteCard.setValue(NetUIResponse.success(APPIAInfo.getQuickMenus()));
//        return menuList;
//    }
    /**
     */
    public MutableLiveData<NetUIResponse<List<CardVO>>> getNewCardList(List<CardVO> cardVOList){
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            List<CardVO> newList = new ArrayList<>();
            String cardNo="";
            try {
                final String cardNoFromDB = databaseHolder.getDatabase().globalDataDao().get("card");
                //DB에 저장된 카드번호와 서버로 부터 받은 카드 리스트 중 카드번호가 일치하는 것이 1개인지 확인
                long count = cardVOList.stream().filter(data -> data.getCardNo().equalsIgnoreCase(cardNoFromDB)).count();
                if(count!=1){//1개가 아닐 경우 서버 리스트에서
                    //카드 상태가 정상이고 카드번호가 있는 것 중에서 발급일자가 가장 최신인 카드의 카드번호 추출
                    cardNo = cardVOList.stream().filter(data -> data.getCardStusNm().equalsIgnoreCase(CardVO.CARD_STATUS_10) && !TextUtils.isEmpty(data.getCardNo())).max(Comparator.comparingInt(data -> Integer.parseInt(data.getCardIsncSubspDt()))).get().getCardNo();
                    if(TextUtils.isEmpty(cardNo)) cardNo="";

                    updateCard(cardNo);
                }else{
                    cardNo = cardNoFromDB;
                }

                for (CardVO cardVO : cardVOList) {
                    if (cardVO.getCardStusNm().equalsIgnoreCase(CARD_STATUS_20)
                            || cardVO.getCardStusNm().equalsIgnoreCase(CARD_STATUS_30)) {
                      //데이터를 추가하지 않음
                    } else if (cardVO.getCardNo().equalsIgnoreCase(cardNo) && !TextUtils.isEmpty(cardNo)) {
                        cardVO.setFavorite(true);
                        newList.add(0, cardVO);
                    } else {
                        cardVO.setFavorite(false);
                        newList.add(cardVO);
                    }
                }
                if(!haveAddCard(newList))
                    newList.add(new CardVO(0,"", "", "", CardVO.CARD_STATUS_99, "", "", "", 0,false));

            } catch (Exception e1) {
                e1.printStackTrace();
                newList=null;
            }
            return newList;
        }), new FutureCallback<List<CardVO>>() {
            @Override
            public void onSuccess(@NullableDecl List<CardVO> cardVOList) {
                if(cardVOList==null){
                    cardVoList.setValue(NetUIResponse.error(0,null));
                }else{
                    cardVoList.setValue(NetUIResponse.success(cardVOList));
                }
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                cardVoList.setValue(NetUIResponse.error(0,null));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());

        return cardVoList;
    }

    public boolean updateCard(String cardNo){
        boolean isAdd = false;
        try{
            GlobalData data = new GlobalData("card",cardNo);
            databaseHolder.getDatabase().globalDataDao().insert(data);
            isAdd=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isAdd;
    }



    //서버로부터 BAR 1001 전문으로 데이터를 받을 때..DB에 그대로 업데이트
    public List<CardVO> insertCardVO(List<CardVO> list){

        try{
            final List<CardVO> blueList = list.stream().filter(data -> data.getIsncCd().equalsIgnoreCase(OilPointVO.OIL_CODE_BLUE)).collect(Collectors.toList()); //원본 리스트에서 블루멤버스 카드를 가저오고
            list = list.stream().filter(data -> !data.getIsncCd().equalsIgnoreCase(OilPointVO.OIL_CODE_BLUE)).collect(Collectors.toList()); //원본 리스트에서 블루멤버스 카드를 제외한다. (서버에서 준 리스트를 블루멤버스와 기타로 분리)
            final String cardNoFromDB = databaseHolder.getDatabase().globalDataDao().get("card"); //db에서 즐겨찾기된 카드를 확인
            if(TextUtils.isEmpty(cardNoFromDB)){//즐겨찾기가 설정되어있지 않고
                //블루멤버스카드가 1개 이상이면
                if(blueList.size()>0){
                    //블루멤버 카드 목록 중 카드 발급 신청일자가 가장 최근인 카드를 가지고온다.
                    CardVO firstCard = blueList.stream().max(Comparator.comparingInt(data -> Integer.parseInt(data.getCardIsncSubspDt()))).get();
                    if(updateCard(firstCard.getCardNo())){//즐겨찾기로 해당 카드를 등록 후에
                        list.add(0, firstCard); //즐겨찾기가 설정된 블루멤버스 카드를 추가해준다.
                    }
                }
            }else{
                //즐겨찾기가 설정되어 있는 경우
                //list에서 즐겨찾기 카드를 찾고
                CardVO cardFavorite = blueList.stream().filter(data -> data.getCardNo().equalsIgnoreCase(cardNoFromDB)).max(Comparator.comparingInt(data -> Integer.parseInt(data.getCardIsncSubspDt()))).get();
                if(cardFavorite!=null){
                    //서버에서준 블루멤버스 리스트에 즐겨찾기된 카드가 있으면
                    list.add(0, cardFavorite); //즐겨찾기가 설정된 블루멤버스 카드를 추가해준다.
                }else{
                    //즐겨찾기된 카드가 서버에서 준 리스트에 없으면
                    if(blueList.size()>0){
                        //블루멤버 카드 목록 중 카드 발급 신청일자가 가장 최근인 카드를 가지고온다.
                        CardVO firstCard = blueList.stream().max(Comparator.comparingInt(data -> Integer.parseInt(data.getCardIsncSubspDt()))).get();
                        if(updateCard(firstCard.getCardNo())){//즐겨찾기로 해당 카드를 등록 후에
                            list.add(0, firstCard); //즐겨찾기가 설정된 블루멤버스 카드를 추가해준다.
                        }
                    }
                }
            }

            //db리스트 전체 삭제 후
            databaseHolder.getDatabase().cardDao().deleteAll();

            if(list.size()>0){//카드가 있는 경우 db에 다시 넣어준다.
                databaseHolder.getDatabase().cardDao().insert(list);
            }
        }catch (Exception e){

        }

        return databaseHolder.getDatabase().cardDao().selectAll();
    }


    public boolean changeCardOrder(List<CardVO> list){
        boolean isAdd=false;
        try{
            //임의로 순서 지정
            for(int i=0; i<list.size();i++){
                list.get(i).setOrderNumber(i);
            }
            databaseHolder.getDatabase().cardDao().insertAndDeleteInTransaction(list);
            isAdd=true;
        }catch (Exception e){
            isAdd=false;
        }
        return isAdd;
    }


    /**
     * 이미 임시로 추가된 "카드 추가" 가 있는지 없는지 유무 확인
     * @param list
     * @return
     */
    public boolean haveAddCard(List<CardVO> list){
        for(CardVO cardVO : list){
            if(cardVO.getCardStusNm().equalsIgnoreCase(CardVO.CARD_STATUS_99)){
                return true;
            }
        }
        return false;
    }


}
