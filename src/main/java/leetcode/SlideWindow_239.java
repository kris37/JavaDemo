package leetcode;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/19 上午10:53
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     双端队列存储索引
 * <br>
 */
public class SlideWindow_239 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int [] output  = new int[nums.length + k -1];
        LinkedList<Integer> queue = new LinkedList<Integer>();

        for (int i = 0; i <nums.length ; i++) {
            //  queue remove index < (i -k +1) 同样保证了 queue 中索引最大范围 [i-k+ 1,i] length = K
            while(!queue.isEmpty() && queue.peekFirst() < (i-k + 1)){
                queue.poll();
            }

            //
            while(! queue.isEmpty() && nums[i] >= nums[queue.peekLast()]){
                queue.pollLast();
            }
            queue.addLast(i);

            if(i >=k - 1){
                output[i - k + 1] = nums[queue.peekFirst()];
            }
        }
        return output;


    }

}
