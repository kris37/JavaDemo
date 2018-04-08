package interview;


import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: panqiang
 * \* Date: 2018/1/30
 * \* Time: 下午3:30
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 数组旋转 如 ABCDE 左旋 3次 -> CEDAB
 * \
 */

final public class StringRotate {

    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    @SuppressWarnings("unchecked")
    public static String rotate(@NotNull String str, int numbers,String direction){
        ArrayList list = new ArrayList<String>();
        for (String s : str.split("", -1)) {
            list.add(s);
        }
        int i = numbers % str.length();
        switch (direction){
            case LEFT:
                i = str.length() - i;
                break;
            default:
                break;
        }
        reverse(list,i,str.length()-1);
        reverse(list,0,i-1);
        reverse(list,0,str.length()-1);

        StringBuffer stringBuffer = new StringBuffer();
        list.forEach(ch -> stringBuffer.append(ch));
        return stringBuffer.toString();
    }

    /**
     *
     * @param list
     * @param start
     * @param end
     * @param <T>
     */
    public  static <T> void reverse(@NotNull List<T> list, int start , int end){

        while (start < end){
            swap(list,start,end);
            start++;
            end --;
        }
    }

    /**
     *  method
     *  交换数组未知
     *  注意数组越界
     */
    public static <T> void swap(@NotNull List<T> list,int index1,int index2){

        T tmp = list.get(index1);
        list.set(index1,list.get(index2));
        list.set(index2,tmp);
    }


    public static void main(String args []){
        String s = "12345";
        String s1 = rotate(s, 4,RIGHT);
        System.out.println(s1);
    }
}
