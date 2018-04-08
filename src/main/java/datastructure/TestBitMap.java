package datastructure;

import org.roaringbitmap.IntConsumer;
import org.roaringbitmap.RoaringBitmap;

/**
 *  应用于处理 用户标签的计算，比如结伴帖子的匹配 排序运算等操作
 */
public class TestBitMap {
    public TestBitMap() {

    }

    public static void main(String[] args) {
        RoaringBitmap rr = RoaringBitmap.bitmapOf(1,7,222,9,120,111,12222,13,1000);
        RoaringBitmap rr1 = RoaringBitmap.bitmapOf(1, 3,5);
        RoaringBitmap rr2 =  RoaringBitmap.bitmapOf(2,3,4);
        RoaringBitmap limit = rr.limit(5);
        System.out.println(rr.last());
        System.out.println(rr.getReverseIntIterator().next());

        //rr.or(rr2); //in-place computation rr 改变
//        boolean equals = rror.equals(rr);// true
//        if (!equals) {
//            throw new RuntimeException("bug");
//        }
        // number of values stored?
        long cardinality = rr.getLongCardinality();

        // a "forEach" is faster than this loop, but a loop is possible:
        final int[] count = {0};
        limit.forEach((IntConsumer) value -> System.out.println(value));

    }
}
