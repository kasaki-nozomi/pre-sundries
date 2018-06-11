package cumt.taskplatform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        AdapterView.OnItemClickListener{

    private ListView listView;
    private FloatingActionButton fab;

    private RelativeLayout progress;
    private LinearLayout layout_no;

    protected QuickAdapter<Post> PostAdapter;

    private TextView headerName;
    private CircleImageView headerImage;
    private TextView headerText;

    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();

        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        listView = (ListView)findViewById(R.id.main_list);
        listView.setOnItemClickListener(this);

        progress = (RelativeLayout)findViewById(R.id.progress);
        layout_no = (LinearLayout)findViewById(R.id.layout_no);

        View headerView = navigationView.getHeaderView(0);
        headerName = (TextView)headerView.findViewById(R.id.header_name);
        headerImage = (CircleImageView)headerView.findViewById(R.id.icon_image);
        headerText = (TextView)headerView.findViewById(R.id.header_text);

        headerImage.setOnClickListener(this);

        initData();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab:
                Intent intent = new Intent(MainActivity.this, NewTask.class);
                startActivityForResult(intent,1);
                break;
            case R.id.icon_image:
                Intent perIntent = new Intent(MainActivity.this, Person_inf.class);
                startActivity(perIntent);
            default:
                break;
        }
    }

    String objectId = "";
    String title = "";
    String content = "";
    String author = "";
    String time = "";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        title = PostAdapter.getItem(position).getTitle();
        content = PostAdapter.getItem(position).getContent();
        time = PostAdapter.getItem(position).getCreatedAt();

        objectId = PostAdapter.getItem(position).getAuthor().getObjectId();

        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<User>(){
            @Override
            public void done(User user, BmobException e){
                author = user.getUsername();
                Intent intent = new Intent(MainActivity.this, TaskContent.class);
                intent.putExtra("User_data", user);
                intent.putExtra("Post_data", PostAdapter.getItem(position));
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("author", author);
                intent.putExtra("time", time);
                startActivity(intent);
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.release:
                Intent myIntent = new Intent(MainActivity.this, MyRelease.class);
                startActivity(myIntent);
                break;
            case R.id.person:
                Intent personIntent = new Intent(MainActivity.this, Person.class);
                startActivity(personIntent);
                break;
            case R.id.more:
                Intent moreIntent = new Intent(MainActivity.this, More.class);
                startActivity(moreIntent);
                break;
            case R.id.accept:
                Intent accIntent = new Intent(MainActivity.this, MyAcceptActivity.class);
                startActivity(accIntent);
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initData() {

        if (PostAdapter == null) {
            PostAdapter = new QuickAdapter<Post>(this, R.layout.list_item) {
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
        queryPost();
    }

    public void queryPost(){
        listView.setVisibility(View.VISIBLE);
        layout_no.setVisibility(View.GONE);

        User curUser = BmobUser.getCurrentUser(User.class);
        headerName.setText(curUser.getUsername());
        headerText.setText(curUser.getProfiles());
        if(curUser.getTouxiang() != null) {
            Glide.with(this).load(curUser.getTouxiang().getFileUrl()).into(headerImage);
        }

        BmobQuery<Post> query = new BmobQuery<>();
        //时间降序排序
        query.order("-createdAt");
        query.addWhereEqualTo("acceptable",false);
        query.findObjects(new FindListener<Post>(){

            public void done(List<Post> post, BmobException e){
                if(e == null){
                    PostAdapter.clear();
                    if(post == null || post.size() == 0)
                    {
                        progress.setVisibility(View.GONE);
                        listView.setVisibility(View.GONE);
                        layout_no.setVisibility(View.VISIBLE);
                        PostAdapter.notifyDataSetChanged();
                        return ;
                    }
                    progress.setVisibility(View.GONE);
                    PostAdapter.addAll(post);
                    listView.setAdapter(PostAdapter);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return ;
        }
        switch(requestCode){
            case 1:
                queryPost();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            BmobUser.logOut();
            finish();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void refreshData(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        queryPost();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        queryPost();
        User curUser = BmobUser.getCurrentUser(User.class);
        headerName.setText(curUser.getUsername());
        headerText.setText(curUser.getProfiles());
        if(curUser.getTouxiang() != null) {
            Glide.with(this).load(curUser.getTouxiang().getFileUrl()).into(headerImage);
        }
    }
}
