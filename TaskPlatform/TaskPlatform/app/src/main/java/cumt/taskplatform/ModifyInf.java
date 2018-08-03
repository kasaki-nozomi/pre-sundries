package cumt.taskplatform;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;

import bean.User;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyInf extends AppCompatActivity implements View.OnClickListener{

    private CircleImageView touxiang;
    private EditText sex;
    private EditText age;
    private EditText address;
    private EditText cademy;
    private EditText qq;
    private EditText tel;
    private EditText profiles;

    private String ssex;
    private String sage;
    private String saddress;
    private String scademy;
    private String sqq;
    private String sprofiles;

    private String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_inf);

        touxiang=(CircleImageView)findViewById(R.id.mod_touxiang);
        sex=(EditText)findViewById(R.id.mod_sex);
        age=(EditText)findViewById(R.id.mod_age);
        address=(EditText)findViewById(R.id.mod_address);
        cademy=(EditText)findViewById(R.id.mod_cademy);
        qq=(EditText)findViewById(R.id.mod_qq);
        profiles=(EditText)findViewById(R.id.mod_profiles);

        User curUser= BmobUser.getCurrentUser(User.class);

        sex.setText(curUser.getSex());
        age.setText(curUser.getAge());
        address.setText(curUser.getAddress());
        cademy.setText(curUser.getCademy());
        qq.setText(curUser.getQQ());
        profiles.setText(curUser.getProfiles());

        if(curUser.getTouxiang() != null) {
            Glide.with(this).load(curUser.getTouxiang().getFileUrl()).into(touxiang);
        }
        touxiang.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_inf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.save_inf:

                ssex = sex.getText().toString();
                sage = age.getText().toString();
                saddress = address.getText().toString();
                scademy = cademy.getText().toString();
                sqq = qq.getText().toString();
                sprofiles = profiles.getText().toString();

                final User user = new User();
                user.setSex(ssex);
                user.setAge(sage);
                user.setAddress(saddress);
                user.setCademy(scademy);
                user.setQQ(sqq);
                user.setProfiles(sprofiles);

                if(!path.equals("")) {
                    final BmobFile file = new BmobFile(new File(path));
                    file.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                user.setTouxiang(new BmobFile(BmobUser.getCurrentUser(User.class).getUsername(), null, file.getFileUrl()));
                                user.update(BmobUser.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(ModifyInf.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(ModifyInf.this, "修改失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ModifyInf.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onProgress(Integer value) {
                        }
                    });
                } else{
                    user.update(BmobUser.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ModifyInf.this, "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ModifyInf.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        // 开启Pictures画面Type设定为image
        intent.setType("image/*");
        // 使用Intent.ACTION_GET_CONTENT这个Action
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // 取得相片后返回本画面
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            path = getImagePath(uri, null);
            ContentResolver cr = this.getContentResolver();
            try {

                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                // 将Bitmap设定到ImageView
                touxiang.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {

            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
