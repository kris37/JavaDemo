package algorithm;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * Created by panqiang on 2017/3/20.
 *
 * 输入两个有序数组 A、B 找出 共有的元素（不包含重复元素）
 * forexample  A=[1,3,3,5,9,10] B=[0,2,3,4,5,6,7,8,9,10]
 * common result=[3,5,9,10]
 * 暴力 O(m*n),二分查找（实现二分查找）O(mlogn),hashtable 平均复杂度O(m+n) 如果是没有排序的数组则需要,双向指针O(m+n)
 *
 */
final public class Common_Atom {
    private Common_Atom(){}

    public static LinkedList<Integer> Common_Array(int[] a, int[]b){
        int[] array_short;
        int[] array_long;
        LinkedList<Integer> reslut=new LinkedList<Integer>();
        if(a.length<b.length){
            array_short=a;
            array_long=b;
        }else{
            array_short=b;
            array_long=a;
        }
        int i=0,j=0;
        while(i<array_short.length && j<array_long.length){
            if(array_short[i]!=array_long[j]){
                if(array_short[i]>array_long[j]){
                    j++;
                }else {
                    i++;
                }
            }else{
                reslut.push(array_short[i]);
                i++;
                j++;
            }
        }
        return reslut;
    }
//distinct reslut
    public static LinkedList<Integer> Common_Array_Disitinct(int[] a, int[]b){
        int[] array_short;
        int[] array_long;

        LinkedList<Integer> reslut=new LinkedList<Integer>();
        if(a.length<b.length){
            array_short=a;
            array_long=b;
        }else{
            array_short=b;
            array_long=a;
        }
        int i=0,j=0;
        while(i<array_short.length && j<array_long.length){
            if(array_short[i]!=array_long[j]){
                if(array_short[i]>array_long[j]){
                    j++;
                }else {
                    i++;
                }
            }else{
                reslut.push(array_short[i]);
                i++;
                j++;
            }
        }
        return reslut;
    }
    public static ArrayList<Integer> distinct(int [] array){
        ArrayList<Integer> result=new ArrayList<Integer>();

        //hashtable distinct
        Hashtable hbt=new Hashtable();
        for (int x: array) {
            if(!hbt.containsKey(x)){
                result.add(x);
            }
        }
            return result;

        // sort compare(i,i+1);
    }
    public static void main(String [] args){
        int[] a={1,3,3,3,3,5,9,9,9,10};
        int[] b={0,2,3,4,5,6,7,8,9,9,10,10};
        //LinkedList<Integer> reslut=Common_Array(a,b);
        LinkedList<Integer> reslut=Common_Array_Disitinct(a,b);
        for (Integer i: reslut) {
            System.out.print(i+"\t");
        }
    }
}
