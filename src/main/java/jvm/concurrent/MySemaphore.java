package jvm.concurrent;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/6/13 上午10:00
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     信号量逻辑实现
 * <br>
 */
public class MySemaphore {
    private volatile int s = 0;
    public MySemaphore(int s){
        this.s = s;
    }

    public synchronized void mywait()  {

            while(s <=0){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            s--;
            System.out.println(Thread.currentThread().getName()+"获取1台机器，剩余"+s+"台");

    }

    public synchronized void mysignal(){

            s++;
            this.notifyAll();
            System.out.println(Thread.currentThread().getName()+"释放1台，剩余"+s+"台");
    }



    static class Worker extends Thread{
        private int id = 0;
        private MySemaphore sema;
        //private MySempaphore mutex ;
        public Worker(int id, MySemaphore sema, MySemaphore mutex){
            this.setName("work thread :"+id);
            this.id = id;
            this.sema = sema;
           // this.mutex = mutex;

        }
        @Override
        public void run() {
            sema.mywait();
            //mutex.mywait();//互斥锁 要对同一个非线程安全对象操作的时候加锁
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //mutex.mysignal();
            sema.mysignal();
        }
    }


    public static void main(String [] arges){
        MySemaphore machine = new MySemaphore(3);
        MySemaphore mutex = new MySemaphore(1);// 互斥锁 只为1
        for(int i = 100;i>0;i--){
            Worker worker = new Worker(i, machine, mutex);
            worker.start();
        }
    }
}
