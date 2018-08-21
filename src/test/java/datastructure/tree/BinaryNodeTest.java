package datastructure.tree;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/6 上午10:33
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
class BinaryNodeTest {
    static BinaryTree.BinaryNode root = null;
    static {
        BinaryTree.BinaryNode binaryNode1 = new BinaryTree.BinaryNode(1);
        BinaryTree.BinaryNode binaryNode2 = new BinaryTree.BinaryNode(2);
        BinaryTree.BinaryNode binaryNode3 = new BinaryTree.BinaryNode(3);
        BinaryTree.BinaryNode binaryNode4 = new BinaryTree.BinaryNode(4);
        BinaryTree.BinaryNode binaryNode5 = new BinaryTree.BinaryNode(5);
        BinaryTree.BinaryNode binaryNode6 = new BinaryTree.BinaryNode(6);
        BinaryTree.BinaryNode binaryNode7 = new BinaryTree.BinaryNode(7);
        BinaryTree.BinaryNode binaryNode8 = new BinaryTree.BinaryNode(8);
        binaryNode1.setLeft(binaryNode2);
        binaryNode1.setRight(binaryNode3);
        binaryNode2.setLeft(binaryNode4);
        binaryNode2.setRight(binaryNode5);
        binaryNode3.setRight(binaryNode6);
        binaryNode5.setLeft(binaryNode7);
        binaryNode5.setRight(binaryNode8);
        root =binaryNode1;
    }


    /**
     *
     */


    public static void main(String [] args){

        int i = BinaryTree.getLeafNumsNoRecursive(root);
        System.out.println(i);
    }

}