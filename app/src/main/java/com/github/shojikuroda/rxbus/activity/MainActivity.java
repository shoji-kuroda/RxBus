package com.github.shojikuroda.rxbus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.shojikuroda.rxbus.R;
import com.github.shojikuroda.rxbus.components.RxBus;
import com.github.shojikuroda.rxbus.components.Subscribe;
import com.github.shojikuroda.rxbus.events.SampleEvent;
import com.github.shojikuroda.rxbus.fragment.ProducerFragment;
import com.github.shojikuroda.rxbus.fragment.SubscriberFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_subscriber, SubscriberFragment.newInstance(), "Subscriber")
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_producer, ProducerFragment.newInstance(), "Producer")
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RxBus.get().register(this);
    }

    @Subscribe
    public void onSubscribeEvent(SampleEvent event) {
        Log.d(TAG, "Say " + event.message);
    }
}
