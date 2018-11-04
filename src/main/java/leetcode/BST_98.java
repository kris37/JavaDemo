package leetcode;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/31 下午5:19
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     BST problem
 * <br>
 */
public class BST_98 {

    private static class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
    }


    public static void main(String [] args){
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(1);
        treeNode.right = new TreeNode(2);
        treeNode.right.right = new TreeNode(3);
        treeNode.right.left = new TreeNode(4);
        System.out.println(new BST_98().inordeByLoop(treeNode));
    }
    /**
     * 98 判断是否是有效的BST
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {

        LinkedList<Integer> pre = new LinkedList<>();
        return inorder(root,pre);
    }

    /**
     * BST 不能有重复的
     *  左子树的max 右子树的最小 node.val > max(node.left) && node.val < min(node.right)
     * @param node
     * @return
     */
    private boolean isValidBSTByRecursive(TreeNode node,Integer max,Integer min){
        if (node == null) return true;
        if(max != null && node.val >= max){
            return false;
        }
        if(min != null && node.val <= min){
            return false;
        }
        return isValidBSTByRecursive(node.left,node.val,min) && isValidBSTByRecursive(node.right,max,node.val);
    }

    private boolean inorder(TreeNode node,LinkedList<Integer> pre){
        if(node == null) return true;
        boolean inorder = inorder(node.left, pre);
        if (!inorder){
            return false;
        }
        if( (!pre.isEmpty()) && node.val <= pre.pop()){
            return false;
        }
        pre.push(node.val);
        return inorder(node.right,pre);
    }

    private boolean inordeByLoop(TreeNode node){
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(node);
        while(!stack.isEmpty()){
            TreeNode pop = stack.pop();
            System.out.println(pop.val);
            if(pop.left != null){
                stack.push(pop.left);
            }
            if(pop.right !=null){
                stack.push(pop.right);
            }
        }

        return false;

    }



}
