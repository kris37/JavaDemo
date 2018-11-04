package leetcode;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/1 下午2:32
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     最优解法 摩尔投票法(也是最优的字符串查找算法 grep)
 *     消消乐 出现 n 个 不同的则消掉 一直保持 <= n-1 在数组内
 * <br>
 */
public class Majority_169 {

  public static void main(String [] args){
      int[] ints = {1,1,1,3,3,2,2,2};
      Set<Integer> res = mooreVote(ints, 3);

      System.out.println(res);
      res.stream().collect(Collectors.toList());


  }

    /**
     * 输入 数组 nums 找出 数组中出现次数 > n/k 的数
     * @param nums
     * @param k
     */
    private static Set<Integer> mooreVote(int[] nums, int k){
        HashMap<Integer, Integer> count = new HashMap<>();
        for (int i = nums.length - 1; i >= 0; i--) {
           if(count.containsKey(nums[i])){
               count.put(nums[i],count.get(nums[i]) + 1);
           }else {
               if(count.size() >= k - 1){
                   Iterator<Map.Entry<Integer, Integer>> iterator = count.entrySet().iterator();
                   while (iterator.hasNext()){
                       Map.Entry<Integer, Integer> next = iterator.next();
                       if (next.getValue() > 1){
                           next.setValue(next.getValue() - 1);
                       }else{
                           iterator.remove();
                       }

                   }
               }else {
                  count.put(nums[i],1);
               }
           }
        }
        for (int j: nums) {
            //if
        }
        count.keySet();
        return null;
    }

}
