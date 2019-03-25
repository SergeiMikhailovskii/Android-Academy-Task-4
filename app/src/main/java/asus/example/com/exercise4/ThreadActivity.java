package asus.example.com.exercise4;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThreadActivity extends AppCompatActivity {

    private Thread counterThread;
    private TextView textCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        Button create = findViewById(R.id.create_button);
        create.setVisibility(View.VISIBLE);
        Button start = findViewById(R.id.start_button);
        Button cancel = findViewById(R.id.cancel_button);
        textCounter = findViewById(R.id.counter);
        create.setOnClickListener(listener);
        start.setOnClickListener(listener);
        cancel.setOnClickListener(listener);
    }

    //Ввнести лисенеры как в main activity, чтобы разделить логику
    private View.OnClickListener listener = new View.OnClickListener() {
        @SuppressLint("HandlerLeak")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.create_button:
                    counterThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int i;
                            for (i = 0; i < Constants.AMOUNT; i++) {
                                final int finalI = i;
                                textCounter.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        textCounter.setText(String.valueOf(finalI));

                                    }

                                });
                                try {
                                    Thread.sleep(Constants.TIMEOUT);
                                } catch (InterruptedException e) {
                                    return;
                                }
                                if (counterThread.isInterrupted()) {
                                    return;
                                }

                            }
                            textCounter.post(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    textCounter.setText(getApplicationContext().getResources()
                                            .getText(R.string.done));
                                }
                            });
                        }
                    });
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getResources().getText(R.string.thread_created)
                            , Toast.LENGTH_SHORT).show();
                    break;
                case R.id.start_button:
                    try {
                        counterThread.start();
                    } catch (IllegalThreadStateException | NullPointerException e) {
                        //Вынести метод для создания тоста в статический метод в новый класс ToastHelper
                        // showShortToast(int resId) {...}
                        Toast.makeText(getApplicationContext(),
                                getApplicationContext().getResources()
                                        .getText(R.string.null_pointer_text), Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case R.id.cancel_button:
                    try {
                        counterThread.interrupt();
                        textCounter.setText(getApplicationContext().getResources()
                                .getText(R.string.start_value));
                    } catch (NullPointerException e) {
                        Toast.makeText(getApplicationContext(),
                                getApplicationContext().getResources()
                                        .getText(R.string.null_pointer_text), Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
            }
        }
    };


}
