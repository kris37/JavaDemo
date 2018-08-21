package jvm.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/7/31 上午12:09
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class ProxyHabit implements InvocationHandler {

    Object proxy = null;
    public ProxyHabit(Object obj){
        this.proxy = obj;
    }
    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        System.out.println("I'm cleaning ....");
        method.invoke(proxy,args);
        System.out.println("good habit!");
        return null;
    }

}
