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
 *      比较好的讲解 <https://www.bilibili.com/video/av23890827/?p=13>
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
        private boolean color = RED ;
        private Node left;
        private Node right;
        private Node parent;
        public Node(K key, V value, boolean color){
            this.key = key ;
            this.value = value ;
            this.color = color;
        }

        public void setLeft(Node left) {
            this.left = left;
            if(left != null ) left.parent = this;
        }

        public void setRight(Node right) {
            this.right = right;
            if(right != null) right.parent = this;
        }
    }

    private boolean isRed(Node node){
        if(Objects.isNull(node)) return false;
        return node.color == RED;
    }
    // search 查找
    public Node search(K key){
        if(Objects.isNull(key)) {
            throw new IllegalArgumentException("key is null !");
        }

        return search(root,key);

    }

    private Node search(Node node, K key){
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
    /**
     *
     * @param key
     * @param value
     * @return
     */
    public void insert(K key,V value) {
        if(Objects.isNull(key)){
            throw new IllegalArgumentException("key is null !");
        }
        if(root == null) {
            root = new Node(key,value,BLACK);
            return;
        }
        Node t = root;
        Node parent ;
        int cmp = 0;
        do{
            parent = t;
            cmp = key.compareTo(t.key);
            if(cmp > 0 ){
                t = t.right;
            }else if(cmp < 0 ){
                t = t.left;
            }else{
                t.value = value;
                return;
            }

        }while (t != null);

        Node x = new Node(key,value,RED);
        if(cmp > 0){
            parent.right = x;
            x.parent = parent;
        } else {
            parent.left = x;
            x.parent = parent;
        }
        // 调整红黑树 parent.color 是红色说明 parent不是root节点。只要不是root节点就一定有父节点
        while (x != root && parentOf(x).color == RED) {
            // 父节点是 祖父节点的左节点(一定是红色)
            if (grandOf(x).left == parentOf(x)) {
                Node uncle =  grandOf(x).right;
                // CASE 1 叔父节点是红色
                if(isRed(uncle)){
                    colorFlip(grandOf(x));
                    x = grandOf(x);
                }else{
                //叔父节点是黑色(null) and  Z型结构
                    if(x == parentOf(x).right){
                        //CASE 2 对父节点进行leftRotate
                        x = parentOf(x);
                        leftRotate(x);//x 的位置已经改变

                    }
                    // 注意 x 的终止条件 此时已经是平衡树
                    rightRotate(grandOf(x));
                }

            }else {
                //swap left and right
                Node uncle = grandOf(x).left;
                if(isRed(uncle)){
                     colorFlip(grandOf(x));
                     x = grandOf(x);
                }else {
                    if(x ==  parentOf(x).left){
                        // Z
                        x = parentOf(x);
                       rightRotate(x);
                    }
                    leftRotate(grandOf(x));
                }

            }
        }

        root.color  = BLACK;
    }



    /**
     *  旋转节点并交换颜色 旋转并不改变树的黑色节点的层高
     *
     *  并且交换旋转节点的 color 注意，因为 这里实现的红黑树只旋转红色节点的父节点
     *  ，所以其实2个节点交换颜色后  x-> y 转成 y-> x x的一定染成红色
     * @return
     */
    private void leftRotate(Node p){
        if(p == null) return;
        Node r = p.right;
        p.right = r.left;
        if(r.left != null){
            r.left.parent = p;
        }
        r.parent = p.parent;
        if(r.parent == null){// node 原本是root节点
            root = r;
        }else {
            // set y.parent.left or right = y
            if(p.parent.left == p){
                r.parent.left = r;
            } else{
                r.parent.right = r;
            }
        }
        r.left = p;
        p.parent = r;

        boolean color = p.color;
        p.color = r.color;
        r.color = color;
    }


    private void rightRotate(Node p){
        if(p == null) return;
        Node l = p.left;
        p.left = l.right;
        if(p.left !=null){
            p.left.parent = p;
        }
        l.parent = p.parent;
        if(p.parent == null ){
            root = l;
        }else {
            if(p.parent.left == p){
                l.parent.left = l;
            }else {
                l.parent.right = l;
            }
        }
        l.right = p;
        p.parent = l;
        boolean color = p.color;
        p.color = l.color;
        l.color = color;
    }

    private void colorFlip(Node node){
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    /**
     * 父节点
     * @param node
     * @return
     */
    private Node parentOf(Node node){
        return node == null?null:node.parent;
    }

    /**
     * 祖父节点
     * @param node
     * @return
     */
    private Node grandOf(Node node){
        return parentOf(parentOf(node));
    }

    private Node leftOf(Node node){
        return null == node ? null:node.left;
    }

    private Node rightOf(Node node){
        return null == node ? null:node.right;
    }


    /**
     * RED-Black Tree delete
     * 参考算法导论及 TreeMap src
     * @param key
     */
    public void delete(K key){
        if (key == null) throw new IllegalArgumentException(" delete key is null !");
        Node serach = search(root, key);
        if(serach== null){
            return;
        }

        // x 是要被删除的节点(对于 getMin Max 情形需要)
        // y 是要替代 x 的节点
        Node x = serach;

        /**
         * 查找被真删除的节点
         * 这里只找右子树的最小节点替换
         */
        if(serach.right != null && serach.left != null){
            Node max = serach.right;
            while (max.left != null){
                max = max.left;
            }
            x  = max;
            serach.key = x.key;
            serach.value = x.value;
        }
        // 此处 x 最多只有一个子节点,并且if y!=null y的双子一定都是 null（如果 y!=null 那么其实 y一定是 RED）
        Node y = (x.left == null? x.right:x.left);
        if(y != null){
            replaceAndBreak(x,y);
            if(x.color == BLACK)
                deleteFixUp(y);
        }else if(x.parent == null){
            // y is null
            root = null;
        }else {
            // y is null 此时用 x 本身进行处理 因为y == null 很难处理变换
            if(x.color == BLACK)
                deleteFixUp(x);

            if(x == x.parent.left){
                x.parent.left = null;
            }else {
                x.parent.right = null;
            }
            breakNode(x);
        }

    }

    /**
     *
     * @param rep 替代的节点
     *            替代节点 要么是 null 要么是红色节点
     *            具体逻辑见算法导论 RBTree
     *
     */
    private void deleteFixUp(Node rep){
        while(rep != root && !isRed(rep)){
            // 删除节点是左孩子
            if (rep == leftOf(parentOf(rep))){
                Node sib = rightOf(parentOf(rep));
                //CASE 1 rep 节点的兄弟节点是RED 则left旋转 rep.parent(一定是黑色)
                if(isRed(sib)){
                    leftRotate(parentOf(rep));
                    sib = rightOf(parentOf(rep));
                }
                //CASE 2 sib 节点双子都为黑色 （实际上第一次的时候只有双子都为null才会进入） 将 sib 涂红 然后向上寻找红色节点涂黑
                if (!isRed(sib.left) && !isRed(sib.right)){
                    sib.color = RED;
                    rep = parentOf(rep);
                }else{
                    //CASE 3 sib 左红右黑
                    if(!isRed(sib.right)){
                        rightRotate(sib);
                        sib = rightOf(parentOf(rep));
                    }
                    //CASE 4 右节点是红色,将右节点置黑
                    rightOf(sib).color = BLACK;
                    leftRotate(parentOf(rep));
                    rep = root;
                }
            }else {
                //  swap left and right mirror
                // 删除节点是右孩子
                Node sib = leftOf(parentOf(rep));
                if(isRed(sib)){
                    rightRotate(parentOf(rep));
                    sib = leftOf(parentOf(rep));
                }

                if(!isRed(sib.left) && !isRed(sib.right)){
                    sib.color = RED;
                    rep = parentOf(rep);
                }else {
                    // CASE 3 左右必有一红
                    if(!isRed(sib.left)){
                        leftRotate(sib);
                        sib = leftOf(parentOf(rep));
                    }
                    //CASE 4
                    leftOf(sib).color = BLACK;
                    rightRotate(parentOf(rep));
                    rep = root;
                }
            }
        }
        rep.color = BLACK;

    }

    /**
     *  用 y 替换 x 节点,并断开x节点与tree 的关联
     *  x 至多有一个子节点
     * @param x
     * @param y
     */
    private void replaceAndBreak(Node x, Node y){
        y.parent = x.parent;
        if(x.parent == null){
            root = y;
        }else if(x.parent.left == x){
            x.parent.setLeft(y);
        }else {
            x.parent.setRight(y);
        }
        breakNode(x);
    }

    private void breakNode(Node node){
        if(node == null) return;
        node.parent = node.left = node.right = null;
    }







    public static void main(String []args){

        ArrayList<Integer> integers = Lists.newArrayList( 10,11,12,13,14,15,16,17,18,19,20,1, 2, 3, 4, 5, 6, 7, 8, 9);
        RedBlackTree<Integer, Integer> integerIntegerRedBlackTree = new RedBlackTree<>();
        integers.forEach(each -> integerIntegerRedBlackTree.insert(each,each));

        ArrayList<Integer> deleteList = Lists.newArrayList( 2,5,3, 4, 5, 13,6, 7, 8, 9, 10,16,20,17,18,19,11,12,13,1,14);
        deleteList.forEach(each -> integerIntegerRedBlackTree.delete(each));


    }

}
