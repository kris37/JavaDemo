package jvm.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/7/31 上午12:14
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class Run {

    public static void main(String [] args){
        MyBehave realBehave = new MyBehave();

        ProxyHabit proxyHabit = new ProxyHabit(realBehave);

        Behave proxy = (Behave)Proxy.newProxyInstance(realBehave.getClass().getClassLoader(), realBehave.getClass().getInterfaces(), proxyHabit);
        proxy.sleep();
        proxy.eat("watermenlon");

    }
}
