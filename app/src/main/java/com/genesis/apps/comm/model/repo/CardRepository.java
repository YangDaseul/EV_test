package com.genesis.apps.comm.model.repo;

import android.text.TextUtils;

import com.genesis.apps.comm.model.vo.CardVO;
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
                newList.add(new CardVO("", "", "", CardVO.CARD_STATUS_99, "", "", "", false));

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


}
