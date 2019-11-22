package com.example.dialogfragmenttest.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dialogfragmenttest.databinding.DialogSimple2Binding;

/**
 * create by hy on 2019/11/21 22:52
 * <p>
 * 重写onCreateView
 */
public class SimpleDialog2 extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这里的Dialog只有一点大小
        DialogSimple2Binding inflate =
                DialogSimple2Binding.inflate(LayoutInflater.from(getContext()), container, false);
        return inflate.getRoot();
    }
}
