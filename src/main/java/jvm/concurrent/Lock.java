package jvm.concurrent;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/28 下午12:16
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     Reentralock
 *
 * <br>
 */
public class Lock {
    /**
     * 读写锁
     */
    public static class TestReentrantReadWriteLock{
        private final Map<Integer,Integer> map = new TreeMap<>();
        private final  ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private final ReentrantReadWriteLock.ReadLock rlc = lock.readLock();
        private final ReentrantReadWriteLock.WriteLock wlc = lock.writeLock();

        public Integer get(Integer key){
            rlc.lock();
            try {
                System.out.println("read lock ...");
                return map.get(key);

            }finally {
                rlc.unlock();
            }
        }

        public void put(Integer key,Integer value){
            wlc.lock();
            try{
                System.out.println("write lock ...");
                map.put(key,value);
            }finally {
                wlc.unlock();
            }
        }


    }
}
