package datastructure.tree;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.sun.tools.javac.util.List;
import sun.jvm.hotspot.utilities.Assert;
import java.util.ArrayList;
import java.util.Objects;

/**
 *  @author panqiang37@gmail.com
 *  @version 0.1
 *
 *  <p
 *      IntervalTree basic of RBTree
 *      附加size属性
 *      max 属性
 *  </p>
 *
 */
public class IntervalRBTree< T extends Comparable,V extends Comparable> {
    public static final boolean RED = true ;
    public static final boolean BLACK = false ;
    private Node root;
    public IntervalRBTree(){
    }

    /**
     *  interval field obj
     * @param <T,V>
     */
    public static class Interval<T extends Comparable,V extends Comparable> implements Comparable{
        private T low;
        private T high;
        private V value;
        public Interval(T low, T high,V value){
            if(low.compareTo(high) > 0){
                throw new IllegalArgumentException(" interval build low > high !");
            }
            this.low = low;
            this.high = high;
            this.value = value;
        }

        public T getLow() {
            return low;
        }

        public T getHigh() {
            return high;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Interval<?,?> intrval = (Interval<?,?>) o;

            if (!low.equals(intrval.low)) return false;
            return high.equals(intrval.high) && value.equals(intrval.value);
        }

        @Override
        public int hashCode() {
            int result = low.hashCode();
            result = 31 * result + high.hashCode();
            return result;
        }

        public static <T extends Comparable,V extends Comparable> Interval build(T low, T high,V value){
            return new Interval<T,V>(low,high,value);
        }

        @Override
        public int compareTo(Object o) {
               int cmp =  this.low .compareTo(((Interval) o).low);
               cmp = (cmp == 0)? this.high.compareTo(((Interval) o).high) : cmp;
               return cmp ==0 ? this.value.compareTo(((Interval) o).value):cmp;

        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("low", low)
                    .add("high", high)
                    .add("value", value)
                    .toString();
        }
    }

    /**
     * 计算逻辑的变化
     */
    private class Node {

        private Interval<T,V> key ;
        private T max;
        private boolean color = RED ;
        private Node left;
        private Node right;
        private Node parent;
        private int size = 1;
        public Node(Interval<T,V> key, boolean color){
            this.key = key ;
            this.color = color;
            this.max = key.getHigh();
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
    public Node search(Interval<T,V> key){
        if(Objects.isNull(key)) {
            throw new IllegalArgumentException("key is null !");
        }

        return search(root,key);

    }

    private Node search(Node node, Interval<T,V> key){
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


    /**
     * insert and update max and size
     * 插入查找的时候顺带进行 update max 操作
     * 因为插入的时候有可能是更新操作，所以不能自上而下进行resize + 1 操作
     * @param key
     * @return
     */
    public void insert(Interval<T,V> key) {
        if(Objects.isNull(key)){
            throw new IllegalArgumentException("key is null !");
        }
        if(root == null) {
            root = new Node(key,BLACK);
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
                return;
            }

        }while (t != null);

        Node x = new Node(key,RED);
        if(cmp > 0){
            parent.right = x;
            x.parent = parent;
        } else {
            parent.left = x;
            x.parent = parent;
        }
        // 调整红黑树 parent.color 是红色说明 parent不是root节点。只要不是root节点就一定有父节点
        while (x != root && parentOf(x).color == RED) {
            x.size =  reComputeSize(x);
            x.max = reMax(x);
            // 父节点是 祖父节点的左节点(一定是红色)
            if (grandOf(x).left == parentOf(x)) {
                Node uncle =  grandOf(x).right;
                // CASE 1 叔父节点是红色
                if(isRed(uncle)){
                    colorFlip(grandOf(x));
                    // reSize
                    x.parent.size = reComputeSize(x.parent);
                    x.parent.max = reMax(x.parent);
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
                     x.parent.size = reComputeSize(x.parent);
                     x.parent.max = reMax(x.parent);
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

        // compute Size from root to new InsertNode trace
        while(x != null){
            x.size = reComputeSize(x);
            x.max = reMax(x);
            x = parentOf(x);
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

        p.size = reComputeSize(p);
        p.max = reMax(p);
        r.size = reComputeSize(r);
        r.max = reMax(r);
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
        // reComputeSize
        p.size = reComputeSize(p);
        p.max = reMax(p);
        l.size = reComputeSize(l);
        l.max = reMax(l);
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
     *
     * @param key
     */
    public void delete(Interval<T,V> key){
        // todo reSize
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
            Node maxNode = serach.right;
            while (maxNode.left != null){
                maxNode = maxNode.left;
            }
            x  = maxNode;
            serach.key = x.key;
            serach.max = x.max;
        }
        // 此处 x 最多只有一个子节点,并且if y!=null y的双子一定都是 null（如果 y!=null 那么其实 y一定是 RED）
        Node y = (x.left == null? x.right:x.left);
        if(y != null){

            Assert.that(y.color == RED,"y color is RED and y !=null");

            replaceAndBreak(x,y);
            if(x.color == BLACK){
                deleteFixUp(y);
                Assert.that(y.color == BLACK,"y color is BLACK and y !=null");
            }
            // todo reSize
            while (y != null){
                y.size = reComputeSize(y);
                y.max = reMax(y);
                y = parentOf(y);
            }
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
            y = parentOf(x);
            breakNode(x);
            //todo resize
            while (y != null){
                y.size = reComputeSize(y);
                y.max = reMax(y);
                y = parentOf(y);
            }
        }

    }

    /**
     *
     * @param rep 替代的节点
     *            替代节点 要么是 null 要么是红色节点
     *            具体逻辑见算法导论 RBTree
     * resize
     *
     */
    private void deleteFixUp(Node rep){
        while(rep != root && !isRed(rep)){

            // 进入到此处说明 一定是删除掉了一个黑色节点，那么就需要补充一个黑色节点
            // 删除节点是左孩子
            if (rep == leftOf(parentOf(rep))){
                Node sib = rightOf(parentOf(rep));
                //CASE 1 rep 节点的兄弟节点是RED 则left旋转 rep.parent(一定是黑色) sib 变为黑色 转移到 2，3，4 状态。2，3，4 都是判断sib 子节点的颜色状态
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

    public int size(){
        return size(root);
    }
    private int size(Node node){
        return node == null ? 0: node.size;
    }
    private int reComputeSize(Node node){
        return size(node.left) + size(node.right) + 1;
    }


    public Node select(int n){
        if (n > size(root) || n < 1){
            return null;
        }
        return select(root,n);
    }
    private Node select(Node node,int n){
        int rank = size(node.left) + 1;
        if(n > rank){
            return select(node.right,n - rank);
        }else if(n <  rank){
            return select(node.left,n);
        }else {
            return node;
        }
    }

    public int rank(Interval<T,V> key){
        if(Objects.isNull(key)){
            throw new IllegalArgumentException(" rank key is null !");
        }
        return rank(root,key);
    }
    private int rank(Node node, Interval<T,V> key){
        int cmp = 0;
        int rank = 0;
        while (node != null){
            cmp = key.compareTo(node.key);
            if(cmp > 0){
                rank = rank + size(node.left) + 1;
                node = node.right;
            }else if(cmp <0 ){
                 node = node.left;
            }else {
                return rank + size(node.left) + 1;
            }
        }
        return -1;
    }

    private T max (Node node){
        if(node == null) return null;
        return node.max;
    }

    /**
     * math.max(node.high,node.left.max,node.right.max)
     * @param node
     * @return
     */
    private T reMax(Node node){

        T t = selectMax(max(node.left),max(node.right),node.key.getHigh());
        return t;
    }


    private T selectMax(T... params){

        T t = params[0];
        for (int i = 1; i < params.length; i++) {
            if(t == null){
                t = params[i];
                continue;
            }
            if(params[i] != null){
                t = t.compareTo(params[i]) > 0 ? t:params[i];
            }

        }
        return t;
    }

    /**
     *  区间树查询 key ∏ v = Ø
     * @param key
     * @return
     */
    public Interval<T,V> intervalSearch(Interval<T,V> key){
        Node x = root;
        while (x != null && !overlap(key,x.key)){
            if(x.left!=null && max(x.left).compareTo(key.getLow()) >= 0){
                x = x.left;
            }else {
                x = x.right;
            }
        }
        return x == null ? null:x.key;
    }

    /**
     *  查找所有交集数据
     *  采用广度优先的策略进行查找减枝
     * @param key
     * @return
     */
    public List<Interval<T,V>> intervalSearchAll(Interval<T,V> key){
        searchAll(root,key);
        return null;
    }
    private void searchAll(Node node,Interval<T,V> key){
        if(node !=null && overlap(key,node.key)){
            System.out.println(node.key);
        }
        if(leftOf(node)!=null && leftOf(node).max.compareTo(key.low) >= 0){
            searchAll(leftOf(node),key);
        }
        if(rightOf(node)!=null && rightOf(node).max.compareTo(key.low) >=0){
            searchAll(rightOf(node),key);
        }

    }

    /**
     *  计算 m 与 n 是否存在相交部分
     * @param m
     * @param n
     * @return
     */
    private boolean overlap(Interval<T,V> m,Interval<T,V> n){

        return !(m.getHigh().compareTo(n.getLow()) < 0 ||
                m.getLow().compareTo(n.getHigh()) > 0 );

    }

    public static void main(String []args){

        ArrayList<Integer> integers = Lists.newArrayList( 10,11,12,13,14,15,16, 2, 3, 4, 5, 6, 7, 8, 9);//17,18,19,20,1,
        IntervalRBTree<Integer,Integer> tree = new IntervalRBTree<Integer,Integer>();
        for (Integer integer : integers) {
            Interval<Integer,Integer> integerInterval = new Interval<>(integer, integer+8,integer);
            tree.insert(integerInterval);
        }
        List<Interval<Integer, Integer>> intervals = tree.intervalSearchAll(new Interval<Integer, Integer>(18, 20, null));


    }

}
