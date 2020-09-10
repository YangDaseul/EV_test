package com.genesis.apps.ui.fragment.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.map.MapViewModel;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.Frame2pBinding;
import com.genesis.apps.ui.fragment.SubFragment;
import com.genesis.apps.ui.view.listview.test.Link;
import com.genesis.apps.ui.view.listview.test.LinkDiffCallback;
import com.genesis.apps.ui.view.listview.test.MyItemClickListener;
import com.genesis.apps.ui.view.listview.test.MyViewModel;
import com.genesis.apps.ui.view.listview.test.TestUserAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


public class FragSecond extends SubFragment<Frame2pBinding> {

    private MapViewModel mapViewModel;
    private MyViewModel myViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_2p);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        me.setLifecycleOwner(getViewLifecycleOwner());
        mapViewModel.getTestCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.v("test", "Second:" + integer);
            }
        });


        myViewModel= new ViewModelProvider(this).get(MyViewModel.class);
        TestUserAdapter testUserAdapter = new TestUserAdapter(new LinkDiffCallback(), new MyItemClickListener() {
            @Override
            public void onClick(Link link) {
                SnackBarUtil.show(getActivity(),"test");
                myViewModel.addUsersList(getListData2());

            }
        });
        myViewModel.usersList.observe(getViewLifecycleOwner(), new Observer<List<Link>>() {
            @Override
            public void onChanged(List<Link> links) {
             testUserAdapter.submitList(links!=null ? new ArrayList<>(links) : null);
            }
        });
        me.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        me.recyclerView.setAdapter(testUserAdapter);
        myViewModel.setUsersList(getListData());
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    //generate a list of Link
    private List<Link> getListData(){
        List<Link> links = new LinkedList<Link>();


        Link link = new Link();
        link.setIcon(R.drawable.ic_link);
        link.setTitle("HMKCODE BLOG");
        link.setUrl("hmkcode.com");

        links.add(link);

        link = new Link();
        link.setIcon(R.drawable.ic_dashboard_black_24dp);
        link.setTitle("@HMKCODE");
        link.setUrl("twitter.com/hmkcode");

        links.add(link);

        link = new Link();
        link.setIcon(R.drawable.ic_home_black_24dp);
        link.setTitle("HMKCODE");
        link.setUrl("github.com/hmkcode");

        links.add(link);
        return links;
    }

    private List<Link> getListData2(){
        List<Link> links = new LinkedList<Link>();

        Link link = new Link();
        link.setIcon(R.drawable.ic_link);
        link.setTitle("HMKCODE BLOG");
        link.setUrl("hmkcode.com");

        links.add(link);

        link = new Link();
        link.setIcon(R.drawable.ic_dashboard_black_24dp);
        link.setTitle("@HMKCODE");
        link.setUrl("twitter.com/hmkcode");

        links.add(link);

        link = new Link();
        link.setIcon(R.drawable.ic_home_black_24dp);
        link.setTitle("HMKCODE");
        link.setUrl("github.com/hmkcode");

        links.add(link);

        link = new Link();
        link.setIcon(R.drawable.ic_home_black_24dp);
        link.setTitle("HMKCODE");
        link.setUrl("github.com/hmkcode");

        links.add(link);
        return links;
    }

}
