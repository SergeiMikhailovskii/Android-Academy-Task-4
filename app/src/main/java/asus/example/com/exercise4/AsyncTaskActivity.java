package asus.example.com.exercise4;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class AsyncTaskActivity extends AppCompatActivity {

    private TextView timeCounterText;
    private CounterAsyncTask counterAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        timeCounterText = findViewById(R.id.counter);
        Button create = findViewById(R.id.create_button);
        findViewById(R.id.create_button).setOnClickListener(createListener);
        create.setVisibility(View.VISIBLE);
        findViewById(R.id.start_button).setOnClickListener(startListener);
        findViewById(R.id.cancel_button).setOnClickListener(cancelListener);
    }

    private View.OnClickListener createListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            counterAsyncTask = new CounterAsyncTask();
            HelpToast.showToast(R.string.asynctask_created, getApplicationContext());
        }
    };

    private View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                counterAsyncTask.execute();
            } catch (IllegalStateException | NullPointerException e) {
                HelpToast.showToast(R.string.null_pointer_text, getApplicationContext());
            }
        }
    };


    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                counterAsyncTask.cancel(true);
                timeCounterText.setText(getText(R.string.start_value));
                HelpToast.showToast(R.string.asynctask_cancelled, getApplicationContext());
            } catch (NullPointerException e) {
                HelpToast.showToast(R.string.null_pointer_text, getApplicationContext());
            }
        }
    };


    private class CounterAsyncTask extends AsyncTask<Void, Integer, Void> implements IAsyncTaskEvents {


        @Override
        protected void onPreExecute() {
            timeCounterText.setText(R.string.start_value);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < Constants.AMOUNT; i++) {
                try {
                    onProgressUpdate(i);
                    TimeUnit.MILLISECONDS.sleep(Constants.TIMEOUT);
                    if (isCancelled()) {
                        return null;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            onPostExecute();
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


        @Override
        public void onPostExecute() {
            timeCounterText.setText(getText(R.string.done));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onProgressUpdate(Integer integer) {
            timeCounterText.setText(integer + "");


        }
    }
}
