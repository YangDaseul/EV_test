package com.genesis.apps.comm.model;

import com.genesis.apps.R;

import java.util.Arrays;

import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_GSCT;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_HDOL;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_SKNO;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_SOIL;

public enum OilCodes {
    GSCT(OIL_CODE_GSCT, "kr.co.gscaltex.gsnpoint", R.layout.view_oil_1_1,R.string.oil_name_gs,R.drawable.img_connect_gs,0),
    HDOL(OIL_CODE_HDOL, "com.hyundaioilbank.android", R.layout.view_oil_1_2,R.string.oil_name_ho,0,0),
    SKNO(OIL_CODE_SKNO, "com.ske.phone.epay", R.layout.view_oil_1_3,R.string.oil_name_sk,R.drawable.img_connect_sk,R.drawable.logo_sk_l),
    SOIL(OIL_CODE_SOIL, "com.soilbonus.goodoilfamily", R.layout.view_oil_1_4,R.string.oil_name_soil,0,0);

    public static final String KEY_OIL_CODE="oilRfnCd";

    private String code;
    private String schema;
    private int layout;
    private int oilNm;
    private int bigSrc;
    private int smallSrc;

    OilCodes(String code, String schema, int layout, int oilNm, int bigSrc, int smallSrc) {
        this.code = code;
        this.schema = schema;
        this.layout = layout;

        this.oilNm = oilNm;
        this.bigSrc = bigSrc;
        this.smallSrc = smallSrc;
    }

    public static OilCodes findCode(String code) {
        return Arrays.asList(OilCodes.values()).stream().filter(data -> data.getCode().equalsIgnoreCase(code)).findAny().orElse(GSCT);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public int getOilNm() {
        return oilNm;
    }

    public void setOilNm(int oilNm) {
        this.oilNm = oilNm;
    }

    public int getBigSrc() {
        return bigSrc;
    }

    public void setBigSrc(int bigSrc) {
        this.bigSrc = bigSrc;
    }

    public int getSmallSrc() {
        return smallSrc;
    }

    public void setSmallSrc(int smallSrc) {
        this.smallSrc = smallSrc;
    }
}