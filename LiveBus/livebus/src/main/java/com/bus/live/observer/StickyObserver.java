package com.bus.live.observer;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public interface StickyObserver<T> {

    void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer);

    void observeStickyForever(@NonNull Observer<? super T> observer);
}
