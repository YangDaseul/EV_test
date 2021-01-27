package com.genesis.apps.comm.model.constants;

import com.genesis.apps.R;

import java.util.Arrays;

import static com.genesis.apps.comm.model.constants.VariableType.WEATHER_NAME_LGT;
import static com.genesis.apps.comm.model.constants.VariableType.WEATHER_NAME_PTY;
import static com.genesis.apps.comm.model.constants.VariableType.WEATHER_NAME_SKY;

public enum WeatherCodes {
    SKY1("SKY_1", WEATHER_NAME_SKY, "1", "맑음", 0, 0, 0, R.color.x_ffffff),
    SKY2("SKY_23", WEATHER_NAME_SKY, "2", "구름조금", 0, 1, 0, R.color.x_ffffff),
    SKY3("SKY_23", WEATHER_NAME_SKY, "3", "구름많음", 0, 1, 0, R.color.x_ffffff),
    SKY4("SKY_4", WEATHER_NAME_SKY, "4", "흐림", 1, 2, 0, R.color.x_ffffff),

    PTY0("PTY_0", WEATHER_NAME_PTY, "0", "없음", 0, 0, 0, R.color.x_ffffff),
    PTY1("PTY_145", WEATHER_NAME_PTY, "1", "비", 3, 3, 1, R.color.x_ffffff),
    PTY2("PTY_26", WEATHER_NAME_PTY, "2", "비/눈", 2, 5, 2, R.color.x_ffffff),
    PTY3("PTY_37", WEATHER_NAME_PTY, "3", "눈", 2, 4, 2, R.color.x_ffffff),
    PTY4("PTY_145", WEATHER_NAME_PTY, "4", "소나기", 3, 3, 1, R.color.x_ffffff),
    PTY5("PTY_145", WEATHER_NAME_PTY, "5", "빗방울", 3, 3, 1, R.color.x_ffffff),
    PTY6("PTY_26", WEATHER_NAME_PTY, "6", "빗방울/눈날림", 2, 5, 2, R.color.x_ffffff),
    PTY7("PTY_37", WEATHER_NAME_PTY, "7", "눈날림", 2, 4, 2, R.color.x_ffffff),

    LGT0("LGT_0", WEATHER_NAME_LGT, "0", "확률없음", 0, 0, 0, R.color.x_ffffff),
    LGT1("LGT_1", WEATHER_NAME_LGT, "1", "낮음", 0, 0, 0, R.color.x_ffffff),
    LGT2("LGT_23", WEATHER_NAME_LGT, "2", "보통", 4, 6, 0, R.color.x_ffffff),
    LGT3("LGT_23", WEATHER_NAME_LGT, "3", "높음", 4, 6, 0, R.color.x_ffffff);

    //0 맑음
    //1 흐림
    //2 눈
    //3 비
    //4 낙뢰
    public static final int[][] BACKGROUND_RESOURCE = {
            {R.raw.cleansky_day, R.raw.cloudy_day, R.raw.snowy_day, R.raw.rainy_day, R.raw.lighting_day},
            {R.raw.cleansky_night, R.raw.cloudy_night, R.raw.snowy_night, R.raw.rainy_night, R.raw.lighting_night}
    };

    public static int getTextColorResource(int day){
        //낮일 때 검은색, 밤일 때 흰색
        return day < VariableType.HOME_TIME_NIGHT ? R.color.x_000000 : R.color.x_ffffff;
    }

    public static int getBackgroundResource(WeatherCodes weatherCodes, int day){
        if(day<VariableType.HOME_TIME_DAY) day=VariableType.HOME_TIME_DAY;//서버에서 주는 낮밤 코드는 1: 낮 2: 밤

        return BACKGROUND_RESOURCE[day-1][weatherCodes.getPosBackground()];
    }
    //0 맑음
    //1 구름있음
    //2 흐림
    //3 비
    //4 눈
    //5 비/눈
    //6 천둥번개
    public static final int[] ICON_RESOURCE={R.drawable.ic_weather_rain,R.drawable.ic_weather_rain,R.drawable.ic_weather_rain,R.drawable.ic_weather_rain,R.drawable.ic_weather_rain,R.drawable.ic_weather_rain,R.drawable.ic_weather_rain};
    public static int getIconResource(WeatherCodes weatherCodes){
        return ICON_RESOURCE[weatherCodes.getPosIcon()];
    }

    //0 없음
    //1 비
    //2 눈
    public static final int[] EFFECT_RESOURCE={0, R.raw.rainfall_project2, R.raw.snow};
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
    private int textColor;

    WeatherCodes(String dbCode, String apiName, String apiValue, String description, int posBackground, int posIcon, int posEffect, int textColor) {
        this.dbCode = dbCode;
        this.apiName = apiName;
        this.apiValue = apiValue;

        this.description = description;
        this.posBackground = posBackground;
        this.posIcon = posIcon;
        this.posEffect = posEffect;
        this.textColor = textColor;
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

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
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