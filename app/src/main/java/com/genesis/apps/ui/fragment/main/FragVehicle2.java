package com.genesis.apps.ui.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.map.MapViewModel;
import com.genesis.apps.databinding.FragVehicle2Binding;
import com.genesis.apps.ui.fragment.SubFragment;
import com.genesis.apps.ui.view.AnimalAdapter;
import com.genesis.apps.ui.view.Animals;

import java.util.ArrayList;
import java.util.List;

public class FragVehicle2 extends SubFragment<FragVehicle2Binding> {
    private MapViewModel mapViewModel;

    AnimalAdapter mAnimalAdapter;
    private List<Animals> mAnimalsList;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frag_vehicle_2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

     }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void onRefresh() {

        addAnimalsToList();
    }


    private void addAnimalsToList() {
        mAnimalsList = new ArrayList<>();

        for(int i=0; i<30; i++){
            mAnimalsList.add(new Animals(i, "Cat"+i));
        }

        mAnimalAdapter = new AnimalAdapter(mAnimalsList, null);
        me.firstRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        me.firstRv.setAdapter(mAnimalAdapter);
    }

}
