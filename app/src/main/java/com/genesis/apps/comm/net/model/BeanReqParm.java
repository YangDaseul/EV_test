package com.genesis.apps.comm.net.model;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.net.NetResultCallback;

import lombok.Data;

public @Data
class BeanReqParm extends BaseData {
    private String data;
    private String url;
    private String type;
    private String className;
    private NetResultCallback callback;
}
