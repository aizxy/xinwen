package com.example.administrator.happyapplication.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.happyapplication.R;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class MyEditText extends EditText implements View.OnFocusChangeListener,TextWatcher{

    private Drawable mClearDrawable;
    private boolean hasFoucs;
    public MyEditText(Context context){
        super(context,null);
    }
    public MyEditText(Context context, AttributeSet attrs){
        super(context,attrs,android.R.attr.editTextStyle);
    }
    public MyEditText(Context context, AttributeSet attrs,int defstyle){
        super(context,attrs,defstyle);
        init();
    }
    private void init(){
        mClearDrawable=getCompoundDrawables()[2];
        if(mClearDrawable==null){
            mClearDrawable=getResources().getDrawable(R.drawable.icon_c);
        }
        mClearDrawable.setBounds(0,0,mClearDrawable.getIntrinsicWidth(),mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }
    protected void setClearIconVisible(boolean visible){
        Drawable right=visible?mClearDrawable:null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],right,getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP){
            if(getCompoundDrawables()[2]!=null){
                boolean touchable=event.getX()>(getWidth()-getTotalPaddingRight())&&(event.getX()<((getWidth()-getPaddingRight())));
                if(touchable){
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        this.hasFoucs=b;
        if(hasFoucs){
            setClearIconVisible(getText().length()>0);
        }else{
            setClearIconVisible(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(hasFoucs){
            setClearIconVisible(text.length()>0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
