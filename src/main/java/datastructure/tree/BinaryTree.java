package datastructure.tree;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/3 下午1:43
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     搜索二叉树
 * <br>
 */
public class BinaryTree {


    public static void main(String [] args){




        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode6 = new TreeNode(6);
        TreeNode treeNode7 = new TreeNode(7);
        TreeNode treeNode8 = new TreeNode(8);

        treeNode1.setFirstChild(treeNode2);
        treeNode2.setNextSibling(treeNode3);
        treeNode2.setFirstChild(treeNode4);
        treeNode4.setNextSibling(treeNode5);
        treeNode5.setFirstChild(treeNode7);
        treeNode7.setNextSibling(treeNode8);
        treeNode3.setFirstChild(treeNode6);


        //preTravel(treeNode1); 二叉树的遍历
        midTravel(treeNode1);
        System.out.println("");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        // binary node

        BinaryNode binaryNode1 = new BinaryNode(1);
        BinaryNode binaryNode2 = new BinaryNode(2);
        BinaryNode binaryNode3 = new BinaryNode(3);
        BinaryNode binaryNode4 = new BinaryNode(4);
        BinaryNode binaryNode5 = new BinaryNode(5);
        BinaryNode binaryNode6 = new BinaryNode(6);
        BinaryNode binaryNode7 = new BinaryNode(7);
        BinaryNode binaryNode8 = new BinaryNode(8);
        binaryNode1.setLeft(binaryNode2);
        binaryNode1.setRight(binaryNode3);
        binaryNode2.setLeft(binaryNode4);
        binaryNode2.setRight(binaryNode5);
        binaryNode3.setRight(binaryNode6);
        binaryNode5.setLeft(binaryNode7);
        binaryNode5.setRight(binaryNode8);

        //BinaryPreTravel(binaryNode1);
        binaryMidTravel(binaryNode1);

        // 二叉树的最大深度








    }

    /**
     * 前序遍历 适用于多叉树和二叉树
     *
     * @param root
     */
    public static void preTravel(TreeNode root){

        System.out.print(root.getNodeID()+" -> ");
        if(root.getFirstChild()!=null){
            preTravel(root.getFirstChild());
        }
        if (root.getNextSibling()!=null){
            preTravel(root.getNextSibling());
        }

    }

    /**
     * 指数用于 二叉树
     * @param root
     */
    public static void midTravel(TreeNode root){
        if(root.getFirstChild()!=null){
            midTravel(root.getFirstChild());
        }
        System.out.print(root.getNodeID()+" -> ");
        if(root.getNextSibling()!=null){
            midTravel(root.getNextSibling());
        }

    }



    public static void BinaryPreTravel(BinaryNode root){
        System.out.print(root.nodeID + " -> ");
        if(!Objects.isNull(root.getLeft())){
            BinaryPreTravel(root.getLeft());
        }
        if(!Objects.isNull(root.getRight())){
            BinaryPreTravel(root.getRight());
        }
    }

    public static void binaryMidTravel(BinaryNode root){
        if(!Objects.isNull(root.getLeft())){
            binaryMidTravel(root.getLeft());
        }
        System.out.println(root.getNodeID());
        if(!Objects.isNull(root.getRight())){
            binaryMidTravel(root.getRight());
        }

    }

    /**
     * 递归计算
     * 类似于二分法，返回左子右子树中最大的深度  + 1
     * 二叉树的深度
     */
    public static int maxDepthRecursive(BinaryNode root){

        if(Objects.isNull(root)){
            return 0;
        }

        int left = maxDepthRecursive(root.getLeft());

        int right = maxDepthRecursive(root.getRight());

        return left > right? left +1: right +1;

    }

    /**
     *  辅助队列计算二叉树队列
     */

    public static int maxDepthNoRecursive(BinaryNode root){
        LinkedList<BinaryNode> queue = new LinkedList();
        queue.offer(root);

        int cur,withd,depth=0,maxwithd=0;

        while(!queue.isEmpty()){
            int size = queue.size();
            maxwithd = Math.max(size,maxwithd);
            while(size >0){
                size --;
                BinaryNode poll = queue.poll();
                if(!Objects.isNull(poll.getLeft())){
                    queue.offer(poll.getLeft());
                }
                if(!Objects.isNull(poll.getRight())){
                    queue.offer(poll.getRight());
                }


            }
            depth ++;
        }
        return depth;

    }


    /**
     * 计算最小的树深度
     *
     */
    public static int minDepthRecursive(BinaryNode root){

        if(Objects.isNull(root)){
            return Integer.MAX_VALUE;
        }
        /**
         * 如果左右节点存在 null 则此节点的树深度最小为 1
         */
        if(Objects.isNull(root.getLeft()) && Objects.isNull(root.getRight())){
            return 1;
        }

        int left = minDepthRecursive(root.getLeft());

        int right = minDepthRecursive(root.getRight());

        return left < right? left +1: right +1;

    }

    /**
     *
     *  非递归方式查找最小子树
     *
     * @param root
     * @return
     */
    public static int minDepthNoRecursive(BinaryNode root){

        Queue<BinaryNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth=0,withd=0;

        while (!queue.isEmpty()){
            boolean off = false;

            int size = queue.size();
            BinaryNode poll = queue.poll();
            while(size>0 && !off){
                size --;
                if(poll.getLeft() == null && poll.getRight() == null) {
                    off = true;
                }
                if(!Objects.isNull(poll.getLeft())){
                    queue.offer(poll.getLeft());
                }
                if(!Objects.isNull(poll.getRight())){
                    queue.offer(poll.getRight());
                }
            }


            if(off){
                return depth;
            }else {
                depth ++;
            }
        }
        return depth;
    }


    /**
     * 递归方式计算 节点数
     * @param node
     * @return
     */
    public static int getNodeNumRecursive(BinaryNode node){
        if(node == null){
            return 0;
        }

        int left = getNodeNumRecursive(node.getLeft());
        int right = getNodeNumRecursive(node.getRight());
        return left + right + 1;
    }

    /**
     *
     * @param node
     * @return
     */
    public static int getNodeNumNoRecursive(BinaryNode node){
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.offer(node);
        int nodeNum = 0;
        while (!queue.isEmpty()){
            int size  = queue.size();
            nodeNum += size;
            while(size > 0){
                size --;
                BinaryNode poll = queue.poll();

                if(poll.getLeft()!=null){
                    queue.offer(poll.getLeft());
                }
                if(poll.getRight()!=null){
                    queue.offer(poll.getRight());
                }

            }
        }
        return nodeNum;
    }


    /**
     * 递归方式计算叶子节点数
     * left and right 都是null的时候就是叶子节点
     */
    public static int getLeafNumsRecursive(BinaryNode node){
        if(Objects.isNull(node)){
            return 0;
        }
        if(node.getLeft() ==null && node.getRight() ==null){
            return 1;
        }
        int left = getLeafNumsRecursive(node.getLeft());
        int right = getLeafNumsRecursive(node.getRight());
        return left + right;
    }


    /**
     *
     * @param node
     * @return
     */

    public static int getLeafNumsNoRecursive(BinaryNode node){
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.offer(node);
        int leafNums = 0 ;
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size>0){
                size --;
                BinaryNode poll = queue.poll();

                if(poll.getLeft()==null && poll.getRight() ==null){
                    leafNums ++;
                    break;
                }
                if(poll.getLeft()!=null){
                    queue.offer(poll.getLeft());
                }
                if(poll.getRight()!=null){
                    queue.offer(poll.getRight());
                }
            }

        }
        return leafNums;

    }







    public static class TreeNode{
        private int nodeID;
        private TreeNode firstChild;
        private TreeNode nextSibling;
        public TreeNode(int id){
            this.nodeID = id;
        }

        public int getNodeID() {
            return nodeID;
        }

        public void setNodeID(int nodeID) {
            this.nodeID = nodeID;
        }

        public TreeNode getFirstChild() {
            return firstChild;
        }

        public void setFirstChild(TreeNode firstChild) {
            this.firstChild = firstChild;
        }

        public TreeNode getNextSibling() {
            return nextSibling;
        }

        public void setNextSibling(TreeNode nextSibling) {
            this.nextSibling = nextSibling;
        }
    }


    public static class BinaryNode{
        private int nodeID;
        private BinaryNode left;
        private BinaryNode right;

        public BinaryNode(int id){
            this.nodeID = id;
        }

        public int getNodeID() {
            return nodeID;
        }

        public void setNodeID(int nodeID) {
            this.nodeID = nodeID;
        }

        public BinaryNode getLeft() {
            return left;
        }

        public void setLeft(BinaryNode left) {
            this.left = left;
        }

        public BinaryNode getRight() {
            return right;
        }

        public void setRight(BinaryNode right) {
            this.right = right;
        }
    }

}
