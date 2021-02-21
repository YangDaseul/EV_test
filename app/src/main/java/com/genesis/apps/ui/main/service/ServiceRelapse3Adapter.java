package com.genesis.apps.ui.main.service;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.VOCInfoVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.databinding.ItemRelapse3BottomBinding;
import com.genesis.apps.databinding.ItemRelapse3RepairHistoryBinding;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCalendar;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ServiceRelapse3Adapter extends BaseRecyclerViewAdapter2<ServiceRelapse3Adapter.RepairData> {
    private static final String TAG = ServiceRelapse3Adapter.class.getSimpleName();
    public static final int REPAIR_HISTORY_MAX_SIZE = 4;//입력창 3개 제공 + 아래쪽 UI용 한 칸

    private static final int VIEW_TYPE_DEFECT_HISTORY = 1;
    private static final int VIEW_TYPE_BOTTOM = 2;

    private final ServiceRelapse3Activity activity;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private String mechanicErrorString;
    private String defectDetailErrorString;
    private String repairDetailErrorString;

    ServiceRelapse3Adapter(ServiceRelapse3Activity activity) {
        this.activity = activity;
        mechanicErrorString = activity.getString(R.string.relapse_3_repair_mechanic_error);
        defectDetailErrorString = activity.getString(R.string.relapse_3_repair_defect_error);
        repairDetailErrorString = activity.getString(R.string.relapse_3_repair_detail_error);

        addRow(null);   //아이템 길이 확보용 더미이고, 수리내역 아래에 붙는 UI 한 벌이다
    }

    public void addRow() {
        addRow(new RepairData());
    }

    @Override
    public void addRow(RepairData row) {
        Log.d(TAG, "addRow: ");
        if (getItemCount() >= REPAIR_HISTORY_MAX_SIZE) {
            return;
        }

        //앞에다 추가한다
        getItems().add(0, row);

        //개폐 상태 뒤로 한 칸씩 밀기 i 범위 잘 보기
        for (int i = getItemCount() - 1; i > 0; --i) {
            selectedItems.put(i, selectedItems.get(i - 1));
        }

        //새로 들어온 첫 칸은 열림 상태로 설정
        selectedItems.put(0, true);
        Log.d(TAG, "add: selected : " + selectedItems);
        notifyDataSetChanged();

        activity.ui.rvRelapse3RepairHistoryList.smoothScrollToPosition(0);

        validateInputData(true);
    }

    @Override
    public void remove(int pos) {
        Log.d(TAG, "remove: " + pos);
        super.remove(pos);

        //개폐 상태 앞으로 한 칸씩 당기기(맨 뒤 UI 뷰는 제외)
        for (int i = pos; i < getItemCount() - 1; ++i) {
            selectedItems.put(i, selectedItems.get(i + 1));
        }

        Log.d(TAG, "remove: selected : " + selectedItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //마지막 아이템은 다른 UI
        return (position == (getItemCount() - 1)) ? VIEW_TYPE_BOTTOM : VIEW_TYPE_DEFECT_HISTORY;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: viewType : " + viewType);
        if (viewType == VIEW_TYPE_DEFECT_HISTORY) {
            return new ServiceRelapse3DefectHistoryViewHolder(getView(parent, R.layout.item_relapse_3_repair_history));
        } else {
            return new ServiceRelapse3BottomViewHolder(getView(parent, R.layout.item_relapse_3_bottom));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Log.d(TAG, "ServiceRelapse3Adapter onBindViewHolder position : " + position);
        holder.onBindView(getItem(position), position, selectedItems);
    }

    //입력 데이터 전체 검사해서 미비한 게 하나라도 있으면 return false
    // 중간에 입력 미비를 발견해도 검사는 끝까지 하면서 어디가 틀렸는지 뷰에 표시함
    public boolean validateInputData() {
        return validateInputData(false);
    }

    public boolean validateInputData(boolean isNew) {
        boolean valid = true;

        boolean isFocus = true;
        for (int i=0; i<getItems().size(); i ++) {
            RepairData data = getItems().get(i);
            if (data != null) {
                if (!data.validateData(isNew, isFocus)) {
                    valid = false;
                    isFocus = false;
                }
            }
        }

        return valid;
    }

    //하자 재발 신청 3단계 입력창 뷰 홀더
    public class ServiceRelapse3DefectHistoryViewHolder extends BaseViewHolder<RepairData, ItemRelapse3RepairHistoryBinding> {
        private static final int DATE_REQ = 1;
        private static final int DATE_FINISH = 2;

        private View detailView;
        private int iconCloseBtn;
        private int iconOpenBtn;
        private int normalColor;
        private int errorColor;

        private TextWatcher mechanicWatcher;
        private TextWatcher defectDetailWatcher;
        private TextWatcher repairDetailWatcher;
        private int position;

        public ServiceRelapse3DefectHistoryViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ServiceRelapse3ViewHolder: ");

            // TODO 리사이클러 뷰 아이템에 접기/펴기 기능 붙는 경우 그대로 복붙(...)해서 쓰기 가능
            // 생성자에서 detailView(상태 변화대상) 저장해놓고
            // setOpenListener(), setViewStatus(), changeViewStatus() 이 셋이 세트
            // 일단 주석통째로 복붙 해두는데.... 이 정도로 다 겹치면 유틸로 빼거나 상위 클래스에 잘 올리거나 하고싶은데
            detailView = getBinding().lRelapse3RepairHistoryDetail;
            iconOpenBtn = R.drawable.btn_arrow_open;
            iconCloseBtn = R.drawable.btn_arrow_close;
            normalColor = getContext().getColor(R.color.x_000000);
            errorColor = getContext().getColor(R.color.x_ba544d);

            getBinding().etRelapse3Mechanic.setOnFocusChangeListener(focusChangeListener);
            getBinding().etRelapse3DefectDetail.setOnFocusChangeListener(focusChangeListener);
            getBinding().etRelapse3RepairDetail.setOnFocusChangeListener(focusChangeListener);

            getBinding().etRelapse3Mechanic.setOnEditorActionListener(editorActionListener);
            getBinding().etRelapse3DefectDetail.setOnEditorActionListener(editorActionListener);
            getBinding().etRelapse3RepairDetail.setOnEditorActionListener(editorActionListener);

            initTextChangeListener();
            initDatePicker();
        }

        EditText.OnFocusChangeListener focusChangeListener = (view, hasFocus) -> {
            if (hasFocus) {
                SoftKeyboardUtil.showKeyboard(activity);
            }
        };

        EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                validateInputData();
            }
            return false;
        };

        private void initTextChangeListener() {
            mechanicWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //do nothing
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //do nothing
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        getBinding().lRelapse3Mechanic.setError(mechanicErrorString);
                    } else {
                        getBinding().lRelapse3Mechanic.setError(null);
                    }
                    ((RepairData) getItem(position)).setMechanic(s.toString());
                }
            };

            defectDetailWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //do nothing
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //do nothing
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        getBinding().lRelapse3DefectDetail.setError(defectDetailErrorString);
                    } else {
                        getBinding().lRelapse3DefectDetail.setError(null);
                    }
                    ((RepairData) getItem(position)).setDefectDetail(s.toString());
                }
            };

            repairDetailWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //do nothing
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //do nothing
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        getBinding().lRelapse3RepairDetail.setError(repairDetailErrorString);
                    } else {
                        getBinding().lRelapse3RepairDetail.setError(null);
                    }
                    ((RepairData) getItem(position)).setRepairDetail(s.toString());
                }
            };
        }

        //이거랑 init 이랑 정비소/증상/수리내용 뷰 맞는지 잘 보기
        // (물론 잘 맞춰았지만 처음에 이거 어긋나서 오작동으로 시작해서 적어둠)
        private void setTextChangeListener(boolean enable) {
            if (enable) {
                getBinding().etRelapse3Mechanic.addTextChangedListener(mechanicWatcher);
                getBinding().etRelapse3DefectDetail.addTextChangedListener(defectDetailWatcher);
                getBinding().etRelapse3RepairDetail.addTextChangedListener(repairDetailWatcher);
            } else {
                getBinding().etRelapse3Mechanic.removeTextChangedListener(mechanicWatcher);
                getBinding().etRelapse3DefectDetail.removeTextChangedListener(defectDetailWatcher);
                getBinding().etRelapse3RepairDetail.removeTextChangedListener(repairDetailWatcher);

                getBinding().lRelapse3Mechanic.setError(null);
                getBinding().lRelapse3DefectDetail.setError(null);
                getBinding().lRelapse3RepairDetail.setError(null);
            }
        }

        private void initDatePicker() {
            getBinding().tvRelapse3RepairReqDateBtn.setOnClickListener(v -> showDatePicker(DATE_REQ));
            getBinding().tvRelapse3RepairFinishDateBtn.setOnClickListener(v -> showDatePicker(DATE_FINISH));
        }

        private void showDatePicker(int which) {
            SoftKeyboardUtil.hideKeyboard(activity, activity.getWindow().getDecorView().getWindowToken());

            DialogCalendar dialogCalendar = new DialogCalendar(getContext(), R.style.BottomSheetDialogTheme);
            dialogCalendar.setOnDismissListener(dialogInterface -> setDate(which, dialogCalendar.calendar));
            dialogCalendar.setCalendarMaximum(Calendar.getInstance(Locale.getDefault()));
            dialogCalendar.setTitle(getContext().getString(which == DATE_REQ ? R.string.relapse_3_repair_pick_req_date : R.string.relapse_3_repair_pick_finish_date));
            dialogCalendar.show();
        }

        private void setDate(int which, Calendar calendar) {
            if (calendar == null) {
                return;
            }

            String date = DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot);
            switch (which) {
                case DATE_REQ:
                    getBinding().tvRelapse3RepairReqDateBtn.setText(date);
                    ((RepairData) getItem(position)).setReqDate(date);

                    getBinding().tvRelapse3RepairReqDateTitle.setTextColor(normalColor);
                    getBinding().tvRelapse3RepairReqDateBtn.setTextColor(normalColor);
                    getBinding().tvRelapse3RepairReqDateBtn.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_141414);
                    getBinding().tvRelapse3RepairReqDateTitle.setVisibility(View.VISIBLE);
                    getBinding().tvRelapse3RepairReqDateError.setVisibility(View.GONE);

                    validateInputData();

                    break;

                case DATE_FINISH:
                    getBinding().tvRelapse3RepairFinishDateBtn.setText(date);
                    ((RepairData) getItem(position)).setFinishDate(date);

                    getBinding().tvRelapse3RepairFinishDateTitle.setTextColor(normalColor);
                    getBinding().tvRelapse3RepairFinishDateBtn.setTextColor(normalColor);
                    getBinding().tvRelapse3RepairFinishDateBtn.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_141414);
                    getBinding().tvRelapse3RepairFinishDateTitle.setVisibility(View.VISIBLE);
                    getBinding().tvRelapse3RepairFinishDateError.setVisibility(View.GONE);

                    validateInputData();

                    break;

                default:
                    //do nothing
                    break;
            }
        }

        @Override
        public void onBindView(RepairData item) {
            //do nothing
        }

        @Override
        public void onBindView(RepairData item, int pos) {
            //do nothing
        }

        @Override
        public void onBindView(RepairData item, int pos, SparseBooleanArray selectedItems) {
            Log.d(TAG, "onBindView{item}: " + selectedItems + " / " + pos);

            position = pos;

            //재사용될 때 꼬이니까 리스너 한 번 떼고
            setTextChangeListener(false);

            //뷰에 들어갈 데이터 넣고
            setData(item, pos);

            //리스너 다시 연결
            setTextChangeListener(true);

            //세부사항 접기/펴기 리스너
            setOpenListener(pos, selectedItems);

            //열림/닫힘 구분해서 세부사항 뷰 접기/열기 아이콘 및 visibility 처리
            setViewStatus(selectedItems.get(pos));
        }

        private void setData(RepairData item, int pos) {
            //몇회차인가(거꾸로 세기, 뒤에 별도 UI가 한 칸 먹음)
            int turn = getBindingAdapter().getItemCount() - pos - 1;
            getBinding().tvRelapse3RepairHistoryTitle.setText(turn + getContext().getString(R.string.relapse_3_repair_count));

            //삭제 버튼. 1회차는 삭제버튼 없다
            getBinding().tvRelapse3RepairHistoryDeleteBtn.lWhole.setOnClickListener(turn == 1 ? null : v -> remove(pos));
            getBinding().tvRelapse3RepairHistoryDeleteBtn.lWhole.setVisibility(turn == 1 ? View.GONE : View.VISIBLE);

            //데이터를 뷰에 출력
            getBinding().etRelapse3Mechanic.setText(item.mechanic);
            getBinding().tvRelapse3RepairReqDateBtn.setText(item.reqDate);
            getBinding().tvRelapse3RepairFinishDateBtn.setText(item.finishDate);
            getBinding().etRelapse3DefectDetail.setText(item.defectDetail);
            getBinding().etRelapse3RepairDetail.setText(item.repairDetail);

            //입력 검사할 때 오류표시를 하기 위해 데이터에도 홀더 인스턴스를 저장
            item.setHolder(this);

            //입력 유효성 검사
            item.validateData(true, false);

            item.setTurn(turn);
        }

        //접기/펴기 리스너 붙이기 :
        private void setOpenListener(int pos, SparseBooleanArray selectedItems) {
            getBinding().tvRelapse3RepairHistoryTitle.setOnClickListener(this::onClickViewOpener);
        }

        private void onClickViewOpener(View v) {
            Log.d(TAG, "recyclerView onClick position : " + selectedItems + " / " + position);

            getBinding().etRelapse3Mechanic.clearFocus();
            getBinding().etRelapse3DefectDetail.clearFocus();
            getBinding().etRelapse3RepairDetail.clearFocus();

            //클릭하면 상태 변경을 저장하고
            if (selectedItems.get(position)) {
                selectedItems.delete(position);  // 펼쳐진 Item 클릭하면 열림 목록에서 삭제
            } else {
                selectedItems.put(position, true);// 닫힌 거 클릭하면 열림 목록에 추가
            }

            //변경된 상태를 반영
            notifyItemChanged(position);
//            changeViewStatus(selectedItems.get(position));
        }

        //세부사항 뷰의 개폐 상태를 처리
        // (화면 밖에 있다가 스크롤되어서 화면 안에 들어오는 경우 호출됨)
        private void setViewStatus(boolean opened) {
            Log.d(TAG, "setViewStatus: " + opened);
            setIcon(opened ? iconCloseBtn : iconOpenBtn);
            detailView.setVisibility(opened ? View.VISIBLE : View.GONE);//TODO 이거 도대체 왜 반영 안 되냐
        }

//        //세부사항 뷰의 개폐 상태가 변경되는 애니메이션을 처리
//        // (클릭해서 상태를 토글할 때 호출됨)
//        private void changeViewStatus(boolean opened) {
//            Log.d(TAG, "changeViewStatus: " + opened);
//            setIcon(opened ? iconCloseBtn : iconOpenBtn);
//            changeVisibility(
//                    detailView, //개폐 애니메이션 대상 뷰
//                    activity.ui.rvRelapse3RepairHistoryList,//null,// (View) detailView.getParent().getParent(), //애니 재생동안 스크롤 막아야되는 뷰
//                    opened); //개폐 상태
//        }

        //드롭다운 아이콘의 개폐 상태를 변경
        private void setIcon(int icon) {
            //setDrawableRight()는 없고 네 방향을 한 번에 넣네 'ㅅ'..
            getBinding().tvRelapse3RepairHistoryTitle.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getContext().getDrawable(icon),
                    null);
        }

        //날짜 표시 뷰 오류 표시 on/off
        public void setDateErrorEnabled(boolean enabled) {
            getBinding().tvRelapse3RepairReqDateTitle.setTextColor(enabled ? errorColor : normalColor);
            getBinding().tvRelapse3RepairFinishDateTitle.setTextColor(enabled ? errorColor : normalColor);

            getBinding().tvRelapse3RepairReqDateBtn.setTextColor(enabled ? errorColor : normalColor);
            getBinding().tvRelapse3RepairFinishDateBtn.setTextColor(enabled ? errorColor : normalColor);
            getBinding().tvRelapse3RepairReqDateBtn.setBackgroundResource(enabled ? R.drawable.ripple_bg_ffffff_stroke_ba544d : R.drawable.ripple_bg_ffffff_stroke_141414);
            getBinding().tvRelapse3RepairFinishDateBtn.setBackgroundResource(enabled ? R.drawable.ripple_bg_ffffff_stroke_ba544d : R.drawable.ripple_bg_ffffff_stroke_141414);

            getBinding().tvRelapse3RepairReqDateError.setVisibility(enabled ? View.VISIBLE : View.GONE);
            getBinding().tvRelapse3RepairFinishDateError.setVisibility(enabled ? View.VISIBLE : View.GONE);
        }

        public void setReqDateErrorEnabled(boolean enabled) {
            getBinding().tvRelapse3RepairReqDateTitle.setTextColor(enabled ? errorColor : normalColor);
            getBinding().tvRelapse3RepairReqDateBtn.setTextColor(enabled ? errorColor : normalColor);
            getBinding().tvRelapse3RepairReqDateBtn.setBackgroundResource(enabled ? R.drawable.ripple_bg_ffffff_stroke_ba544d : R.drawable.ripple_bg_ffffff_stroke_141414);
            getBinding().tvRelapse3RepairReqDateError.setVisibility(enabled ? View.VISIBLE : View.GONE);
        }

        public void setFinishDateErrorEnabled(boolean enabled) {
            getBinding().tvRelapse3RepairFinishDateTitle.setTextColor(enabled ? errorColor : normalColor);
            getBinding().tvRelapse3RepairFinishDateBtn.setTextColor(enabled ? errorColor : normalColor);
            getBinding().tvRelapse3RepairFinishDateBtn.setBackgroundResource(enabled ? R.drawable.ripple_bg_ffffff_stroke_ba544d : R.drawable.ripple_bg_ffffff_stroke_141414);
            getBinding().tvRelapse3RepairFinishDateError.setVisibility(enabled ? View.VISIBLE : View.GONE);
        }
    }

    //수리 내역 입력창 밑에 붙는 UI
    public class ServiceRelapse3BottomViewHolder extends BaseViewHolder<RepairData, ItemRelapse3BottomBinding> {
        private BottomListDialog defectListDialog;

        public ServiceRelapse3BottomViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(RepairData item) {

        }

        @Override
        public void onBindView(RepairData item, int pos) {

        }

        @Override
        public void onBindView(RepairData item, int pos, SparseBooleanArray selectedItems) {
            setListener();
            setViewStatus(determineAddBtnVisibility());

            //아이템이 하단 UI뿐인 상태이면 하자 구분 대화상자 즉시 호출
            if (getBindingAdapter().getItemCount() == 1) {
                showDefectListDialog(null);
            }
        }

        private void setListener() {
            getBinding().tvRelapse3RepairAddBtn.setOnClickListener(v -> addRow(new RepairData()));
            getBinding().tvRelapseDefectSelect.setOnClickListener(this::showDefectListDialog);
        }

        //하자 구분 다이얼로그
        private void showDefectListDialog(View v) {
            Log.d(TAG, "showDefectListDialog: " + defectListDialog);

            if (defectListDialog == null) {
                defectListDialog = new BottomListDialog(getContext(), R.style.BottomSheetDialogTheme);
                defectListDialog.setTitle(getContext().getString(R.string.relapse_list_01));
                final List<String> defectList = Arrays.asList(getContext().getResources().getStringArray(R.array.service_relapse_defect_list));
                defectListDialog.setDatas(defectList);
                defectListDialog.setOnDismissListener(this::onDismissDefectListDialog);
            }

            defectListDialog.setSelectItem(null);
            defectListDialog.show();
        }

        //수리내역 입력창 열기
        private void onDismissDefectListDialog(DialogInterface dialog) {
            if (defectListDialog.getSelectItem() != null) {
                //선택한 내용을 버튼에 표시(따로 저장은 안 함. 버튼 글씨만 변함)
                getBinding().tvRelapseDefectSelect.setText(defectListDialog.getSelectItem());
                Paris.style(getBinding().tvRelapseDefectSelect).apply(R.style.CommonSpinnerItemEnable);
                getBinding().tvTitleRelapseDefectSelect.setVisibility(View.VISIBLE);
                if(defectListDialog.getSelectItem().equalsIgnoreCase(activity.getString(R.string.relapse_req_result_defect_high))){
                    //중대한 하자
                    activity.setFlawCd(VOCInfoVO.DEFECT_LEVEL_HIGH);
                }else{
                    //일반 하자
                    activity.setFlawCd(VOCInfoVO.DEFECT_LEVEL_LOW);
                }
                //입력창 하나 추가(아이템이 UI 뷰 하나만 있을 때 한정)
                if (getItemCount() == 1) {
                    addRow(new RepairData());
                    activity.changeStatusToDefectHistory();
                }
            }
        }

        //세부사항 뷰의 개폐 상태를 처리
        private void setViewStatus(boolean opened) {
            getBinding().lRelapse3RepairAddContainer.setVisibility(opened ? View.VISIBLE : View.GONE);
        }

        public boolean determineAddBtnVisibility() {
            //1개인 경우는 데이터가 아니라 UI 뷰라서 제외 : 1개보다 많고 최대치보다 적으면 add 버튼 활성화
            return 1 < getItemCount() && getItemCount() < REPAIR_HISTORY_MAX_SIZE;
        }

    }

    //입력 데이터
    public class RepairData extends BaseData {
        private ServiceRelapse3DefectHistoryViewHolder holder;
        private String mechanic;
        private String reqDate;     //YYYYMMDD
        private String finishDate;  //YYYYMMDD
        private String defectDetail;
        private String repairDetail;
        private int turn;

        public RepairData() {
            mechanic = "";
            reqDate = "";
            finishDate = "";
            defectDetail = "";
            repairDetail = "";
        }

        //입력 창 전체 검사해서 미비한 게 하나라도 있으면 return false
        // 중간에 입력 미비를 발견해도 검사는 끝까지 하면서 어디가 틀렸는지 뷰에 표시함
        // 홀더 없으면 false가 맞나?
        public boolean validateData(boolean isNew, boolean isFocus) {
            if (holder == null) {
                return false;
            }

            holder.getBinding().etRelapse3Mechanic.clearFocus();
            holder.getBinding().etRelapse3DefectDetail.clearFocus();
            holder.getBinding().etRelapse3RepairDetail.clearFocus();

            boolean isFocusChk = isFocus;
            Log.d("JJJJ", "isFocusChk : " + isFocusChk);
            if (isNew) { //인스턴스 갱신할 때 전부 빈칸인 건 봐줌
                if (TextUtils.isEmpty(mechanic) &&
                        TextUtils.isEmpty(defectDetail) &&
                        TextUtils.isEmpty(repairDetail) &&
                        TextUtils.isEmpty(reqDate) &&
                        TextUtils.isEmpty(finishDate)) {
                    holder.setDateErrorEnabled(false);
                    return false;
                }
            }

            boolean valid = true;

            if (TextUtils.isEmpty(repairDetail)) {
                holder.getBinding().lRelapse3RepairDetail.setError(repairDetailErrorString);
                if(isFocusChk) {
                    if (!selectedItems.get(holder.getLayoutPosition())) {
                        holder.getBinding().tvRelapse3RepairHistoryTitle.performClick();
                    }

                    holder.getBinding().etRelapse3RepairDetail.requestFocus();
                    isFocusChk = false;
                }
//                valid = false;
                return false;
            }

            if (TextUtils.isEmpty(defectDetail)) {
                holder.getBinding().lRelapse3DefectDetail.setError(defectDetailErrorString);
                if(isFocusChk) {
                    if (!selectedItems.get(holder.getLayoutPosition())) {
                        holder.getBinding().tvRelapse3RepairHistoryTitle.performClick();
                    }

                    holder.getBinding().etRelapse3DefectDetail.requestFocus();
                    isFocusChk = false;
                }
//                valid = false;
                return false;
            }

            if(TextUtils.isEmpty(finishDate)) {
                holder.setFinishDateErrorEnabled(true);
                if(isFocusChk) {
                    if (!selectedItems.get(holder.getLayoutPosition())) {
                        holder.getBinding().tvRelapse3RepairHistoryTitle.performClick();
                    }

                    holder.getBinding().tvRelapse3RepairFinishDateBtn.performClick();
                    isFocusChk = false;
                }

//                valid = false;
                return false;
            }

            //날짜 입력 안 했거나
            // 수리 완료일이 요청일보다 미래가 아니면 오류(같은 건 ㅇㅋ 당일에 해결했나보지)
            if(TextUtils.isEmpty(reqDate)) {
                holder.setReqDateErrorEnabled(true);
                if(isFocusChk) {
                    if (!selectedItems.get(holder.getLayoutPosition())) {
                        holder.getBinding().tvRelapse3RepairHistoryTitle.performClick();
                    }

                    holder.getBinding().tvRelapse3RepairReqDateBtn.performClick();
                    isFocusChk = false;
                }

//                valid = false;
                return false;
            }

            if(!TextUtils.isEmpty(reqDate) && !TextUtils.isEmpty(finishDate)) {
                if (0 < reqDate.compareTo(finishDate)) {
                    holder.setDateErrorEnabled(true);

//                    valid = false;
                    return false;
                }
            }

            //검사하는 뷰랑 오류 표시하는 뷰 싱크로 주의

            if (TextUtils.isEmpty(mechanic)) {
                holder.getBinding().lRelapse3Mechanic.setError(mechanicErrorString);
                if(isFocusChk) {
                    if (!selectedItems.get(holder.getLayoutPosition())) {
                        holder.getBinding().tvRelapse3RepairHistoryTitle.performClick();
                    }

                    holder.getBinding().etRelapse3Mechanic.requestFocus();
                    isFocusChk = false;
                }
//                valid = false;
                return false;
            }

            return true;
        }

        public ServiceRelapse3DefectHistoryViewHolder getHolder() {
            return holder;
        }

        public void setHolder(ServiceRelapse3DefectHistoryViewHolder holder) {
            this.holder = holder;
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

        public void setTurn(int turn) {
            this.turn = turn;
        }

        public int getTurn() {
            return turn;
        }
    }
}
