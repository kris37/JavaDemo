package jvm.lambdas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/31 下午4:13
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class Test {
    public static void main(String [] args){
        Runnable run = () -> {System.out.println("lambdas create runnable");};
        new Thread(run).start();
        MyLambdas<Object> lambdas = s -> System.out.println(s.toString().concat("!"));


    }
}
