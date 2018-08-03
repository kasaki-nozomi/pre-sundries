package others;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cumt.taskplatform.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ImageTextButton extends LinearLayout {

    private CircleImageView imageView1;
    private ImageView imageView2;
    private TextView textView1;
    private TextView textView2;

    public ImageTextButton(Context context) {
        super(context);
    }

    public ImageTextButton(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        LayoutInflater.from(context).inflate(R.layout.img_text_button,this,true);

        this.imageView1 = (CircleImageView)findViewById(R.id.circle_image);
        this.imageView2 = (ImageView)findViewById(R.id.right);
        this.textView1 = (TextView)findViewById(R.id.you_name);
        this.textView2 = (TextView)findViewById(R.id.contact);

        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setImgResource1(Context context, String resourceID) {
        Glide.with(context).load(resourceID).into(imageView1);
    }

    public void setImgResource2(int resourceID) {
        this.imageView2.setImageResource(resourceID);
    }

    public void setText1(String name) {
        this.textView1.setText(name);
    }

    public void setText2(String contact) {
        this.textView2.setText(contact);
    }

}
