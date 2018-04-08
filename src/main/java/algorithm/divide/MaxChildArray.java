package algorithm.divide;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: panqiang
 * \* Date: 2018/2/8
 * \* Time: 下午6:21
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class MaxChildArray {
    public static void main(String [] args){
        int[] a={3,-1,2,5,-3,4,-6,-7,1,8,-3,5,9};
        Tuple3 max = findMax(a, 0, a.length - 1);
        System.out.println(max.maxsum +"leftidnex->"+max.left + "rightindex->"+max.right) ;
        System.out.println(dynamic(a));
    }

    /**
     *  暴力搜索 3次for循环 o(n^3)
     * @param array
     * @return
     */
    public static int forMAXSUM(int [] array){

        int maxsum = Integer.MIN_VALUE;
        int startindex = 0;
        int endindex = 0;
        for (int i = 0; i <array.length ; i++) {
            for (int j = 0; j <=i ; j++) {
                int tmp = 0;
                for (int k = j; k <=i ; k++) {
                        tmp+=array[k];
                    }
                if(tmp >= maxsum){
                    maxsum= tmp;
                    startindex = j;
                    endindex = i;
                }
                }
            }
        System.out.println("startindex->"+startindex +"\t endindex ->"+endindex);
        return maxsum;
    }

    /**
     * 动态规划算法 o(n)
     * @param array
     * @return
     */
    public static int dynamic(int [] array){
        int currSum = 0;
        int maxSum = Integer.MIN_VALUE;
        int startindex = 0;
        int endindex = 0;
        int tmpStartindex = 0;

        for (int i = 0; i < array.length; i++) {
            currSum+=array[i];
            if(currSum >= maxSum){
                maxSum = currSum;
                endindex = i;
                startindex = tmpStartindex;
            }
            if(currSum < 0){
                currSum = 0;
                tmpStartindex = i+1;
            }
        }
        System.out.println("startindex->"+startindex+"\t endindex ->"+endindex);
        return maxSum;
    }

    /**
     * 分治法
     * @param array
     * @return
     */
    public static int recursive(int [] array){


        return 0;
    }

    /**
     *  private for recursive divided
     *
     *
     * @param array
     * @param low
     * @param mid
     * @param high
     * @return
     */
    private static Tuple3 findCrossMaxSum(int [] array,int low, int mid,int high){

        //left
        int leftmax = Integer.MIN_VALUE;
        int leftTmp = 0;
        int leftIndex = 0;
        for(int i = mid;i>=low;i--){
            if(array[i]+leftTmp > leftmax){
                leftmax = array[i]+leftTmp;
                leftIndex = i;
            }
            leftTmp = array[i]+leftTmp;
        }

        //right
        int rightmax = Integer.MIN_VALUE;
        int rightTmp = 0;
        int rightIndex = 0;
        for (int j = mid+1 ; j <= high  ; j++) {
            if(array[j]+rightTmp > rightmax){
                rightmax = array[j]+rightTmp;
                rightIndex = j;
            }
            rightTmp = array[j]+rightTmp;
        }

        return new Tuple3(leftIndex,rightIndex,leftmax+rightmax);




    }

    private static Tuple3 findMax(int [] array, int low, int high){

        // 最小处理逻辑
        if(low == high){
            return new Tuple3(low,high,array[low]);
        }

        int mid =  (low+high)>> 1;
        Tuple3 leftmax = findMax(array, low, mid);
        Tuple3 rightmax = findMax(array, mid+1, high);
        Tuple3 crossMaxSum = findCrossMaxSum(array,low, mid,high);
        //merge
        if(leftmax.maxsum >= rightmax.maxsum && leftmax.maxsum >= crossMaxSum.maxsum){
            return new Tuple3(leftmax.left,leftmax.right,leftmax.maxsum);
        }else if(rightmax.maxsum >= leftmax.maxsum && rightmax.maxsum >= crossMaxSum.maxsum){
            return new Tuple3(rightmax.left,rightmax.right,rightmax.maxsum);
        }else{
            return new Tuple3(crossMaxSum.left,crossMaxSum.right,crossMaxSum.maxsum);
        }
    }
}
