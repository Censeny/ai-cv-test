import com.ksy.AiCvApplication;
import com.ksy.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AiCvApplication.class)
public class YuTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void findList(){


        System.out.println(userDao.findList());
    }
}
