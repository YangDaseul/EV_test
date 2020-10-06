package com.genesis.apps.comm.model.constants;

import java.util.Arrays;

import static com.genesis.apps.comm.model.constants.VariableType.WEATHER_NAME_LGT;
import static com.genesis.apps.comm.model.constants.VariableType.WEATHER_NAME_PTY;
import static com.genesis.apps.comm.model.constants.VariableType.WEATHER_NAME_SKY;

public enum WeatherCodes {
    SKY1("SKY_1", WEATHER_NAME_SKY, "1", "맑음", 0, 0, 0, true),
    SKY2("SKY_23", WEATHER_NAME_SKY, "2", "구름조금", 1, 1, 0, false),
    SKY3("SKY_23", WEATHER_NAME_SKY, "3", "구름많음", 0, 1, 0, true),
    SKY4("SKY_4", WEATHER_NAME_SKY, "4", "흐림", 1, 2, 0, false),

    PTY0("PTY_0", WEATHER_NAME_PTY, "0", "없음", 0, -1, 0, true),
    PTY1("PTY_145", WEATHER_NAME_PTY, "1", "비", 3, 3, 1, false),
    PTY2("PTY_26", WEATHER_NAME_PTY, "2", "비/눈(진눈개비)", 2, 5, 2, true),
    PTY3("PTY_37", WEATHER_NAME_PTY, "3", "눈", 2, 4, 2, true),
    PTY4("PTY_145", WEATHER_NAME_PTY, "4", "소나기", 3, 3, 1, false),
    PTY5("PTY_145", WEATHER_NAME_PTY, "5", "빗방울", 3, 3, 1, false),
    PTY6("PTY_26", WEATHER_NAME_PTY, "6", "빗방울/눈날림", 2, 5, 2, true),
    PTY7("PTY_37", WEATHER_NAME_PTY, "7", "눈날림", 2, 4, 2, true),

    LGT0("LGT_0", WEATHER_NAME_LGT, "0", "확률없음", -1, -1, 0, true),
    LGT1("LGT_1", WEATHER_NAME_LGT, "1", "낮음", -1, -1, 0, true),
    LGT2("LGT_23", WEATHER_NAME_LGT, "2", "보통", 4, 6, 0, false),
    LGT3("LGT_23", WEATHER_NAME_LGT, "3", "높음", 4, 6, 0, false);

    //0 맑음
    //1 흐림
    //2 눈
    //3 비
    //4 낙뢰
    public static final int[][] BACKGROUND_RESOURCE={{0,0,0,0,0},{0,0,0,0,0}};
    public static int getBackgroundResource(WeatherCodes weatherCodes, int day){
        if(day<1) day=1;

        return BACKGROUND_RESOURCE[day-1][weatherCodes.getPosBackground()];
    }
    //0 맑음
    //1 구름있음
    //2 흐림
    //3 비
    //4 눈
    //5 비/눈
    //6 천둥번개
    public static final int[] ICON_RESOURCE={0,0,0,0,0,0,0};
    public static int getIconResource(WeatherCodes weatherCodes){
        return ICON_RESOURCE[weatherCodes.getPosIcon()];
    }

    //0 없음
    //1 비
    //2 눈
    public static final int[] EFFECT_RESOURCE={0,0,0};
    public static int getEffectResource(WeatherCodes weatherCodes) {
        return EFFECT_RESOURCE[weatherCodes.getPosEffect()];
    }
    private String dbCode;
    private String apiName;
    private String apiValue;
    private String description;
    private int posBackground;
    private int posIcon;
    private int posEffect;
    private boolean isWhite;

    WeatherCodes(String dbCode, String apiName, String apiValue, String description, int posBackground, int posIcon, int posEffect, boolean isWhite) {
        this.dbCode = dbCode;
        this.apiName = apiName;
        this.apiValue = apiValue;

        this.description = description;
        this.posBackground = posBackground;
        this.posIcon = posIcon;
        this.posEffect = posEffect;
        this.isWhite = isWhite;
    }

    public static WeatherCodes findCode(String apiName, String apiValue) {
        return Arrays.asList(WeatherCodes.values()).stream().filter(data -> (data.getApiName().equalsIgnoreCase(apiName)&&data.getApiValue().equalsIgnoreCase(apiValue))).findAny().orElse(SKY1);
    }

    /**
     * @brief 날씨코드 확인
     * LGN_0005에서 수신받은 날씨 코드를 기준으로 매칭되는 사전 정의 코드 검색
     * @param lgt 낙뢰 정보
     * @param pty 강수 형태
     * @param sky 하늘 상태
     * @return
     */
    public static WeatherCodes decideCode(String lgt, String pty, String sky){
        if(lgt.equalsIgnoreCase("2")||lgt.equalsIgnoreCase("3")){
            return findCode(WEATHER_NAME_LGT, lgt);
        }else if(pty.equalsIgnoreCase("1")||pty.equalsIgnoreCase("2")||pty.equalsIgnoreCase("3")||pty.equalsIgnoreCase("4")||pty.equalsIgnoreCase("5")||pty.equalsIgnoreCase("6")||pty.equalsIgnoreCase("7")){
            return findCode(WEATHER_NAME_PTY, pty);
        }else{
            return findCode(WEATHER_NAME_SKY, sky);
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public int getPosEffect() {
        return posEffect;
    }

    public void setPosEffect(int posEffect) {
        this.posEffect = posEffect;
    }

    public String getDbCode() {
        return dbCode;
    }

    public void setDbCode(String dbCode) {
        this.dbCode = dbCode;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiValue() {
        return apiValue;
    }

    public void setApiValue(String apiValue) {
        this.apiValue = apiValue;
    }

    public int getPosBackground() {
        return posBackground;
    }

    public void setPosBackground(int posBackground) {
        this.posBackground = posBackground;
    }

    public int getPosIcon() {
        return posIcon;
    }

    public void setPosIcon(int posIcon) {
        this.posIcon = posIcon;
    }
}