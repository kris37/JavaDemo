package leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/28 下午9:33
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     leet code k-sum type problem
 *     对于 k-sum 的问题 可以使用递归的思想 将 问题降到子问题为 2sum 进行求解
 *     但是还没有想到好的办法在运算的时候将结果去重
 *     现在的做法是
 *      将结果数组进行封装为对象 采用 tree set 进行比较去重
 * <br>
 */
public class KSum_15 {



    public static void main(String [] args){
        List<List<Integer>> lists = new KSum_15().threeSum(new int[]{0, 0, 0});
        System.out.println(lists);
    }
    public List<List<Integer>> threeSum(int[] nums) {
       return sum(nums,3);
    }

    public  List<List<Integer>> sum(int [] ints,int k){
        if (ints == null || ints.length < k){
            return null;
        }
        Arrays.sort(ints);
        List<List<Integer>> result= new LinkedList<List<Integer>>();
        LinkedList<Integer> cur = new LinkedList();
        List<List<Integer>> lists =  ksum(ints, 0, 3, 0, result, cur);
        // 去重
        TreeSet<Node> set = new TreeSet<>();
        lists.stream().forEach(ele -> {
            set.add(Node.create(ele.toArray(new Integer[ele.size()])));
        });
        return set.stream().map(node -> Arrays.asList(node.getKey())).collect(Collectors.toList());
    }

    private static class Node implements Comparable<Node>{
        private Integer [] key;
        public static Node create(Integer [] key){
            Arrays.sort(key);
            return new Node(key);
        }
        private Node(Integer [] key){
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            return Arrays.equals(key, node.key);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(key);
        }

        public Integer[] getKey() {
            return key;
        }

        @Override
        public int compareTo(Node o) {
            for (int i = 0; i <this.key.length ; i++) {
                if (this.key[i] != o.key[i]){
                    return this.key[i] - o.key[i];
                }
            }
            return 0;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.key.length << 1);
            for (int i : this.key) {
                stringBuilder.append( ","+i );
            }
            return stringBuilder.toString().substring(1);
        }
    }

    /**
     *  ksum 通用计算
     * @param nums sort from small to big
     * @param start 循环开始索引
     * @param k
     * @param taget sum 目标
     * @return
     */
    private List<List<Integer>> ksum(int [] nums,int start,int k,int taget,List<List<Integer>> result,LinkedList<Integer> cur){
        //  退化为2sum 查找
        if (k == 2){
          int i = start;
          int j = nums.length - 1;
          while(i<j){
              // 处理连续相同数字
              int twosum = nums[i] + nums[j];
              if(twosum == taget){
                  ArrayList<Integer> tmp = new ArrayList<Integer>(cur);
                  tmp.add(nums[i]);
                  tmp.add(nums[j]);
                  result.add(tmp);
                  i = continuousEndIndex(nums,i) + 1;
                j = continuousStartIndex(nums,j) - 1;
              }else if(twosum > taget){
                  j = continuousStartIndex(nums,j) - 1;
              }else {
                  i = continuousEndIndex(nums,i) + 1;
              }
          }
          return result;
        }

        for (int i = start; i <= nums.length - k ; i++) {
            cur.push(nums[i]);
            ksum(nums,i+1,k-1,taget -nums[i],result,cur);
            cur.pop();
        }

        return result;

    }
    private int continuousEndIndex(int [] nums,int start){
        int end = nums.length - 1;
        if(start >= end){
            return start;
        }
        while (nums[start] == nums[start+1]){
            start +=1;
            if(start >= end){
                return start;
            }
        }
        return start;

    }
    private int continuousStartIndex(int [] nums,int end){

        if(end <= 0){
            return end;
        }
        while (nums[end] == nums[end-1]){
            end +=-1;
            if(end <= 0){
                return end;
            }
        }
        return end;

    }
}
