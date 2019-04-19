package asus.example.com.exercise4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class LoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private TextView counter;
    private Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        Button startButton = findViewById(R.id.start_button);
        Button cancelButton = findViewById(R.id.cancel_button);
        startButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);
        counter = findViewById(R.id.counter);
        loader = getSupportLoaderManager().initLoader(0, null,
                LoaderActivity.this);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start_button:
                    loader.forceLoad();
                    break;
                case R.id.cancel_button:
                    loader.cancelLoad();
                    break;
            }
        }
    };




    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int i, @Nullable Bundle bundle) {
        ILoaderEvents iLoaderEvents = new ILoaderEvents() {
            @Override
            public void onPreExecute() {
                counter.setText(R.string.start_value);
            }

            @Override
            public void onLoaderProgressEvent(int i) {
                counter.setText(i+"");
            }

            @Override
            public void onPostExecute() {
                counter.setText(R.string.done);
            }
        };
        return new MyAsyncTaskLoader(LoaderActivity.this, iLoaderEvents);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {
        counter.setText(getApplicationContext().getResources().getText(R.string.done));
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }


    @SuppressLint("StaticFieldLeak")
    public static class MyAsyncTaskLoader extends AsyncTaskLoader<Void> {

        private ILoaderEvents iLoaderEvents;

        MyAsyncTaskLoader(@NonNull Context context, ILoaderEvents iLoaderEvents) {
            super(context);
            this.iLoaderEvents = iLoaderEvents;
        }

        @Nullable
        @Override
        public Void loadInBackground() {
            iLoaderEvents.onPreExecute();
            for (int i = 0; i < Constants.AMOUNT; i++) {
                if (isLoadInBackgroundCanceled()) {
                    return null;
                }
                iLoaderEvents.onLoaderProgressEvent(i);
                SystemClock.sleep(Constants.TIMEOUT);
            }
            iLoaderEvents.onPostExecute();
            return null;
        }
    }


}
