import designpattern.singleton.EnumInstance;
import designpattern.singleton.StaticInnerClassSingleton;

import org.junit.Test;
public class StaticInnerClassSingletonTest {
    @Test
    public void test1(){

        StaticInnerClassSingleton.getInstance();
    }
    @Test
    public void test2(){
        EnumInstance.INSTANCE.todo(1);
    }
}
