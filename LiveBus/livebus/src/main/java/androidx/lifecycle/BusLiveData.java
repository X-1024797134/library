package androidx.lifecycle;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import com.bus.live.LiveBus;
import com.bus.live.observer.BaseObserver;
import com.bus.live.observer.ForeverActiveObserver;
import com.bus.live.observer.LifecycleBindObserver;
import com.bus.live.observer.StickyObserver;
import com.bus.live.util.Dog;

import java.util.HashMap;
import java.util.Map;

public final class BusLiveData<T> extends LiveData<T> implements StickyObserver<T> {

    private static final String TAG = BusLiveData.class.getSimpleName();

    private String mEvent;
    private Handler mUIHandler;
    private Map<Observer<? super T>, Observer<T>> mObservers = new HashMap<>();

    public BusLiveData(String event) {
        mEvent = event;
    }

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        Observer<T> existing = mObservers.get(observer);
        Dog.i(TAG, "observe : existing " + (existing == null ? "is null" : "not null"));
        if (existing == null) {
            existing = new LifecycleBindObserver(observer, this, owner);
            mObservers.put(observer, existing);
            owner.getLifecycle().addObserver((LifecycleObserver) existing);
        }
        super.observe(owner, existing);
    }

    @MainThread
    @Override
    public void observeForever(@NonNull Observer<? super T> observer) {
        Observer<T> existing = mObservers.get(observer);
        Dog.i(TAG, "observeForever : existing " + (existing == null ? "is null" : "not null"));
        if (existing == null) {
            existing = new ForeverActiveObserver(observer, this);
            mObservers.put(observer, existing);
        }
        super.observeForever(existing);
    }

    @MainThread
    @Override
    public void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        Dog.i(TAG, "observeSticky");
        super.observe(owner, observer);
    }

    @MainThread
    @Override
    public void observeStickyForever(@NonNull Observer<? super T> observer) {
        Dog.i(TAG, "observeStickyForever");
        super.observeForever(observer);
    }

    @MainThread
    @Override
    public void removeObserver(@NonNull Observer<? super T> observer) {
        Observer<T> removed = mObservers.remove(observer);
        Dog.i(TAG, "removeObserver : removed " + (removed == null ? "is null" : "not null"));
        if (removed == null) super.removeObserver(observer);
        else super.removeObserver(removed);
    }

    @MainThread
    @Override
    public void removeObservers(@NonNull LifecycleOwner owner) {
        for (Map.Entry<Observer<? super T>, Observer<T>> entry : mObservers.entrySet()) {
            boolean isAttachedTo = ((BaseObserver<T>) entry.getValue()).isAttachedTo(owner);
            Dog.i(TAG, "removeObservers : isAttachedTo = " + isAttachedTo);
            if (isAttachedTo) mObservers.remove(entry.getKey());
        }
        super.removeObservers(owner);
    }

    @MainThread
    @Override
    protected void onInactive() {
        Dog.i(TAG, "onInactive : hasObservers = " + hasObservers());
        if (!hasObservers()) LiveBus.getDefault().removeBus(mEvent);
        super.onInactive();
    }

    @MainThread
    @Override
    public int getVersion() {
        return super.getVersion();
    }

    @Override
    public void postValue(final T value) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            setValue(value);
            return;
        }
        if (mUIHandler == null) mUIHandler = new Handler(Looper.getMainLooper());
        mUIHandler.post(new PostValueRunnable(value));
    }

    private class PostValueRunnable implements Runnable {

        private T mValue;

        public PostValueRunnable(T value) {
            mValue = value;
        }

        @Override
        public void run() {
            Dog.i(TAG, "PostValueRunnable : setValue");
            setValue(mValue);
        }
    }
}
