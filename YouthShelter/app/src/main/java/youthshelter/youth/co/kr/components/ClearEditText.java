package youthshelter.youth.co.kr.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;

import youthshelter.youth.co.kr.R;

public class ClearEditText extends AppCompatEditText implements TextWatcher, OnTouchListener, OnFocusChangeListener {
    private Drawable clearDrawable;
    private OnFocusChangeListener onFocusChangeListener;
    private OnTouchListener onTouchListener;

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    private void init() {
        this.clearDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(getContext(), R.drawable.ic_clear_black_24dp));
        DrawableCompat.setTintList(this.clearDrawable, getHintTextColors());
        this.clearDrawable.setBounds(0, 0, this.clearDrawable.getIntrinsicWidth(), this.clearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    public void onFocusChange(View view, boolean hasFocus) {
        boolean z = false;
        if (hasFocus) {
            if (getText().length() > 0) {
                z = true;
            }
            setClearIconVisible(z);
        } else {
            setClearIconVisible(false);
        }
        if (this.onFocusChangeListener != null) {
            this.onFocusChangeListener.onFocusChange(view, hasFocus);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        if (this.clearDrawable.isVisible() && x > (getWidth() - getPaddingRight()) - this.clearDrawable.getIntrinsicWidth()) {
            if (motionEvent.getAction() == 1) {
                setError(null);
                setText(null);
            }
            return true;
        } else if (this.onTouchListener != null) {
            return this.onTouchListener.onTouch(view, motionEvent);
        } else {
            return false;
        }
    }

    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isFocused()) {
            setClearIconVisible(s.length() > 0);
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
    }

    private void setClearIconVisible(boolean visible) {
        this.clearDrawable.setVisible(visible, false);
        setCompoundDrawables(null, null, visible ? this.clearDrawable : null, null);
    }
}
