package cumt.taskplatform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import bean.User;
import cn.bmob.v3.BmobUser;
import others.ImageTextButton;

public class Person extends AppCompatActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Button myTaskButton = (Button)findViewById(R.id.myTask);
        myTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Person.this, MyRelease.class);
                startActivity(intent);
            }
        });

        Button myAcceptButton = (Button)findViewById(R.id.myaccept);
        myAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Person.this, MyAcceptActivity.class);
                startActivity(intent);
            }
        });

        User curUser = BmobUser.getCurrentUser(User.class);

        ImageTextButton imageTextButton = (ImageTextButton)findViewById(R.id.image_text_button);
        if(curUser.getTouxiang() != null)
        {
            imageTextButton.setImgResource1(this, curUser.getTouxiang().getFileUrl());
        }
        imageTextButton.setImgResource2(R.drawable.right);
        imageTextButton.setText1(curUser.getUsername());
        imageTextButton.setText2("Nothing");

        imageTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent personIntent = new Intent(Person.this, Person_inf.class);
                User curUser = BmobUser.getCurrentUser(User.class);
                personIntent.putExtra("User", curUser);
                startActivity(personIntent);
            }
        });

        Button detailButton = (Button)findViewById(R.id.details);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Person.this, Person_inf.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.inf_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.xiu_gai:
                Intent intent = new Intent(Person.this, ModifyInf.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
