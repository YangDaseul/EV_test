package com.genesis.apps.comm.model.constants;

import java.util.Arrays;

public enum WeatherCodes {
    SKY1("SKY1", "맑음", 0, 0, true),
    SKY2("SKY2", "구름조금", 0, 0, false),
    SKY3("SKY3", "구름많음", 0, 0, true),
    SKY4("SKY4", "흐림", 0, 0, false),

    PTY0("PTY0", "없음", 0, 0, true),
    PTY1("PTY1", "비", 0, 0, false),
    PTY2("PTY2", "비/눈(진눈개비)", 0, 0, true),
    PTY3("PTY3", "눈", 0, 0, true),
    PTY4("PTY4", "소나기", 0, 0, false),
    PTY5("PTY5", "빗방울", 0, 0, false),
    PTY6("PTY6", "빗방울/눈날림", 0, 0, true),
    PTY7("PTY7", "눈날림", 0, 0, true),

    LGT0("LGT0", "확률없음", 0, 0, true),
    LGT1("LGT1", "낮음", 0, 0, true),
    LGT2("LGT2", "보통", 0, 0, false),
    LGT3("LGT3", "높음", 0, 0, false);


    private String code;
    private String description;
    private int resBackground;
    private int resIcon;
    private boolean isWhite;

    WeatherCodes(String code, String description, int resBackground, int resIcon, boolean isWhite) {
        this.code = code;
        this.description = description;
        this.resBackground = resBackground;
        this.resIcon = resIcon;
        this.isWhite = isWhite;
    }

    public static WeatherCodes findCode(String code) {
        return Arrays.asList(WeatherCodes.values()).stream().filter(data -> data.getCode().equalsIgnoreCase(code)).findAny().orElse(SKY1);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getResBackground() {
        return resBackground;
    }

    public void setResBackground(int resBackground) {
        this.resBackground = resBackground;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }
}