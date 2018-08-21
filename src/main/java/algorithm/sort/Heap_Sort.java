package algorithm.sort;

/**
 * Created by panqiang on 2017/3/20.
 * sort heap top k ,min n
 */
public class Heap_Sort {


    public static void build_Heap_Max(int[] array,int length){

        // reason from 0 begin so the leftChild shuld (mid<<1)+1
        int mid=(length-1)>>1;
        for(int i=mid;i>=0;i--){
            adJustHeap(array,i,length);
        }


    }

    /**
     *
     * @param mid node
     * @return
     */
    public static int leftChild(int mid){
        return (mid<<1)+1;
    }

    public static void swap(int[] array,int pointer_a,int pointer_b){
        int temp=array[pointer_a];
        array[pointer_a]=array[pointer_b];
        array[pointer_b]=temp;
    }

    /**
     *
     * @param array
     * @param node 指定调整堆节点，要递归实现
     * @param lenth 指定 调整数组长度，需要排序就要将最大或者最小堆元素依次放到数组堆尾部，所以数组可用范围需要递减
     */
    public static void adJustHeap(int [] array,int node,int lenth){
        int child=leftChild(node);
        if(child<lenth){//如果存在左子节点
            // 判断是否存在右子节点，如果存在挑选最大的节点与父节点比较
            if(child+1<lenth &&array[child]<array[child+1]){//存在右节点 并且 右节点大于左子节点 child 节点指针 换做右子节点
                child++;
            }

        }else{
            return;
        }
        if(array[child]>array[node]){
            swap(array,child,node);
            adJustHeap(array,child,lenth);// 递归调用 调整改变的堆
        }

    }
    public static void main(String[] args){

        int[] array={1,9,10,5,7,8,7,6,7};
        build_Heap_Max(array, array.length-1);//build heap max
        for(int i=array.length-1;i>0;i--) { //adjust the heap
                 swap(array,0,i);
            adJustHeap(array,0,i);
            }

        for (int i: array) {

            System.out.print(i+"\t");
        }

    }

}
