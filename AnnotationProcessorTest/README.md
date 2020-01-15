# AnnotationProcessor - 自定义注解处理器

最近在学习`Navigation`的时候，`Navigation`的菜单设置是直接写`xml`文件，于是想着能不能根据自定义注解动态生成`json`文件然后去解析定制底部导航栏。由于这些东西都是比较新的东西之前没怎么接触过，所以特地写个`demo`来记录一下。<br/>

这个`Demo`中以新建项目中的 **`Bottom Navigation Activity`** 模板为例，实现三个`Fragment`实现对应`json`文件的解析。<br/>


初始化环境
---
1. `app`下的`build.gradle`中统一**JDK**版本
   ```groovy
   android {
        ...
    
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    }

   ```
2. 创建一个新的`moudle`名为`libmyannotationprocessor`，记得选择类型的时候选择**Java Library**。
3. 统一**JDK**环境，将moudle的`build.gradle`中的`sourceCompatibility`和`targetCompatibility`改为`8`。
   ```groovy
   dependencies {
       implementation fileTree(dir: 'libs', include: ['*.jar'])
       ...
   }

   sourceCompatibility = "8"
   targetCompatibility = "8"
   ```
4. `build.gradle`中引入所需资源
    这里用到了[fastjson](https://github.com/alibaba/fastjson)用作json转换处理，[auto-service](https://github.com/google/auto)辅助实现注解处理器，最新版本可至git仓库中获取。
    ```groovy
    
    dependencies {
        implementation fileTree(dir: 'libs', include: ['*.jar'])
    
        //fastjson
        implementation 'com.alibaba:fastjson:1.2.59'
        
        //auto service
        implementation 'com.google.auto.service:auto-service:1.0-rc6'
        annotationProcessor 'com.google.auto.service:auto-service:1.0-rc6'
    }
    ```
    
自定义注解
---
先自定义两个注解分别用于给`Activity`和`Fragment`标记。
```java
//public @interface FragmentDestination {
@Target(ElementType.TYPE) //标记在类、接口上
public @interface FragmentDestination {

    String pageUrl();  //url

    boolean needLogin() default false;  //是否需要登录 默认false

    boolean asStarter() default false;  //是否是首页  默认false
}

``` 
```java
//ActivityDestination
@Target(ElementType.TYPE) //标记在类、接口上
public @interface ActivityDestination {

    String pageUrl();  //url

    boolean needLogin() default false;  //是否需要登录 默认false

    boolean asStarter() default false;  //是否是首页  默认false
}

``` 

自定义注解处理器
---
```java
/**
 * Created by Hy on 2020/01/15 21:27
 * <p>
 * SupportedSourceVersion  源码类型  也可通过{@link #getSupportedSourceVersion()}设置
 * SupportedAnnotationTypes 设定需要处理的注解 也可通过{@link #getSupportedAnnotationTypes()}设置
 **/
@SuppressWarnings("unused")
@AutoService(Processor.class)  //auto-service
@SupportedSourceVersion(SourceVersion.RELEASE_8)  //源码类型 1.8
@SupportedAnnotationTypes({"com.yu.hu.libmyannotationprocessor.annotation.ActivityDestination", "com.yu.hu.libmyannotationprocessor.annotation.FragmentDestination"})
public class NavProcessor extends AbstractProcessor {

    private static final String OUTPUT_FILE_NAME = "destination.json";

    private Messager messager; //使用日志打印

    private Filer filer;  //用于文件处理

    //该方法再编译期间会被注入一个ProcessingEnvironment对象，该对象包含了很多有用的工具类。
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //日志打印,在java环境下不能使用android.util.log.e()
        this.messager = processingEnvironment.getMessager();
        //文件处理工具
        this.filer = processingEnvironment.getFiler();
    }

    /**
     * 该方法将一轮一轮的遍历源代码
     *
     * @param set              该方法需要处理的注解类型
     * @param roundEnvironment 关于一轮遍历中提供给我们调用的信息.
     * @return 改轮注解是否处理完成 true 下轮或者其他的注解处理器将不会接收到次类型的注解.用处不大.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //通过处理器环境上下文roundEnv分别获取 项目中标记的FragmentDestination.class 和ActivityDestination.class注解。
        //此目的就是为了收集项目中哪些类 被注解标记了
        Set<? extends Element> fragmentElements = roundEnvironment.getElementsAnnotatedWith(FragmentDestination.class);
        Set<? extends Element> activityElements = roundEnvironment.getElementsAnnotatedWith(ActivityDestination.class);

        if (!fragmentElements.isEmpty() || !activityElements.isEmpty()) {
            HashMap<String, JSONObject> destMap = new HashMap<>();
            //分别 处理FragmentDestination  和 ActivityDestination 注解类型
            //并收集到destMap 这个map中。以此就能记录下所有的页面信息了
            handleDestination(fragmentElements, FragmentDestination.class, destMap);
            handleDestination(activityElements, ActivityDestination.class, destMap);

            // app/src/assets
            FileOutputStream fos = null;
            OutputStreamWriter writer = null;
            try {
                //filer.createResource()意思是创建源文件
                //我们可以指定为class文件输出的地方，
                //StandardLocation.CLASS_OUTPUT：java文件生成class文件的位置，/app/build/intermediates/javac/debug/classes/目录下
                //StandardLocation.SOURCE_OUTPUT：java文件的位置，一般在/ppjoke/app/build/generated/source/apt/目录下
                //StandardLocation.CLASS_PATH 和 StandardLocation.SOURCE_PATH用的不多，指的了这个参数，就要指定生成文件的pkg包名了
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_NAME);
                String resourcePath = resource.toUri().getPath();
                messager.printMessage(Diagnostic.Kind.NOTE, "resourcePath: " + resourcePath);

                //由于我们想要把json文件生成在app/src/main/assets/目录下,所以这里可以对字符串做一个截取，
                //以此便能准确获取项目在每个电脑上的 /app/src/main/assets/的路径
                String appPath = resourcePath.substring(0, resourcePath.indexOf("app") + 4);
                String assetsPath = appPath + "src/main/assets";

                File file = new File(assetsPath);
                if (!file.exists()) {
                    file.mkdir();
                }

                //写入文件
                File outputFile = new File(file, OUTPUT_FILE_NAME);
                if (outputFile.exists()) {
                    outputFile.delete();
                }
                outputFile.createNewFile();

                //利用fastjson把收集到的所有的页面信息 转换成JSON格式的。并输出到文件中
                String content = JSON.toJSONString(destMap);
                fos = new FileOutputStream(outputFile);
                writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                writer.write(content);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return true;
    }

    private void handleDestination(Set<? extends Element> elements, Class<? extends Annotation> annotationClass, HashMap<String, JSONObject> destMap) {
        for (Element element : elements) {
            //TypeElement是Element的一种。
            //如果我们的注解标记在了类名上。所以可以直接强转一下。使用它得到全类名
            TypeElement typeElement = (TypeElement) element;
            //全类名
            String className = typeElement.getQualifiedName().toString();
            //页面的id.此处不能重复,使用页面的类名做hascode即可
            int id = Math.abs(className.hashCode());
            //是否需要登录
            boolean needLogin = false;
            //页面的pageUrl相当于隐式跳转意图中的host://schem/path格式
            String pageUrl = null;
            //是否作为首页的第一个展示的页面
            boolean asStarter = false;
            //标记该页面是fragment 还是activity类型的
            boolean isFragment = false;

            Annotation annotation = typeElement.getAnnotation(annotationClass);
            if (annotation instanceof FragmentDestination) {
                FragmentDestination dest = (FragmentDestination) annotation;
                pageUrl = dest.pageUrl();
                needLogin = dest.needLogin();
                asStarter = dest.asStarter();
                isFragment = true;
            } else if (annotation instanceof ActivityDestination) {
                ActivityDestination dest = (ActivityDestination) annotation;
                pageUrl = dest.pageUrl();
                needLogin = dest.needLogin();
                asStarter = dest.asStarter();
            }

            if (destMap.containsKey(pageUrl)) {
                messager.printMessage(Diagnostic.Kind.ERROR, "不同的页面不允许使用相同的pageUrl" + className);
            } else {
                JSONObject object = new JSONObject();
                object.put("id", id);
                object.put("needLogin", needLogin);
                object.put("asStarter", asStarter);
                object.put("pageUrl", pageUrl);
                object.put("className", className);
                object.put("isFragment", isFragment);
                destMap.put(pageUrl, object);
            }
        }
    }

    /**
     * 返回我们Java的版本.
     * <p>
     * 也可以通过{@link SupportedSourceVersion}注解来设置
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        //return SourceVersion.latest();
        return super.getSupportedSourceVersion();
    }

    /**
     * 返回我们将要处理的注解
     * <p>
     * 也可通过{@link SupportedAnnotationTypes}注解来设置
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
//        Set<String> annotataions = new LinkedHashSet<>();
//        annotataions.add(ActivityDestination.class.getCanonicalName());
//        annotataions.add(FragmentDestination.class.getCanonicalName());
//        return annotataions;
        return super.getSupportedAnnotationTypes();
    }

}
``` 

使用
---
1. 在`app`的`build.gradle`中引入自定义注解处理器
   ```groovy 
   dependencies {
        ...
        
        //引入项目已使用注解 如果觉得跟下面的合起来有点怪可以单独再创建一个moudle，把注解的定义放到那里面去
        implementation project(path: ':libmyannotationprocessor')
        //配置注解处理器
        annotationProcessor project(':libmyannotationprocessor')
    
    }
   ``` 
2. 使用自定义注解，给默认生成的三个`fragment`加上自定义的注解：
    ```java
    @FragmentDestination(pageUrl = "tabs/dashboard")
    public class DashboardFragment extends Fragment {
        ...
    }
    
    @FragmentDestination(pageUrl = "tabs/home")
    public class HomeFragment extends Fragment {
        ...
    }
    
    
    @FragmentDestination(pageUrl = "tabs/notification")
    public class NotificationsFragment extends Fragment {
        ...
    }
    ``` 
3. 标记好注解后，重新`Make Project`即可在`app/src/main/assets/`生成对应的`destination.json`文件：
    ```json
    {
        "tabs/dashboard":{
            "isFragment":true,
            "asStarter":false,
            "needLogin":false,
            "pageUrl":"tabs/dashboard",
            "className":"com.yu.hu.annotationprocessortest.ui.dashboard.DashboardFragment",
            "id":1222462875
        },
        "tabs/notification":{
            "isFragment":true,
            "asStarter":false,
            "needLogin":false,
            "pageUrl":"tabs/notification",
            "className":"com.yu.hu.annotationprocessortest.ui.notifications.NotificationsFragment",
            "id":2030977523
        },
        "tabs/home":{
            "isFragment":true,
            "asStarter":false,
            "needLogin":false,
            "pageUrl":"tabs/home",
            "className":"com.yu.hu.annotationprocessortest.ui.home.HomeFragment",
            "id":1050166585
        }
    }
    ```

参考文章
---
 * [Java中的注解-自定义注解](https://juejin.im/post/5a619f886fb9a01c9f5b7e4f)
 * [Java注解解析-搭建自己的注解处理器（CLASS注解使用篇）](https://juejin.im/post/5b8f53355188255c520cecf1)