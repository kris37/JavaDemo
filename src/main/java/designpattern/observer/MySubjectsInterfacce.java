package designpattern.observer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/6/5 下午2:56
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     观察者模式测试
 * <br>
 */
public interface MySubjectsInterfacce {
    void registerObserver(MyObserverInterface ob);
    void removeObserver(MyObserverInterface ob);
    void notifyObserver();
}
