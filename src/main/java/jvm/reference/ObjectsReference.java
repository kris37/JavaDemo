package jvm.reference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/8/27 下午9:03
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     JVM 的对象引用的四种状态
 *     1.强引用 正常对象
 *     2.软引用 SoftReference 可被回收
 *     3.弱引用 WeakReference
 *     4.幻象引用
 *
 *     所有引用类型都是 java.lang.ref.Reference 的子类
 *     引用都可以和一个引用队列结合起来，当GC
 *     时候会将引用加入到与之关联的引用队列中
 *     非静态内部类包含对其宿主类的实例的引用(
 * <br>
 */
public class ObjectsReference {
    public static void main(String [] args) throws InterruptedException {
        ReferenceQueue<Mark> refer = new ReferenceQueue<Mark>();
        Map<Integer, Reference> map = new ConcurrentHashMap<>(2<<15);
        Thread thread = new Thread(){
            @Override
            public void run() {
                check(refer,map);
            }
        };
        thread.setDaemon(true);
        thread.start();
        softRefer(refer,map);
    }
    public static void check(ReferenceQueue<Mark> refer,Map<Integer, Reference> map){
        try {
            System.out.println("check");
            while(true){
                Reference<? extends Mark> mk = refer.remove();
                if(mk.get() ==null) {
                    System.out.println("gc 回收软引用 ");
                    map.remove(mk.get().getId());
                    mk = null;
                }else{
                    System.out.println("gc not work");
                    System.out.println(map.size());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * 只有在内存不足的时候JVM才会回收该对象
     */
    public static void softRefer(ReferenceQueue<Mark> refer,Map<Integer, Reference> map) throws InterruptedException {
        for (int i = 0; i < 10000 ; i++) {
            try {
                SoftReference<Mark> markSoftReference = new SoftReference<Mark>(new Mark("soft " + i), refer);
                map.put(i,markSoftReference);
//                Mark mark = (Mark) map.get(i).get();
//                System.out.println(mark.getId() + "add map.");
//                if (i -5000 > 0){
//                    if (map.get(i -5000 ).get() == null){
//                        System.out.println(i -5000 + "gc 已经完成");
//                    }
//                }
            }catch (Throwable e){
                System.out.println("oom");
            }
        }


    }
    public static class Mark{
        private String id;
        private byte [] allocate = new byte[1024*1024];

        public String getId() {
            return id;
        }

        public Mark(String id){
            this.id = id;
        }
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("finalize mark id is " +id);
        }
    }


}
