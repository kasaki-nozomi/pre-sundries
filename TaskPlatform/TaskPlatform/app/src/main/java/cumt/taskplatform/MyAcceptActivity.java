package cumt.taskplatform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import adapter.BaseAdapterHelper;
import adapter.QuickAdapter;
import bean.Post;
import bean.User;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class MyAcceptActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    protected QuickAdapter<Post> MyAdapter;
    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_accept);

        listView = (ListView)findViewById(R.id.my_acc_list);

        if (MyAdapter == null) {
            MyAdapter = new QuickAdapter<Post>(this, R.layout.list_item) {
                @Override
                protected void convert(final BaseAdapterHelper helper, final Post item) {

                    String object = item.getAuthor().getObjectId();

                    BmobQuery<User> query = new BmobQuery<>();
                    query.getObject(object, new QueryListener<User>(){
                        @Override
                        public void done(User user, BmobException e){
                            String name = user.getUsername();
                            if(user.getTouxiang() != null) {
                                String url = user.getTouxiang().getFileUrl();
                                helper.setText(R.id.title, item.getTitle())
                                        .setText(R.id.author, name)
                                        .setText(R.id.time, item.getCreatedAt())
                                        .setImageUrl(R.id.tx_image, url);
                            }else{
                                helper.setText(R.id.title, item.getTitle())
                                        .setText(R.id.author, name)
                                        .setText(R.id.time, item.getCreatedAt());
                            }
                        }
                    });
                }
            };
        }
        listView.setAdapter(MyAdapter);

        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("accepter", user);  // 查询当前用户接收的所有帖子
        query.order("-updatedAt");
        query.findObjects(new FindListener<Post>() {

            @Override
            public void done(List<Post> object, BmobException e) {
                if(e==null){
                    MyAdapter.clear();
                    if(object == null || object.size() == 0) {
                        MyAdapter.notifyDataSetChanged();
                        return ;
                    }
                }
                MyAdapter.addAll(object);
                listView.setAdapter(MyAdapter);
            }
        });
        listView.setOnItemClickListener(this);
    }

    String objectId = "";
    String title = "";
    String content = "";
    String author = "";
    String time = "";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        finish();

        title = MyAdapter.getItem(position).getTitle();
        content = MyAdapter.getItem(position).getContent();
        time = MyAdapter.getItem(position).getCreatedAt();

        objectId = MyAdapter.getItem(position).getAuthor().getObjectId();

        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<User>(){
            @Override
            public void done(User user, BmobException e){
                author = user.getUsername();
                Intent intent = new Intent(MyAcceptActivity.this, TaskContent.class);
                intent.putExtra("Post_data", MyAdapter.getItem(position));
                intent.putExtra("User_data", user);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("author", author);
                intent.putExtra("time", time);
                startActivity(intent);
            }
        });
    }

}
