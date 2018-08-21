package algorithm.peak_finder;

/**
 * Created with IntelliJ IDEA.
 *
 * @version kris37
 * Date: 2018/3/16 下午6:32
 * To change this template use File | Settings | File Templates.
 * Description:
 * @auhtor panqiang panqiang37@gmail.com
 * 二维数组查找 峰值查找
 */
public class PeakFinder2D {

    public static void main(String[] args){

        int [] array = {1,3,2,5,2,1,6,4,3,2};
        //int finder = recuriveFinder(array, 0, array.length - 1);
        //int finder = peakfinder(array);
        //System.out.println(finder+"\t"+array[finder]);

    }


    /**
     * 暴力查找 O（n^2）
     */

    private  int violenceFinder(int[][] magic){
        int row = magic.length-1;
        int column = magic[0].length-1;
        int i =0;
        int j =0;
        // 边界状况
        boolean slide = (i-1 > 0)&&(i + 1 < row)&&(j - 1 > 0)&&(j + 1 < column);

        // TODO: 2018/6/5
        if(!isSide(i,row,j,column) && false){

        }else{
            // TODO: 2018/6/5
        }
        return 0;
    }


    private boolean isSide(int i ,int row,int j,int column){
        return (i-1 > 0)&&(i + 1 < row)&&(j - 1 > 0)&&(j + 1 < column);
    }

}
