package com.bus.live.demo;

import android.os.Message;
import androidx.lifecycle.LifecycleOwner;
import com.bus.live.LiveBus;
import com.bus.live.demo.event.Event;
import com.bus.live.demo.event.IEvent;
import com.bus.live.util.Dog;

public class Observer {

    private static final String TAG = "Demo";

    private static final class SingleHolder {
        private static final Observer INSTANCE = new Observer();
    }

    private Observer() {

    }

    public static Observer getInstance() {
        return SingleHolder.INSTANCE;
    }

    private int mObserve;
    private int mObserveForever;
    private int mObserveSticky;
    private int mObserveStickyForever;
    private int mObserveInterface;

    public <T> void observe(LifecycleOwner owner) {
        mObserve ++;
        Dog.i(TAG, "注册 第 " + mObserve + " 个 " + Event.EVENT1 + " observe");
        LiveBus.getDefault().observe(Event.EVENT1, owner, new androidx.lifecycle.Observer<T>() {
            @Override
            public void onChanged(T value) {
                Dog.i(TAG, "接收到 " + Event.EVENT1 + " value = " + value);
            }
        });
    }

    public <T> void observeForever() {
        mObserveForever ++;
        Dog.i(TAG, "注册 第 " + mObserveForever + " 个 " + Event.EVENT2 + " observeForever");
        LiveBus.getDefault().observeForever(Event.EVENT2, new androidx.lifecycle.Observer<T>() {
            @Override
            public void onChanged(T value) {
                Dog.i(TAG, "接收到 " + Event.EVENT2 + " value = " + value);
            }
        });
    }

    public <T> void observeSticky(LifecycleOwner owner) {
        mObserveSticky ++;
        Dog.i(TAG, "注册 第 " + mObserveSticky + " 个 " + Event.EVENT3 + " observeSticky");
        LiveBus.getDefault().observeSticky(Event.EVENT3, owner, new androidx.lifecycle.Observer<T>() {
            @Override
            public void onChanged(T value) {
                Dog.i(TAG, "接收到 " + Event.EVENT3 + " value = " + value);
            }
        });
    }

    public <T> void observeStickyForever() {
        mObserveStickyForever ++;
        Dog.i(TAG, "注册 第 " + mObserveStickyForever + " 个 " + Event.EVENT4 + " observeStickyForever");
        LiveBus.getDefault().observeStickyForever(Event.EVENT4, new androidx.lifecycle.Observer<T>() {
            @Override
            public void onChanged(T value) {
                Dog.i(TAG, "接收到 " + Event.EVENT4 + " value = " + value);
            }
        });
    }

    public void observeInterface(LifecycleOwner owner) {
        mObserveInterface ++;
        Dog.i(TAG, "注册 第 " + mObserveInterface + " 个 自定义事件 observeInterface");
        LiveBus.getDefault().of(IEvent.class).getMessage().observeSticky(owner, new androidx.lifecycle.Observer<Message>() {
            @Override
            public void onChanged(Message message) {
                Dog.i(TAG, "接收到 自定义事件 message = " + message.toString());
            }
        });
    }
}
