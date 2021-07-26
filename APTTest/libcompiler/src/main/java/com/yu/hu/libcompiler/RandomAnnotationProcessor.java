package com.yu.hu.libcompiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.yu.hu.libannotation.RandomInt;
import com.yu.hu.libannotation.RandomString;
import com.yu.hu.libannotation.RandomUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RandomAnnotationProcessor extends AbstractProcessor {


    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //日志打印，在java环境下不能使用android.util.log.e()
        this.messager = processingEnvironment.getMessager();
        //文件处理工具
        this.filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE, "RandomAnnotationProcessor 解析开始");

        Map<TypeElement, List<RandomConfig>> map = new HashMap<>();
        Set<? extends Element> randomIntSet = roundEnvironment.getElementsAnnotatedWith(RandomInt.class);
        Set<? extends Element> randomStrSet = roundEnvironment.getElementsAnnotatedWith(RandomString.class);
        for (Element element : randomIntSet) parseRandomElement(element, RandomInt.class, map);
        for (Element element : randomStrSet) parseRandomElement(element, RandomString.class, map);

        for (Map.Entry<TypeElement, List<RandomConfig>> entry : map.entrySet()) {
            TypeElement typeElement = entry.getKey();
            List<RandomConfig> randomConfigs = entry.getValue();
            generateAssistantFile(typeElement, randomConfigs);
        }
        return true;
    }

    private void generateAssistantFile(TypeElement typeElement, List<RandomConfig> randomConfigs) {
        String targetName = typeElement.getSimpleName().toString() + RandomUtils.CLASS_SUFFIX;
        String targetPkg = getPkg(typeElement);
        try {
            TypeSpec typeSpec = TypeSpec.classBuilder(targetName)
//                    .addJavadoc("Generated by the $L, please do not modify", RandomAnnotationProcessor.class.getSimpleName())
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(generateConstructor(typeElement, randomConfigs))
                    .build();
            JavaFile file = JavaFile.builder(targetPkg, typeSpec)
                    .addFileComment("Generated by the $L, please do not modify", RandomAnnotationProcessor.class.getSimpleName())
                    .build();
            messager.printMessage(Diagnostic.Kind.NOTE, "写文件： " + targetPkg + "，" + targetName);
            file.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.ERROR, "写入文件失败 " + e.getCause());
        }
    }

    //生成构造方法
    private MethodSpec generateConstructor(TypeElement typeElement, List<RandomConfig> randomConfigs) {
        TypeName typeName = TypeName.get(typeElement.asType()); //注意这里
        messager.printMessage(Diagnostic.Kind.NOTE, "typeName:" + typeName.toString());

        MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(typeName, "source", Modifier.FINAL);
        for (RandomConfig config : randomConfigs) {
            if (config instanceof IntRandomConfig) {
                IntRandomConfig intRandomConfig = (IntRandomConfig) config;
                methodBuilder.addStatement("source.$L = $T.randomInt($L, $L)",
                        intRandomConfig.fieldName, RandomUtils.class,
                        intRandomConfig.minValue, intRandomConfig.maxValue);
            } else if (config instanceof StringRandomConfig) {
                StringRandomConfig stringRandomConfig = (StringRandomConfig) config;
                methodBuilder.addStatement("source.$L = $T.randomString($L)",
                        stringRandomConfig.fieldName, RandomUtils.class, stringRandomConfig.length);
            }
        }
        return methodBuilder.build();
    }

    private String getPkg(TypeElement typeElement) {
        //获取全类名 包名+类名
        String qualifiedName = typeElement.getQualifiedName().toString();
        int lastPointIndex = qualifiedName.lastIndexOf(".");
        return qualifiedName.substring(0, lastPointIndex);
    }

    /**
     * TypeElement 表示一个类或接口程序元素
     * VariableElement 表示一个字段、enum 常量、方法或构造方法参数、局部变量或异常参数。
     * <p>
     * 还有一些合规性监测，这里就先不弄了
     */
    private void parseRandomElement(Element element, Class<? extends Annotation> annotationClass, Map<TypeElement, List<RandomConfig>> map) {
        //这里传进来的是属性对应VariableElement，需要拿到类对应element
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        map.putIfAbsent(enclosingElement, new ArrayList<>());
        List<RandomConfig> randomConfigs = map.get(enclosingElement);

        String fieldName = element.getSimpleName().toString();
        if (element instanceof RandomInt) {
            messager.printMessage(Diagnostic.Kind.NOTE, "is randomInt");
        }

        RandomConfig randomConfig = null;
        Annotation annotation = element.getAnnotation(annotationClass);
        if (annotation instanceof RandomInt) {
            RandomInt randomInt = (RandomInt) annotation;
            randomConfig = new IntRandomConfig(randomInt.minValue(), randomInt.maxValue());
        } else if (annotation instanceof RandomString) {
            randomConfig = new StringRandomConfig(((RandomString) annotation).length());
        } else {
            messager.printMessage(Diagnostic.Kind.ERROR, "unexpected type " + annotationClass.getSimpleName());
        }
        randomConfig.fieldName = element.getSimpleName().toString();
        randomConfigs.add(randomConfig);

        Name simpleName = enclosingElement.getSimpleName();  //获取类名
        messager.printMessage(Diagnostic.Kind.NOTE, "解析属性:"
                + annotationClass.getSimpleName() + ":" + fieldName + " to " + simpleName.toString());
    }

    /**
     * 返回支持的注解类型
     * <p>
     * 也可以通过@SupportedAnnotationTypes完成
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> res = new HashSet<>();
        List<Class<? extends Annotation>> supports = Arrays.asList(RandomInt.class, RandomString.class);
        for (Class<? extends Annotation> anoCls : supports) {
            res.add(anoCls.getCanonicalName());
        }
        return res;
    }

    private static class RandomConfig {
        String fieldName;
    }

    private static class IntRandomConfig extends RandomConfig {
        int minValue;
        int maxValue;

        public IntRandomConfig(int minValue, int maxValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
        }
    }

    private static class StringRandomConfig extends RandomConfig {
        int length;

        public StringRandomConfig(int length) {
            this.length = length;
        }
    }

}
