package jvm.concurrent;

import java.util.concurrent.locks.StampedLock;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/28 下午6:17
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * http://ifeve.com/jdk8%E4%B8%ADstampedlock%E5%8E%9F%E7%90%86%E6%8E%A2%E7%A9%B6/
 *
 *
 * <br>
 */
public class JDK8StampedLock {
    // 成员变量
    private double x, y;

    // 锁实例
    private final StampedLock sl = new StampedLock();

    // 排它锁-写锁（writeLock）
    void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    // 乐观读锁（tryOptimisticRead）

    /**
     * long stamp = lock.tryOptimisticRead(); //非阻塞获取版本信息
     copyVaraibale2ThreadMemory();//拷贝变量到线程本地堆栈
     if(!lock.validate(stamp)){ // 校验
     long stamp = lock.readLock();//获取读锁
     try {
     copyVaraibale2ThreadMemory();//拷贝变量到线程本地堆栈
     } finally {
     lock.unlock(stamp);//释放悲观锁
     }

     }

     useThreadMemoryVarables();//使用线程本地堆栈里面的数据进行操作
     * @return
     */
    double distanceFromOrigin() {

        // 尝试获取乐观读锁（1）
        long stamp = sl.tryOptimisticRead();
        // 将全部变量拷贝到方法体栈内（2）
        double currentX = x, currentY = y;
        // 检查在（1）获取到读锁票据后，锁有没被其他写线程排它性抢占（3）
        //别忘了拷贝后还有一道validate,如果这时候有线程修改了x,y中的值，那么肯定是有线程在调用validate前sl.tryOptimisticRead后获取了写锁
        // ，那么validate时候就会失败。现在应该明白了吧，这也是乐观读设计的精妙之处也是使用时候容易出问题的地方
        if (!sl.validate(stamp)) {
            // 如果被抢占则获取一个共享读锁（悲观获取）（4）
            stamp = sl.readLock();
            try {
                // 将全部变量拷贝到方法体栈内（5）
                currentX = x;
                currentY = y;
            } finally {
                // 释放共享读锁（6）
                sl.unlockRead(stamp);
            }
        }
        // 返回计算结果（7）
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    // 使用悲观锁获取读锁，并尝试转换为写锁
    void moveIfAtOrigin(double newX, double newY) {
        // 这里可以使用乐观读锁替换（1）
        long stamp = sl.readLock();
        try {
            // 如果当前点在原点则移动（2）
            while (x == 0.0 && y == 0.0) {
                // 尝试将获取的读锁升级为写锁（3）
                long ws = sl.tryConvertToWriteLock(stamp);
                // 升级成功，则更新票据，并设置坐标值，然后退出循环（4）
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    // 读锁升级写锁失败则释放读锁，显示获取独占写锁，然后循环重试（5）
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
        } finally {
            // 释放锁（6）
            sl.unlock(stamp);
        }
    }
}
