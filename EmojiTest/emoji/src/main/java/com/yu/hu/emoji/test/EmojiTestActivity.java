package com.yu.hu.emoji.test;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yu.hu.emoji.EmojiManager;
import com.yu.hu.emoji.R;
import com.yu.hu.emoji.databinding.ActivityEmojiTestBinding;
import com.yu.hu.emoji.entity.Emoji;

import java.util.List;

public class EmojiTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEmojiTestBinding viewDataBinding
                = DataBindingUtil.setContentView(this, R.layout.activity_emoji_test);

        viewDataBinding.setBtn.setOnClickListener(v -> {
            //viewDataBinding.emojiText.setEmojiText(viewDataBinding.emojiEditText.getText().toString();
            viewDataBinding.emojiText.setEmojiText("sadasaaa[q_cool\\tr]s[q_bz\\tr]sa[q_cool2\\tr]");
        });

        viewDataBinding.getBtn.setOnClickListener(v -> {
            CharSequence text = viewDataBinding.emojiText.getText();
            Toast.makeText(EmojiTestActivity.this, text, Toast.LENGTH_SHORT).show();
        });

        viewDataBinding.emoji1.setOnClickListener(v -> {

        });

        List<Emoji> allQQEmoji = EmojiManager.getAllQQEmoji();
        Log.d("hytest", "onCreate: size = " + allQQEmoji.size() + "," + allQQEmoji.get(0).emojiText);
        viewDataBinding.gridLayout.addEmojis(allQQEmoji);

        viewDataBinding.recycler.setEmojis(allQQEmoji);
    }
}
