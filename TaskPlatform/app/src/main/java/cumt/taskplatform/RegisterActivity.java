package cumt.taskplatform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bean.User;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import others.Util;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button btnReg;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etComfirmPsd;
    private EditText etPhone;

    private String username = null;
    private String password = null;
    private String confirmPsd = null;
    private String phone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        etComfirmPsd = (EditText) findViewById(R.id.confirm_password);
        etPhone = (EditText) findViewById(R.id.phone);

        btnReg = (Button) findViewById(R.id.join);
        btnReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.join:
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                confirmPsd = etComfirmPsd.getText().toString();
                phone = etPhone.getText().toString();

                if(!Util.isNetworkConnected(this)) {
                    toast("木有网络(⊙o⊙)");
                    break;
                } else if (username.equals("")){
                    toast("用户名？");
                    break;
                }else if (password.equals("")) {
                    toast("密码？");
                    break;
                } else if (confirmPsd.equals("")) {
                    toast("确认密码？");
                    break;
                } else if (phone.equals("")) {
                    toast("手机号？");
                    break;
                }  else if (!confirmPsd.equals(password)) {
                    toast("手抖什么？两次密码输入不一致");
                    break;
                } else if(!Util.isPhoneNumberValid(phone)) {
                    toast("手机号码不正确");
                }else {
                    Intent intent = new Intent(RegisterActivity.this, SMSActivity.class);
                    intent.putExtra("name",username);
                    intent.putExtra("password",password);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }

                break;
            default:
                break;
        }
    }
    public void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
