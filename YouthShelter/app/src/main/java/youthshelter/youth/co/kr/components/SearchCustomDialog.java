package youthshelter.youth.co.kr.components;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import youthshelter.youth.co.kr.R;

public class SearchCustomDialog extends Dialog {

    private ClearEditText editText;
    private ImageButton SearchButton;

    private String result;

    InputMethodManager immhide;
    public SearchCustomDialog(Context context) {
        super(context);
    }

    public void clear() {
        if(editText != null)
            editText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        immhide = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        getWindow().setGravity(Gravity.TOP);

        setContentView(R.layout.dialog_search);

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 0);
        getWindow().setBackgroundDrawable(inset);

        getWindow().getAttributes().width = android.view.WindowManager.LayoutParams.MATCH_PARENT;





        editText = (ClearEditText) findViewById(R.id.search_ClearEditText);
        SearchButton = (ImageButton) findViewById(R.id.search_ImageButton);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent){

                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
                switch (i) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        SearchButton.callOnClick();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = editText.getText().toString();
                if(result=="")
                    result="   ";
                dismiss();
            }
        });

    }
    public String getAddCategoryStr() {
        return result;
    }

    public void setAddCategoryStr(String addCategoryStr) {
        this.result = addCategoryStr;
    }

}

