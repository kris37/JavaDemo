/**
 * Created by panqiang on 2017/3/23.
 */
public class List_Reverse {
    public static void main(String []args){
        Node head =build_List(8);
        System.out.println(head.getNext());
    while(null!=head){
        System.out.print(head.getData()+"=>");
        head=head.getNext();
    }
        Node res=reverse_head_Norecursive(build_List(8));
        System.out.println("reverse:");
        while(null!=res){
            System.out.print(res.getData()+"=>");
            res=res.getNext();
        }

    }
    public static Node build_List(int length){
        Node lastest=new Node(null,length);
        Node temp=lastest;
        for(int i=length-1;i>0;i--){
            temp=new Node(temp,i);
        }
        return temp;//node head
    }
    public static Node reverse_head_Norecursive(Node origin_head){
        Node pre=origin_head;
        Node current=origin_head.getNext();
        Node next=null;
        while(null!=current){
            next=current.getNext();
            current.setNext(pre);//
            pre=current;
            current=next;
        }
        origin_head.setNext(null);
        return pre;
    }
}

class Node{
    private Node next;
    private int data;
    public Node(Node node, int data){
        this.next=node;
        this.data=data;
    }
    public Node(Node node){
        this.next=node;
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