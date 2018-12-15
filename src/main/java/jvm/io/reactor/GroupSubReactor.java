package jvm.io.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/22 上午10:54
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     分组管理 SubReactor
 *     非线程安全
 * <br>
 */
public class GroupSubReactor {
    private static final Logger LOG = LoggerFactory.getLogger(GroupSubReactor.class);
    private int index = 0;
    private static volatile GroupSubReactor group;
    private SubReactor [] pool;


    private GroupSubReactor(int nums) throws IOException {
        this.pool = new SubReactor[nums];
        for (int i = 0; i < this.pool.length ; i++) {
            this.pool[i] =  SubReactor.create();
        }
    }
    public static GroupSubReactor create(int nums) throws IOException {
        return new GroupSubReactor(nums);
    }

    public void start(){
        for (SubReactor subReactor : this.pool) {
            new Thread(subReactor).start();
        }
    }

    /**
     *  轮询返回 next SubReactor
     * @return next SubReactor
     */
    public synchronized SubReactor next(){
        if ((++index) >= this.pool.length){
            index = 0;
        }
        LOG.info("SubReactor : "+index + " recive connect...");
        return pool[index];
    }

}
