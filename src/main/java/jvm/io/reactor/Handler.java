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
public class Handler implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Handler.class);
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private SelectionKey sk ;
    private SocketChannel sc;
    private static final int READING = 0,WRITING = 1,RUNNING = 2;
    private  int state = READING;
    private static byte END = 0x0a;

    private AtomicInteger readcounter;
    private AtomicInteger writeCounter;
    private AtomicInteger execCounter;
    public Handler(SelectionKey sk, AtomicInteger readcounter,AtomicInteger writeCounter,AtomicInteger execCounter){
        this.sk = sk;
        this.sc = (SocketChannel) sk.channel();
        this.execCounter = execCounter;
        this.readcounter = readcounter;
        this.writeCounter = writeCounter;
    }

    private void process(){
        this.buffer.flip();
        LOG.info(new String(buffer.array()));
    }
    private  void read() throws IOException {
        if(state != READING) return;
        state = RUNNING;
        LOG.debug("OBJ Handler --------------------"+this.hashCode() + "------------------"+ "READ");
        LOG.debug((Thread.currentThread().getName()+"reading data ..."));
        this.sc.read(buffer);
        if(isReadComplete()){
            process();
        }
        readcounter.incrementAndGet();
        state = WRITING;
        sk.interestOps(SelectionKey.OP_WRITE);

    }

    private  void write() throws IOException {
        if (state != WRITING) return;
        state = RUNNING;
        LOG.debug("OBJ Handler --------------------"+this.hashCode() + "------------------"+ "WRITE");
        LOG.debug((Thread.currentThread().getName()+"writing data ..."));
        buffer.clear();
        buffer.put((Thread.currentThread().getName()+"had recive your message").getBytes());
        this.sc.write(buffer);
        writeCounter.incrementAndGet();
        sk.cancel();
        sc.close();
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
                execCounter.incrementAndGet();
                switch (state) {
                    case READING:
                        read();
                        break;
                    case WRITING:
                        write();
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
