package cumt.taskplatform;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import bean.Post;
import bean.User;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class NewTask extends AppCompatActivity {

    private EditText titleEdit;
    private EditText editText;
    private FloatingActionButton fab;

    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titleEdit = (EditText)findViewById(R.id.title_editText);
        editText = (EditText)findViewById(R.id.content_editText);


        fab = (FloatingActionButton)findViewById(R.id.fab_yes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                content = editText.getText().toString();
                title = titleEdit.getText().toString();

                if(TextUtils.isEmpty(title)){
                    Toast.makeText(NewTask.this, "请填写标题", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(content)){
                    Toast.makeText(NewTask.this, "请填写内容", Toast.LENGTH_SHORT).show();
                }else{

                    User user = BmobUser.getCurrentUser(User.class);
                    Post post = new Post();
                    post.setTitle(title);
                    post.setContent(content);
                    post.setAuthor(user);
                    post.setAcceptable(false);
                    post.save(new SaveListener<String>() {

                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(NewTask.this, "添加成功", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(NewTask.this, "添加失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
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
                content = editText.getText().toString();
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
