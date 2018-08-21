package jvm.io;

import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/21 上午11:01
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 * <br>
 */
public class Clinet {

        public static void main(String [] args) {

            for(int i = 0 ; i <= 100; i++){
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            Socket socket = new Socket("172.16.30.169", 3337);
                            for (;;) {
                                socket.getOutputStream().write(new byte[] { 1, 2, 3, 4, 5 });
                                Thread.sleep(100);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            while(true){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

}
