import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by panqiang on 2017/3/23.
 */
public class Test_Common_Element {
    public static void main(String[]args){
        int []a={1,2,3,4,5,6,7,8};
        int []b={0,4,8,9};
        Iterator<Integer> iter=common_element(a,b).listIterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
    }
    public static LinkedList<Integer> common_element(int[] a,int []b){
        LinkedList<Integer> list= new LinkedList<Integer>();
        int i=0,j=0;
        while(i <a.length && j<b.length){
            if(a[i]==b[j]){
                list.add(a[i]);
                i++;
                j++;
            }else{
                if(a[i]>b[j]){
                    j++;
                }else{
                    i++;
                }
            }
        }
        return list;
    }
}
