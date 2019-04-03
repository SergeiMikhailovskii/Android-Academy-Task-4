package asus.example.com.exercise4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private View.OnClickListener asyncTaskListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openActivity(AsyncTaskActivity.class);
        }
    };

    private View.OnClickListener loaderListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openActivity(LoaderActivity.class);
        }
    };

    private View.OnClickListener threadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openActivity(ThreadActivity.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.async_task).setOnClickListener(asyncTaskListener);
        findViewById(R.id.loader).setOnClickListener(loaderListener);
        findViewById(R.id.threads).setOnClickListener(threadListener);
    }

    private void openActivity(Class openClass){
        Intent intent = new Intent(MainActivity.this, openClass);
        startActivity(intent);
    }
}
