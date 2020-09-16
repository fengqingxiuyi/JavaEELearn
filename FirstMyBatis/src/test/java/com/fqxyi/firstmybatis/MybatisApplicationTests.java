package com.fqxyi.firstmybatis;

import com.fqxyi.firstmybatis.MybatisApplication;
import com.fqxyi.firstmybatis.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MybatisApplication.class)
public class MybatisApplicationTests {

//	@Autowired
//	private UserDao userDao;

	@Test
	@Rollback
	public void findByName() {
		//本地
//		String name = userDao.queryName(1);
//		Assert.assertEquals("fqxyi", name);
		//部署
	}

}
