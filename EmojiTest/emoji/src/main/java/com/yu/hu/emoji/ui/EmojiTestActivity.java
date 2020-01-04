package com.yu.hu.emoji.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.yu.hu.emoji.EmojiManager;
import com.yu.hu.emoji.R;
import com.yu.hu.emoji.databinding.ActivityEmojiTestBinding;
import com.yu.hu.emoji.entity.Emoji;
import com.yu.hu.emoji.vm.TestViewModel;

import java.util.List;

public class EmojiTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEmojiTestBinding viewDataBinding
                = DataBindingUtil.setContentView(this, R.layout.activity_emoji_test);
        TestViewModel viewModel = new ViewModelProvider(this).get(TestViewModel.class);
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.setViewModel(viewModel);

        viewDataBinding.getBtn.setOnClickListener(v -> {
            CharSequence text = viewDataBinding.emojiText.getText();
            Toast.makeText(EmojiTestActivity.this, text, Toast.LENGTH_SHORT).show();
        });

        viewDataBinding.getEmojiBtn.setOnClickListener(v -> {
            CharSequence text = viewDataBinding.emojiEditText.getText();
            Toast.makeText(EmojiTestActivity.this, text, Toast.LENGTH_SHORT).show();
        });

        List<Emoji> allQQEmoji = EmojiManager.getAllQQEmoji();
        //viewDataBinding.gridLayout.addEmojis(allQQEmoji);

        viewDataBinding.recycler.setEmojis(allQQEmoji);
        viewDataBinding.recycler.setOnItemClickListner(emoji -> {
            MutableLiveData<String> emojiText = viewModel.getEmojiText();
            String value = emojiText.getValue();
            viewModel.getEmojiText().postValue(value + emoji.emojiText);
            viewDataBinding.emojiEditText.addEmoji(emoji);
        });
    }
}
