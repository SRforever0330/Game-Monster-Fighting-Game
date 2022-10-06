package cn.sxau.zy.tool;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;



public class GetSession {
   public static SqlSessionFactory factory ;

   public static SqlSessionFactory init() throws IOException {	//初始化
		InputStream is = Resources.getResourceAsStream("sqlMapConfig.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
		
		return factory;
   }


	
}
