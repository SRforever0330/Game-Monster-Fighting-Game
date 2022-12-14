package test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




public class TestMybatis {
private SqlSessionFactory factory;
	
	@BeforeEach			//5.0改，4.0 @Before
	private void init() throws IOException {	//初始化
		InputStream is = Resources.getResourceAsStream("sqlMapConfig.xml");
		factory = new SqlSessionFactoryBuilder().build(is);
	}
	
	@Test
	public void mybatis() {
		SqlSession session = factory.openSession();
		session.commit();
		session.close();
	}
	
}
