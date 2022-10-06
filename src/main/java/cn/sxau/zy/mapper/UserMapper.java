package cn.sxau.zy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import cn.sxau.zy.pojo.User;

public interface UserMapper {
	public User exist(String name);//注册时查询是否存在该用户
	public User check(@Param("username")String name, 
			@Param("password")String pswd);//登录时检查
	public void update(User user);
	// add an new user's information
	public void add(@Param("username")String name, @Param("password")String pswd);
	// get grade or level by name
	public User get(String name);
	public List<User> getUsers();
	
}
