package jvm.concurrent.thread;


import com.google.common.base.MoreObjects;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/21 上午11:06
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     线程池的线程工厂 自定义
 * <br>
 */
public class MyThreadFactory implements ThreadFactory{

    private final String namePrefix ;
    private final AtomicInteger nextid = new AtomicInteger();

    public MyThreadFactory(String groupName){
        namePrefix = "MyThreadFactory's " + groupName + " -worker-";
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(null,r,namePrefix+nextid.incrementAndGet(),0);
    }


    public static class MyTask implements Runnable{
        private final AtomicInteger counter = new AtomicInteger();
        @Override
        public void run() {
            int now = counter.incrementAndGet();
            System.out.println(Thread.currentThread().getName()+" running-- "+now);
        }

        @Override
        public String toString() {
            return " running-- reject  ";
        }
    }

    public static class MyRejectHander implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("task rejecting...."+r.toString());
        }
    }
    public static void main(String [] args){
        //
        BlockingQueue queue = new LinkedBlockingQueue(5);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8,16,5, TimeUnit.SECONDS,
                queue,new MyThreadFactory("print"),new MyRejectHander());

        Runnable task = new MyTask();
        for (int i = 0;i< 400;i++){
            try {
                threadPoolExecutor.execute(task);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }

}
