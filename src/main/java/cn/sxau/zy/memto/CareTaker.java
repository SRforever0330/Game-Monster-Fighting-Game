package cn.sxau.zy.memto;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.sxau.zy.mapper.UserMapper;
import cn.sxau.zy.pojo.User;
import cn.sxau.zy.tool.GetSession;

public class CareTaker {
	private Memento memento=new Memento();
	private SqlSessionFactory factory;
	private User user;
    public CareTaker(String name) throws IOException {
    	factory = GetSession.init();
    	SqlSession session = factory.openSession();
		UserMapper mapper=session.getMapper(UserMapper.class);
    	user=mapper.get(name);
    	session.close();
    }
	
	public Memento getMemento(){
		int grade = user.getGrade();
		int level = user.getLevel();
		int blood = user.getBlood();
		int energy = user.getEnergy();
		int time = user.getTime();
		memento.setGrade(grade);
		memento.setLevel(level);
		memento.setBlood(blood);
		memento.setEnergy(energy);
		memento.setTime(time);
		return memento;
	}
	
	public void saveMemento(Memento memento){
		SqlSession session = factory.openSession();
		UserMapper mapper=session.getMapper(UserMapper.class);
		user.setGrade(memento.getGrade());
		user.setLevel(memento.getLevel());
		user.setBlood(memento.getBlood());
		user.setEnergy(memento.getEnergy());
		user.setTime(memento.getTime()+user.getTime());
		mapper.update(user);
		session.commit();
		session.close();
	}
	public void saveTime(String name,int time) {
    	SqlSession session = factory.openSession();
		UserMapper mapper=session.getMapper(UserMapper.class);
		user.setTime(time+user.getTime());
		mapper.update(user);
		session.commit();
		session.close();
	}
}
