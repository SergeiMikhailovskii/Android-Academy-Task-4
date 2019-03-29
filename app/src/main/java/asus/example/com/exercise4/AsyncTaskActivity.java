package asus.example.com.exercise4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Button start = findViewById(R.id.start_button);
        Button cancel = findViewById(R.id.cancel_button);
        create.setOnClickListener(listener);
        create.setVisibility(View.VISIBLE);
        start.setOnClickListener(listener);
        cancel.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.create_button:
                    counterAsyncTask = new CounterAsyncTask();
                    showToast(R.string.asynctask_created);
                    break;
                case R.id.start_button:
                    try {
                        counterAsyncTask.execute();
                    } catch (IllegalStateException | NullPointerException e) {
                        showToast(R.string.null_pointer_text);
                    }
                    break;
                case R.id.cancel_button:
                    try {
                        counterAsyncTask.cancel(true);
                        timeCounterText.setText(getText(R.string.start_value));
                        showToast(R.string.asynctask_cancelled);
                    } catch (NullPointerException e) {
                        showToast(R.string.null_pointer_text);
                    }
                    break;
            }
        }
    };


    private class CounterAsyncTask extends AsyncTask<Void, Integer, Void>  implements IAsyncTaskEvents {

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

    private void showToast(int textId) {
        Toast.makeText(getApplicationContext(), getText(textId), Toast.LENGTH_SHORT).show();
    }


}
