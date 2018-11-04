package leetcode;

import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/24 下午5:48
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     leetcode test for code
 * <br>
 */
public class LeetCode {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if(k<2 || head ==null){
            return head;
        }

        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next =head;
        ListNode pre_tail = dummy;
        ListNode cur = head ;
        ListNode next_head ;
        ListNode start = head;
        int count = 0;
        while(cur !=null){
            count ++;
            if(count == k){
                count = 0;
                next_head = cur.next;
                pre_tail = reverse(start, k, pre_tail, next_head);
                start = next_head;
                cur = next_head;
            }else {
                cur = cur.next;
            }
        }
        return dummy.next;
    }




    private  ListNode reverse(ListNode head,int k,ListNode pre_tail,ListNode next_head){
        // pre_tail 最终指向 tail  head.next = next_head return head 作为 下一次的变量 pre_tail
        ListNode cur = head;
        ListNode pre = null;
        while(k > 0 ){
            k --;
            ListNode next = cur.next;
            cur.next = pre;
            // exchange var
            pre = cur;
            cur = next;
        }
        pre_tail.next = pre;
        head.next = next_head;
        return head;
    }

    public static void main(String [] args){

        ListNode head = new ListNode(1);
        ListNode listNode = head;
        for (Integer e : Lists.newArrayList(2)) {
            listNode.next = new ListNode(e);
            listNode = listNode.next;
        }
        ListNode listNode1 = new LeetCode().reverseKGroup(head, 2);
        while(listNode1 != null){
            System.out.println(listNode1.val);
            listNode1 = listNode1.next;
        }
    }
}
