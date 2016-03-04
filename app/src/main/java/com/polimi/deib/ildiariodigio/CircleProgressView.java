package com.polimi.deib.ildiariodigio;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by marc on 03/03/16.
 */
public class CircleProgressView extends View {

    private float circleCenterPointX;
    private float circleCenterPointY;
    private int roadColor;
    private float roadStrokeWidth;
    private float roadRadius;
    private int roadInnerCircleColor;
    private float roadInnerCircleStrokeWidth;
    private float roadInnerCircleRadius;
    private int roadOuterCircleColor;
    private float roadOuterCircleStrokeWidth;
    private float roadOuterCircleRadius;
    private int arcLoadingColor;
    private float arcLoadingStrokeWidth;
    private float arcLoadingDashLength;
    private float arcLoadingDistanceBetweenDashes;
    private float arcLoadingStartAngle;

    private int progress;

    public Handler mHandler = new Handler();

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAttributes(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circleCenterPointX = w / 2;
        circleCenterPointY = h / 2;

        int paddingInContainer = 3;
        roadRadius = (w / 2) - (roadStrokeWidth / 2) - paddingInContainer;

        int innerCirclesPadding = 3;
        roadOuterCircleRadius = (w / 2) - paddingInContainer -
                (roadOuterCircleStrokeWidth / 2) - innerCirclesPadding;

        roadInnerCircleRadius = roadRadius - (roadStrokeWidth / 2)
                + (roadInnerCircleStrokeWidth / 2) + innerCirclesPadding;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(arcLoadingColor);
        paint.setStrokeWidth(arcLoadingStrokeWidth);
        //paint.setPathEffect(new DashPathEffect(new float[] {arcLoadingDashLength, arcLoadingDistanceBetweenDashes}, 0));
        float delta = circleCenterPointX - roadRadius;
        float arcSize = (circleCenterPointX - (delta / 2f)) * 2f;
        RectF box = new RectF(delta, delta, arcSize, arcSize);
        float sweep = 360 * progress * 0.01f;
        canvas.drawArc(box, arcLoadingStartAngle, -sweep, true, paint);
    }

    private void initializeAttributes(Context context, AttributeSet attrs){
        circleCenterPointX = 54f;
        circleCenterPointY = 54f;
        arcLoadingColor = Color.parseColor("#E6A639");
        arcLoadingStrokeWidth = 3f;
        arcLoadingDashLength = 10f;
        arcLoadingDistanceBetweenDashes = 5f;
        arcLoadingStartAngle = 270f;
    }

    public void setProgress(int progress){
        this.progress = progress;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }
}
