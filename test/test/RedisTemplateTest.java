package test;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations={"classpath:applicationContext-redis.xml"})
public class RedisTemplateTest extends AbstractJUnit4SpringContextTests{

	@Resource(name = "redisTemplate")
	private RedisTemplate redisTemplate;
	
	@Test
	public void test() {
		
		this.redisTemplate.
	}

}
