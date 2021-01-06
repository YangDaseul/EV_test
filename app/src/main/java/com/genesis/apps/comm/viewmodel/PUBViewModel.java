package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.gra.PUB_1001;
import com.genesis.apps.comm.model.api.gra.PUB_1002;
import com.genesis.apps.comm.model.api.gra.PUB_1003;
import com.genesis.apps.comm.model.repo.PUBRepo;
import com.genesis.apps.comm.model.vo.AddressCityVO;
import com.genesis.apps.comm.model.vo.AddressGuVO;
import com.genesis.apps.comm.net.NetUIResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

public @Data
class PUBViewModel extends ViewModel {

    private final PUBRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<PUB_1001.Response>> RES_PUB_1001;
    private MutableLiveData<NetUIResponse<PUB_1002.Response>> RES_PUB_1002;
    private MutableLiveData<NetUIResponse<PUB_1003.Response>> RES_PUB_1003;

//    public final LiveData<List<String>> listAddrSidoNm = Transformations.map(RES_PUB_1002, input -> getAddrNm(input.data.getSidoList()));
//    public final LiveData<List<String>> listAddrGuNm = Transformations.map(RES_PUB_1003, input -> getAddrGuNm(input.data.getGugunList()));

    private MutableLiveData<List<String>> filterInfo = new MutableLiveData<>();


    @ViewModelInject
    PUBViewModel(
            PUBRepo repository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_PUB_1001 = repository.RES_PUB_1001;
        RES_PUB_1002 = repository.RES_PUB_1002;
        RES_PUB_1003 = repository.RES_PUB_1003;
    }

    public void reqPUB1001(final PUB_1001.Request reqData) {
        repository.REQ_PUB_1001(reqData);
    }

    public void reqPUB1002(final PUB_1002.Request reqData) {
        repository.REQ_PUB_1002(reqData);
    }

    public void reqPUB1003(final PUB_1003.Request reqData) {
        repository.REQ_PUB_1003(reqData);
    }


    public void setFilterInfo(String fillerCd, String addr, String addrDtl){
        List<String> fillterInfo = new ArrayList<>();
        fillterInfo.add(0,fillerCd);
        fillterInfo.add(1,addr);
        fillterInfo.add(2,addrDtl);
        filterInfo.setValue(fillterInfo);
    }


    public List<String> getAddrNm(){
        List<String> addrNmList = new ArrayList<>();

        try {
            for (AddressCityVO addressCityVO : RES_PUB_1002.getValue().data.getSidoList()) {
                addrNmList.add(addressCityVO.getSidoNm());
            }
        }catch (Exception e){

        }

        return addrNmList;
    }

    public List<String> getAddrGuNm(){
        List<String> addrNmList = new ArrayList<>();
        if(RES_PUB_1003.getValue().data.getGugunList()!=null&&RES_PUB_1003.getValue().data.getGugunList().size()>0){
            addrNmList = RES_PUB_1003.getValue().data.getGugunList().stream().sorted(Comparator.comparing(AddressGuVO::getGugunNm)).map(AddressGuVO::getGugunNm).collect(Collectors.toList()); //구군명칭 순 가나다 정렬 후 get String List
        }
//        List<String> addrNmList = new ArrayList<>();
//        try {
//            for (AddressGuVO addressGuVO : RES_PUB_1003.getValue().data.getGugunList()) {
//                addrNmList.add(addressGuVO.getGugunNm());
//            }
//        }catch (Exception e){
//
//        }
        return addrNmList;
    }

    public String getSidoCode(String sidoNm){

        String sidoCd="";

        try{
            List<AddressCityVO> list = RES_PUB_1002.getValue().data.getSidoList();

            for(AddressCityVO addressCityVO : list){
                if(addressCityVO.getSidoNm().equalsIgnoreCase(sidoNm)){
                    sidoCd = addressCityVO.getSidoCd();
                    break;
                }
            }
        }catch (Exception e){

        }

        return sidoCd;
    }

    public String getGuCode(String guNm){
        String guCd="";

        try{
            List<AddressGuVO> list = RES_PUB_1003.getValue().data.getGugunList();

            for(AddressGuVO addressGuVO : list){
                if(addressGuVO.getGugunNm().equalsIgnoreCase(guNm)){
                    guCd = addressGuVO.getGugunCd();
                    break;
                }
            }
        }catch (Exception e){

        }
        return guCd;
    }
}