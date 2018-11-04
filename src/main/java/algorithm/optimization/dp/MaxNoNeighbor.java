package algorithm.optimization.dp;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/9/30 上午9:02
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     计算最大不相邻和
 *     arr = {1,2,4,7,8,2,6,7,2,1}
 * <br>
 */
public class MaxNoNeighbor {

    public static void main(String [] args){
        int [] arr = {1,2,4,7,8,2,6,7,2,1};
        int i = rec_opt(arr, arr.length - 1);
        int j = dp_opt(arr);
        System.out.println(i +" and " +j);

    }

    /**
     *  递归recursive 实现
     * @return
     */
    public static int rec_opt(int [] arr,int i){
        if(i == 0){
            return arr[0];
        }
        if(i == 1){
            return Math.max(arr[0],arr[1]);
        }
         int chose = rec_opt(arr,i -2) + arr[i];
         int nochose = rec_opt(arr,i -1);
         return Math.max(chose,nochose);
    }
    /**
     * DP 实现
     */
    public static int dp_opt(int [] arr){
        // 记录历史结果，opt[i] 表示的是 第i阶 的最好优化
        int [] opt = new int[arr.length];
        opt[0] = arr[0];
        opt[1] = Math.max(arr[0],arr[1]);
        for (int i = 2; i < arr.length; i++) {
            int chose = opt[i - 2] + arr[i];
            int nochose = opt[i - 1];
            opt[i] = Math.max(chose,nochose);
        }
        return opt[arr.length -1];
    }

}
