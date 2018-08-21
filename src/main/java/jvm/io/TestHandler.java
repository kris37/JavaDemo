package jvm.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/20 下午5:52
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class TestHandler implements Handler{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestHandler.class);

    @Override
    public void handle(Connection con) {
        ByteBuffer readBuffer = con.getReadBuffer();
        readBuffer.flip();
        String s = new String(Arrays.copyOf(readBuffer.array(),con.getTotal()), Charset.forName("utf-8"));
        System.out.println(Thread.currentThread().getName()+"\t"+con.getId()+"\t recive "+s);
        //todo
//        try {
//            con.setSendBuffer(ByteBuffer.wrap("hello".concat(s).getBytes("utf-8")));
//            con.write();
//        } catch (IOException  e) {
//            LOGGER.warn("setSendBuffer",e);
//        }
    }
}
