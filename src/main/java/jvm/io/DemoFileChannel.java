package jvm.io;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/16 上午11:15
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     FileChannel 无法设置为非阻塞模式
 *     单线程 NIO 读取多个文件
 * <br>
 */
public class DemoFileChannel {

    public static void main(String [] args) throws IOException {

        //
        //Path path = Paths.get("/Users/panqiang/Desktop/20180815_log.1534295631344");
        //FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ);

        RandomAccessFile files = new RandomAccessFile("/Users/panqiang/Desktop/20180815_log.1534295631344","rw");
        FileChannel channel = files.getChannel();
        ByteBuffer bbuf = ByteBuffer.allocate(64);
        while ( channel.read(bbuf) != -1){
            //设置buffer中 position  = 0 因为 读的时候buffer position 指向的是读入后的最后一个位置
            bbuf.flip();
            while (bbuf.hasRemaining()){
                System.out.println((char)bbuf.get());
            }
            bbuf.clear();
        }

        files.close();

    }
}
