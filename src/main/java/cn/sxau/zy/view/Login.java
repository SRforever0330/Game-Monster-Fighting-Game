package cn.sxau.zy.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.sxau.zy.bean.Hero;
import cn.sxau.zy.mapper.UserMapper;
import cn.sxau.zy.memto.CareTaker;
import cn.sxau.zy.memto.Memento;
import cn.sxau.zy.pojo.User;
import cn.sxau.zy.tool.GetSession;

public class Login extends JFrame implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 按钮
	private JButton bt_signup;
	private JButton bt_login;
	private JTextField tf_name;
	private JTextField tf_pass;
	private SqlSessionFactory factory;
	//英雄
	private Hero hero;
	Login() throws IOException {
        //database
		factory=GetSession.init();
		hero=Hero.getHero();
		this.setTitle("怪兽大作战");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// 文本框
		JPanel p_text1 = new JPanel();
		JPanel p_text2 = new JPanel();
		tf_name = new JTextField(20);
		tf_pass = new JTextField(20);
		JPanel p_username=new JPanel();
		JPanel p_password=new JPanel();
		p_text2.add(p_username,BorderLayout.NORTH);
		p_text2.add(Box.createVerticalStrut(50),BorderLayout.CENTER);
		p_text2.add(p_password,BorderLayout.SOUTH);
		p_username.add(new JLabel("账 号："));
		p_username.add(tf_name);
		p_password.add(new JLabel("密 码："));
		p_password.add(tf_pass);
		
		// 按钮栏
		JPanel p_bt = new JPanel();
		p_bt.setSize(350,80);
		bt_signup = new JButton("注册账号");
		bt_login = new JButton("登陆游戏");
		p_bt.add(bt_signup);
		p_bt.add(bt_login);

		// 监听器
		bt_signup.addMouseListener(this);
		bt_login.addMouseListener(this);
		
		this.add(p_text1, BorderLayout.NORTH);
		this.add(p_text2, BorderLayout.CENTER);
		this.add(p_bt, BorderLayout.SOUTH);
		this.setBounds((dim.width - 350) / 2, (dim.height - 180) / 2, 350, 180);// 界面居中
		this.setResizable(false);// 大小不可改变
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
	}
	// 主函数
		public static void main(String[] args) throws IOException {
			new Login();
	}
	@Override
	//鼠标点击事件
	public void mouseClicked(MouseEvent e) {
		String name = tf_name.getText().toString().trim();
		String pass = tf_pass.getText().toString().trim();
	    //点击注册按钮
		if (e.getSource() == bt_signup) {
           //打开数据库
			SqlSession session = factory.openSession();
			UserMapper mapper=session.getMapper(UserMapper.class);
			if (name.equals("")||name.length()==0||pass.equals("")||pass.length()==0) {
				// 输出错误信息
				JOptionPane.showMessageDialog(null, "请输入账号和密码!");
			} else {
				if (mapper.exist(name)!=null) {
					JOptionPane.showMessageDialog(null, "该账号已存在!请重新输入");
				}else {
					mapper.add(name, pass);
					session.commit();
					JOptionPane.showMessageDialog(null, "注册成功，请登陆！");
				}
			}
			session.close();
		} else if (e.getSource() == bt_login) { 
        //打开数据库
			SqlSession session = factory.openSession();
			UserMapper mapper=session.getMapper(UserMapper.class);
			if (name.equals("")||name.length()==0||pass.equals("")||pass.length()==0) {
				// 输出错误信息
				JOptionPane.showMessageDialog(null, "请输入账号和密码!");
			} else {
				if( mapper.check(name, pass) !=null) {  //登陆成功
					// 跳转主界面
					this.setVisible(false);
					User user=mapper.get(name);
				    int level = user.getLevel();
				    int time = user.getTime();
				    CareTaker care;
					try {
                        //备忘录模式恢复数据
						care = new CareTaker(name);
						Memento mem = care.getMemento();
						hero.restoreFromtoMeneto(mem);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					session.close();
					new Start(level, name, time);
				} else {
					// 不跳转 输出错误信息
					JOptionPane.showMessageDialog(null, "请输入正确的账号和密码!");
				}
			}
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
