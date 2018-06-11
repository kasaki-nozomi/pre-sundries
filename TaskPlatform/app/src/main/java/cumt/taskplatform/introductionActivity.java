package cumt.taskplatform;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class introductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        ImageView functionImageView = (ImageView)findViewById(R.id.function_image_view);
        TextView functionContentText = (TextView)findViewById(R.id.function_content_text);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("功能与介绍：");
        Picasso.with(this).load(R.drawable.tushuguan).into(functionImageView);
        functionContentText.setText("面向校园用户的app\n" +
                "用户拥有个人账号\n" +
                "可发布任务，接受任务\n" +
                "任务发布者需向完成任务者提供一定酬劳\n" +
                "任务形式：代取物品，资源共享，做指定的某件事\n" +
                "\n" +
                "E.g：校园内让别人帮忙代取快递，期末寻求复习资料，让别人帮忙去学院楼交作业等\n" +
                "\n" +
                "包含了部分快递君、外卖的功能，但更多的是针对用户个人的个性化服务，具体到了生活中的各种小事\n");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
