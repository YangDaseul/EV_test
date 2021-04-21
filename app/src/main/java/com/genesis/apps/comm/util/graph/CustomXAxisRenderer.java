package com.genesis.apps.comm.util.graph;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * @brief x라벨 2줄 표기
 * @author hjpark
 * x라벨에 \n과 같은 줄바꿈이 진행
 * 될 경우 2줄로 표시하도록 처리하기 위한 renderer.
 * (해당 renderer을 사용하지 않으면 한줄로 표기됨)
 */
public class CustomXAxisRenderer extends XAxisRenderer {

    public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    @Override
    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
        // super.drawLabel (c, formattedLabel, x, y, anchor, angleDegrees); // this comment out, or coordinate label replication twice
        String[] lines = formattedLabel.split("\n");
        for (int i = 0; i < lines.length; i++) {
            float vOffset = i * mAxisLabelPaint.getTextSize() * 1.5f;
            Utils.drawXAxisValue(c, lines[i], x, y + vOffset, mAxisLabelPaint, anchor, angleDegrees);
        }
    }
}
