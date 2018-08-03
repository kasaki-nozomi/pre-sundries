package cumt.taskplatform;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bean.Post;
import bean.User;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class TaskContent extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvTime;
    private TextView tvAuthor;
    private User user;
    private Post post;

    private String title = "";
    private String content = "";
    private String time = "";
    private String author = "";

    private FloatingActionButton accFab;
    private FloatingActionButton notFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_content);

        tvTitle = (TextView)findViewById(R.id.content_title);
        tvContent = (TextView)findViewById(R.id.content2);
        tvTime = (TextView)findViewById(R.id.content_time);
        tvAuthor = (TextView)findViewById(R.id.content_author);
        accFab = (FloatingActionButton)findViewById(R.id.fab_ac);
        notFab = (FloatingActionButton)findViewById(R.id.accepted);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        time = getIntent().getStringExtra("time");
        author = getIntent().getStringExtra("author");
        user = (User)getIntent().getSerializableExtra("User_data");
        post = (Post)getIntent().getSerializableExtra("Post_data");

        tvTitle.setText(title);
        tvContent.setText(content+"\n\n\n\n\n");
        tvTime.setText(time);
        tvAuthor.setText(author);

        others.mImageButton imageButton =(others.mImageButton)findViewById(R.id.mImage);
        if(user.getTouxiang() != null) {
            imageButton.setImgResource(this, user.getTouxiang().getFileUrl());
        }
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent personIntent = new Intent(TaskContent.this, PersonalInformation.class);
                personIntent.putExtra("User", user);
                startActivity(personIntent);
            }
        });

        accFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BmobUser.getCurrentUser(User.class).getObjectId().equals(user.getObjectId())){
                    Toast.makeText(TaskContent.this, "你不能接受自己发布的任务", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TaskContent.this);
                    dialog.setTitle("");
                    dialog.setMessage("确定是否接受此任务？");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("接受", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            post.setAcceptable(true);
                            post.setAccepter(BmobUser.getCurrentUser(User.class));
                            post.update(post.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e == null){
                                        Intent accIntent = new Intent(TaskContent.this, MyAcceptActivity.class);
                                        startActivity(accIntent);
                                    }else{
                                        Toast.makeText(TaskContent.this, "接收任务失败", Toast.LENGTH_SHORT).show();
                                        Log.i("失败", e.getMessage()+e.getErrorCode());
                                    }
                                }
                            });
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            }
        });

        notFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(BmobUser.getCurrentUser(User.class).getObjectId().equals(user.getObjectId())){
                    Toast.makeText(TaskContent.this, "你不能操作自己发布的任务", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TaskContent.this);
                    dialog.setTitle("");
                    dialog.setMessage("是否取消接受此任务？");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            post.setAcceptable(false);
                            post.remove("accepter");
                            post.update(post.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        finish();
                                    } else {
                                        Toast.makeText(TaskContent.this, "取消任务失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                    dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            }
        });

        if(post.getAcceptable()){
            accFab.setVisibility(View.GONE);
            notFab.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.share:
                content = tvContent.getText().toString();
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, content);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
