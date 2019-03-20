package asus.example.com.exercise4;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class AsyncTaskActivity extends AppCompatActivity {

    private TextView tCounter;
    private AsyncTaskClass asyncTaskClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        tCounter = findViewById(R.id.counter);
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
            switch (v.getId()){
                case R.id.create_button:
                    asyncTaskClass = new AsyncTaskClass();
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getResources().getText(R.string.asynctask_created)
                            , Toast.LENGTH_SHORT).show();
                    break;
                case R.id.start_button:
                    try {
                        asyncTaskClass.execute();
                    }catch (IllegalStateException | NullPointerException e){
                        Toast.makeText(getApplicationContext(),
                                getApplicationContext().getResources()
                                        .getText(R.string.null_pointer_text), Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case R.id.cancel_button:
                    try {
                        asyncTaskClass.cancel(true);
                        tCounter.setText(getApplicationContext().getResources()
                                .getText(R.string.start_value));
                        Toast.makeText(getApplicationContext(), getApplicationContext()
                                        .getResources().getText(R.string.asynctask_cancelled)
                                , Toast.LENGTH_SHORT).show();
                    }catch (NullPointerException e){
                        Toast.makeText(getApplicationContext(),
                                getApplicationContext().getResources()
                                        .getText(R.string.null_pointer_text), Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
            }
        }
    };

    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskClass extends AsyncTask<Void, Void, Void>{

        final String finishText = "Done!";

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i<getApplicationContext().getResources().getInteger(R.integer.amount);i++) {
                try {
                    tCounter.setText(String.valueOf(i));
                    TimeUnit.MILLISECONDS.sleep(getApplicationContext().getResources()
                            .getInteger(R.integer.timeout));
                    if (isCancelled()){
                        return null;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            tCounter.setText(finishText);
        }

        @Override
        protected void onCancelled(){
            super.onCancelled();
        }
    }


}
