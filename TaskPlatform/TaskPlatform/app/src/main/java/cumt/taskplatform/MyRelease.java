package cumt.taskplatform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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
import cn.bmob.v3.listener.UpdateListener;
import popupList.PopupList;


public class MyRelease extends AppCompatActivity implements AdapterView.OnItemClickListener{

    protected QuickAdapter<Post> MyAdapter;
    protected ListView listView;
    private List<String> popupMenuItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_release);

        popupMenuItemList.add("编辑");
        popupMenuItemList.add("删除");
        popupMenuItemList.add("更多...");

        listView = (ListView)findViewById(R.id.my_list);

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

        PopupList popupList = new PopupList(this);
        popupList.bind(listView, popupMenuItemList, new PopupList.PopupListListener() {
            @Override
            public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                return true;
            }

            @Override
            public void onPopupListClick(View contextView, final int contextPosition, int position) {
                switch(position){
                    case 0:
                        title = MyAdapter.getItem(contextPosition).getTitle();
                        content = MyAdapter.getItem(contextPosition).getContent();
                        time = MyAdapter.getItem(contextPosition).getCreatedAt();

                        objectId = MyAdapter.getItem(contextPosition).getAuthor().getObjectId();

                        BmobQuery<User> query = new BmobQuery<>();
                        query.getObject(objectId, new QueryListener<User>(){
                            @Override
                            public void done(User user, BmobException e){
                                author = user.getUsername();
                                Intent intent = new Intent(MyRelease.this, EditActivity.class);
                                intent.putExtra("Post_data", MyAdapter.getItem(contextPosition));
                                intent.putExtra("User_data", user);
                                intent.putExtra("title", title);
                                intent.putExtra("content", content);
                                startActivity(intent);
                            }
                        });
                        break;
                    case 1:
                        Post post = new Post();
                        post.setObjectId(MyAdapter.getItem(contextPosition).getObjectId());
                        post.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(MyRelease.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MyRelease.this, "删除失败！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        queryPost();
                        break;
                    case 2:
                        title = MyAdapter.getItem(contextPosition).getTitle();
                        content = MyAdapter.getItem(contextPosition).getContent();
                        time = MyAdapter.getItem(contextPosition).getCreatedAt();

                        objectId = MyAdapter.getItem(contextPosition).getAuthor().getObjectId();

                        BmobQuery<User> postquery = new BmobQuery<>();
                        postquery.getObject(objectId, new QueryListener<User>(){
                            @Override
                            public void done(User user, BmobException e){
                                author = user.getUsername();
                                Intent intent = new Intent(MyRelease.this, TaskContent.class);
                                intent.putExtra("Post_data", MyAdapter.getItem(contextPosition));
                                intent.putExtra("User_data", user);
                                intent.putExtra("title", title);
                                intent.putExtra("content", content);
                                intent.putExtra("author", author);
                                intent.putExtra("time", time);
                                startActivity(intent);
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });

        queryPost();

        listView.setOnItemClickListener(this);
    }

    String objectId = "";
    String title = "";
    String content = "";
    String author = "";
    String time = "";

    private void queryPost(){
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.addWhereEqualTo("author", user);  // 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.findObjects(new FindListener<Post>() {

            @Override
            public void done(List<Post> object, BmobException e) {
                if(e==null){
                    MyAdapter.clear();
                    if(object == null || object.size() == 0)
                    {
                        MyAdapter.notifyDataSetChanged();
                        return ;
                    }
                }
                MyAdapter.addAll(object);
                listView.setAdapter(MyAdapter);
            }

        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        title = MyAdapter.getItem(position).getTitle();
        content = MyAdapter.getItem(position).getContent();
        time = MyAdapter.getItem(position).getCreatedAt();

        objectId = MyAdapter.getItem(position).getAuthor().getObjectId();

        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<User>(){
            @Override
            public void done(User user, BmobException e){
                author = user.getUsername();
                Intent intent = new Intent(MyRelease.this, TaskContent.class);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        queryPost();
    }
}
