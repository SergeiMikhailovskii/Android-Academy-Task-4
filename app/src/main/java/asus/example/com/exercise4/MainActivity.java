package asus.example.com.exercise4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Intent intent;


    private View.OnClickListener asyncTaskListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AsyncTaskActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener loaderListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent = new Intent(MainActivity.this, LoaderActivity.class);
            startActivity(intent);

        }
    };

    private View.OnClickListener threadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent = new Intent(MainActivity.this, ThreadActivity.class);
            startActivity(intent);
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
}
