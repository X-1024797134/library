package com.bus.live.demo.event;

import android.os.Message;
import androidx.lifecycle.BusLiveData;

public interface IEvent {
    BusLiveData<Message> getMessage();
}
