package datastructure.list;

/**
 * Created by panqiang on 2017/3/21.
 * list reverse
 */
public class LinkList_Reverse {

    public static Node list_Reverse_NoRecursive(Node head) {//输入开始节点
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

    public static void main(String[] args) {
        Node end = new Node(null, 0);
        Node cur = end;
        Node tmp;
        for (int i = 1; i < 8; i++) {
            tmp = new Node(cur, i);
            cur = tmp;
        }
       // datastructure.list.Node res=list_Reverse_NoRecursive(cur);
        Node res=cur;
        while(null!=res){
            System.out.println(res.getData());
            res=res.getNext();
        }

    }
}


 class Node {

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