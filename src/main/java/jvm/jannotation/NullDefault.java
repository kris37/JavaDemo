package jvm.jannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 *
 * @author panqiang37@gmail.com
 * @version kris37
 * Date: 2018/12/13 下午6:16
 * To change this template use File | Settings | File Templates.
 * Description:
 * <p>
 *     检出null的变量，并赋予default值
 * <br>
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullDefault {

}
