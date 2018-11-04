package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/18 上午10:22
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *
 * <br>
 */
public class TwoSum_1 {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int i =0 ;i < nums.length;i++){
            if (map.containsKey(target - nums[i])){
                return new int[]{i,map.get(target - nums[i])};
            }else {
                map.put(nums[i],i);
            }
        }
        return null;
    }
}
