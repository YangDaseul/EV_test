package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ChargeSearchCategorytype;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.ChargeSearchCategoryVO;
import com.genesis.apps.databinding.FragmentInputChargePlaceBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomCheckListDialog;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class Name : InputChargePlaceFragment
 * 충전소 검색 입력 및 필터 Fragment.
 *
 * @author Ki-man Kim
 * @since 2021-04-09
 */
public class InputChargePlaceFragment extends SubFragment<FragmentInputChargePlaceBinding> {

    public interface FilterChangeListener {
        void onFilterChanged(SEARCH_TYPE type, List<ChargeSearchCategoryVO> filterList);

        void onSearchAddress();

        void onSearchMap();
    }

    public enum SEARCH_TYPE {
        MY_LOCATION(null),
        MY_CAR(null),
        ADDRESS(null);

        private AddressVO addressVO;

        SEARCH_TYPE(AddressVO addressVO) {
            this.addressVO = addressVO;
        }

        public AddressVO getAddressVO() {
            return addressVO;
        }

        public void setAddressVO(AddressVO addressVO) {
            this.addressVO = addressVO;
        }
    }

    private SEARCH_TYPE currentType;
    private FilterChangeListener listener;
    private ChargeSearchFilterAdapter adapter;

    public static InputChargePlaceFragment newInstance(ArrayList<ChargeSearchCategoryVO> filterList) {
        Bundle args = new Bundle();
        if (filterList != null && filterList.size() > 0) {
            args.putParcelableArrayList(KeyNames.KEY_NAME_FILTER_INFO, filterList);
        }
        InputChargePlaceFragment fragment = new InputChargePlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SEARCH_TYPE getCurrentType() {
        return currentType;
    }

    public InputChargePlaceFragment() {
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_input_charge_place);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(InputChargePlaceFragment.this);
        initView();
    }

    @Override
    public void onRefresh() {
//        updateSearchType(currentType);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.rb_btn_my_location: {
                // 내 위치 기준 버튼
                if (currentType != SEARCH_TYPE.MY_LOCATION) {
                    updateSearchType(SEARCH_TYPE.MY_LOCATION);
                    if (this.listener != null) {
                        this.listener.onFilterChanged(currentType, adapter.getSelectedItems());
                    }
                }
                break;
            }
            case R.id.rb_btn_my_car: {
                // 내 차 위치 기준 버튼.
                if (currentType != SEARCH_TYPE.MY_CAR) {
                    updateSearchType(SEARCH_TYPE.MY_CAR);
                    if (this.listener != null) {
                        this.listener.onFilterChanged(currentType, adapter.getSelectedItems());
                    }
                }
                break;
            }
            case R.id.rb_btn_search: {
                // 주소 검색 기준 버튼.
                if (currentType != SEARCH_TYPE.ADDRESS) {
                    SEARCH_TYPE.ADDRESS.setAddressVO(null);
                    updateSearchType(SEARCH_TYPE.ADDRESS);
                    //주소 검색 탭 클릭 시 onFilterChanged 를 호출해도
                    //currentType의 address가 null 이기 때문에 (내위치 혹은 내차위치 선택 시 address 초기화 됨)
                    //서버에 요청은 하지 않으나
                    //검색 리스트를 초기화 하는데에 목적이 있음
                    if (this.listener != null) {
                        this.listener.onFilterChanged(currentType, adapter.getSelectedItems());
                    }
                }
                break;
            }
            case R.id.tv_search_addr: {
                // 주소 검색 버튼
                if (this.listener != null) {
                    this.listener.onSearchAddress();
                }
                break;
            }
            case R.id.iv_btn_map: {
                // 지도 버튼
                if (this.listener != null) {
                    this.listener.onSearchMap();
                }
                break;
            }
            case R.id.tv_name: {
                // 충전소 검색 목록 필터 버튼.
                if (tag instanceof ChargeSearchCategoryVO) {
                    ChargeSearchCategoryVO chargeSearchCategoryVO = (ChargeSearchCategoryVO) tag;
                    switch (chargeSearchCategoryVO.getComponentType()) {
                        case RADIO: {
                            // 선택 팝업 표시 - Radio Type.
                            showRadioBottomDialog(chargeSearchCategoryVO);
                            break;
                        }
                        case CHECK: {
                            // 선택 팝업 표시 - Check Type.
                            showCheckBottomDialog(chargeSearchCategoryVO);
                            break;
                        }
                        default:
                        case ONLY_ONE: {
                            /**
                             * 해당 필터 값은 하나이기에 선택/미선택 전환 처리.
                             */
                            chargeSearchCategoryVO.setSelected(!chargeSearchCategoryVO.isSelected());
                            adapter.notifyDataSetChanged();
                            if (this.listener != null) {
                                this.listener.onFilterChanged(currentType, adapter.getSelectedItems());
                            }
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    public void eventSearchAddress() {
        me.rbBtnSearch.performClick();
    }

    public void setGuideErrorMsg() {
        me.tvGuideLocationError.setVisibility(View.VISIBLE);
        switch (currentType) {
            case MY_LOCATION:
                me.tvGuideLocation.setText(Html.fromHtml(getString(R.string.sm_evss01_07_0), Html.FROM_HTML_MODE_COMPACT));
                me.tvGuideLocationError.setText(Html.fromHtml(getString(R.string.sm_evss01_07_1), Html.FROM_HTML_MODE_COMPACT));
                break;
            case MY_CAR:
                me.tvGuideLocation.setText(Html.fromHtml(getString(R.string.sm_evss01_08_0), Html.FROM_HTML_MODE_COMPACT));
                me.tvGuideLocationError.setText(Html.fromHtml(getString(R.string.sm_evss01_08_1), Html.FROM_HTML_MODE_COMPACT));
                break;
            default:
                me.tvGuideLocationError.setVisibility(View.GONE);
                break;
        }
    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void initView() {
        me.rbBtnMyLocation.setChecked(true);
        updateSearchType(SEARCH_TYPE.MY_LOCATION);
        adapter = new ChargeSearchFilterAdapter(InputChargePlaceFragment.this);
        if(getArguments() != null && getArguments().getParcelableArrayList(KeyNames.KEY_NAME_FILTER_INFO) != null) {
            adapter.setRows(getArguments().getParcelableArrayList(KeyNames.KEY_NAME_FILTER_INFO));
        } else {
            adapter.setRows(new ArrayList(Arrays.asList(
                    new ChargeSearchCategoryVO(R.string.sm_evss01_15, ChargeSearchCategoryVO.COMPONENT_TYPE.ONLY_ONE, null),
                    new ChargeSearchCategoryVO(R.string.sm_evss01_16, ChargeSearchCategoryVO.COMPONENT_TYPE.RADIO, Arrays.asList(ChargeSearchCategorytype.ALL,
//                            ChargeSearchCategorytype.GENESIS, // 9월 까지 충전소 종류 "제네시스 충전소"는 미노출.
                            ChargeSearchCategorytype.E_PIT, ChargeSearchCategorytype.HI_CHARGER)),
                    new ChargeSearchCategoryVO(R.string.sm_evss01_21, ChargeSearchCategoryVO.COMPONENT_TYPE.CHECK, Arrays.asList(ChargeSearchCategorytype.SUPER_SPEED, ChargeSearchCategorytype.HIGH_SPEED, ChargeSearchCategorytype.SLOW_SPEED)),
                    new ChargeSearchCategoryVO(R.string.sm_evss01_25, ChargeSearchCategoryVO.COMPONENT_TYPE.CHECK, Arrays.asList(ChargeSearchCategorytype.S_TRAFFIC_CRADIT_PAY, ChargeSearchCategorytype.CAR_PAY))
            )));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        me.rvFilter.setLayoutManager(layoutManager);
        me.rvFilter.setAdapter(adapter);
    }

    private void updateSearchType(SEARCH_TYPE type) {
        currentType = type;
        switch (type) {
            case MY_LOCATION: {
                // 내 현재 위치 기준
                me.tvGuideLocation.setText(Html.fromHtml(getString(R.string.sm_evss01_07), Html.FROM_HTML_MODE_COMPACT));
                me.tvSearchAddr.setVisibility(View.GONE);
                me.tvGuideLocationError.setVisibility(View.GONE);
                me.tvFilter.setVisibility(View.VISIBLE);
                me.ivBtnMap.lWhole.setVisibility(View.VISIBLE);
                me.rvFilter.setVisibility(View.VISIBLE);
                me.line0.setVisibility(View.VISIBLE);
                break;
            }
            case MY_CAR: {
                // 내 차량 위치 기준
                me.tvGuideLocation.setText(Html.fromHtml(getString(R.string.sm_evss01_08), Html.FROM_HTML_MODE_COMPACT));
                me.tvSearchAddr.setVisibility(View.GONE);
                me.tvGuideLocationError.setVisibility(View.GONE);
                me.tvFilter.setVisibility(View.VISIBLE);
                me.ivBtnMap.lWhole.setVisibility(View.VISIBLE);
                me.rvFilter.setVisibility(View.VISIBLE);
                me.line0.setVisibility(View.VISIBLE);
                break;
            }
            case ADDRESS: {
                // 주소 검색 기준
                me.tvGuideLocation.setText(Html.fromHtml(getString(R.string.sm_evss01_09), Html.FROM_HTML_MODE_COMPACT));
                me.tvSearchAddr.setVisibility(View.VISIBLE);
                me.tvGuideLocationError.setVisibility(View.GONE);
                if (currentType.getAddressVO() == null) {
                    me.tvFilter.setVisibility(View.GONE);
                    me.ivBtnMap.lWhole.setVisibility(View.GONE);
                    me.rvFilter.setVisibility(View.GONE);
                    me.line0.setVisibility(View.GONE);
                    me.tvSearchAddr.setText(R.string.sm_evss01_14);
                    me.tvSearchAddr.setSelected(false);
                } else {
                    me.tvFilter.setVisibility(View.VISIBLE);
                    me.ivBtnMap.lWhole.setVisibility(View.VISIBLE);
                    me.rvFilter.setVisibility(View.VISIBLE);
                    me.line0.setVisibility(View.VISIBLE);
                    String[] address = SubActivity.getAddress(currentType.getAddressVO());
                    me.tvSearchAddr.setText(address[0] + (TextUtils.isEmpty(address[1]) ? "" : " " + address[1]));
                    me.tvSearchAddr.setSelected(true);
                }
                break;
            }
        }
    }

    private void showRadioBottomDialog(ChargeSearchCategoryVO data) {
        BottomListDialog bottomDialog = new BottomListDialog(getContext(), R.style.BottomSheetDialogTheme);
        bottomDialog.setDatas(data.getTypeStringList(getContext()));
        bottomDialog.setTitle(getString(data.getTitleResId()) + " " + getString(R.string.sm_evss01_28));
        bottomDialog.setOnDismissListener(
                dialog -> {
                    String result = bottomDialog.getSelectItem();
                    if (!TextUtils.isEmpty(result)) {
                        try {
                            data.clearSelectedItems();
                            data.addSelectedItem(data.getTypeList().stream().filter(it -> getString(it.getTitleResId()).equals(result)).findFirst().get());
                            adapter.notifyDataSetChanged();
                            if (this.listener != null) {
                                this.listener.onFilterChanged(currentType, adapter.getSelectedItems());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        bottomDialog.show();
    }

    private void showCheckBottomDialog(ChargeSearchCategoryVO data) {
        BottomCheckListDialog bottomDialog = new BottomCheckListDialog(getContext(), R.style.BottomSheetDialogTheme);
        bottomDialog.setTitle(getString(data.getTitleResId()) + " " + getString(R.string.sm_evss01_28));
        bottomDialog.setDatas(data.getTypeStringList(getContext()));
        bottomDialog.setCheckDatas(data.getSelectedItemStringList(getContext()));
        bottomDialog.setBottomBtnTitle(getString(R.string.sm_evss01_29));
        bottomDialog.setOnDismissListener(
                dialog -> {
                    if(bottomDialog.isClickNetBtn()) {
                        data.clearSelectedItems();
                        if (bottomDialog.getSelectItems() != null) {
                            ArrayList<ChargeSearchCategorytype> selectedList = new ArrayList<>();
                            for (String item : bottomDialog.getSelectItems()) {
                                try {
                                    selectedList.add(data.getTypeList().stream().filter(it -> getString(it.getTitleResId()).equals(item)).findFirst().get());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (selectedList.size() > 0) {
                                data.addSelectedItems(selectedList);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        if (this.listener != null) {
                            this.listener.onFilterChanged(currentType, adapter.getSelectedItems());
                        }
                    }
                }
        );

        bottomDialog.setSelectItems(null);
        bottomDialog.show();
    }


    /****************************************************************************************************
     * Method - Public
     ****************************************************************************************************/
    public void setAddress(AddressVO addressVO) {
        currentType.setAddressVO(addressVO);
        updateSearchType(currentType);
    }

    public void setOnFilterChangedListener(FilterChangeListener listener) {
        this.listener = listener;
    }

    public ArrayList<ChargeSearchCategoryVO> getSearchCategoryList() {
        ArrayList<ChargeSearchCategoryVO> list = new ArrayList<>();
        list.addAll(adapter.getItems());
        return list;
    }

    public void updateFilter(ArrayList<ChargeSearchCategoryVO> list) {
        adapter.setRows(list);
        adapter.notifyDataSetChanged();
    }
} // end of class InputChargePlaceFragment
