package algorithm.sort;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by kris37 on 2017/3/16.
 * quicksort small to big recursive moudle
 */
public class QuickSort_Recursive {

    public QuickSort_Recursive(){
    }

    public static void main(String args[]){
        System.out.println("define input array lenth");
        Scanner sc=new Scanner(System.in);
        int input[]=new int[sc.nextInt()];
        System.out.println("please input "+input.length+" numbers :");
        for(int i=0;i<input.length;i++){
            try {
                input[i] = sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("not a int number");
            }
        }
        System.out.println("input end and sort result is : ");
        qiucksort_recurion(0,input.length-1,input);
        for (int i: input) {
            System.out.print(i+"\t");
        }

    }

    public static void qiucksort_recurion(int left,int right, int []array){
        if(left<right) {
            int mid = partition(left, right, array);
            qiucksort_recurion(left, mid - 1, array);
            qiucksort_recurion(mid + 1, right, array);
        }
    }
    public static int partition(int left,int right,int[] array){

        if(left>right){
            System.out.println("check input index");
            System.exit(2);
        }
        // the last num as the reference
        int reference =array[right];
        while(left<right){
            while(left<right&&array[left]<=reference){
                left++;
            }
            swap(array,left,right);
            while(left<right&&array[right]>=reference){
                right--;
            }
            swap(array,left,right);
        }
        //end with left=right
        return left;
    }

    public static void swap(int []a ,int index1,int index2){
        int temp=a[index1];
        a[index1]=a[index2];
        a[index2]=temp;
    }

}
