package jvm.io.reactor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/22 下午2:26
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class ProcessHandler implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessHandler.class);
    private SelectionKey sk ;
    private SocketChannel sc;
    private static final int READING = 0,WRITING = 1,RUNNING = 2;
    private  int state = READING;
    private static byte END = 0x0a;
    public String s;
    public ProcessHandler(SelectionKey sk,String s){
        this.sk = sk;
        this.sc = (SocketChannel) sk.channel();
        this.s = s;

    }


    private  void processread() throws IOException {
        if(state != READING) return;
        state = RUNNING;
        LOG.debug("OBJ ProcessHandler --------------------"+this.hashCode() + "------------------"+ "READ");
        LOG.info(s);
        this.s = this.hashCode()+"老子写完了";
        this.sk.attach(this);
        state = WRITING;
        sk.interestOps(SelectionKey.OP_WRITE);

    }

    private  void processwrite() throws IOException {
        if (state != WRITING) return;
        state = RUNNING;
        LOG.debug("OBJ Handler --------------------"+this.hashCode() + "------------------"+ "WRITE");
        LOG.debug((Thread.currentThread().getName()+s));
    }

    private boolean isReadComplete(){
        return true;
        //byte b = buffer.get(buffer.position());
        //return Byte.compare(b,END) == 0;
    }
    @Override
    public void run() {
        try {

            synchronized (this) {

                switch (state) {
                    case READING:
                        processread();
                        break;
                    case WRITING:
                        processwrite();
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
