package algorithm.sort;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/7/26 上午9:36
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     选择排序
 *     时间复杂度 O(n^2)
 *     空间复杂度O(1)
 * <br>
 */
public class SelectSort {

    public static void main(String[] args){
        int [] test = {2,5,2,8,9,3,5,8,0,8,7,5,4,3,1,7,3,8,6};
        sort(test);
        for (int i : test) {
            System.out.print( i + "->");
        }
    }

    public static void sort(int [] array){
        int length = array.length;
        for (int i = 0; i < length ; i++) {
            int index = i;
            for (int j = length - 1; j > i  ; j--) {
                if(array[j] < array[index]){
                    index = j;
                }
            }
            BubbleSort.swap(array,i,index);
        }
    }
}
