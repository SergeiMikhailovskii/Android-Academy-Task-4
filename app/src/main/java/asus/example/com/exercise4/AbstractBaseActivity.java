package asus.example.com.exercise4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public abstract class AbstractBaseActivity extends AppCompatActivity {

    protected TextView timeCounterText;
    protected Button create;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        timeCounterText = findViewById(R.id.counter);
        create = findViewById(R.id.create_button);
        findViewById(R.id.create_button).setOnClickListener(onButtonCreateListener);
        findViewById(R.id.start_button).setOnClickListener(onButtonStartListener);
        findViewById(R.id.cancel_button).setOnClickListener(onButtonCancelListener);
        if(isCreateButtonVisible()){
            create.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener onButtonCreateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onButtonCreateClick();
        }
    };

    public abstract void onButtonCreateClick();

    private View.OnClickListener onButtonStartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onButtonStartClick();
        }
    };

    public abstract void onButtonStartClick();


    private View.OnClickListener onButtonCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onButtonCancelClick();
        }
    };

    public abstract void onButtonCancelClick();

    public abstract boolean isCreateButtonVisible();

}
