package asus.example.com.exercise4;

public interface IAsyncTaskEvents {
    void onPostExecute();

    void onProgressUpdate(Integer integer);

    void onPreExecute();
}
