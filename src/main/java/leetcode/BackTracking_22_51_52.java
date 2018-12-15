package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/19 上午10:09
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class BackTracking_22_51_52 {


    public  List<String> generateParenthesis(int n) {
        List<String> result = new LinkedList<>();
        genParenthesis(0,0,n,"",result);
        return result;
    }

    /**
     *
     * @param left
     * @param right
     * @param n
     * @param ser
     */
    private  void  genParenthesis(int left,int right,int n,String ser,List<String> result ){
        // 对于 >= 1 的任何位置只要 左括号个数>= right 就可以，所以左括号可以一直添加，直到 left ==n
        // 对于右括号添加是 right< left and can put right
        if(left == n){
            result.add(ser.concat(mkString(")", n - right)));
        }
        if(right < left && left < n){
            genParenthesis(left,right+1,n,ser.concat(")"),result);
        }
        if(left < n){
            genParenthesis(left+1,right,n,ser.concat("("),result);
        }

    }

    private  String mkString(String s,int times) {
        if (times <= 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }


    /**
     *  leetcode 51 n-queens
     * @param n
     * @return
     */
    /**
   # n-queens true is blank can put
     puthon3 code
    def NQueen(n):
        bp=[[True]*n for i in range(n)]
        record = []
    def DFS(i,s):
            if(i>=n):
            record.append([ x for x in s.split(',') if len(x)>0])
            return
    stack=[]
            for j in range(n):
            if bp[i][j]:
    fillMap(i,j,n,bp,stack)
    DFS(i+1,s+mkstring(j,n)+',')
    rollback(bp,stack)
        return
    DFS(0,'')
    return record


    def fillMap(i,j,n,boolmap,stack):
            #sweep row
    for col in range(n):
            if boolmap[i][col]:
            stack.append((i,col))
    boolmap[i][col] = False

    # 扫描固定列 变动行 >= i 开始
    for row in range(i,n):
            if boolmap[row][j]:
            stack.append((row,j))
    boolmap[row][j] = False
    # 扫描 左斜边
    row = i
            col = j
    while row < n and col >=0:
            if boolmap[row][col]:
            stack.append((row,col))
    boolmap[row][col] = False
    row +=1
    col -=1
            # 右斜边
            row = i
    col = j
    while row < n and col < n:
            if boolmap[row][col]:
            stack.append((row,col))
    boolmap[row][col] = False
    row +=1
    col +=1

    def rollback(boolmap,stack):
            for i,j in stack:
    boolmap[i][j] = True

    def mkstring(j,n):
    li = ['.']*n
    li[j] = 'Q'
            return ''.join(li)

    */

}
