package jvm.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/20 下午6:28
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class BootStart {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootStart.class);
    public static void main(String [] args) throws IOException {
        int threads = 3;
         NioReactor[] pool = new NioReactor[3];
        for (int i = 0; i <threads ; i++) {
            pool[i] = new NioReactor();
            System.out.println("NioReactor :" +i+" start...");
            new Thread(pool[i]).start();
        }
        Acceptor acceptor = new Acceptor(3337,"test echo",pool);
        System.out.println("acceptor : start...");
        acceptor.start();

    }

}
