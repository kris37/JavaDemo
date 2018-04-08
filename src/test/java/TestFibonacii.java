/**
 * Created by panqiang on 2017/3/23.
 */
public class TestFibonacii {
    public static void main(String[] args){

        //System.out.println(fibonacii_Norecursive(9,1,1));
        System.out.println(fib_array(9));

    }
    public static int fibonacii_recursive(int n){
            if(n<2){
                return 1;
            }
        return fibonacii_recursive(n-2)+fibonacii_recursive(n-1);
    }

    public static int fibonacii_Norecursive(int n,int init,int total){
        if(n<=2){
            return total;
        }
          return  fibonacii_Norecursive(n-1,total,init+total);

    }
    public static int fib_array(int n){
        int[] array=new int[n];
        array[0]=array[1]=1;
        if(n<1){
            return -1;
        }else{
            for(int i=2;i<n;i++){
                array[i]=array[i-2]+array[i-1];
            }
            return array[n-1];
        }

    }
}
