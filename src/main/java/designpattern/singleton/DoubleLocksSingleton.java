package designpattern.singleton;
/**
 * @author panqiang 2017/12/07
 *  </p>
 *  双重锁单例模式 懒汉式
 *  nitice  1. 注意 <b> @volatile 关键字的在此处的对 jvm 和cup 指令重排</b> 的限制
 *          2.
 */

public class DoubleLocksSingleton {

    private DoubleLocksSingleton(){

    }

    private static volatile  DoubleLocksSingleton singleton = null;

    // 饿汉式

    /**
     *   private static final  DoubleLocksSingleton singleton = new DoubleLocksSingleton();
     */

    public static DoubleLocksSingleton getSingleton(){
        if(singleton == null){
            synchronized (DoubleLocksSingleton.class){
                if(singleton == null){
                    /**
                     *  例如jvm或者cup 优化时候可能会这样执行操作 说白了 new DoubleLocksSingleton()非原子操作
                     *  1.memory = allocate() 分配内存空间
                     *  2. actorInstance(memory) 初始化对象
                     *  3. instance = memory instance 指向内存地址
                     *  jvm 或者cup 优化有可能 将2,3 互换，那么有可能 返回的singleton 是个未完全初始化的对象 风险
                     *  volatile 可以 限制cup 或者jvm 对此进行优化
                     */
                    singleton = new DoubleLocksSingleton();
                }
                return singleton;
            }
        }else{
            return singleton;
        }
    }

    //test

    public static void main(String [] args){
        DoubleLocksSingleton single1 = DoubleLocksSingleton.getSingleton();
        DoubleLocksSingleton single2 = DoubleLocksSingleton.getSingleton();
        System.out.println(single1 == single2);
    }
}

