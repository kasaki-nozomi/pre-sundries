package others;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import cumt.taskplatform.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class mImageButton extends LinearLayout {

    private CircleImageView circleImageView;

    public mImageButton(Context context) {
        super(context);
    }

    public mImageButton(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        LayoutInflater.from(context).inflate(R.layout.image_button,this,true);

        this.circleImageView = (CircleImageView)findViewById(R.id.content_image);

        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setImgResource(Context context, String resourceID) {
        Glide.with(context).load(resourceID).into(circleImageView);
    }
}
