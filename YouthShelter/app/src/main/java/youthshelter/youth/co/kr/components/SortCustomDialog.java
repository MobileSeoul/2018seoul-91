package youthshelter.youth.co.kr.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import youthshelter.youth.co.kr.R;

public class SortCustomDialog extends Dialog {
    public SortCustomDialog(Context context) {
        super(context);
    }
    private int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        getWindow().setGravity(Gravity.TOP);

        setContentView(R.layout.dialog_sort);

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 0);
        getWindow().setBackgroundDrawable(inset);
        getWindow().getAttributes().width = android.view.WindowManager.LayoutParams.MATCH_PARENT;



        RadioGroup group=(RadioGroup)findViewById(R.id.sort_RadioGroup);
        RadioButton distanceRadioButton=(RadioButton)findViewById(R.id.sort_distance_RadioButton);
        RadioButton abcRadioButton=(RadioButton)findViewById(R.id.sort_abc_RadioButton);
        RadioButton likeRadioButton=(RadioButton)findViewById(R.id.sort_like_RadioButton);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.sort_distance_RadioButton:
                        result = 0;
                        break;
                    case R.id.sort_abc_RadioButton:
                        result = 2;
                        break;
                    case R.id.sort_like_RadioButton:
                        result = 1;
                        break;
                }
                dismiss();
            }
        });

    }

    public int getAddCategoryStr() {
        return result;
    }

    public void setAddCategoryStr(int addCategoryStr) {
        this.result = addCategoryStr;
    }

}

