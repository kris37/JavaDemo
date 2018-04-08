package algorithm.peak_finder;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @version kris37
 * Date: 2018/3/15 下午3:54
 * To change this template use File | Settings | File Templates.
 * Description:
 * @auhtor panqiang panqiang37@gmail.com
 *
 * for a b c  if(b> a & b >c) is true
 */
public class PeakFinder {

    //test

    public static void main(String [] args){

        int [] arr = {10,11,7,5,10,9,8};
        int finder = recuriveFinder(arr,0,arr.length-1);
        System.out.println("index is :"+finder+"\t "+arr[finder]);
    }


    /**
     *  recurive binary search the peak
     * @param nums
     * @param left
     * @param right
     * @return
     */
    private static int recuriveFinder(int [] nums,int left,int right){

        Objects.requireNonNull(nums);

        int mid = (left+right) >> 1;
        if(left==right){
            return right;
        }

        // 防止越界
        if(nums[mid] > nums[mid+1]){
            return recuriveFinder(nums,left,mid);
            //todo finder in [mid,right]
        }else{
            return recuriveFinder(nums,mid+1,right);
        }

    }

    private static int finder(int [] nums){
        Objects.requireNonNull(nums);
        int left = 0;
        int right = nums.length-1;
        while(left < right){
            int mid = (left + right) >> 1;
            if(nums[mid]< nums[mid+1]){
                left = mid+1;// important 避免 left + right >>1 时候一直  = left
            }else{
                right = mid;
            }
        }
        return right;

    }

    /**
     *    forloop solution Time complexity O(N)
     *    使用forloop 遍历整个数组进行查找，好处是可以找出所有峰值
     *    遍历找出第一个峰值 peak
     */
     private static int forloopFinder(int nums[]){
        for (int i = 0; i < nums.length -1 ; i++) {
            if(nums[i] > nums[i+1]){
                return i;
            }
        }
        return nums.length - 1;
    }
}
