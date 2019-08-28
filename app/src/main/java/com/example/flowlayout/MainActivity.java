package com.example.flowlayout;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FlowLayout flowLayout;

    private String[] titles = {"Android开发艺术探索","Thinking in Java","Web","IOS","Android","不同的技术规划"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flowLayout = findViewById(R.id.flowLayout);
    }

    public void changeFlowCount(View view){

        String title = "";
        switch (view.getId()){
            case R.id.button0:
                int index = new Random().nextInt(6);
                title = titles[index];
                break;
            case R.id.button1:
                title = "测试第一个测试第一个测-->试第一个测试第一个测试第一个";
                break;
        }
        TextView textView = new TextView(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(lp);
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        textView.setBackgroundResource(R.drawable.bg_corner);

        textView.setText(title);

        flowLayout.addView(textView);
    }

    public void deleteItem(View view){
        int childCount = flowLayout.getChildCount();

        int index = 0;
        switch (view.getId()){
            case R.id.button2:
                index = childCount - 1;
                break;
            case R.id.button3:
                //第三个
                index = 2;
                break;
        }
        View childView = flowLayout.getChildAt(index);
        flowLayout.removeView(childView);
    }
}
