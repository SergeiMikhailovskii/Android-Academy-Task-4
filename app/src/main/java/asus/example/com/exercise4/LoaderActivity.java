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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    @SuppressLint("StaticFieldLeak")
    private TextView counter;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        Button startButton = findViewById(R.id.start_button);
        Button cancelButton = findViewById(R.id.cancel_button);
        startButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);
        counter = findViewById(R.id.counter);
        EventBus.getDefault().register(this);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.start_button:
                    Loader loader = getSupportLoaderManager().initLoader(0, null, LoaderActivity.this);
                    loader.forceLoad();
                    Log.i(TAG, "Button start clicked");
                    break;
                case R.id.cancel_button:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoaderProgressEvent(MyAsyncTaskLoader.ProgressEvent event){
        counter.setText(""+event.getNumber());
        Log.i(TAG, "Value = "+event.getNumber()+" setted");
    }

    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new MyAsyncTaskLoader(LoaderActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {
        counter.setText("Done!");
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }




    @SuppressLint("StaticFieldLeak")
    public static class MyAsyncTaskLoader extends AsyncTaskLoader<Void> {

        public static class ProgressEvent{
            private final int number;

            ProgressEvent(int number){
                this.number = number;
            }

            public int getNumber(){
                return number;
            }

        }


        MyAsyncTaskLoader(@NonNull Context context) {
            super(context);
        }

        @Nullable
        @Override
        public Void loadInBackground() {
            for (int i = 0; i<10;i++){
                Log.i(getClass().getSimpleName(), "i = "+i+" in loadInBackground");
                EventBus.getDefault().post(new ProgressEvent(i));
                SystemClock.sleep(500);
            }
            return null;
        }
    }





}