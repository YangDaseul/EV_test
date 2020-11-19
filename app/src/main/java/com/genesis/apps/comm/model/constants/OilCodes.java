package com.genesis.apps.comm.model.constants;

import com.genesis.apps.R;

import java.util.Arrays;

import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_GSCT;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_HDOL;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_SKNO;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_SOIL;

public enum OilCodes {
    GSCT(OIL_CODE_GSCT, "kr.co.gscaltex.gsnpoint", R.layout.view_oil_1_1, R.string.oil_name_gs, R.string.oil_point_name_gs,  R.drawable.img_connect_gs,R.drawable.logo_gs_l),
    HDOL(OIL_CODE_HDOL, "com.hyundaioilbank.android", R.layout.view_oil_1_2,R.string.oil_name_ho,R.string.oil_point_name_ho,R.drawable.img_connect_ho,R.drawable.logo_hyundaioilbank_l),
    SKNO(OIL_CODE_SKNO, "com.ske.phone.epay", R.layout.view_oil_1_3,R.string.oil_name_sk,R.string.oil_point_name_sk,R.drawable.img_connect_sk,R.drawable.logo_sk_l),
    SOIL(OIL_CODE_SOIL, "com.soilbonus.goodoilfamily", R.layout.view_oil_1_4,R.string.oil_name_soil,R.string.oil_point_name_soil,R.drawable.img_connect_soil,R.drawable.logo_s_oil_l);

    public static final String KEY_OIL_CODE="oilRfnCd";

    private String code;
    private String schema;
    private int layout;
    private int oilNm;
    private int oilPontNm;
    private int bigSrc;
    private int smallSrc;

    OilCodes(String code, String schema, int layout, int oilNm, int oilPontNm, int bigSrc, int smallSrc) {
        this.code = code;
        this.schema = schema;
        this.layout = layout;
        this.oilNm = oilNm;
        this.oilPontNm = oilPontNm;
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

    public int getOilPontNm() {
        return oilPontNm;
    }

    public void setOilPontNm(int oilPontNm) {
        this.oilPontNm = oilPontNm;
    }
}