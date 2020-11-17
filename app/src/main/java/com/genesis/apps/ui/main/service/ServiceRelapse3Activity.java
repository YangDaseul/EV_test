package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.VOCInfoVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.VOCViewModel;
import com.genesis.apps.databinding.ActivityServiceRelapseApply3Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;

import java.util.Arrays;
import java.util.List;

public class ServiceRelapse3Activity extends SubActivity<ActivityServiceRelapseApply3Binding> {
    private static final String TAG = ServiceRelapse3Activity.class.getSimpleName();
    private static final int REPAIR_HISTORY_SIZE = 3;

    private BottomListDialog defectListDialog;

    private VOCViewModel viewModel;
    private VehicleVO mainVehicle;
    private VOCInfoVO vocInfoVO;
    private RepairData[] repairHistory = new RepairData[REPAIR_HISTORY_SIZE];//레이아웃 레벨에서 입력창 3개 제공

    public int yesNoVisibility = View.GONE;
    public boolean more4;
    public int totalTermVisibility = View.GONE;
    public int repairCountVisibility = View.GONE;
    public int addHistoryVisibility = View.GONE;
    public int dummyLineVisibility = View.GONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataFromIntent();
        setViewModel();
        setObserver();
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon: " + v.getId());

        switch (v.getId()) {
            //수리 횟수 추가
            case R.id.tv_relapse_3_repair_add_btn:
                //todo impl

                break;

            //하자 구분
            case R.id.tv_relapse_defect_select:
                //todo impl
                break;

            default:
                //do nothing
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(VOCViewModel.class);
    }

    @Override
    public void setObserver() {
        Log.d(TAG, "setObserver: ");

        viewModel.getRES_VOC_1002().observe(this, result -> {
            Log.d(TAG, "setObserver VOC 1002: " + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getRtCd() != null) {
                        if (result.data.getRtCd().equals(BaseResponse.RETURN_CODE_SUCC)) {
                            //TODO impl
                        }

                        showProgressDialog(false);
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    SnackBarUtil.show(this, getString(result.message));
                    //todo : 구체적인 예외처리
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        //todo mainVehicle 데이터 획득(딴 데서 해도 되고...)

    }

    //하자 구분 다이얼로그
    private void showDefectListDialog() {
        Log.d(TAG, "showDefectListDialog: " + defectListDialog);

        //TODO 123번 중 몇 번인지 구분하여 처리하기

        if (defectListDialog == null) {
            defectListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
            defectListDialog.setTitle(getString(R.string.relapse_list_01));
            final List<String> defectList = Arrays.asList(getResources().getStringArray(R.array.service_relapse_defect_list));
            defectListDialog.setDatas(defectList);
            defectListDialog.setOnDismissListener(
                    dialog -> {
                        if (defectListDialog.getSelectItem() != null) {

                            //TODO 선택 결과↓ 처리
                            String selected = defectListDialog.getSelectItem();

                        }
                    }
            );
        }

        defectListDialog.setSelectItem(null);
        defectListDialog.show();
    }

    //TODO 1,2단계 입력정보 및 그 앞 단계인 회원정보 집어넣기
    // 특히 mainVehicle 아직 처리 안 한 상태라 null임
    private VOCInfoVO makeVOCInfoVO(RepairData[] repairHistory) {
        final int FIRST = 0;
        final int SECOND = 1;
        final int THIRD = 2;

        for (RepairData data : repairHistory) {
            if (data == null) {
                //TODO 이거 dataList에 반영되는 거 맞는지 확인
                data = new RepairData();//데이터 없으면 공백문자열로 채우기
            }
        }

        //todo 중대한 하자인가?
        // 클래스 필드로 두거나 해서 어쨌든 유효값 넣기.
        boolean isCritical = true;
        return null;

//        return new VOCInfoVO(
//                "고객 이름",
//                "고객 생년월일",
//                "이메일",
//                "도로명 우편번호",
//                "도로명주소",
//                "도로명 상세주소",
//                "연락처1",//010
//                "연락처2",//전번 앞자리
//                "연락처3",//전번  뒷자리
//
//                mainVehicle.getRecvYmd(),   //인도날짜(recvDt)인데 이거 맞나?
//                mainVehicle.getMdlNm(),     //차명(carNm)인데 이거 맞나?
//                mainVehicle.getMdlCd(),     //차종코드(crnVehlCd)인데 이거 맞나?
//                mainVehicle.getRecvYmd(),   //출고일자(whotYmd)는 인도날짜(이거 세 칸 앞에 거)랑 다른 건가?
//                "주행거리",
//                mainVehicle.getCarRgstNo(),
//                mainVehicle.getVin(),
//                "운행지역 시도",
//                "운행지역 시군구",
//                isCritical ? VOCInfoVO.DEFECT_LEVEL_HIGH : VOCInfoVO.DEFECT_LEVEL_LOW,
//
//                repairHistory[FIRST].getMechanic(),
//                repairHistory[FIRST].getReqDate(),
//                repairHistory[FIRST].getFinishDate(),
//                "주행거리 1회차",//이건 도대체 어떻게 알아내는 거여?
//                repairHistory[FIRST].getDefectDetail(),
//                repairHistory[FIRST].getRepairDetail(),
//
//                repairHistory[SECOND].getMechanic(),
//                repairHistory[SECOND].getReqDate(),
//                repairHistory[SECOND].getFinishDate(),
//                "주행거리 2회차",
//                repairHistory[SECOND].getDefectDetail(),
//                repairHistory[SECOND].getRepairDetail(),
//
//                repairHistory[THIRD].getMechanic(),
//                repairHistory[THIRD].getReqDate(),
//                repairHistory[THIRD].getFinishDate(),
//                "주행거리 3회차",
//                repairHistory[THIRD].getDefectDetail(),
//                repairHistory[THIRD].getRepairDetail(),
//
//                "",//수리시도회수4회이상여부 "Y" "N"
//                "누적수리기간",
//                "개인정보취급동의",//"Y" "N"
//                "",//미사용
//                "",//미사용
//                ""//미사용
//        );
    }

    private class RepairData {
        private String mechanic;
        private String reqDate;     //YYYYMMDD
        private String finishDate;  //YYYYMMDD
        private String defectDetail;
        private String repairDetail;

        public RepairData() {
            mechanic = "";
            reqDate = "";
            finishDate = "";
            defectDetail = "";
            repairDetail = "";
        }

        public String getMechanic() {
            return mechanic;
        }

        public void setMechanic(String mechanic) {
            this.mechanic = mechanic;
        }

        public String getReqDate() {
            return reqDate;
        }

        public void setReqDate(String reqDate) {
            this.reqDate = reqDate;
        }

        public String getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(String finishDate) {
            this.finishDate = finishDate;
        }

        public String getDefectDetail() {
            return defectDetail;
        }

        public void setDefectDetail(String defectDetail) {
            this.defectDetail = defectDetail;
        }

        public String getRepairDetail() {
            return repairDetail;
        }

        public void setRepairDetail(String repairDetail) {
            this.repairDetail = repairDetail;
        }
    }
}
