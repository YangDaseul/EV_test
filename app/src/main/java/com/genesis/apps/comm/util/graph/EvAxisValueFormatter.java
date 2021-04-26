package com.genesis.apps.comm.util.graph;

import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * @brief EV 차량의 차계부 X좌표 출력 처리
 */
public class EvAxisValueFormatter extends ValueFormatter {

    public static String[] xNames = {"충전","충전\n크레딧","정비","세차","기타"};

    @Override
    public String getFormattedValue(float position) {
        return getMonth((int) position);
    }

    private String getMonth(int position) {

        int pos = position % xNames.length;
        if (pos > xNames.length-1)
            pos = 4;

        return xNames[pos];
    }
}
