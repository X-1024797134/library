package com.bus.live;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public interface ILiveBus {

    <T> void observe(@NonNull String event, @NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer);

    <T> void observeForever(@NonNull String event, @NonNull Observer<? super T> observer);

    <T> void observeSticky(@NonNull String event, @NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer);

    <T> void observeStickyForever(@NonNull String event, @NonNull Observer<? super T> observer);

    <T> void removeObserver(@NonNull String event, @NonNull Observer<? super T> observer);

    void removeObservers(@NonNull String event, @NonNull LifecycleOwner owner);

    <T> void postValue(@NonNull String event, @NonNull T value);

    void removeBus(@NonNull String event);

    <E> E of(Class<E> eClass);
}
