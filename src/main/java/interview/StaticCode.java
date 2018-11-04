package interview;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/17 下午6:07
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class StaticCode {

    private static  List<String> lines;
    static {
        try {
            System.out.println("init file");
            lines = Files.readAllLines(Paths.get("/Users/panqiang/Study/JavaDemo/src/main/java/interview/DictionaryCalculator.java"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public StaticCode(){

    }
    public void print(){
        lines.forEach(line -> System.out.println(line));
    }

    public static void main(String[] args){
        StaticCode staticCode = new StaticCode();
        staticCode.print();
        StaticCode staticCode2 = new StaticCode();
        staticCode2.print();
    }

}
