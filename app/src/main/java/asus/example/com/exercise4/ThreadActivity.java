package asus.example.com.exercise4;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThreadActivity extends AppCompatActivity {

    private Thread counterThread;
    private TextView textCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        Button create = findViewById(R.id.create_button);
        create.setVisibility(View.VISIBLE);
        textCounter = findViewById(R.id.counter);
        create.setOnClickListener(createListener);
        findViewById(R.id.start_button).setOnClickListener(startListener);
        findViewById(R.id.cancel_button).setOnClickListener(cancelListener);

    }


    private View.OnClickListener createListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
            HelpToast.showToast(R.string.thread_created, getApplicationContext());

        }
    };

    private View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                counterThread.start();
            } catch (IllegalThreadStateException | NullPointerException e) {
                HelpToast.showToast(R.string.null_pointer_text, getApplicationContext());

            }
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                counterThread.interrupt();
                textCounter.setText(getApplicationContext().getResources()
                        .getText(R.string.start_value));
                HelpToast.showToast(R.string.thread_cancelled, getApplicationContext());
            } catch (NullPointerException e) {
                HelpToast.showToast(R.string.null_pointer_text, getApplicationContext());
            }

        }
    };


}
