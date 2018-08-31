package jvm.io.filecopy;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/27 下午3:00
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     java copy 文件的方法
 * <br>
 */
public class FileCopy {

    public static void fileCopyByNIO(File source, File dest) throws IOException {
        try(FileChannel input = new FileInputStream(source).getChannel();
            FileChannel out = new FileOutputStream(dest).getChannel();
        ){
            for (long size = input.size();size >0;){
                long transfered = input.transferTo(input.position(), size, out);
                input.position(input.position() + transfered);
                size -= transfered;
            }
        }
    }
    public static void main(String [] args) throws IOException {
        fileCopyByNIO(new File("/Users/panqiang/Study/JavaDemo/src/main/java/jvm/io/DemoFileChannel.java"),
                new File("/Users/panqiang/Study/JavaDemo/src/main/java/jvm/io/filecopy/DemoFileChannel.java"));

    }
}
