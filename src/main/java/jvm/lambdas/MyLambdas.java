package jvm.lambdas;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/31 下午4:18
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
@FunctionalInterface
public interface MyLambdas<T> {
    public abstract void pass(T t);
}
