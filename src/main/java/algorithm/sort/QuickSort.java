package algorithm.sort;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/7/27 下午9:29
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     快速排序
 * <br>
 */
public class QuickSort {


    public static void main(String [] args){
        int [] array = {2,5,2,8,9,3,5,8,0,8,7,5,4,3,1,7,3,8,6};
       // int[] array = {1,3,5,7,2,4,6};
        sort(array,0,array.length-1);
        for (int i : array) {
            System.out.print(i+ "-> ");
        }
    }

    /**
     * @param array
     */


    static void sort(int [] array,int low,int high){

        int left = low;
        int right = high;
        if(left >= right){return;}
        int temp = array[left];
        while(left < right){
            while((left < right) && array[right] >= temp ){
                right--;
            }
            BubbleSort.swap(array,left,right);
            while((left < right)  && array[left] < temp){
                left ++;
            }
            BubbleSort.swap(array,left,right);
        }

        array[left] = temp;

        sort(array,low,left -1 );
        sort(array,low+1,high );
    }









}
