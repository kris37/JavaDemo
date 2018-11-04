package leetcode;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/1 上午11:26
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     分治法 x^n = x^[n/2] * x^[n/2]
 * <br>
 */
public class Pow_50 {
    public double myPowByRecursive(double x, long n) {

        if(n == 0){
            return 1;
        }
        // 负数
        if(n < 0){
            return 1/myPowByRecursive(x,Math.abs(n));
        }else{
            // 奇数
            double y =  myPowByRecursive(x,n/2);
            if((n & 1) == 1){
                return x*y*y;
            }else {
                return y*y;
            }
        }

    }
    public double myPowByLoop(double x, long n) {
        if(n < 0){
            x = 1/x;
            n = -n;
        }
        double pow = 1.0;
        //自下而上的
        while (n > 0){
            if((n & 1) == 1){// 奇数
                pow *= x;
            }
            x *= x;
            n >>= 1;
        }
        return pow;
    }

    public static void main(String [] args){
        System.out.println(new Pow_50().myPowByRecursive(2,10));
    }
}
