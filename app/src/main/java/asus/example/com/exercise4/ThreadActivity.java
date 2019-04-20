package asus.example.com.exercise4;

import android.annotation.SuppressLint;


public class ThreadActivity extends AbstractBaseActivity {

    private Thread counterThread;

    @Override
    public void onButtonCreateClick() {
        counterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i;
                for (i = 0; i < Constants.AMOUNT; i++) {
                    final int finalI = i;
                    timeCounterText.post(new Runnable() {
                        @Override
                        public void run() {
                            timeCounterText.setText(String.valueOf(finalI));

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
                timeCounterText.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        timeCounterText.setText(getApplicationContext().getResources()
                                .getText(R.string.done));
                    }
                });
            }
        });
        HelpToast.showToast(R.string.thread_created, getApplicationContext());
    }

    @Override
    public void onButtonStartClick() {
        try {
            counterThread.start();
        } catch (IllegalThreadStateException | NullPointerException e) {
            HelpToast.showToast(R.string.null_pointer_text_create, getApplicationContext());

        }
    }

    @Override
    public void onButtonCancelClick() {
        try {
            counterThread.interrupt();
            timeCounterText.setText(getApplicationContext().getResources()
                    .getText(R.string.start_value));
            HelpToast.showToast(R.string.thread_cancelled, getApplicationContext());
        } catch (NullPointerException e) {
            HelpToast.showToast(R.string.null_pointer_text_create, getApplicationContext());
        }
    }

    @Override
    public boolean isCreateButtonVisible() {
        return true;
    }



}
