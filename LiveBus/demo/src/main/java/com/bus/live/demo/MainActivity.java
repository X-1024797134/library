package com.bus.live.demo;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bus.live.demo.event.Event;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observer.getInstance().observe(MainActivity.this);
                Poster.getInstance().send(Event.EVENT1, "EVENT1");
                Poster.getInstance().send(Event.EVENT3, "EVENT3");
                Observer.getInstance().observeSticky(MainActivity.this);
            }
        });

        findViewById(R.id.btn_send_interface).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observer.getInstance().observeInterface(MainActivity.this);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Poster.getInstance().sendMessage();
                    }
                }.start();
            }
        });

        findViewById(R.id.btn_send_forever).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Observer.getInstance().observeForever();
        Poster.getInstance().send(Event.EVENT2, "EVENT2");
        Poster.getInstance().send(Event.EVENT4, "EVENT4");
        Observer.getInstance().observeStickyForever();
    }
}
