package com.github.shojikuroda.rxbus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.shojikuroda.rxbus.components.RxBus;
import com.github.shojikuroda.rxbus.databinding.FragmentProducerBinding;
import com.github.shojikuroda.rxbus.events.SampleEvent;

/**
 * Created by shoji.kuroda on 2016/10/14.
 */
public class ProducerFragment extends Fragment {

    private FragmentProducerBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentProducerBinding.inflate(inflater, container, false);
        this.binding.setHandler(this);
        return this.binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static ProducerFragment newInstance() {
        Bundle args = new Bundle();
        ProducerFragment fragment = new ProducerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onButtonClick(View view) {
        RxBus.get().post(new SampleEvent("HELLO"));
    }
}
