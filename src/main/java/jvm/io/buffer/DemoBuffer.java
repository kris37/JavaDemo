package jvm.io.buffer;



import com.google.common.base.CharMatcher;
import com.sun.istack.internal.NotNull;

import java.io.UnsupportedEncodingException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/10/26 上午9:13
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     Buffer (堆内buffer) DirectBuffer 堆外buffer
 *     Buffer.isDirect
 *     ByteBuffer.allocateDirect()创建堆外buffer
 *
 *     本质上 directbuffer 属于冰山对象,本身极小 但是涉及的内存很大
 *     利用幻想引用 PhantomReference 队列 当 directbuffer对象本身进入到 finalizer 过程之后 gc之前，
 *     从引用队列中拉出来 进行 clean 即可
 *
 *     MappedByteBuffer 内存映射 https://www.jianshu.com/p/f90866dcbffc
 *
 * <br>
 */
public class DemoBuffer {


    public static void main(String [] args) throws InterruptedException, UnsupportedEncodingException {



    }



    /**
     * 引用 reference 必须有强引用，否则自身就被gc了
     *  cleanr 引用的是 object 对象referent
     *  q 是引用队列 cleaner 会在 referent gc 的时候 从 q中取出
     *  clr.cleaner() 执行 之前的
     */

    public static  class Cleaner extends PhantomReference {
        // q 是 队列中 引用所指向的 对象类型 即  ReferenceQueue<T> 中存储的是 Reference<T> 对象
        public final static ReferenceQueue<Object> q = new ReferenceQueue();
        public final static HashSet<Cleaner> set = new HashSet<>();
        private Runnable clr;

        /**
         * Creates a new phantom reference that refers to the given object and
         * is registered with the given queue.
         * <p>
         * <p> It is possible to create a phantom reference with a <tt>null</tt>
         * queue, but such a reference is completely useless: Its <tt>get</tt>
         * method will always return null and, since it does not have a queue, it
         * will never be enqueued.
         *
         * @param referent the object the new phantom reference will refer to
         */
        private Cleaner(@NotNull Object referent, @NotNull Runnable clr) {
            super(referent, q);
            Objects.requireNonNull(clr);
            this.clr = clr;
        }


        public void cleaner(){
            this.clr.run();
        }

        public static void add(Object listen,Runnable clr){
           set.add(new Cleaner(listen,clr));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Cleaner cleaner = (Cleaner) o;

            return clr.equals(cleaner.clr);
        }

        @Override
        public int hashCode() {
            return clr.hashCode();
        }
    }

}
