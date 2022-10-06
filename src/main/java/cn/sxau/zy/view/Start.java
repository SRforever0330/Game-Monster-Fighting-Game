package cn.sxau.zy.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.sxau.zy.bean.Hero;

public class Start extends JFrame implements ActionListener{
	private static final long serialVersionUID = 293145988993785462L;
	private JButton button_restart = new JButton();
	private JButton button_start = new JButton();
	private int level;
	private String name;
	private Hero hero=Hero.getHero();
	private JFrame frame;
	public Start(int level, String name, int time) {
		this.level= level;
        this.name=name;
		frame = new JFrame("开始界面");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((dim.width - 400) / 2, (dim.height - 200) / 2, 400, 200);//居中
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button_restart.setText("重新开始");
		button_restart.addActionListener(this);//为开始游戏按钮增加动作监听器
		button_start.setText("继续游戏");
		button_start.addActionListener(this);//为结束游戏按钮增加动作监听器
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JTextField Level = new JTextField(20);  
		JTextField Grade = new JTextField(20);
		JTextField Time = new JTextField(20);
		
		Level.setEnabled(false);
		Grade.setEnabled(false);
		Time.setEnabled(false);
		
		Level.setText(level+"");
		Grade.setText(hero.getScore()+"");
		Time.setText(time+"秒");
		
		panel1.add(button_restart);
		panel1.add(button_start);
		panel2.add(new JLabel("等级："));  
        panel2.add(Level);  
        panel3.add(new JLabel("成绩："));  
        panel3.add(Grade);
        panel4.add(new JLabel("累计在线："));
        panel4.add(Time);
        panel5.add(panel2,BorderLayout.NORTH);
        panel5.add(panel3,BorderLayout.CENTER);
        panel5.add(panel4,BorderLayout.SOUTH);
        
		frame.add(panel1, BorderLayout.SOUTH);
		frame.add(panel5, BorderLayout.CENTER);
		frame.setResizable(false);//设置窗体大小不可改变
		frame.setVisible(true);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button_restart) {
			try {
				hero.setBlood(200);
				hero.setEnergy(0);
				hero.setScore(0);
				hero.setTime(0);
				new Started(0, name);
				frame.setVisible(false);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		} 
		if (e.getSource() == button_start) {
			try {
				new Started(level, name);
				frame.setVisible(false);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
	}
}
