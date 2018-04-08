import jvm.NetClassLoader;
import sun.misc.Launcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Objects;

/**
 * Created by panqiang on 2017/3/16.
 */
public class Test {

    @org.junit.Test
    public void TestQuickSort(){
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urLs) {
            System.out.println(url.toExternalForm());
        }
        //同样获取
        System.out.println(System.getProperty("sun.boot.class.path"));
    }

    @org.junit.Test
    public void TestClassLoader(){
        ClassLoader classLoader = List_Reverse.class.getClassLoader();
        while(!Objects.isNull(classLoader)){
            System.out.println(classLoader.getClass().getName());
            classLoader = classLoader.getParent();
        }
        System.out.println(classLoader);
    }
    @org.junit.Test
    public void NetClassLoader() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, InterruptedException {
        NetClassLoader netClassLoader = new NetClassLoader();
        Class<?> aClass = netClassLoader.loadClass("https://album-album.oss-cn-beijing.aliyuncs.com/test/StdDraw.class");
        //Class<?> aClass = netClassLoader.loadClass("/Users/panqiang/Study/algorithm/src/main/java/utils/StdDraw.class");
        System.out.println(aClass.getClassLoader().toString());
        //Object obj = aClass.newInstance();
        Method square = aClass.getDeclaredMethod("square",new Class[]{double.class,double.class,double.class});
        square.invoke(aClass,new Object[]{0.2,0.8,0.1});
        Thread.sleep(10000);


    }



}

