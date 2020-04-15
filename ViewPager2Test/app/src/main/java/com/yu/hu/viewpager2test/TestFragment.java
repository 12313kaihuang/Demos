package com.yu.hu.viewpager2test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author Hy
 * created on 2020/04/15 21:37
 **/
public class TestFragment extends Fragment {

    private View rootView;

    public static TestFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_test, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textView = rootView.findViewById(R.id.text_view);
        String text = getArguments() != null ? getArguments().getString("text") : null;
        textView.setText(text);
    }
}
