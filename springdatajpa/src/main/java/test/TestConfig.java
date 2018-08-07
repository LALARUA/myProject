package test;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javax.sql.DataSource;
import java.sql.SQLException;

public class TestConfig {
    private static ApplicationContext ctx;
    static {
        ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }
    @Test
    public void testDataSource()throws SQLException{

        DataSource dataSource = (DataSource)ctx.getBean("dataSource");
        System.out.println(dataSource);
        System.out.println(dataSource.getConnection());
    }
    @Test
    public void aa(){
        System.out.println("hello world");
    }


}
