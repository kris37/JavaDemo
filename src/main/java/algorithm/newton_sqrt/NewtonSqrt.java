package algorithm.newton_sqrt;

/**
 * Created with IntelliJ IDEA.
 *
 * @version kris37
 * Date: 2018/3/28 下午3:20
 * To change this template use File | Settings | File Templates.
 * Description:
 * @auhtor panqiang panqiang37@gmail.com
 * 牛顿法求平方根
 * 牛顿迭代法 f(x) =  x^2 -k
 *  其中 f(x) = 0 的一个根
 *  X(n+1) = Xn - f(Xn)/f'(Xn)
 */
public class NewtonSqrt {


public static void main(String [] args){
    double v = newtonSqrt(7, 10);
    System.out.println(v);
}

    private static double newtonSqrt(double k,int dot){
        double x = k/7;
        int i =0;
        //x = x - (x*x -k)/(2*x);
        while(i< dot) {
            x = x / 2 + k / (2 * x);
            i++;
        }
        return  x;

    }

}
