package com.polimi.deib.ildiariodigio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by marc on 03/03/16.
 */
public class CountDownInputView extends View {

    InputMethodManager imm;
    private String time;
    private String formated_time;
    private int textColor;
    private int backgroundColor;
    private float text_size;
    private Context mContext;
    private RectF rect;
    private Typeface tf;
    private Paint textPaint;
    private Paint backgroundPaint;

    public CountDownInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setFocusableInTouchMode(true);
        setFocusable(true);
        requestFocus();
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        initializeAttributes(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //int imageSize = (widthMeasureSpec < heightMeasureSpec) ? widthMeasureSpec : heightMeasureSpec;
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw Rectangle
        backgroundPaint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.FILL);

        backgroundPaint.setColor(backgroundColor);
        float left = 0;
        float top = 0;
        float right = getWidth();
        float bottom = getHeight();
        rect = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rect, 20f, 20f, backgroundPaint);

        // Draw Text
        text_size = ((float)(getWidth())/ textPaint.measureText("00:00") * textPaint.getTextSize()) * 0.7f;
        //text_size = ((float)(getWidth())/ textPaint.measureText("00:00")) * 10;
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(text_size);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(tf);
        textPaint.setTextAlign(Paint.Align.CENTER);

        int positionX = (getMeasuredWidth() / 2);
        int positionY = (int) ((getMeasuredHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
        canvas.drawText(formated_time, positionX, positionY, textPaint);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("TAG", "KEYCODE: " + Integer.toString(keyCode));
        if (keyCode == 4) {
            Log.e("TAG", "BACK!");
            checkInputCoherence();
        }
        else if (7 <= keyCode && keyCode <= 16) {
            Log.e("TAG", "NUMERO");
            time = time.substring(1);
            String new_character = "" + (char) (event.getUnicodeChar());
            time = time.concat(new_character);
            formated_time = format(time);
            invalidate();
        }
        else {
            Log.e("TAG", "ALTRES");
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        super.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_UP) {
                float xPos = event.getX();
                float yPos = event.getY();
                if (rect.contains((int)xPos, (int)yPos)) {
                    Log.e("TAG", "click inside");
                    requestFocus();
                    if (imm.isActive()) {
                        imm.showSoftInput(this, 0);
                    }
                }
                else {
                    Log.e("TAG", "click outside");
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
                        checkInputCoherence();
                    }
                }
        }
        return true;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        Log.d("TAG", "onCreateInputConnection");

        outAttrs.actionLabel = null;
        outAttrs.label="TEST TEXT";
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER;
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE;

        return new MyInputConnection(this,true);

    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect r) {
        super.onFocusChanged(gainFocus, direction, r);
        Log.e("TAG", "focus!");
        if (!gainFocus) {
            Log.e("TAG", "lost focus!");
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
            checkInputCoherence();
        }
    }


    private void initializeAttributes(Context context, AttributeSet attrs){
        textColor = Color.parseColor("#ffffff");
        backgroundColor = Color.parseColor("#474747");
        backgroundPaint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint();
        text_size = 20f;
        time = "0000";
        formated_time = format(time);
        tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto/Roboto-Regular.ttf");
    }

    private String format(String s) {
        return s.substring(0,2) + ":" + s.substring(2,4);
    }

    private void checkInputCoherence() {
        int minutes = Integer.parseInt(time.substring(0,2));
        int seconds = Integer.parseInt(time.substring(2,4));
        if (seconds > 59) time = time.substring(0,2) + "59";
        formated_time = format(time);
        invalidate();
    }

    public void setFont(String path) {
        tf = Typeface.createFromAsset(mContext.getAssets(), path);
        invalidate();
    }

    public void setTextColor(int new_color) {
        textColor = new_color;
        invalidate();
    }

    public void setBackgroundColor(int new_color) {
        backgroundColor = new_color;
        invalidate();
    }

    public int getMilliseconds() {
        int minutes = Integer.parseInt(time.substring(0,2));
        int seconds = Integer.parseInt(time.substring(2,4));
        int milliseconds = (seconds * 1000) + (minutes * 60 * 1000);
        return milliseconds;
    }

    class MyInputConnection extends BaseInputConnection {
        private SpannableStringBuilder myeditable;
        CountDownInputView mycustomview;

        public MyInputConnection(View targetView, boolean fullEditor) {
            super(targetView, fullEditor);
            mycustomview = (CountDownInputView) targetView;
        }

        public Editable getEditable() {
            if (myeditable == null) {
                myeditable = (SpannableStringBuilder) Editable.Factory.getInstance()
                        .newEditable("Placeholder");
            }
            return myeditable;
        }

        public boolean commitText(CharSequence text, int newCursorPosition) {
            invalidate();
            myeditable.append(text);
            //mycustomview.setText(text);
            //time = text.toString();
            return true;
        }
    }

}