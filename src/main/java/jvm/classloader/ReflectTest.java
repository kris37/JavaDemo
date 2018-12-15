package jvm.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/26 下午3:28
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     反射测试
 * <br>
 */
public class ReflectTest {
    public static class Test1{
        public  void say(int i){
            System.out.println("hello nums "+i);
        }
    }

    public static void main(String [] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> reflect = Class.forName("jvm.classloader.ReflectTest.Test1");
        Method say = reflect.getMethod("say", int.class);
        say.invoke(0);
        for (Method method : reflect.getDeclaredMethods()) {
            System.out.println(method.getName().toString());
        }

    }
}
