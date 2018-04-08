package jvm;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
 */
public class NetClassLoader extends ClassLoader {


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clz = this.findLoadedClass(name); // 检查父类是否加载过 or  = null

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
        byte[] buff = new byte[1024*16];
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
