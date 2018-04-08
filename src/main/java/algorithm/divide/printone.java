package algorithm.divide;

/**
 * Created with IntelliJ IDEA.
 *
 * @version kris37
 * Date: 2018/3/28 下午6:45
 * To change this template use File | Settings | File Templates.
 * Description:
 * @auhtor panqiang panqiang37@gmail.com
 * 假设只有一个函数 printOne() 只能输出 0-9 的数字 ，试图使用其输出 大于9的整数 到控制台
 */
public class printone {

    public static void main(String [] args){

        int x = 124352325;
        printOut(x);

    }
    private static void printOut(int x ){
        if(x > 9){
            printOut(x / 10);
        }
        pirntOne(x % 10);
    }

    private static void pirntOne(int x) throws RuntimeException {

        if(x > 9 || x < 0 ){
            throw new RuntimeException("can't print number not in 0-9. ");
        }

        System.out.print(x);
    }
}
