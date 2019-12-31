package com.yu.hu.emoji.ui.vm;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.yu.hu.emoji.repository.EmojiRepository;

/**
 * Created by Hy on 2019/12/31 11:40
 **/
public class TestViewModel extends AndroidViewModel {

    private EmojiRepository emojiRepository;

    private MutableLiveData<String> emojiText;

    public TestViewModel(@NonNull Application application) {
        super(application);
        emojiText = new MutableLiveData<>();
        emojiRepository = EmojiRepository.getInstance(application);
    }

    public void setEmojiText() {
        emojiText.postValue("sadasaaa[q_cool\\tr]s[q_bz\\tr]sa[q_cool2\\tr]");
    }

    public MutableLiveData<String> getEmojiText() {
        return emojiText;
    }
}
