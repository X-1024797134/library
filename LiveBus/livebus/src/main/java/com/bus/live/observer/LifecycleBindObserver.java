package com.bus.live.observer;

import androidx.lifecycle.*;
import com.bus.live.util.Dog;

public final class LifecycleBindObserver<T> extends BaseObserver<T> implements LifecycleObserver {

    private static final String TAG = LifecycleBindObserver.class.getSimpleName();

    private final LifecycleOwner mLifecycleOwner;

    public LifecycleBindObserver(Observer<T> observer, BusLiveData<T> liveData, LifecycleOwner lifecycleOwner) {
        super(observer, liveData);
        mLifecycleOwner = lifecycleOwner;
    }

    @Override
    public boolean isAttachedTo(LifecycleOwner owner) {
        return owner == mLifecycleOwner;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Dog.i(TAG, "onDestroy");
        mLiveData.removeObserver(mObserver);
        mLifecycleOwner.getLifecycle().removeObserver(this);
    }
}
