package jvm.string;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/7/22 下午9:00
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class StringTest {
    public static void main(String [] args){

        String f = "mytest";
        String g = "my"+"test";
        String a = new String("mytest").intern();
        String b = new String("my")+new String("test");
        String c = new String("my").intern() + new String("test");
        String d = new String("my").intern() + new String("test").intern();
        String e = new StringBuffer().append("my").append("test").toString();

        String [] array = {a, b, c, d, e, f, g};
        String [] index = {"a", "b", "c", "d", "e", "f", "g"};

        for (int i = 0; i < array.length; i++) {
            for(int j = i;j<array.length;j++){
                System.out.print("index:"+index[i] +"=="+index[j]+"->");
                System.out.println( array[i]==array[j]);
            }
        }
    }
}
