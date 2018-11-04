package leetcode;

import java.awt.List;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/18 下午9:44
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     statck
 * <br>
 */
public class ValidParentheses_20 {

    private Map<Character,Character> map = new HashMap<Character,Character>(){{
        put(')','(');
        put(']','[');
        put('}','{');
    }};
    private LinkedList output = new LinkedList<Integer>();


    public boolean isValid(String s) {
        Deque<Character> stack = new LinkedList<Character>();
        char[] chars = s.toCharArray();
         for (char c : chars) {
             // right part push
            if (map.containsKey(c)){
                //pop
                if(stack.isEmpty()){
                    return false;
                }else {
                    Character pop = stack.pop();
                    if(!map.get(c).equals(pop)) {
                        return false;
                    }
                }
            }else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }
}
