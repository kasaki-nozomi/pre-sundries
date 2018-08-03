package cumt.taskplatform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import bean.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalInformation extends AppCompatActivity {

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
        setContentView(R.layout.activity_personal_information);

        name = (TextView)findViewById(R.id.inf_name);
        profiles = (TextView)findViewById(R.id.inf_profiles);
        sex = (TextView)findViewById(R.id.inf_sex);
        age = (TextView)findViewById(R.id.inf_age);
        address = (TextView)findViewById(R.id.inf_address);
        cademy = (TextView)findViewById(R.id.inf_cademy);
        qq = (TextView)findViewById(R.id.inf_qq);
        tel = (TextView)findViewById(R.id.inf_tel);
        image = (CircleImageView)findViewById(R.id.inf_image);

        User curUser = (User)getIntent().getSerializableExtra("User");

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

            }
        });
    }
}
