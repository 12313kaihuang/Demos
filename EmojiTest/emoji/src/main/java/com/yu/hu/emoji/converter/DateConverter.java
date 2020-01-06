package com.yu.hu.emoji.converter;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by Hy on 2019/12/30 11:12
 * <p>
 * 日期转换器
 * room无法直接存储{@link java.util.Date}类型数据，所以需要进行一个转换
 **/
public class DateConverter {

    @TypeConverter
    public static Date revertDate(long time) {
        return time == 0 ? null : new Date(time);
    }

    @TypeConverter
    public static long converterDate(@Nullable Date date) {
        return date == null ? 0 : date.getTime();
    }
}
