package cumt.taskplatform;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import bean.Post;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class EditActivity extends AppCompatActivity {

    private EditText titleEdit;
    private EditText editText;
    private FloatingActionButton fab;

    private String title="";
    private String content="";
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleEdit = (EditText)findViewById(R.id.title_editText);
        editText = (EditText)findViewById(R.id.content_editText);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        post = (Post)getIntent().getSerializableExtra("Post_data");

        titleEdit.setText(title);
        editText.setText(content);

        fab = (FloatingActionButton)findViewById(R.id.fab_yes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleEdit.getText().toString();
                content = editText.getText().toString();
                post.setTitle(title);
                post.setContent(content);
                post.update(post.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(EditActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(EditActivity.this, "更新失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
