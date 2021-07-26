package com.yu.hu.libannotation;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class RandomUtils {

    public static final String CLASS_SUFFIX = "_Random";

    private static final Random sRandom = new Random(System.currentTimeMillis());

    //这里传入的是Object
    public static void inject(Object source) {
        try {
            Class<?> sourceClass = source.getClass();
            //注意这里需要加包名 否则会找不到类
            Class<?> targetClass = sourceClass.getClassLoader()
                    .loadClass(sourceClass.getCanonicalName() + CLASS_SUFFIX);
            Constructor<?> constructor = targetClass.getConstructor(sourceClass);
            constructor.newInstance(source);
        } catch (ClassNotFoundException | NoSuchMethodException
                | IllegalAccessException | InstantiationException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static int randomInt(int min, int max) {
        if (min > max) return 0;
        return sRandom.nextInt(max - min + 1) + min;
    }

    public static String randomString(int length) {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            int r = sRandom.nextInt(3);
            if (r == 0) chars[i] = (char) randomInt('a', 'z');
            if (r == 1) chars[i] = (char) randomInt('A', 'Z');
            if (r == 2) chars[i] = (char) randomInt('0', '9');
        }
        return String.valueOf(chars);
    }
}
