package cumt.taskplatform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import bean.User;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class Person_inf extends AppCompatActivity {

    private TextView name;
    private TextView profiles;
    private TextView sex;
    private TextView age;
    private TextView address;
    private TextView cademy;
    private TextView qq;
    private TextView tel;
    private CircleImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_inf);

        name = (TextView)findViewById(R.id.inf_name2);
        profiles = (TextView)findViewById(R.id.inf_profiles2);
        sex = (TextView)findViewById(R.id.inf_sex2);
        age = (TextView)findViewById(R.id.inf_age2);
        address = (TextView)findViewById(R.id.inf_address2);
        cademy = (TextView)findViewById(R.id.inf_cademy2);
        qq = (TextView)findViewById(R.id.inf_qq2);
        tel = (TextView)findViewById(R.id.inf_tel2);
        image = (CircleImageView)findViewById(R.id.inf_image2);

        User curUser = BmobUser.getCurrentUser(User.class);

        name.setText(curUser.getUsername());
        profiles.setText(curUser.getProfiles());
        sex.setText(curUser.getSex());
        age.setText(curUser.getAge());
        address.setText(curUser.getAddress());
        cademy.setText(curUser.getCademy());
        qq.setText(curUser.getQQ());
        tel.setText(curUser.getMobilePhoneNumber());
        if(curUser.getTouxiang() != null) {
            Glide.with(this).load(curUser.getTouxiang().getFileUrl()).into(image);
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Person_inf.this, ModifyInf.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        User curUser = BmobUser.getCurrentUser(User.class);

        name.setText(curUser.getUsername());
        profiles.setText(curUser.getProfiles());
        sex.setText(curUser.getSex());
        age.setText(curUser.getAge());
        address.setText(curUser.getAddress());
        cademy.setText(curUser.getCademy());
        qq.setText(curUser.getQQ());
        tel.setText(curUser.getMobilePhoneNumber());
        if(curUser.getTouxiang() != null) {
            Glide.with(this).load(curUser.getTouxiang().getFileUrl()).into(image);
        }
    }
}
