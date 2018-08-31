package jvm.string;

import com.google.common.base.Utf8;
import com.google.common.collect.Lists;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
    public static void main(String [] args) throws UnsupportedEncodingException {
//
//        String f = "mytest";
//        String g = "my"+"test";
//        String a = new String("mytest").intern();
//        String b = new String("my")+new String("test");
//        String c = new String("my").intern() + new String("test");
//        String d = new String("my").intern() + new String("test").intern();
//        String e = new StringBuffer().append("my").append("test").toString();
//
//        String [] array = {a, b, c, d, e, f, g};
//        String [] index = {"a", "b", "c", "d", "e", "f", "g"};
//
//        for (int i = 0; i < array.length; i++) {
//            for(int j = i;j<array.length;j++){
//                System.out.print("index:"+index[i] +"=="+index[j]+"->");
//                System.out.println( array[i]==array[j]);
//            }
//        }


        String fc="{\"utmcsr\":\"so.com\",\"utmccn\":\"(organic)\",\"utmcmd\":\"organic\",\"utmctr\":\"æ\u0084\u008Få¤§å\u0088©ç\u00AD¾è¯\u0081ç\u0094³è¯·è¡¨å¡«å\u0086\u0099æ ·æ\u009C¬\",\"utmcct\":\"\"}";


        String a = "»õºÅ ";
        String b = new String(a.getBytes("windows-1252"),"gbk");
        System.out.println(b);
       // boolean wellFormed = Utf8.isWellFormed(fc.getBytes());windows-1252
        byte[] bytes = fc.getBytes(Charset.forName("windows-1252"));

        String s1 = new String(bytes,Charset.forName("UTF-8"));
        System.out.print(s1);

    }


}
