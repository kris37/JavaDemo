package designpattern.observer;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/6/5 下午3:03
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public interface MyObserverInterface {

    void update(float temparture,float fliex,float speed);

}
