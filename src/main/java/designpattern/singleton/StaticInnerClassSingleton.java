package designpattern.singleton;

/**
 * @author panqiang 2017/12/07
 *  </p>
 *  静态内部类单例模式 懒汉式
 */

public class StaticInnerClassSingleton {
    private StaticInnerClassSingleton(){
    }
    public static StaticInnerClassSingleton getInstance(){
        return GetInstanceClass.INSTANCE;
    }
    private static class GetInstanceClass{
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }

}


