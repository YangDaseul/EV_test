package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.databinding.ActivityMygMenuBinding;
import com.genesis.apps.room.DatabaseHolder;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.listview.ExtncPlanPontAdapter;
import com.genesis.apps.ui.common.view.listview.MenuAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

public class MyGMenuActivity extends SubActivity<ActivityMygMenuBinding> {

    @Inject
    public DatabaseHolder databaseHolder;

    private MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_menu);

        adapter = new MenuAdapter((v, position) -> {
            //todo delete..
        });
        adapter.setRows(APPIAInfo.getQuickMenus());
        ui.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);



        ui.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = s.toString();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



//            UserVO user3 = new Gson().fromJson(test2, UserVO.class);
//            databaseHolder.getDatabase().userDao().insert(user3);
//            UserVO user4 = databaseHolder.getDatabase().userDao().select();

    }



    @Override
    public void onSingleClick(View v) {

    }




    public void search(final String search, TextView empty) {

        this.search = search;

        if (TextUtils.isEmpty(search)) {
            addAllItem(dataRecently);
            empty.setVisibility(View.GONE);
            layoutRecently.setVisibility(dataRecently.size()>0 ? View.VISIBLE : View.GONE);
        } else {
            ArrayList<DummyVehicle> temps = new ArrayList<DummyVehicle>();

            if (dataOriginal != null && dataOriginal.size() > 0) {
                for (int i = 0; i < dataOriginal.size(); i++) {
                    DummyVehicle info = dataOriginal.get(i);
                    String[] compares = {info.vrn,info.vin,info.brand,info.code};
                    for (String compare : compares) {
                        if (!TextUtils.isEmpty(compare)) {
                            boolean hasSearch = SoundSearcher.matchString(compare.toLowerCase(), search.toLowerCase());
                            if (hasSearch) {
                                temps.add(info);
                                break;
                            }
                        }
                    }
                }
            }
            addAllItem(temps);
            empty.setVisibility(temps.size()<1 ? View.VISIBLE : View.GONE);
            layoutRecently.setVisibility(View.GONE);
        }
        //notifyDataSetChanged();


        notifyItemRangeChanged(0, getItemCount());
    }






}
