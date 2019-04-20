package asus.example.com.exercise4;

import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

public class AsyncTaskActivity extends AbstractBaseActivity {

    private CounterAsyncTask counterAsyncTask;


    @Override
    public void onButtonCreateClick() {
        IAsyncTaskEvents iAsyncTaskEvents = new IAsyncTaskEvents() {
            @Override
            public void onPostExecute() {
                timeCounterText.setText(R.string.done);
            }

            @Override
            public void onProgressUpdate(Integer integer) {
                timeCounterText.setText(integer.toString());
            }

            @Override
            public void onPreExecute() {
                timeCounterText.setText(R.string.start_value);
            }
        };
        counterAsyncTask = new CounterAsyncTask(iAsyncTaskEvents);
        HelpToast.showToast(R.string.asynctask_created, getApplicationContext());
    }

    @Override
    public void onButtonStartClick() {
        try {
            counterAsyncTask.execute();
        } catch (IllegalStateException | NullPointerException e) {
            HelpToast.showToast(R.string.null_pointer_text_create, getApplicationContext());
        }
    }

    @Override
    public void onButtonCancelClick() {
        try {
            counterAsyncTask.cancel(true);
            timeCounterText.setText(getText(R.string.start_value));
            HelpToast.showToast(R.string.asynctask_cancelled, getApplicationContext());
        } catch (NullPointerException e) {
            HelpToast.showToast(R.string.null_pointer_text_create, getApplicationContext());
        }
    }

    @Override
    public boolean isCreateButtonVisible() {
        return true;
    }


    private static class CounterAsyncTask extends AsyncTask<Void, Integer, Void> {

        private IAsyncTaskEvents iAsyncTaskEvents;

        CounterAsyncTask(IAsyncTaskEvents iAsyncTaskEvents) {
            this.iAsyncTaskEvents = iAsyncTaskEvents;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            iAsyncTaskEvents.onPreExecute();
            for (int i = 0; i < Constants.AMOUNT; i++) {
                try {
                    iAsyncTaskEvents.onProgressUpdate(i);
                    TimeUnit.MILLISECONDS.sleep(Constants.TIMEOUT);
                    if (isCancelled()) {
                        return null;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            iAsyncTaskEvents.onPostExecute();
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }
}
