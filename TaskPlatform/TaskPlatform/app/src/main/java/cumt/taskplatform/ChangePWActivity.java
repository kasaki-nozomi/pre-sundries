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

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ChangePWActivity extends AppCompatActivity {

    private EditText passOne;
    private EditText passTwo;
    private Button passBt;

    private String pass;
    private String confirmPass;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        key = getIntent().getStringExtra("key");

        passOne = (EditText)findViewById(R.id.pass_one);
        passTwo = (EditText)findViewById(R.id.pass_two);
        passBt = (Button)findViewById(R.id.pass_bt);

        passBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass = passOne.getText().toString();
                confirmPass = passTwo.getText().toString();
                if(!pass.equals(confirmPass)){
                    Toast.makeText(ChangePWActivity.this, "两次输入不一致！", Toast.LENGTH_SHORT).show();
                }else{
                    BmobUser.resetPasswordBySMSCode(key, pass, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(ChangePWActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ChangePWActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(ChangePWActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                                finish();
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
