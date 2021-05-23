package com.example.asynctaskexample;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class ProgressAsyncActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_async);
        btn = findViewById(R.id.btn3);
        ProgressBar pb = findViewById(R.id.pb);
        AsyncTask<Void, Integer, Void> task = new TimerTask(pb).execute();
        btn.setOnClickListener(view -> {
            Toast.makeText(this, "well done", Toast.LENGTH_SHORT).show();
            task.cancel(true);
        });

    }

    class TimerTask extends AsyncTask<Void, Integer, Void> {

        private final WeakReference<ProgressBar> pbRef;

        public TimerTask(ProgressBar pb) {
            pbRef = new WeakReference<>(pb);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(100);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pbRef.get().setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            new AlertDialog.Builder(ProgressAsyncActivity.this)
                    .setTitle("Times Up")
                    .setMessage("you loose, your time is over!")
                    .setPositiveButton("Restart", (dialogInterface, i) -> {
                    })
                    .create()
                    .show();
        }
    }
}