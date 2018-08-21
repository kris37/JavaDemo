package algorithm.dictionary_permutations;

/**
 * Created with IntelliJ IDEA.
 *
 * @version kris37
 * Date: 2018/4/9 上午9:35
 * To change this template use File | Settings | File Templates.
 * Description:
 * @auhtor panqiang panqiang37@gmail.com
 */
public class DictionPermutations {

    private  String [] input ;
    private  int len=0;
    private boolean flag = true;
    public DictionPermutations(String [] input){
        this.input = input;
        this.len = input.length;
    }

    public static void main(String[] args){
        String a = "12345";
        String[] split = a.split("");
        DictionPermutations dictionPermutations = new DictionPermutations(split);
        while (dictionPermutations.flag){
            dictionPermutations.next_permutation();
        }
    }

    /**
     输入序列
     *@return
     */

    public void next_permutation(){
        synchronized(input){
            endPeak(input);
            if(!flag){
                return;
            }
           sysout(input );
        }

    }


    public void endPeak(String [] array){

        // 找出右边第一个peak
        //从数组A右边开始找到第一组相邻 A[i] < A[i+1]

        int len = array.length-1;
        while(len >0 && array[len].compareTo(array[len-1]) <= 0){
            len--;
        }
        // 如果是最大值，则flag = false 无后续
        if(len <= 0){
            flag = false ;
            return;
        }
        // 找出 peak 值右侧的 MiniMax
        //在 i 的右侧找到 A[j] = Min{A[i+1 ... n] > A[i] 因为A[i]之后一直是递减的，所以按顺序找到即可}
        int i = len -1 ;
        int j = array.length-1;
        while(array[j].compareTo(array[i]) < 0){
            j--;
        }

        //交换 Swap(A[i],A[j])
        swap(array,i,j);
        reverse(array,i+1);


    }

    private  void swap(String[] array,int i,int j){
        String tmp = array[i];
        array[i]=array[j];
        array[j] = tmp;
    }
    // 翻转start-> end 数组
    private  void reverse(String[] array,int start){
        int len = array.length-1;
        while(start < len){
            swap(array,start,len);
            len--;
            start++;
        }
    }

    private void sysout(String[] input){
        // 输出next
        StringBuffer sb = new StringBuffer();
        for (String s : input) {
            sb.append(s);
        }
        System.out.println(sb.toString());
    }

}
