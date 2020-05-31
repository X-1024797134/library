package com.bus.live;

import androidx.annotation.NonNull;
import androidx.lifecycle.BusLiveData;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.bus.live.util.Dog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public final class LiveBus implements ILiveBus {

    private static final String TAG = LiveBus.class.getSimpleName();

    private Map<String, LiveData<?>> mBusies;

    private static final class SingleHolder {
        private static final LiveBus INSTANCE = new LiveBus();
    }

    private LiveBus() {
        if (mBusies == null)
            mBusies = new HashMap<>();
    }

    public static ILiveBus getDefault() {
        return SingleHolder.INSTANCE;
    }

    @Override
    public <T> void observe(@NonNull String event, @NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        Dog.i(TAG, "observe : event = " + event);
        get(event).observe(owner, (Observer<? super Object>) observer);
    }

    @Override
    public <T> void observeForever(@NonNull String event, @NonNull Observer<? super T> observer) {
        Dog.i(TAG, "observeForever : event = " + event);
        get(event).observeForever((Observer<? super Object>) observer);
    }

    @Override
    public <T> void observeSticky(@NonNull String event, @NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        Dog.i(TAG, "observeSticky : event = " + event);
        get(event).observeSticky(owner, (Observer<? super Object>) observer);
    }

    @Override
    public <T> void observeStickyForever(@NonNull String event, @NonNull Observer<? super T> observer) {
        Dog.i(TAG, "observeStickyForever : event = " + event);
        get(event).observeStickyForever((Observer<? super Object>) observer);
    }

    @Override
    public <T> void removeObserver(@NonNull String event, @NonNull Observer<? super T> observer) {
        Dog.i(TAG, "removeObserver : event = " + event);
        get(event).removeObserver((Observer<? super Object>) observer);
    }

    @Override
    public void removeObservers(@NonNull String event, @NonNull LifecycleOwner owner) {
        Dog.i(TAG, "removeObservers : event = " + event);
        get(event).removeObservers(owner);
    }

    @Override
    public <T> void postValue(@NonNull String event, @NonNull T value) {
        Dog.i(TAG, "postValue : event = " + event);
        get(event).postValue(value);
    }

    @Override
    public void removeBus(@NonNull String event) {
        Dog.i(TAG, "removeBus : event = " + event);
        remove(event);
    }

    @Override
    public <E> E of(final Class<E> eClass) {
        Dog.i(TAG, "of : eClass = " + eClass);
        if (!eClass.isInterface())
            throwException(eClass.getSimpleName() + " must be an interface class");
        if (0 == eClass.getDeclaredMethods().length)
            throwException("There is at least one function in the " + eClass.getSimpleName() + " interface class");
        return (E) Proxy.newProxyInstance(eClass.getClassLoader(), new Class[]{eClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                String returnType = method.getReturnType().getCanonicalName();
                if (!BusLiveData.class.getCanonicalName().equals(returnType))
                    throwException("The return value type must be BusLiveData class");
                String event = method.getDeclaringClass().getCanonicalName() + "." + method.getName();
                return get(event);
            }
        });
    }

    private <T> BusLiveData<T> get(String event) {
        Dog.i(TAG, "get : event = " + event);
        LiveData<?> liveData = mBusies.get(event);
        Dog.i(TAG, "get : liveData " + (liveData == null ? "is null" : "not null"));
        if (liveData == null) {
            liveData = new BusLiveData<T>(event);
            mBusies.put(event, liveData);
        }
        return (BusLiveData<T>) liveData;
    }

    private <T> BusLiveData<T> remove(String event) {
        Dog.i(TAG, "remove : event = " + event);
        LiveData<?> liveData = mBusies.remove(event);
        Dog.i(TAG, "remove : liveData " + (liveData == null ? "is null" : "not null"));
        if (liveData == null)
            return null;
        return (BusLiveData<T>) liveData;
    }

    private void throwException(String s) {
        throw new IllegalStateException(s);
    }
}
