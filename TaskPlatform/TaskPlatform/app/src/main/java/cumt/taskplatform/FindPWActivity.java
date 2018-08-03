package cumt.taskplatform;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import others.Util;

public class FindPWActivity extends AppCompatActivity {

    private EditText findEt;
    private Button findBt;

    private String phone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        findEt = (EditText)findViewById(R.id.find_et);
        findBt = (Button)findViewById(R.id.find_bt);

        findBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = findEt.getText().toString();
                if(!Util.isPhoneNumberValid(phone)){
                    Toast.makeText(FindPWActivity.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
                }else {
                    BmobQuery<User> query = new BmobQuery<>();
                    query.addWhereEqualTo("mobilePhoneNumber",phone);
                    query.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if(e==null) {
                                User user = list.get(0);
                                Intent intent = new Intent(FindPWActivity.this, SMS2Activity.class);
                                intent.putExtra("User_data", user);
                                startActivity(intent);
                            }else{
                                Toast.makeText(FindPWActivity.this, "未注册的号码！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
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
