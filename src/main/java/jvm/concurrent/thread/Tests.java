package jvm.concurrent.thread;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/21 下午12:06
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class Tests {

    public static void main(String [] args){
        int size = Integer.SIZE - 3;
        System.out.println(Integer.toBinaryString((1<<size) -1));
    }
}
