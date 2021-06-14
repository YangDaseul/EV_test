package com.genesis.apps.comm.model.constants;

import com.genesis.apps.R;

import java.util.Arrays;

public enum SpidCodes {

    BA("BA", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "부안군"),
    BG("BG", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "비긴스"),
    BT("BT", R.drawable.pin_botari_small , R.drawable.pin_botari_large, "보타리에너지"),
    CT("CT", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "씨티카"),
    CV("CV", R.drawable.pin_chaevi_small , R.drawable.pin_chaevi_large, "대영채비"),
    DE("DE", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "대구환경공단"),
    DG("DG", R.drawable.pin_daegu_environmental_small , R.drawable.pin_daegu_environmental_large, "대구시"),
    EM("EM", R.drawable.pin_evmost_small , R.drawable.pin_evmost_large, "EvMost"),
    EP("EP", R.drawable.pin_ecarplug_small , R.drawable.pin_ecarplug_large, "이카플러그"),
    EV("EV", R.drawable.pin_everon_small , R.drawable.pin_everon_large, "에버온"),
    EZ("EZ", R.drawable.pin_chargev_small , R.drawable.pin_chargev_large, "차지인"),
    G2("G2", R.drawable.pin_gwangju_small , R.drawable.pin_gwangju_large, "광주시청"),
    GH("GH", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "김해시"),
    GN("GN", R.drawable.pin_gntel_small , R.drawable.pin_gntel_large, "지엔텔"),
    GP("GP", R.drawable.pin_gunpo_small , R.drawable.pin_gunpo_large, "군포시"),
    GS("GS", R.drawable.pin_gs_small , R.drawable.pin_gs_large, "GS칼텍스"),
    HE("HE", R.drawable.pin_kevcs_small , R.drawable.pin_kevcs_large, "한국전기차충전서비스"),
    IK("IK", R.drawable.pin_iksan_small , R.drawable.pin_iksan_large, "익산시"),
    JC("JC", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "제주에너지공사"),
    JD("JD", R.drawable.pin_jeju_small , R.drawable.pin_jeju_large, "제주도청"),
    JE("JE", R.drawable.pin_jeju_electric_small , R.drawable.pin_jeju_electric_large, "제주전기자동차서비스"),
    JJ("JJ", R.drawable.pin_jeonju_small , R.drawable.pin_jeonju_large, "전주시"),
    JT("JT", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "제주테크노파크"),
    JU("JU", R.drawable.pin_jeongeup_small , R.drawable.pin_jeongeup_large, "정읍시"),
    KC("KC", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "한국컴퓨터"),
    KE("KE", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "한국전기차인프라기술"),
    KL("KL", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "클린일렉스"),
    KN("KN", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "한국환경공단"),
    KP("KP", R.drawable.pin_kepco_small , R.drawable.pin_kepco_large, "한국전력"),
    KT("KT", R.drawable.pin_kt_small , R.drawable.pin_kt_large, "KT"),
    LH("LH", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "LG 헬로비전"),
    ME("ME", R.drawable.pin_environment_small , R.drawable.pin_environment_large, "환경부"),
    MO("MO", R.drawable.pin_manageon_small , R.drawable.pin_manageon_large, "매니지온"),
    NJ("NJ", R.drawable.pin_naju_small , R.drawable.pin_naju_large, "나주시"),
    PI("PI", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "포스코ICT"),
    PW("PW", R.drawable.pin_cube_small , R.drawable.pin_cube_large, "파워큐브"),
    SC("SC", R.drawable.pin_samcheok_small , R.drawable.pin_samcheok_large, "삼척시"),
    SE("SE", R.drawable.pin_seoul_small , R.drawable.pin_seoul_large, "서울시(한국자동차환경협회)"),
    SF("SF", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "스타코프"),
    SG("SG", R.drawable.pin_signet_small , R.drawable.pin_signet_large, "시그넷"),
    SK("SK", R.drawable.pin_sk_small , R.drawable.pin_sk_large, "SK에너지"),
    SS("SS", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "삼성이브이씨"),
    ST("ST", R.drawable.pin_straffic_small , R.drawable.pin_straffic_large, "에스트래픽"),
    SW("SW", R.drawable.pin_suwon_small , R.drawable.pin_suwon_large, "수원시"),
    TB("TB", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "태백시"),
    TD("TD", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "타디스테크놀로지"),
    US("US", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "환경부"),
    XB("XB", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "BMW"),
    XE("XE", R.drawable.pin_electro_small , R.drawable.pin_electro_large, "일렉트로하이퍼(에스트레픽)"),
    HD("HD", R.drawable.pin_hyundai_small , R.drawable.pin_hyundai_large, "현대자동차"),
    KI("KI", R.drawable.pin_kia_small , R.drawable.pin_kia_large, "기아자동차"),
    XX("XX", R.drawable.pin_etc_small , R.drawable.pin_etc_large, "기타"),
    YY("YY", R.drawable.pin_yangyang_small , R.drawable.pin_yangyang_large, "양양군");

    private String spid;
    private int pinSmall;
    private int pinLarge;
    private String description;

    SpidCodes(String spid, int pinSmall, int pinLarge, String description) {
        this.spid = spid;
        this.pinSmall = pinSmall;
        this.pinLarge = pinLarge;
        this.description = description;
    }

    public static SpidCodes findCode(String spid) {
        return Arrays.asList(SpidCodes.values()).stream().filter(data -> (data.getSpid().equalsIgnoreCase(spid))).findAny().orElse(XX);
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public int getPinSmall() {
        return pinSmall;
    }

    public int getPinLarge() {
        return pinLarge;
    }
}