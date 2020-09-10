import cn.smbms.dao.Singleton;
import org.junit.Test;

public class SingletonTest {
    @Test
    public void test01() {
        Singleton singleton2 = Singleton.getInstance();
        Singleton singleton1 = Singleton.getInstance();
        System.out.println(singleton1 == singleton2);

    }


}
