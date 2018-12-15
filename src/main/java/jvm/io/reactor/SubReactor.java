package jvm.io.reactor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/22 上午10:44
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *
 * <br>
 */
public class SubReactor implements Runnable{

    private static final Logger LOG = LoggerFactory.getLogger(SubReactor.class);
    private static final ThreadPoolExecutor POOL;
    private Selector selector;
    static {
        // init
        BlockingQueue queue =  new LinkedBlockingQueue(2 << 10);
        POOL = new ThreadPoolExecutor(4,8,60, TimeUnit.SECONDS,
                queue,new MyThreadFactory("reactor exec demo"),
                new MyThreadFactory.MyRejectHander());
    }

    private SubReactor() throws IOException {

        this.selector = Selector.open();
    }


    public static SubReactor create() throws IOException {
        return new SubReactor();
    }

    /**
     *  register write and read listen
     *  and create handler attach Selectkey
     * @param sc
     */
    public void register(SocketChannel sc,AtomicInteger a1,AtomicInteger a2,AtomicInteger a3) throws IOException {

        LOG.debug(Thread.currentThread().getName() +" register sc in subreactor obj :"+ this.hashCode());
        try {

            sc.configureBlocking(false);
            LOG.debug(Thread.currentThread().getName() +" register sc in subreactor obj :"+ this.hashCode()+" set non-block");
            SelectionKey sk = sc.register(this.selector, SelectionKey.OP_READ);
            sk.interestOps(SelectionKey.OP_READ);
            LOG.debug(Thread.currentThread().getName() +" register sc in subreactor obj :"+ this.hashCode()+" register selector non-block");
        } catch (ClosedChannelException e) {
            //todo log
            LOG.error(e.getMessage());
        }
    }

    public Selector getSelector() {
        return this.selector;
    }

    private String readChannel(SocketChannel sc) throws IOException {
        LOG.debug("read channel data .... ....");
        if (sc ==null){
            return "";
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        sc.read(buffer);
        return new String(buffer.array());
    }

    private void writeChannel(SocketChannel sc,String str) throws IOException {
        LOG.debug("write"+str);
        sc.write(ByteBuffer.wrap(str.getBytes()));
        sc.close();
    }

    @Override
    public void run() {
        LOG.debug(Thread.currentThread().getName() + "running subreactor :" + this.hashCode());

        while (true){
            try {
                int select = selector.selectNow();
                if(select <=0) continue;
               // if (select <= 0) continue;
                Set<SelectionKey> selectionKeys  = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if(!next.isValid()) {
                        LOG.debug("" + next.isValid());
                    }
                    if(next.isValid()) {
                        if ( next.isReadable()) {
                            //todo 其实 读写部分应该有此线程来进行 然后线程池进行处理操作
                            String s = readChannel((SocketChannel) next.channel());
                            next.interestOps(0);
                            //LOG.info(Thread.currentThread().getName() + " --- " + next.hashCode() + "...........is READABLE .....");
                            ProcessHandler processHandler = new ProcessHandler(next,s);
                            POOL.execute(processHandler);//

                        } else if ( next.isWritable()) {
                            next.interestOps(0);
                            ProcessHandler attachment = (ProcessHandler) next.attachment();
                            writeChannel((SocketChannel) next.channel(),attachment.s);
                            next.cancel();

                        }
                    }
                }
                selectionKeys.clear();


            } catch (Exception e) {
                // todo log
                LOG.error(e.getMessage());
            }
        }
    }
}
