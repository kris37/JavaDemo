package jvm.io.reactor;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/11/22 下午4:33
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class Demo {
    public static void main(String [] args) throws IOException {
        GroupSubReactor groupSubReactor = GroupSubReactor.create(3);
        Acceptor acceptor = Acceptor.getSingleInstance(8080, groupSubReactor);
        groupSubReactor.start();
        acceptor.start();
    }
}
