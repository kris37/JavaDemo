package leetcode;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/4 下午9:46
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     BST BFS 类型题 102 107
 *     正常来讲是一般通过 queue 的遍历
 *     也可以通过递归的方式进行 处理
 * <br>
 */
public class TreeTraversal_102_107_104_111_101 {
     private class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;

         TreeNode(int x) {
             val = x;
         }
     }


    /**
     *  leetcode 102 107
     *  BFS
     *  可以 统计 每一层的节点数据
     *  也可以插入特殊节点
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null) return new LinkedList<>();
        LinkedList<TreeNode>  queue = new LinkedList<>();
        List<List<Integer>> list = new ArrayList<>();
        //init
        queue.push(root);
        while (!queue.isEmpty()){
            int size = queue.size();
            List<Integer> ele = new ArrayList<>(2<<5);
            while(size > 0 ){
                TreeNode node = queue.poll();
                if(node.left != null){
                    queue.add(node.left);
                }
                if(node.right != null){
                    queue.add(node.right);
                }
                ele.add(node.val);
                size -- ;
            }
            list.add(ele);
        }

        Collections.reverse(list);// or push a stack
        return list;
    }


    /**
     * leetcode 104
     * 1。分治法
     * 2。 依据 102 的分层遍历统计层数
     *
     * @param root
     * @return
     */
    public static int maxDepth(TreeNode root) {
        return maxdepthByRecursive(root);
    }

    private static int maxdepthByRecursive(TreeNode node){
        if(node == null) return 0;
        return Math.max(maxdepthByRecursive(node.left)+1,
                maxdepthByRecursive(node.right) +1);

    }

    private static int maxDepathByLoop(TreeNode node){
        int max = 0;
        if(node == null) return max;
        LinkedList<TreeNode> queue = new LinkedList();
        queue.add(node);
        while(!queue.isEmpty()){
            int size = queue.size();
            while (size > 0){
                TreeNode poll = queue.poll();
                if(poll.left != null){
                    queue.add(poll.left);
                }
                if(poll.right != null){
                    queue.add(poll.right);
                }
                size --;
            }
            max ++;
        }
        return max;
    }


    public static int minDepth(TreeNode root) {
        return minDepthByRecursive(root);
    }

    private static int minDepthByRecursive(TreeNode node){
        if(node == null) return 0;
        int left = minDepthByRecursive(node.left);
        int right = minDepthByRecursive(node.right);
        if(node.left == null || node.right == null){
            return Math.max(left,right) +1;
        }else {
            return Math.min(left,right) +1;
        }
    }

    /**
     *  leetcode 101
     * @param root
     * @return
     */
    public static boolean isSymmetric(TreeNode root) {
        //return isSymmetricByLoop(root);
        if(root == null) return true;
        return isSymmetricByRecursive(root.left,root.right);
    }

    /**
     *
     * @param node
     * @return
     */
    public static boolean isSymmetricByLoop(TreeNode node){
        if(node == null) return true;
        LinkedList<TreeNode>  queue = new LinkedList();
        queue.add(node.left);
        queue.add(node.right);
        while (!queue.isEmpty()){
            TreeNode first = queue.pollFirst();
            TreeNode last = queue.pollLast();
            if(first == null && last == null){
                continue;
            }
            if(first == null || last == null){
                return false;
            }
            if(first.val == last.val){
                return false;
            }
            queue.addFirst(first.right);
            queue.addFirst(first.left);
            queue.addLast(last.left);
            queue.addLast(last.right);
        }
        return true;
    }

    /**
     *
     * @return
     */
    public static boolean isSymmetricByRecursive(TreeNode left,TreeNode right){

        if(left ==null || right == null){
            return left == right;
        }
        if(left.val != right.val){
            return  false;
        }

        return isSymmetricByRecursive(left.left,right.right)
                && isSymmetricByRecursive(left.right,right.left);


    }
}
