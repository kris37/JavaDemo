package algorithm.divide;

/**
 * Created by panqiang on 2017/3/22.
 */
public class BinarySearch {
    public static void main(String[]args){
        int[] array={1,2,3,4,5,6,7,8,9};
        //System.out.println(search_Recursive(array,20,0,array.length-1));
        System.out.println(search_NoRecursive(array,9));
    }

    public static int search_NoRecursive(int[] array,int search){
        int left=0;
        int right=array.length-1;
        while(left<=right){
            int mid=(left+right)>>1;
            if(array[mid]==search){
                return mid;

            }else{
                if(array[mid]>search){
                    right=mid-1;
                }else{
                    left=mid+1;
                }
            }
        }

        return -1;
    }

    public static int search_Recursive(int[] array,int search,int left,int right){
        if(left>right){
            return -1;
        }
        int mid=(left+right)>>1;//from 0 begin the pointer shift left
        if(search==array[mid]){
            return mid;
        }else{
            if(search>array[mid]){
                return search_Recursive(array,search,mid+1,right);
            }else{
                return search_Recursive(array,search,left,mid-1);
            }
        }

    }


}
