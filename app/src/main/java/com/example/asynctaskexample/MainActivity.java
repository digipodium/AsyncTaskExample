package com.example.asynctaskexample;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btnStartTask);
        Button btn2 = findViewById(R.id.btnNext);
        text1 = findViewById(R.id.text1);
        btn.setOnClickListener(view -> {
            new SimpleAsyncTask(text1).execute();
        });
        if (savedInstanceState != null) {
            text1.setText(savedInstanceState.getString("text"));
        }

        btn2.setOnClickListener(view -> {
            startActivity(new Intent(this, ProgressAsyncActivity.class));
        });


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", text1.getText().toString());
    }

    class SimpleAsyncTask extends AsyncTask<Void, Void, String> {
        private WeakReference<TextView> mTextView;

        public SimpleAsyncTask(TextView tv) {
            mTextView = new WeakReference<>(tv);
        }

        @Override
        protected String doInBackground(Void... voids) {
            Random r = new Random();
            int n = r.nextInt(11);
            int s = n * 200;
            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Awake at last after sleeping for " + s + " ms!";
        }

        @Override
        protected void onPostExecute(String s) {
            mTextView.get().setText(s);
        }
    }
}