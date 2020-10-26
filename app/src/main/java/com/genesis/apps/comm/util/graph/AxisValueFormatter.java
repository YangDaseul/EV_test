package com.genesis.apps.comm.util.graph;

import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * @brief X좌표 출력 처리
 * 그래프 사용 시 X좌표의 포맷을 month+월로 표기 처리
 */
public class AxisValueFormatter extends ValueFormatter {

    String[] xNames = {"주유/충전","정비","세차","기타"};

    @Override
    public String getFormattedValue(float position) {
        return getMonth((int) position);
    }

    private String getMonth(int position) {

        int pos = position % xNames.length;
        if (pos > xNames.length-1)
            pos = 3;

        return xNames[pos];
    }
}