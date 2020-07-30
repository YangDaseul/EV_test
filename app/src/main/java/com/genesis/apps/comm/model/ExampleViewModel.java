package com.genesis.apps.comm.model;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.net.NetUIResponse;

import java.util.ArrayList;

public class ExampleViewModel extends ViewModel {

    private final ExampleRepository repository;
    private final SavedStateHandle savedStateHandle;

//    private final MutableLiveData<NetUIResponse<ExampleResVO>> exampleResVo = new MutableLiveData<>();
//    public final LiveData<ArrayList<ExampleResVO>> datas = Transformations.map(exampleResVo, exampleResVo->parsingOnlyData(exampleResVo) );


    private MutableLiveData<NetUIResponse<ExampleResVO>> exampleResVo = new MutableLiveData<>();
    public final LiveData<ArrayList<ExampleResVO>> datas = Transformations.map(exampleResVo, exampleResVo->parsingOnlyData(exampleResVo) );


    @ViewModelInject
    ExampleViewModel(
            ExampleRepository repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
    }

    //무조건 데이터를 로딩하는 방식, 데이터가 없으면 로딩하는 것도 넣어줘야할듯 ? 혹은 onResume에 넣으면.. 같이 해결되지 않을까..
    public void reqData(ExampleReqVO reqData, String url, String type){
        exampleResVo = repository.reqDataTest(reqData, url, type);
    }

    public MutableLiveData<NetUIResponse<ExampleResVO>> getResVo() {
        return exampleResVo;
    }


    public MutableLiveData<ExampleResVO> getExampleResVO(){

        MutableLiveData<ExampleResVO> data = new MutableLiveData<>();
        if(exampleResVo!=null&&exampleResVo.getValue()!=null){
            data.setValue(exampleResVo.getValue().data);
        }

        return data;
    }

    private ArrayList<ExampleResVO> parsingOnlyData(NetUIResponse<ExampleResVO> exampleResVo){

        ArrayList<ExampleResVO> datas = new ArrayList<>();
        if(exampleResVo!=null)
            datas.add(exampleResVo.data);

        return datas;
    }


//    private LiveData<ArrayList<ExampleResVO>> parsingOnlyData(NetUIResponse<ExampleResVO> exampleResVo){
//
//        MutableLiveData<ArrayList<ExampleResVO>> data = new MutableLiveData<>();
//
//        ArrayList<ExampleResVO> datas = new ArrayList<>();
//        if(exampleResVo!=null) {
//            datas.add(exampleResVo.data);
//            data.setValue(datas);
//        }
//        return data;
//    }



    public void setAdd(){
        exampleResVo.getValue().data.setValue(exampleResVo.getValue().data.getValue()+1);
        exampleResVo.setValue(exampleResVo.getValue());
    }
}