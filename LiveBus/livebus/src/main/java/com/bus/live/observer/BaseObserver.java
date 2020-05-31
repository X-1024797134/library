package com.bus.live.observer;

import androidx.lifecycle.BusLiveData;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.bus.live.util.Dog;

public abstract class BaseObserver<T> implements Observer<T> {

    private static final String TAG = BaseObserver.class.getSimpleName();

    protected final Observer<T> mObserver;
    protected final BusLiveData<T> mLiveData;

    private int mLastVersion;

    public BaseObserver(Observer<T> observer, BusLiveData<T> liveData) {
        mObserver = observer;
        mLiveData = liveData;
        mLastVersion = liveData.getVersion();
    }

    @Override
    public void onChanged(T t) {
        Dog.i(TAG, "onChanged : mLastVersion = " + mLastVersion + " , getVersion = " + mLiveData.getVersion());
        if (mLastVersion >= mLiveData.getVersion())
            return;
        mLastVersion = mLiveData.getVersion();
        mObserver.onChanged(t);
    }

    public boolean isAttachedTo(LifecycleOwner owner) {
        return false;
    }
}
