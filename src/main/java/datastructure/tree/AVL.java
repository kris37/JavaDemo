package datastructure.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/30 下午5:53
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *
 * <br>
 */
public class AVL<K extends Comparable,V> {

    private Node root;
    public  class Node{
        private K key;
        private V value;
        private int height = -1 ;
        private int size = 0;
        private Node left;
        private Node right;
        public Node(int height,int size, K key,V value){
            this.height = height;
            this.size = size;
            this.key = key;
            this.value = value;
        }
    }


    public int size(){
        return size(root);
    }
    private int size(Node node){
        if(Objects.isNull(node)){
            return 0;
        }
        return node.size;
    }

    public int height(){
        return height(root);
    }

    private int height(Node node){
        if(Objects.isNull(node)){
            return -1;
        }
        return node.height;
    }

    public boolean contains(K key){

        return null != getValue(key);
    }

    //查询
    public Node getValue(K key){
        return getValue(root,key);
    }

    private Node getValue(Node node,K key){
        if(Objects.isNull(node)){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp > 0 ){
            return getValue(node.right,key);
        }else if(cmp < 0){
            return getValue(node.left,key);
        }else{
            return node;
        }
    }

    /**
     *    增 && 改
     *    因为增 删都可能影响相对的 subroot
     */

    public void insert(K key,V value){
        root = insert(root,key,value);
    }

    private Node insert(Node node,K key,V value){
        if(Objects.isNull(node)){
            return new Node(0,1,key,value);
        }
        int cmp = key.compareTo(node.key);
        if(cmp > 0){
            node.right = insert(node.right,key,value);
        }else if(cmp < 0){
            node.left = insert(node.left,key,value);
        }else {
            node.value = value;
        }

        node.size = currentSize(node);
        node.height = currentHeight(node);
        return reBalance(node);

    }

    //delete 删除

    public void delete (K key){
        if(Objects.isNull(key)){
            throw new IllegalArgumentException(" input key is null");
        }
        if(contains(key)) {
            root = delete(root, key);
        }
    }

    /**
     *  1. if deleteNode is  leafnode => subroot.x = null update size and height recursive
     *  2. if deleteNode only has left | right node ，则subroot.x = currentnode.x update size and height recursive
     *  3
     *      3.1 deleteNode has both left and right ,findMax(deleteNode.left)
     *      3.2 deleteNode has both left and right ,findMin(deleteNode.right) update all size and height
     *
     *
     * @param node
     * @param key
     * @return
     */
    private Node delete(Node node,K key){
        if(Objects.isNull(node)){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp > 0){
            node.right = delete(node.right,key);
        }else if(cmp < 0){
            node.left = delete(node.left,key);
        }else {
            // if leaf node
            if(node.height == 0){
                return null;
            }
            if(Objects.isNull(node.left)){
               return node.right;
            }
            if(Objects.isNull(node.right)){
               return node.left;
            }

            Node tmp = node;

            // both left and right node
            if(balanceFactor(node) > 0){

                node =  findMax(node.left);
                node.left = deleteMaxNode(tmp.left);
                node.right = tmp.right;

            }else{
                node =  findMin(node.right);
                node.right = deleteMinNode(tmp.right);
                node.left = tmp.left;
            }
        }

        node.size = currentSize(node);
        node.height = currentHeight(node);
        return reBalance(node);
    }

    private Node findMax(Node node){
        if(Objects.isNull(node.right)){
            return node;
        }
        return findMax(node.right);
    }

    private Node findMin(Node node){
        if(Objects.isNull(node.left)){
            return node;
        }
        return findMin(node.left);
    }


    private Node deleteMaxNode(Node node){
        if(Objects.isNull(node.right)){
            return node.left;
        }
        node.right = deleteMaxNode(node.right);
        node.size = currentSize(node);
        node.height = currentHeight(node);
        return reBalance(node);
    }

    private Node deleteMinNode(Node node){
        if(Objects.isNull(node.left)){
            return node.right;
        }
        node.left = deleteMinNode(node.left);
        node.size = currentSize(node);
        node.height = currentHeight(node);
        return reBalance(node);
    }

    private Node reBalance(Node node){
        int factor = balanceFactor(node);
        if(factor < -1){
            if(balanceFactor(node.right) > 0){
                node.right = rightRotate(node.right);
            }
            node = leftRotate(node);
        }else if(factor > 1){
            if(balanceFactor(node.left) <0){
                node.left  = leftRotate(node.left);
            }
            node = rightRotate(node);
        }
        return node;
    }

    private int balanceFactor(Node node){
        return height(node.left) - height(node.right);
    }

    private Node leftRotate(Node  node){
        Node tmp = node.right;
        node.right = tmp.left;
        tmp.left = node;
        //重新计算 node 及 tmp 节点的高度 size，先计算node节点的
        node.size = currentSize(node);
        node.height = currentHeight(node);
        tmp.size = currentSize(tmp);
        tmp.height = currentHeight(tmp);
        return tmp;
    }
    private Node rightRotate(Node node){
        Node tmp = node.left;
        node.left =  tmp.right;
        tmp.right =  node;
        node.size = currentSize(node);
        node.height = currentHeight(node);
        tmp.size = currentSize(tmp);
        tmp.height = currentHeight(tmp);
        return tmp;
    }


    private int currentSize(Node node){
        return size(node.left) + size(node.right) +1;
    }

    private int currentHeight(Node node){
        return Math.max(height(node.left),height(node.right)) +1;
    }


    /**
     * inorder lookup
     */
    public List<K> inOrder(){
        List list = new ArrayList<K>();
        inOrder(root,list);
        return list;

    }

    private List inOrder(Node node,List<K> list){
        if(Objects.isNull(node)){
            return list;
        }
        if(!Objects.isNull(node.left)){
            inOrder(node.left,list);
        }
        list.add(node.key);
        if(!Objects.isNull(node.right)){
            inOrder(node.right,list);
        }
        return list;
    }



    public static void main(String [] args){

        List<Integer> list = Arrays.asList(3,4,2,1,4,6,3,6,8,4,6,7,2,4,310,23,35,2,34);
        AVL<Integer, Integer> avl = new AVL<>();
        list.forEach(each -> {
            avl.insert(each,each);
            List<Integer> nodes = avl.inOrder();
            for (Integer node:nodes
                    ) {
                System.out.print(node + " -> ");
            }
            System.out.println(" height: "+ avl.height() + " size: "+ avl.size());
        });

        list.forEach(each -> {
            avl.delete(each);
            List<Integer> nodes = avl.inOrder();
            for (Integer node:nodes
                    ) {
                System.out.print(node + " -> ");
            }
            System.out.println(" height: "+ avl.height() + " size: "+ avl.size());
        });

    }

}
