import com.red.verb.CacheApplication;
import com.red.verb.cache.utils.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/28     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-28 14:46
 * @since 1.0.0
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CacheApplication.class)
public class test {
	@Test
	public void testRedis(){
		JedisUtil.getJedis();
		JedisUtil.setJson("key", "value");
	}
}
