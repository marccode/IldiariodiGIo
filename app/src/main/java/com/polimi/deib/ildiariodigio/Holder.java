package com.polimi.deib.ildiariodigio;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class Holder extends View implements GestureDetector.OnDoubleTapListener {

InputMethodManager imm;
private Paint paint;
private static final String TAG="Holder";
private CharSequence mText="original";

public Holder(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        //requestFocus();
        setFocusableInTouchMode(true);
        setFocusable(true);
        requestFocus();


    setOnKeyListener(new OnKeyListener() {

        public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyListener");
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
        // Perform action on key press
        Log.d(TAG, "ACTION_DOWN");
        return true;
        }
        return false;
        }
        });

        }

public void init(){
        paint = new Paint();
        paint.setTextSize(12);
        paint.setColor(0xFF668800);
        paint.setStyle(Paint.Style.FILL);
        }

@Override
protected void onDraw(Canvas canvas) {
        canvas.drawText(mText.toString(), 100, 100, paint);
        }

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(150,200);
        }

public void setText(CharSequence text) {
        mText = text;
        requestLayout();
        invalidate();
        }




public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Log.d(TAG, "onTOUCH");
        if (event.getAction() == MotionEvent.ACTION_UP) {

        // show the keyboard so we can enter text
        InputMethodManager imm = (InputMethodManager) getContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(this,0);
        }
        return true;
        }

@Override
public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        Log.d(TAG, "onCreateInputConnection");

        outAttrs.actionLabel = null;
        outAttrs.label="TEST TEXT";
        outAttrs.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE;

        return new MyInputConnection(this,true);

        }

@Override
public boolean onCheckIsTextEditor() {
        Log.d(TAG, "onCheckIsTextEditor");
        return true;
        }

@Override
public boolean onDoubleTap(MotionEvent e) {
        // TODO Auto-generated method stub
        View view = Holder.this.getRootView();
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        return false;
        }

@Override
public boolean onDoubleTapEvent(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
        }

@Override
public boolean onSingleTapConfirmed(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
        }



class MyInputConnection extends BaseInputConnection {
    private SpannableStringBuilder myeditable;
    Holder mycustomview;

    public MyInputConnection(View targetView, boolean fullEditor) {
        super(targetView, fullEditor);
        mycustomview = (Holder) targetView;
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
        mycustomview.setText(text);
        return true;
    }
}


}