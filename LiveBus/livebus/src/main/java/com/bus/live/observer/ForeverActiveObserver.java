package com.bus.live.observer;

import androidx.lifecycle.BusLiveData;
import androidx.lifecycle.Observer;

public final class ForeverActiveObserver<T> extends BaseObserver<T> {

    public ForeverActiveObserver(Observer<T> observer, BusLiveData<T> liveData) {
        super(observer, liveData);
    }
}
