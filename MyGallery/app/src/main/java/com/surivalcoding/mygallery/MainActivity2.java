package com.surivalcoding.mygallery;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 오래걸리는
                // ...

                // UI 업데이트
                runOnUiThread(updateUI());
                Log.d("MainActivity2", "run: ");
            }
        });

        thread.start();

        runOnUiThread(() -> {
            // UI
            updateUI().run();
        });
    }


    Runnable updateUI() {
        // UI
        return null;
    }
}