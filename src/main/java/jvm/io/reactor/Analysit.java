package jvm.io.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/23 下午2:28
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class Analysit extends Thread{

    private static final Logger LOG = LoggerFactory.getLogger(Analysit.class);
    private AtomicInteger read;
    private AtomicInteger write;
    private AtomicInteger exec;
    public Analysit(AtomicInteger read,AtomicInteger write,AtomicInteger exec){
        this.read =read;
        this.write =write;
        this.exec =exec;
    }
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(5000);
                LOG.info("--read--:" + read.get() + "  -----write---:" + write.get() + " ------exec-----:" + exec.get());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
