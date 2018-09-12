package datastructure.tree;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Objects;

/**
 *  @author panqiang37@gmail.com
 *  @version 0.1
 *
 *  <p
 *      简单实现红黑树
 *  </p>
 *
 */
public class RedBlackTree<K extends Comparable,V> {
    public static final boolean RED = true ;
    public static final boolean BLACK = false ;
    private Node root;
    public RedBlackTree(){
    }

    private class Node{

        private K key ;
        private V value ;
        private int size ;
        private boolean color = RED ;
        private Node left;
        private Node right;
        private Node parent;
        public Node(K key, V value, boolean color, int size){
            this.key = key ;
            this.value = value ;
            this.color = color;
            this.size = size;
        }
    }

    private boolean isRed(Node node){
        if(Objects.isNull(node)) return false;
        return node.color == RED;
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

    // serach 查找
    public Node serach(K key){
        if(Objects.isNull(key)) {
            throw new IllegalArgumentException("key is null !");
        }

        return serach(root,key);

    }

    private Node serach(Node node,K key){
        while(!Objects.isNull(node)){
            int cmp = key.compareTo(node.key);
            if(cmp > 0 ){
                node = node.right;
            }else if (cmp < 0 ){
                node = node.left;
            }else {
                return node;
            }
        }
        return null;
    }

    // insert and update

    public void insert(K key,V value){
        if(Objects.isNull(key)){
            throw new IllegalArgumentException("key is null !");
        }
        insert(root,key,value);
        root.color = BLACK;
    }

    private void setParent(Node node){
        if(node.left != null){
            node.left.parent = node;
        }
        if(node.right != null){
            node.right.parent = node;
        }
    }

    /**
     *  todo color and rebalance
     *
     * @param node
     * @param key
     * @param value
     * @return
     */
    private void insert(Node node,K key,V value) {
        if(node == null) {
            root = new Node(key,value,BLACK,1);
            return;
        }
        Node x = new Node(key,value,RED,1);
        //首先插入节点
        while(true){
            int cmp = key.compareTo(node.key);
            if(cmp >0){
                if(node.right == null){
                    node.right = x;
                    setParent(node);
                    break;
                }
                node = node.right;
            }else if(cmp < 0){
                if(node.left == null) {
                    node.left = x ;
                    setParent(node);
                    break;
                }
                node = node.left;
            }else {
                node.value = x.value;
                return ;
            }

        }

        // 到在此处的 node 节点是插入节点 x 的父节点
        // 调整红黑树
        while (x != root && x.parent.color == RED) {
            // 父节点是 祖父节点的左节点(一定是红色)
            if (x.parent.parent.left == x.parent) {
                Node uncle =  x.parent.parent.right;
                // CASE 1 叔父节点是红色
                if(isRed(uncle)){
                    x = colorFlip(x.parent.parent);
                    break;
                }else{
                //叔父节点是黑色 and  Z型结构
                    if(x == x.parent.right){
                        //CASE 2 对父节点进行leftRotate
                        x = leftRotate(x.parent);
                    }
                    x = rightRotate(x.parent);
                }

            }else {
                //swap left and right
                Node uncle = x.parent.parent.left;
                if(isRed(uncle)){
                    x = colorFlip(x.parent.parent);
                    break;
                }else {
                    if(x ==  x.parent.left){
                        // Z
                        x = rightRotate(x.parent);
                    }
                    x = leftRotate(x.parent.parent);
                }

            }
        }
    }



    /**
     *  旋转节点
     *  并且交换旋转节点的 color 注意，因为 这里实现的红黑树只旋转红色节点的父节点
     *  ，所以其实2个节点交换颜色后  x-> y 转成 y-> x x的一定染成红色
     * @param node
     * @return
     */
    private Node leftRotate(Node node){

        Node y = node.right;
        y.parent = node.parent;
        if(node == root){
            root = y;
        }else {
            // set y.parent.left or right = y
            if(y.parent.left == node){
                y.parent.left = y;
            }else {
                y.parent.right = y;
            }
        }

        node.right = y.left;
        if(y.left != null) {
            y.left.parent = node;
        }
        y.left = node;
        node.parent = y;
        // exchang node color
        boolean color = y.color;
        y.color = node.color;
        node.color = color;
        return y;
    }

    private Node rightRotate(Node node){
        Node y = node.left;
        y.parent = node.parent;
        if(node == root) {
            root = y;
        }else {
            // set y.parent.left or right = y
            if(y.parent.left == node){
                y.parent.left = y;
            }else {
                y.parent.right = y;
            }
        }

        node.left = y.right;
        if(y.right != null) {
            y.right.parent = node;
        }
        y.right = node;
        node.parent = y;

        boolean color = y.color;
        y.color = node.color;
        node.color = color;
        return y;
    }

    private Node colorFlip(Node node){
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
        return node;
    }




    public static void main(String []args){
        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12,13,14,15,16,17,18,19,20);
        RedBlackTree<Integer, Integer> integerIntegerRedBlackTree = new RedBlackTree<>();

        integers.forEach(each -> integerIntegerRedBlackTree.insert(each,each));


    }

}
