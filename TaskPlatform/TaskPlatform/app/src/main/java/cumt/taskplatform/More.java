package cumt.taskplatform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class More extends AppCompatActivity implements View.OnClickListener{

    private Button introductionButton = null;
    private Button opinionButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        introductionButton = (Button)findViewById(R.id.function);
        opinionButton = (Button)findViewById(R.id.opinion);
        introductionButton.setOnClickListener(this);
        opinionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.function:
                Intent funIntent = new Intent(More.this, introductionActivity.class);
                startActivity(funIntent);
                break;
            case R.id.opinion:
                Intent opinionIntent = new Intent(More.this, OpinionActivity.class);
                startActivity(opinionIntent);
            default:
                break;
        }
    }
}
