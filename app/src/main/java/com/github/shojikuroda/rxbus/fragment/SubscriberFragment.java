package com.github.shojikuroda.rxbus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.shojikuroda.rxbus.components.RxBus;
import com.github.shojikuroda.rxbus.components.Subscribe;
import com.github.shojikuroda.rxbus.databinding.FragmentSubscriberBinding;
import com.github.shojikuroda.rxbus.events.SampleEvent;

/**
 * Created by shoji.kuroda on 2016/10/14.
 */
public class SubscriberFragment extends Fragment {

    private FragmentSubscriberBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentSubscriberBinding.inflate(inflater, container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        RxBus.get().register(this);
    }

    public static SubscriberFragment newInstance() {
        Bundle args = new Bundle();
        SubscriberFragment fragment = new SubscriberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe
    public void onSubscribeEvent(SampleEvent event) {
        this.binding.subscribe.setText(event.message);
    }
}
