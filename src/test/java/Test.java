import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import jvm.classloader.NetClassLoader;
import sun.misc.Launcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * Created by panqiang on 2017/3/16.
 */
public class Test {

    @org.junit.Test
    public void TestQuickSort(){
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urLs) {
            System.out.println(url.toExternalForm());
        }
        //同样获取
        System.out.println(System.getProperty("sun.boot.class.path"));
    }

    @org.junit.Test
    public void TestClassLoader(){
        ClassLoader classLoader = List_Reverse.class.getClassLoader();
        while(!Objects.isNull(classLoader)){
            System.out.println(classLoader.getClass().getName());
            classLoader = classLoader.getParent();
        }
        System.out.println(classLoader);
    }
    @org.junit.Test
    public void NetClassLoader() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, InterruptedException {
        NetClassLoader netClassLoader = new NetClassLoader();
        Class<?> aClass = netClassLoader.loadClass("https://album-album.oss-cn-beijing.aliyuncs.com/test/StdDraw.class");
        //Class<?> aClass = netClassLoader.loadClass("/Users/panqiang/Study/algorithm/src/main/java/utils/StdDraw.class");
        System.out.println(aClass.getClassLoader().toString());
        //Object obj = aClass.newInstance();
        Method square = aClass.getDeclaredMethod("square",new Class[]{double.class,double.class,double.class});
        square.invoke(aClass,new Object[]{0.2,0.8,0.1});
        Thread.sleep(10000);


    }

    @org.junit.Test
    public void testStringFormat(){
        String s = "what the fuck is %s";

    }

    @org.junit.Test
    public void testParser(){
        final String dbType = JdbcConstants.MYSQL;
        String sql = "select threads,count(1) as total from qyer1TO5.bbs1TO100  as t1 join tables2 as t2 on (t1.id=t2.id) where from_unixtime(t1.ctime,'%Y%m%d')  >= 'starttime' and t1.a ='b'  group by threads; ";
        List<SQLStatement> mysql = SQLUtils.parseStatements(sql, dbType);
        MySqlASTVisitorAdapter visitor = new MySqlASTVisitorAdapter();
        SchemaStatVisitor schemaStatVisitor = new SchemaStatVisitor();
        mysql.forEach(stat->{
            stat.accept(schemaStatVisitor);
        });
        System.out.println(schemaStatVisitor.getTables().toString());
    }


    @org.junit.Test
    public void testGet(){
        String sql = "(select count(1) as total from {table} where FROM_UNIXTIME(utime,'%Y%m%d') >= 'startday' and FROM_UNIXTIME(utime,'%Y%m%d') < 'endday' ) as temp";
        System.out.println(sql.replace("{table}","tb"));
    }



}

