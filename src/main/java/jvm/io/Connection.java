package jvm.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/20 上午11:34
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     客户端链接对象
 * <br>
 */
public class Connection {
    private static final Logger LOGGER = LoggerFactory.getLogger(Connection.class);
    private long id;
    private SocketChannel sc ;
    private SelectionKey sk;
    private static final int bfSize = 729;//Math.pow(3,6)
    private ByteBuffer readBuffer = ByteBuffer.allocate(bfSize);
    private ByteBuffer sendBuffer;
    private int total;


    public Connection(long id,SocketChannel socketChannel){
        this.id = id;
        this.sc = socketChannel;

    }

    public long getId() {
        return id;
    }

    public SocketChannel getSc() {
        return sc;
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public void setSendBuffer(ByteBuffer sendBuffer) {
        this.sendBuffer = sendBuffer;
    }

    public int getTotal() {
        return total;
    }

    /**
     * 将channel 中的内容以utf-8格式读出为字符串
     */
    public void read() throws IOException {
        readBuffer.clear();
        int size ;

        while((size = sc.read(readBuffer)) >= 0){
            total += size;
            if(size == 0){
                if(!sc.isOpen()){
                    sc.close();
                    break;
                }
            }
        }
        if(size == -1){
            //end
            sc.close();
        }

    }

    /**
     * 输出写出
     */
    public void write() throws IOException {
            sendBuffer.flip();
            sc.write(sendBuffer);
            // sk 加入 write 检测唤醒
            sk.interestOps(sk.interestOps() | SelectionKey.OP_WRITE);
            sk.selector().wakeup();
    }

    /**
     * 将此对象sc 注册到对应 selector
     * 并且将此 链接对象 attch 到 selectkey中
     * @param selector
     */
    public void register(Selector selector) throws ClosedChannelException {

        this.sk = sc.register(selector,SelectionKey.OP_READ,this);
    }
}
