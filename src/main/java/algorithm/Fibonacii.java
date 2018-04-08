package algorithm;

/**
 * Created by panqiang on 2017/3/20.
 */
final public  class Fibonacii {
    //input function(int i) print f(i)
    //f(n)=f(n-2)+f(n-1)
    /*
     f(1)=1 f(2)=1 f(3)=2 f(4)=3 f(5)=5 f(6)=8 f(7)=13 f(8)=21
     */
    private Fibonacii(){}
    public static int fucntion(int i){
        if(i<=2){
            return 1;
        }
        return fucntion(i-2)+fucntion(i-1);
    }
    public static int Fibonacii_NoRecursive(int n,int f_0,int f_1){

        if(n<=2){
            //f_1 is the toatal
            return f_1;
        }
        return Fibonacii_NoRecursive(n-1,f_1,f_0+f_1 );
    }
    public static int Fibonacii_Array(int n){
        int [] array=new int[n+1];
        for(int i=1;i<3;i++){
            array[i]=1;
        }
        for(int y=3;y<n+1;y++){
            array[y]=array[y-2]+array[y-1];
        }
        return array[n];

    }

    public static void main(String [] args){
        System.out.println(fucntion(8));
        System.out.println(Fibonacii_NoRecursive(8,1,1));
        System.out.println(Fibonacii_Array(8));
    }

}
