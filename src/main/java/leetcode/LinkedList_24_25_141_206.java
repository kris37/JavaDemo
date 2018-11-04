package leetcode;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/18 下午12:02
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *
 * <br>
 */
public class LinkedList_24_25_141_206 {
    private static class ListNode{
        int val;
       ListNode next;
       ListNode(int x) { val = x; }
    }

    /**
     *  leetcode 206
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while(cur !=null){
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    /**
     * leetcode 24
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        ListNode next ;
        ListNode newHead = cur.next ==null?cur:cur.next;
        while(cur != null && cur.next != null){
            next = cur.next;
            ListNode tmp = next.next;
            cur.next = tmp;
            next.next = cur;
            if (pre!=null) pre.next = next;
            //update
            pre = cur;
            cur = tmp;
        }

        return newHead;
    }

    /**
     * leetcode 25
     * @param head node
     * @param k group K
     * @return reverse node
     */
    public  static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) return null;
        if(k == 1) return head;
        int count = 0;
        ListNode finalHead = head;
        ListNode index = head;
        ListNode groupHead = head;
        ListNode groupTail = null;
        boolean first = false;

        do{
            count+=1;
            if(count == k ){
                count = 0;
                index = index.next;// 对象引用
                if(!first){
                    first = true;
                    finalHead = reverse(groupHead,k);
                }else{
                    groupTail.next = reverse(groupHead,k);
                }
                groupTail = groupHead;
                groupHead = index;
                groupTail.next = groupHead;//指向下一个groupHead
            }else {
                index = index.next;
            }

        }while (index!=null);
        return finalHead;
    }

    /**
     *
     * @param head group 's head node
     * @param k
     * @return pre 事实上已经是当前group 的head
     *         但是next 指针指向 null 注意重新赋值
     */
    private static ListNode reverse(ListNode head,int k){
        int count = 0;
        ListNode cur = head;
        ListNode next;
        ListNode pre = null;
        while(count < k){
            next = cur.next;
            cur.next = pre;
            //update
            pre = cur;
            cur = next;
            count +=1;
        }
        return pre;
    }

    /**
     *
     * leetcode 141
     * @param head
     * @return 是否存在环
     */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head,fast = head;
        if (head==null) return false;
        do {
            slow = slow.next;
            fast = (fast.next ==null) ?null:fast.next.next;
            if(fast ==null || slow == fast){
                break;
            }
        }while(true);
        return fast == null? false:true;
    }

    public static void main(String[] args){
        ListNode node = new ListNode(1);
        ListNode head = node;
        for (Integer ele : Lists.newArrayList(2, 3, 4, 5)) {
            node.next = new ListNode(ele);
            node = node.next;
        }

        ListNode listNode = reverseKGroup(head, 2);
        ListNode next = listNode.next;


    }
}
