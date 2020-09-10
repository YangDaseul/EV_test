package com.genesis.apps.ui.view.listview.test;

import com.genesis.apps.comm.model.BaseData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public @Data class Link extends BaseData {

    public Link(){

    }

    private int icon;
    private String title;
    private String url;
}
