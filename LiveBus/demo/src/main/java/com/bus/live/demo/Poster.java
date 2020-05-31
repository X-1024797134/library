package com.bus.live.demo;

import android.os.Message;
import com.bus.live.LiveBus;
import com.bus.live.demo.event.IEvent;
import com.bus.live.util.Dog;

public class Poster {

    private static final String TAG = "Demo";

    private static final class SingleHolder {
        private static final Poster INSTANCE = new Poster();
    }

    private Poster() {}

    public static Poster getInstance() {
        return Poster.SingleHolder.INSTANCE;
    }

    public <T> void send(String event, T value) {
        Dog.i(TAG, "发送 " + event);
        LiveBus.getDefault().postValue(event, value);
    }

    public void sendMessage() {
        Dog.i(TAG, "发送 自定义事件");
        LiveBus.getDefault().of(IEvent.class).getMessage().postValue(new Message());
    }
}
