package cn.sxau.zy.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cn.sxau.zy.bean.Hero;
import cn.sxau.zy.controller.FirstJpanel;
import cn.sxau.zy.controller.MyJpanel;
import cn.sxau.zy.controller.SecondJpanel;
import cn.sxau.zy.memto.CareTaker;
import cn.sxau.zy.memto.Memento;

public class Started extends JFrame implements WindowListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Hero hero=Hero.getHero();
	String name;//用户名
	MyJpanel Jpanel;
	
	//获取屏幕分辨率
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public Started() {}
	public Started(int level, String name) throws Exception {
		this.name=name;
		setJpanel(level,name,0);
	}
	public void setJpanel(int level,String name, int status) throws Exception {
		if(level==1) {
			 this.name=name;
			 Jpanel=new SecondJpanel(dim,status,name,this);
		}else if(level==0){
			 this.name=name;
			 Jpanel=new FirstJpanel(dim,status,name,this);
		}
		
		//设置窗体为全屏
		this.setBounds(0, 0, dim.width, dim.height);
		this.add(Jpanel);
		
		//设置窗体大小不可改变
		this.setResizable(false);
		//设置关闭方式
		this.addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//定义线程
		Thread thread = new Thread(Jpanel);
		//启动线程
		thread.start();
		this.setVisible(true);
		//将焦点转移到该面板上，键盘事件才能监听
		Jpanel.requestFocus();
		
	}
	public void windowClosing(WindowEvent e) {
		int a = JOptionPane.showConfirmDialog(null, "是否保存数据？", "温馨提示",
				JOptionPane.YES_NO_OPTION);
		if (a == 1) {  
			System.exit(0);  //关闭
		}else {//保存数据
			int curTime = (int)System.currentTimeMillis();
			int time = (curTime - hero.getLastTime())/1000 ;
			CareTaker ctCareTaker;
			try {
				ctCareTaker = new CareTaker(name);
				hero.setTime(time);
				Memento memento = hero.createMeneto();
				ctCareTaker.saveMemento(memento);
				System.exit(0); 
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	@Override
	public void windowOpened(WindowEvent e) {
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		
	} 
     
}
