package asus.example.com.exercise4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Я бы сделал отдельный клик лисенер на каждую кнопку(это уже вкусовщина),
    //так получается более явное разделение логики для каждой кнопки
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.async_task:
                    //запуск активити лучше вынести в функцию
                    Intent intent = new Intent(MainActivity.this,
                            AsyncTaskActivity.class);
                    startActivity(intent);
                    break;
                case R.id.loader:
                    intent = new Intent(MainActivity.this, LoaderActivity.class);
                    startActivity(intent);
                    break;
                case R.id.threads:
                    intent = new Intent(MainActivity.this, ThreadActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //можно не создавать переменные для кнопок, а сразу вешать OnClickListener на findViewById(
        // findViewById(R.id.testId).senOnClickListener
        Button asyncTaskButton = findViewById(R.id.async_task);
        Button loaderButton = findViewById(R.id.loader);
        Button threadsButton = findViewById(R.id.threads);

        asyncTaskButton.setOnClickListener(listener);
        loaderButton.setOnClickListener(listener);
        threadsButton.setOnClickListener(listener);
    }
}
