package jvm.io;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/17 下午6:51
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public interface Handler   {

    void handle(Connection con);
}
