package jvm.io.tcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/20 下午2:01
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     web server
 * <br>
 */
public class Acceptor extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(Acceptor.class);
    private NioReactor[] pool;
    private int currentPoolIndex;
    private int port ;
    private Selector selector;
    private  ServerSocketChannel server ;


    public Acceptor(NioReactor [] pool,String serverName) throws IOException {
        this(8080,serverName,pool);
    }

    public Acceptor(int port,String serverName,NioReactor [] pool) throws IOException {
        super.setName(serverName);
        this.pool = pool;
        this.port = port;
        this.selector = Selector.open();
    }
    private void init() throws IOException {

            this.server = ServerSocketChannel.open();
            this.server.configureBlocking(false);
            this.server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            this.server.setOption(StandardSocketOptions.SO_RCVBUF, 1024);
            this.server.bind(new InetSocketAddress(this.port));
            this.server.register(this.selector, SelectionKey.OP_ACCEPT);
            System.out.println(server.getLocalAddress());

    }

    private NioReactor getPoolNext(){

        if(currentPoolIndex >= pool.length){
            currentPoolIndex = 0;
        }
        return pool[currentPoolIndex++];
    }


    @Override
    public void run() {

        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (! Thread.interrupted()) {
            int select = 0;// 阻塞等待链接
            Set<SelectionKey> selectionKeys = null;
            try {
                select = selector.select();
                if (select <= 0) continue;
                selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    //
                    if (next.isValid() && next.isAcceptable()) {
                        //SocketChannel sc = ((ServerSocketChannel) next.channel()).accept();
                        SocketChannel sc = server.accept();
                        sc.configureBlocking(false);
                        sc.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                        sc.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
                        sc.setOption(StandardSocketOptions.SO_RCVBUF, 1024);
                        sc.setOption(StandardSocketOptions.SO_SNDBUF, 1024);
                        sc.setOption(StandardSocketOptions.TCP_NODELAY, true);
                        // pool select hadler
                        NioReactor nioReactor;
                        Connection connection = new Connection(IDGenerator.next(), sc);
                        boolean inQueue = false;
                        while (!inQueue) {
                            nioReactor = getPoolNext();
                            inQueue = nioReactor.addQueue(connection);
                        }
                    } else {
                        next.cancel();
                    }
                }

            } catch (IOException e) {
                LOGGER.warn(getName(), e);
            } finally {
                selectionKeys.clear();
            }
        }




    }
}
