package jvm.dynamicproxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/7/31 上午12:05
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class MyBehave implements Behave {

    @Override
    public void sleep() {
        System.out.print("I want to go to bed.");
    }

    @Override
    public void eat(String food) {
        System.out.print("I want to eat" + food);

    }
}
