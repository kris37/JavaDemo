package jvm.io.tcpServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/17 下午2:03
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *    基于NIO实现的 Reactor模型
 *
 * <br>
 */
public class NioReactor implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(NioReactor.class);
    /**
     * 处理任务的队列 需要线程安全一般使用阻塞队列
     */
    private  ArrayBlockingQueue<Connection> taskQueue ;

    private final Selector selector ;
    /**
     * reactor 处理 taskQueue 的handler线程池
     */
    private Handler handler = null;
    public NioReactor() throws IOException {
        this(1024,new TestHandler());
    }

    public NioReactor(int size,Handler hadler) throws IOException {
        /**
         *  keepAliveTime 指的是当当前线程池中线程大于 corePoolSize 的时候，
         *  如果超过 keepAliveTime 还没有新的任务，则释放大于 corePoolSize 部分的线程
         */
      //  fixThreads = thread;

        this.selector = Selector.open();
        this.taskQueue = new ArrayBlockingQueue(size);
        this.handler = hadler;
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
//                .setNameFormat("process-hanlder-%d").setDaemon(false).build();
//        this.executor = new ThreadPoolExecutor(fixThreads,2*fixThreads,60, TimeUnit.SECONDS,
//                new LinkedBlockingQueue<Runnable>(256),namedThreadFactory,new ThreadPoolExecutor.DiscardPolicy());

    }

    public Selector getSelector() {
        return selector;
    }

    public boolean addQueue(Connection con){
        try {

            boolean ok= this.taskQueue.offer(con,200,TimeUnit.MILLISECONDS);
            if(ok){
                selector.wakeup();
            }
            return ok;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void register(){
        if(taskQueue.isEmpty()){
            return;
        }
        try {
            Connection connection = null;

            while ((connection = taskQueue.poll()) != null){
                connection.register(selector);
            }

        } catch (ClosedChannelException  e) {
            LOGGER.warn("ClosedChannelException",e);
        }
    }


    @Override
    public void run() {
        while (!Thread.interrupted()){
            register();
            try {

                int select = selector.select();
                //todo
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator =
                        selectionKeys.iterator();

                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    Connection attachment = (Connection)next.attachment();
                    if(!Objects.isNull(attachment) && next.isValid()){
                        if(next.isReadable()) {
                            attachment.read();
                            handler.handle(attachment);
                        }else if(next.isWritable()){
                            attachment.write();
                        }else {
                            next.cancel();
                        }

                    }

                }


                selectionKeys.clear();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
