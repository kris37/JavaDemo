package jvm.classloader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @version kris37
 * Date: 2018/4/8 下午3:44
 * To change this template use File | Settings | File Templates.
 * Description:
 * @auhtor panqiang panqiang37@gmail.com
 *
 *  模拟自定义 classloader 获取网络中的class文件并且运行
 *  自定义ClassLoader
    不知道大家有没有发现，不管是Bootstrap ClassLoader还是ExtClassLoader等，这些类加载器都只是加载指定的目录下的jar包或者资源。如果在某种情况下，我们需要动态加载一些东西呢？比如从D盘某个文件夹加载一个class文件，或者从网络上下载class主内容然后再进行加载，这样可以吗？
    如果要这样做的话，需要我们自定义一个classloader。
    自定义步骤
    1.编写一个类继承自ClassLoader抽象类。
    2.复写它的findClass()方法。
    3.在findClass()方法中调用defineClass()。
 */
public final class NetClassLoader extends ClassLoader {


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clz = super.findLoadedClass(name); // 检查父类是否加载过 or  = null

        if(!Objects.isNull(clz)){
            return clz;
        }

        try {
            byte[] classData = getClassData(name);
            if(classData == null){
                throw new ClassNotFoundException(name);
            }
            System.out.println("classData.length is :"+classData.length);

            clz = defineClass("StdDraw",classData,0,classData.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clz;
    }


    /**
     *  自定义读取 class 文件装配
     * @param name
     * @return
     * @throws IOException
     */
    private byte[] getClassData(String name) throws IOException {

        URL url = new URL(name);
        ByteArrayOutputStream byteArrayBuffer = new ByteArrayOutputStream();
        InputStream isr = null;
        try {
            // 特别注意读取字节流
            isr = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int len = -1;
        byte[] buff = new byte[1024<<4];
        while ((len = isr.read(buff)) !=-1){
            byteArrayBuffer.write(buff,0,len);
        }
        return byteArrayBuffer.toByteArray();

//        Path path = Paths.get(name);
//        byte[] cLassBytes = Files.readAllBytes(path);
//        return cLassBytes;
    }
    public static void main(String [] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InterruptedException {
        NetClassLoader netClassLoader = new NetClassLoader();
        Class<?> aClass = netClassLoader.loadClass("https://album-album.oss-cn-beijing.aliyuncs.com/test/StdDraw.class");
        //Class<?> aClass = netClassLoader.loadClass("/Users/panqiang/Study/algorithm/src/main/java/utils/StdDraw.class");
        System.out.println(aClass.getClassLoader().toString());
        //Object obj = aClass.newInstance();
        Method square = aClass.getDeclaredMethod("square",new Class[]{double.class,double.class,double.class});
        square.invoke(aClass,new Object[]{0.2,0.8,0.1});
    }
}
