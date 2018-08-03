package cumt.taskplatform;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bean.User;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import others.Util;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button btnLogin;
    private Button btnReg;
    private Button btnResetPsd;
    private EditText etUsername;
    private EditText etPassword;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this,"3b95e9676089e50483643fd9f1781e05");
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.login);
        btnReg = (Button) findViewById(R.id.register);
        btnResetPsd = (Button) findViewById(R.id.forget);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        btnResetPsd.setOnClickListener(this);

        getUserInfo();
    }

    private void getUserInfo() {
        SharedPreferences sp = getSharedPreferences("UserInfo", 0);
        etUsername.setText(sp.getString("username", null));
        etPassword.setText(sp.getString("password", null));
    }

    //保存用户的登陆记录
    private void saveUserInfo(String username, String password) {
        SharedPreferences sp = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.login:
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                if(!Util.isNetworkConnected(this)){
                    toast("断网了啊！");
                    break;
                }else if (username.equals("")) {
                    toast("快输用户名！");
                    break;
                }else if(password.equals("")){
                    toast("快输密码！");
                    break;
                }else{
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if(e == null) {
                                toast("Login is Successful!");
                                //保存用户信息
                                saveUserInfo(username, password);
                                //跳转
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                toast("Username or Password Wrong!");
                            }
                        }
                    });
                }
                break;
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forget:
                Intent intent2 = new Intent(LoginActivity.this, FindPWActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    public void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
