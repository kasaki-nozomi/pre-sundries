package cumt.taskplatform;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import bean.User;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class SMSActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText keyEt;
    private Button keyBt;
    private Button reqBt;
    private TextView text;

    private String username = null;
    private String password = null;
    private String phone = null;

    private String key;

    private String sendTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        keyEt = (EditText) findViewById(R.id.et_smsKey);
        keyBt = (Button) findViewById(R.id.btn_smsKey);
        reqBt = (Button) findViewById(R.id.bt_smsReg);
        text = (TextView) findViewById(R.id.sms_text);

        keyBt.setOnClickListener(this);
        reqBt.setOnClickListener(this);

        username = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_smsKey:
                SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sendTime = format.format(new Date());
                if(phone.length()!=11){
                    Toast.makeText(this,"请输入有效手机号码",Toast.LENGTH_LONG).show();
                }
                else{
                    //获取验证码和倒计时1分钟
                    BmobSMS.requestSMSCode(this.phone,"短信验证",new QueryListener<Integer>(){
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if(e==null){
                                //发送成功时，让获取验证码按钮不可点击,且为灰色
                                text.setText("已向你的手机发送了一条短信，请输入短信中的验证码:");
                                keyBt.setClickable(false);
                                keyBt.setDrawingCacheBackgroundColor(Color.GRAY);
                                Toast.makeText(SMSActivity.this,"验证码发送成功",Toast.LENGTH_SHORT).show();
                                new CountDownTimer(60000,1000){
                                    @Override
                                    public void onTick(long l) {
                                        keyBt.setText(l/1000+"秒");
                                    }

                                    @Override
                                    public void onFinish() {
                                        keyBt.setClickable(true);
                                        keyBt.setText("重新发送");
                                    }
                                }.start();
                            }
                            else{
                                Log.i("wocao",e.getMessage());
                                Toast.makeText(SMSActivity.this,"验证码发送失败，请重试",Toast.LENGTH_SHORT).show();
                            }
;
                        }
                    });
                }
                break;
            case R.id.bt_smsReg:
                key = keyEt.getText().toString();
                if(phone.length()==0||phone.length()!=11||key.length()==0){
                    Toast.makeText(this,"手机号或验证码错误",Toast.LENGTH_SHORT).show();
                }
                else{
                    BmobSMS.verifySmsCode(this.phone, key, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                User user = new User();
                                user.setUsername(username);
                                user.setPassword(password);
                                user.setMobilePhoneNumber(phone);
                                user.setMobilePhoneNumberVerified(true);
                                user.setProfiles("暂无简介");
                                user.signUp(new SaveListener<User>(){
                                    public void done(User user, BmobException e){

                                        if(e == null){
                                            Toast.makeText(SMSActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SMSActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(SMSActivity.this,"用户名或手机号已被占用!",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                            }
                            else{
                                Toast.makeText(SMSActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
        }
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
