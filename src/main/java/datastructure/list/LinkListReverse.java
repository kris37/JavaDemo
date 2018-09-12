package datastructure.list;

import java.util.Objects;

/**
 * Created by panqiang on 2017/3/21.
 * list reverse
 */
public class LinkListReverse {

    /**
     *  非递归方法反转链表
     * @param head
     * @return
     */
    public static Node reverseNoRecursive(Node head) {
        Node pre = head;
        Node current_node=head.getNext();
        Node next_node;
        // next ==null dealwith
        while (null !=current_node ) {//not lastest node
            next_node=current_node.getNext();
            current_node.setNext(pre);
            pre=current_node;
            current_node = next_node;
        }
        head.setNext(null);
        return pre;
    }

    /**
     * 1,2,3,4,5
     * 5 null
     * @param pre
     * @param cur
     * @return 新的head 节点
     */
    public static Node reverseRecursive(Node pre,Node cur)  {

        if(Objects.isNull(cur)){
            return pre;
        }
        Node next = cur.next;
        cur.next  = pre;
        return reverseRecursive(cur,next);
    }

    public static void main(String[] args) {

        Node start = new Node(null, 0);
        Node cur = start;
        Node tmp;
        for (int i = 1; i < 8; i++) {
            tmp = new Node(null,i);
            cur.next = tmp;
            cur = tmp;
        }
       // datastructure.list.Node res=list_Reverse_NoRecursive(cur);
        //Node res=cur;
        Node res = reverseRecursive(null, start);
        while(null!=res){
            System.out.println(res.getData());
            res=res.getNext();
        }



    }

    private static class Node {

        private Node next;
        private int data;
        public Node(Node next, int data) {
            this.next = next;
            this.data = data;
        }
        public Node getNext() {
            return next;
        }
        public void setNext(Node next) {
            this.next = next;
        }
        public int getData() {
            return data;
        }
        public void setData(int data) {
            this.data = data;
        }


    }
}

