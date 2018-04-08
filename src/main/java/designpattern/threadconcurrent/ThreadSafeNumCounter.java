package designpattern.threadconcurrent;

/**
 *  @author panqiang 2017/12/08
 *  完成一个线程安全的计数程序，采用volatile 和 Synchronized
 */
public class ThreadSafeNumCounter {
    public  ThreadSafeNumCounter(){
    }

    /**
     *  @parms num 保证读可见 通过 volatile 关键字，如果通过 synchronized进行处理那么每次只有一个线程可读及可写
     */
    private volatile int num =0;
    public int getNum() {
        return num;
    }

    public synchronized void addIncr (int incr) {
        num+=incr;
    }

    /**
     * 采用syncronized 对读锁定
     */
    public static class SynchronizedNumCounter{
        public SynchronizedNumCounter(){

        }
        private int num =0;
        public synchronized void addIncr (int incr){
            num+=incr;
        }
        public synchronized int getNum (){
            return num;
        }


    }
}
