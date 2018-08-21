package algorithm.sort;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/7/26 上午9:08
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     冒泡排序
 *     时间复杂度: O(n^2)
 *     空间复杂度
 *
 * <br>
 */
public class BubbleSort {


    public static void main(String [] args){

        int [] test = {2,5,2,8,9,3,5,8,0,8,7,5,4,3,1,7,3,8,6};
        sort(test);
        for (int i : test) {
            System.out.print( i + "->");
        }
    }
    public static void sort(int [] array){
        int boundary = array.length  ;

        for (int i =0 ;i < boundary ;i++){
            for (int j = boundary -1 ;j>i;j--){
                if(array[j] < array[j-1]){
                    swap(array,j,j-1);
                }
            }
        }
    }

    public static void swap(int [] array,int index1,int index2){
        if(index1 == index2){
            return;
        }
        int temp = array[index1];
        array[index1]  = array[index2];
        array[index2] = temp;
    }
}
