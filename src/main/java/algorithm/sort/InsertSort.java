package algorithm.sort;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: panqiang
 * \* Date: 2018/2/8
 * \* Time: 下午5:05
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class InsertSort {
    public static void main(String [] args){

        int[] array = {1,3,5,7,2,4,6};
        sort(array);
        for (int i : array) {
            System.out.println(i);
        }
    }

    public static void sort(int [] array){
        int length = array.length;
        for(int i =1;i< length;i++){
            int tmp = array[i];
            int j = i-1;
            while( j>0 && array[j]>tmp ){
                array[j+1]=array[j];
                j--;
            }
            array[++j]=tmp;
        }

    }
    public static void swap(int [] array,int i,int j){
        int tmp = array[i];
        array[i]=array[j];
        array[j]=tmp;
    }

}
