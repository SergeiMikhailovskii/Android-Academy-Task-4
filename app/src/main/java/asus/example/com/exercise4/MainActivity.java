package asus.example.com.exercise4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;


    private View.OnClickListener asyncTaskListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //3 раза одинаковый кусок кода по открытию активити - желательно такие вещи выносить в отдельную функцию
            // openActivity(Class clazz) {
            // Intent intent = new Intent(MainActivity.this, class;
            //            startActivity(intent);
            //}
            //как-то так
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
        //все изменения с лисенерами - можно использовать и в других активити)
        //аналогично
        findViewById(R.id.async_task).setOnClickListener(asyncTaskListener);
        findViewById(R.id.loader).setOnClickListener(loaderListener);
        findViewById(R.id.threads).setOnClickListener(threadListener);
    }
}
