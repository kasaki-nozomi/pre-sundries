package com.baidu.ardemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private String mKey;
    private int mType;
    private String mKey_two;
    private int mType_two;
    private String mTitle;
    private String mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mKey = "10169598";
        mType = 5;
        mKey_two = "10192795";
        mType_two = 0;
        mTitle = "WEAR+E";
        mDescription = "“朝而往，暮而归，四时之景不同，而其乐无穷”。即便是相同的景观，在不同的时间，不同的季节观赏起来也有不同的风味。\n" +
                "当游客来到某景点，利用AR技术便可以为游客展现该景点的不同状态，几分钟看尽景点的春夏秋冬，极大的提高旅行的体验。";
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.intro_title)).setText(mTitle);
        ((TextView) findViewById(R.id.intro_detail)).setText(mDescription);

        findViewById(R.id.call_ar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ARActivity.class);
                intent.putExtra("ar_key", mKey);
                intent.putExtra("ar_type", mType);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        findViewById(R.id.call_ar_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ARActivity.class);
                intent.putExtra("ar_key", mKey_two);
                intent.putExtra("ar_type", mType_two);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}