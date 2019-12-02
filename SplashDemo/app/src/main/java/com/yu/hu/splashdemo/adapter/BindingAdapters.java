package com.yu.hu.splashdemo.adapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * Created by Hy on 2019/12/02 16:55
 **/
public class BindingAdapters {


    @BindingAdapter("url")
    public static void setImgUrl(ImageView imageView, String url) {
        if (url != null) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }

}
