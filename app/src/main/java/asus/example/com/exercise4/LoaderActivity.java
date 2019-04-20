package asus.example.com.exercise4;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.widget.Toast;


public class LoaderActivity extends AbstractBaseActivity implements LoaderManager.LoaderCallbacks {

    private Loader loader;

    @Override
    public void onButtonCreateClick() {

    }

    @Override
    public void onButtonStartClick() {
        loader = getSupportLoaderManager().initLoader(0, null,
                LoaderActivity.this);
        loader.forceLoad();

    }

    @Override
    public void onButtonCancelClick() {
        try {
            loader.cancelLoad();
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), R.string.null_pointer_text_start, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isCreateButtonVisible() {
        return false;
    }


    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int i, @Nullable Bundle bundle) {
        ILoaderEvents iLoaderEvents = new ILoaderEvents() {
            @Override
            public void onPreExecute() {
                timeCounterText.setText(R.string.start_value);
            }

            @Override
            public void onLoaderProgressEvent(int i) {
                timeCounterText.setText(Integer.toString(i));
            }

            @Override
            public void onPostExecute() {
                timeCounterText.setText(R.string.done);
            }
        };
        return new MyAsyncTaskLoader(LoaderActivity.this, iLoaderEvents);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {
        timeCounterText.setText(getApplicationContext().getResources().getText(R.string.done));
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        timeCounterText.setText(R.string.start_value);
    }


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
