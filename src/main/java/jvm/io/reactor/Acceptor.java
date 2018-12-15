package jvm.io.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/22 上午9:20
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     单例模式只有一个
 *     也是主 Reactor
 *     clinet 链接通过 accpetor 进行分发到不同的SubReactor
 *
 * <br>
 */
public class Acceptor extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(Acceptor.class);
    private static volatile  Acceptor INSTANCE;
    private  ServerSocketChannel server;
    private  int port;
    private  Selector selector;
    private GroupSubReactor group;
    private Acceptor(int port,GroupSubReactor group) throws IOException {
        this.port = port;
        this.selector = Selector.open();
        this.group = group;
        init();
    }
    private Acceptor(GroupSubReactor group) throws IOException {
        this(8080,group);
    }
    private void init() throws IOException {
        this.server = ServerSocketChannel.open();
        this.server.bind(new InetSocketAddress(this.port));
        this.server.configureBlocking(false);
        this.server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        this.server.setOption(StandardSocketOptions.SO_RCVBUF, 1024);
        this.server.register(this.selector, SelectionKey.OP_ACCEPT);
    }
    public static Acceptor getSingleInstance(int port,GroupSubReactor group) throws IOException {

        if(INSTANCE == null){
            synchronized (Acceptor.class){
                if (INSTANCE == null){
                    INSTANCE = new Acceptor(port,group);
                    return INSTANCE;
                }else {
                    return INSTANCE;
                }
            }
        }else {
            return INSTANCE;
        }
    }

    private static final AtomicInteger readcounter = new AtomicInteger();
    private static final AtomicInteger writecounter = new AtomicInteger();
    private static final AtomicInteger execcounter = new AtomicInteger();

    @Override
    public void run() {
        new Analysit(readcounter,writecounter,execcounter).start();
        while (!Thread.interrupted()){
            try {
                int select = selector.select();
                if(select <=0) continue;
                // 因为这里 selector 只监听 server 所以 可以直接从 server获取 socketchannel
//               SocketChannel sc = server.accept();
                LOG.debug("acceptor recive ....");
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey sk = iterator.next();
                    iterator.remove();// 一定要删除
                    if (sk.isValid() && sk.isAcceptable()) {
                        SocketChannel sc = server.accept();
                        if (sc != null) {
                            SubReactor reactor = group.next();
                            reactor.register(sc,readcounter,writecounter,execcounter);
                        }
                    }


                }
                //
                selectionKeys.clear();

            } catch (IOException e) {
                LOG.error(e.getMessage());
            }finally {

            }
        }
    }
}
