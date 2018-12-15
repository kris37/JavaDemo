package jvm.concurrent;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/28 上午9:08
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     偏向锁测试
 * <br>
 */
public class BiasedLock {
    static Lock lock = new Lock();
    static int counter = 0;
    static void foo(){
        synchronized (lock){
            counter ++;
        }
    }
    public static void main(String [] args){
        //lock.hashCode();
        //System.identityHashCode(lock);
        for (int i = 0;i < 1_000_000;i++){
            foo();
        }


    }
    static class Lock{
        @Override
        public int hashCode() {
            return 0;
        }
    }
}
