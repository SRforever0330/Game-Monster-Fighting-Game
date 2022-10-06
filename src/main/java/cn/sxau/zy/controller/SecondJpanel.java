package cn.sxau.zy.controller;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.sxau.zy.bean.Bullet;
import cn.sxau.zy.bean.Bullet2;
import cn.sxau.zy.bean.Heart;
import cn.sxau.zy.bean.Hero;
import cn.sxau.zy.bean.Master;
import cn.sxau.zy.bean.Music;
import cn.sxau.zy.mapper.UserMapper;
import cn.sxau.zy.memto.CareTaker;
import cn.sxau.zy.memto.Memento;
import cn.sxau.zy.pojo.User;
import cn.sxau.zy.tool.GetSession;
import cn.sxau.zy.view.Started;

public class SecondJpanel extends MyJpanel{
	private static final long serialVersionUID = 5179918663894372869L;
	private boolean isActive=false;//是否启动清屏
	private String name;
    private int bgX;//背景大小
    private int bgY;
    private boolean pause;
    private int bulletT=0;
    private SqlSessionFactory factory;
    //怪兽集合
    private ArrayList<Master> masters=new ArrayList<Master>();
    private ArrayList<Bullet> bullets=new ArrayList<Bullet>();
    private ArrayList<Heart> hearts=new ArrayList<Heart>();
    int masNum=25;
    int heartNum=3;
    String path="E:/Java/eclipse/masterGame2/src/main/resources";
    private Image bgImg=null;
    private Image goImg=new ImageIcon(path+"/img/GameOver.jpg").getImage();
    private Image pauseImg=new ImageIcon(path+"/img/pause.png").getImage();
    private Hero hero=Hero.getHero();
    //状态模式
    Started start;
    Music audio=new Music(path+"/music/background.mp3");
    //计时
  	private int curTime, lastTime;
    //游戏结束后按钮
    private JButton bt_rank;
	private JButton bt_restart;
    public SecondJpanel(Dimension dim,int status,String name, Started start) throws Exception {
    	lastTime = (int)System.currentTimeMillis();
    	hero.setLastTime(lastTime);
    	hero.setLevel(1);
    	if(status==0) {
    		audio.start();
    	}
		this.name=name;
		//状态模式
		this.start=start;
		// 设定焦点在本窗体并付与监听对象
		setFocusable(true);
		addKeyListener(this);
		//鼠标监听
		this.addMouseListener(this);
		//初始化屏幕的宽，高
		bgX=dim.width;
		bgY=dim.height;
		//初始化英雄
		hero.setR(50);
		hero.setX(hero.getR());
		hero.setY((bgY-hero.getR()*2)/2);
		hero.setAllBlood(200);
		hero.setBlood(hero.getBlood());
		hero.setScore(hero.getScore());
		hero.setScoreX(1000);
		hero.setScoreY(40);
		hero.setBloodY(20);
	    hero.setAllEnergy(200);
	    hero.setEnergy(hero.getEnergy());
	    hero.setTime(0);
		//初始化怪兽
		for(int i=0;i<masNum;i++) {
			Master mas=new Master();
			mas.setX((int)(Math.random()*bgX)+bgX/3);
			mas.setY((int)(Math.random()*bgY));
			mas.setR(25);
			mas.setSpeed((int)(Math.random()*5)+1);
			masters.add(mas);
		}
		//初始化道具
    	for(int i=0;i<heartNum;i++) {
    		Heart heart=new Heart();
    		heart.setX((int)(Math.random()*bgX));
    		heart.setY((int)(Math.random()*bgY));
    		heart.setR(25);
    		hearts.add(heart);
    	}
		//初始化背景
		int bgNum=(int)(Math.random()*3)+1;
		bgImg=new ImageIcon(path+"/img/bg"+bgNum+".jpg").getImage();
		//游戏结束后按钮
		// 按钮栏
		bt_rank = new JButton("排行榜");
		bt_rank.setBounds(bgX/2-130, bgY-150, 80, 30);
		bt_restart = new JButton("重新开始");
		bt_restart.setBounds(bgX/2, bgY-150, 100, 30);
		// 监听器
		bt_rank.addMouseListener(this);
		bt_restart.addMouseListener(this);
		//设置绝对布局
		this.setLayout(null);
    }
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);//防止出现流水
    	//绘制背景
    	g.drawImage(bgImg, 0, 0, bgX, bgY, null);
    	//绘制怪兽
    	for(int i=0;i<masters.size();i++) {
    		masters.get(i).paint(g);
    	}
    	//绘制道具
    	for(int i=0;i<hearts.size();i++) {
    		hearts.get(i).paint(g);
    	}
    	//绘制英雄
    	hero.paint(g);
    	//绘制子弹
    	for(int i=0;i<bullets.size();i++) {
    		Bullet bu=bullets.get(i);
    		bu.paint(g);
    	}
    	if(pause) {
    		g.drawImage(pauseImg, bgX/2-25, bgY/2-26,50 ,52 , null);
    	}
    	//结束游戏
    	if(hero.getBlood()<=0) {
    		this.add(bt_rank);
			this.add(bt_restart);
			this.validate();
    		g.drawImage(goImg, 0, 0, bgX, bgY, null);
    		g.setFont(new Font("楷体", Font.BOLD, 20));
    		g.drawString("本次得分："+hero.getScore(), bgX/2-100, bgY-200);
    		try {
    			g.setFont(new Font("楷体", Font.BOLD, 20));
				g.drawString("最高得分："+getMaxScore(), bgX/2-100, bgY-170);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    //Runnable接口需要实现的方法
    public void run() {
		while(true) {
			while(pause) {
				repaint();
				try {
					Thread.sleep(1000);
				}catch(InterruptedException e1){
					e1.printStackTrace();
				}
			}
			if(hero.getBlood()>0) {
				if(isActive==false) {
				//让怪兽走
				for(int i=0;i<masters.size();i++) {
					Master mas=masters.get(i);
					if(mas.getX()<=0) {
						mas.setY((int)(Math.random()*bgY));
						mas.setX(bgX+mas.getR()*2);
						mas.setSpeed(((int)(Math.random()*5)+1)*2);
						mas.changeImg();
						masters.remove(i);
						masters.add(mas);
					}else {
						mas.setX(mas.getX()-mas.getSpeed());
					}
					//判断怪兽是否与英雄现状
					boolean b=isHeat(mas,hero);
					if(b==true) {
						mas.setY((int)(Math.random()*bgY));
						mas.setX(bgX);
						mas.setSpeed(((int)(Math.random()*5)+1)*2);
						mas.changeImg();
						masters.remove(i);
						masters.add(mas);
						hero.setBlood(hero.getBlood()-hero.getAllBlood()/5);
					}
				}
				
				//让子弹飞
				for(int k=0;k<bullets.size();k++) {
					Bullet bu=bullets.get(k);
					if(bu.getX()>=bgX+bu.getR()) {
						bullets.remove(k);
						continue;
					}else {
						bu.setX(bu.getX()+10);//子弹频率
					}
					for(int j=0;j<masters.size();j++) {
						Master mas=masters.get(j);
						boolean b=isHeat(bu,mas);
						//怪兽被子弹打中
						if(b==true) {
							mas.setState(0);
							repaint();
							new Music(path+"/music/bomb2.mp3").start();
							if(bullets.get(k) != null) {
								bullets.remove(k);
							}
							try {
								Thread.sleep(50);
							}catch(InterruptedException e){
								e.printStackTrace();
							}
							int speed=mas.getSpeed();
							hero.setScore(hero.getScore()+speed*2);
							if(hero.getEnergy()<hero.getAllBlood()) {
								hero.setEnergy(hero.getEnergy()+hero.getAllEnergy()/10);
							}
							if(mas.getState()==0) {
							mas.setX(bgX);
							mas.setY((int)(Math.random()*bgY));
							mas.setSpeed(((int)(Math.random()*5)+1)*2);
							mas.setState(1);
							mas.changeImg();
							masters.remove(j);
							masters.add(mas);
							}
							
						}
					}
				}
				//判断英雄和道具是否碰撞
				for(int j=0;j<hearts.size();j++) {
					Heart heart=hearts.get(j);
					boolean b=isHeat(heart,hero);
					
					if(b==true) {
						new Music(path+"/music/fullblood.mp3").start();
						if(hearts.get(j) != null) {
							heart.setX((int)(Math.random()*bgX));
				    		heart.setY((int)(Math.random()*bgY));
				    		heart.setR(25);
							hearts.remove(j);
							hearts.add(heart);
						    
						}
						hero.setBlood(hero.getAllBlood());
					}
				}
				repaint();
				try {
					Thread.sleep(10);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(isActive==true){
				new Music(path+"/music/bomb.mp3").start();
				//清屏
				//将怪兽都消灭
				for(int j=0;j<masters.size();j++) {
					Master mas=masters.get(j);
						int speed=mas.getSpeed();
						hero.setScore(hero.getScore()+speed*2);
						hero.setEnergy(0);
						if(masters.get(j)!=null) {							
							masters.remove(j);
						}
				}
			
				try {
					Thread.sleep(50);
				}catch(InterruptedException e1){
					e1.printStackTrace();
				}
				
				isActive=false;
				//新建怪兽
				for(int j=0;j<masNum;j++) {
					Master mas=new Master();
					mas.setX(bgX);
					mas.setY((int)(Math.random()*bgY));
					mas.setSpeed(((int)(Math.random()*5)+1)*2);
					mas.setState(1);
					mas.changeImg();
					masters.add(mas);
						
				}
			}
			}else {//游戏结束存入数据库
				curTime = (int)System.currentTimeMillis();
				int time = (curTime - lastTime)/1000 ;
				CareTaker ctCareTaker;
				try {
					ctCareTaker = new CareTaker(name);
					hero.setTime(time);
					hero.setMaxScore(getMaxScore());
					Memento memento = hero.createMeneto();
					ctCareTaker.saveMemento(memento);
					getMaxScore();
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
}
    private void initBullet() {
    	if(bulletT==0) {
        	Bullet bu=new Bullet();
    		bu.setX(hero.getX()-10);
    		bu.setY(hero.getY()+20);
    		bu.setR(50);
    		bullets.add(bu);
        	}else {
        	Bullet2 bu=new Bullet2();
    		bu.setX(hero.getX()-10);
    		bu.setY(hero.getY()+20);
    		bu.setR(75);
    		bullets.add(bu);
        	}
	}
//碰撞
//    判断怪兽是否与子弹相撞
    private boolean isHeat(Bullet bu,Master mas) {
    	int x=bu.getX()+bu.getR()-mas.getX()-mas.getR();
    	int y=bu.getY()+bu.getR()-mas.getY()-mas.getR();
    	int r=bu.getR()+mas.getR();
    	if((x*x+y*y)<=(r*r)) {
    		return true;
    	}
		return false;
    }
  //判断英雄是否与怪兽相撞
  	private boolean isHeat(Master mas, Hero hero) {
  		int x=hero.getX()+hero.getR()-mas.getX()-mas.getR();
    	int y=hero.getY()+hero.getR()-mas.getY()-mas.getR();
    	int r=hero.getR()+mas.getR();
  		if ((x*x+y*y)<=(r*r)) {
  			return true;
  		} else {
  			return false;
  		}
  	}
  	 //判断英雄是否与道具相撞
  	private boolean isHeat(Heart heart, Hero hero) {
  		int x=hero.getX()+hero.getR()-heart.getX()-heart.getR();
    	int y=hero.getY()+hero.getR()-heart.getY()-heart.getR();
    	int r=hero.getR()+heart.getR();
  		if ((x*x+y*y)<=(r*r)) {
  			return true;
  		} else {
  			return false;
  		}
  	}
	//检测键盘按下
    public void keyPressed(KeyEvent e) {
    	int key=e.getKeyCode();
    	if(key==KeyEvent.VK_UP) {
    		hero.setY(hero.getY()-hero.getSpeed());
    		 if(hero.getY()<0) {
     	    	hero.setY(0);
     	    }
    		}
    	if(key==KeyEvent.VK_DOWN) {
    		hero.setY(hero.getY()+hero.getSpeed());
    		 if(hero.getY()>bgY) {
     	    	hero.setY(bgY);
     	    }
    		}
    	if(key==KeyEvent.VK_LEFT) {
    		hero.setX(hero.getX()-hero.getSpeed());
    	    if(hero.getX()<0) {
    	    	hero.setX(0);
    	    }
    	}
    	if(key==KeyEvent.VK_RIGHT)
    		hero.setX(hero.getX()+hero.getSpeed());
    	if(key==KeyEvent.VK_SPACE) {
    		if(!pause) {
    			pause=true;
    		}else {
    			pause=false;
    		}
    	}
    	if(key==KeyEvent.VK_C) {
    		if(bulletT==0) {
    			bulletT=1;
    		}
    		else{
    			bulletT=0;
    		}
    		
    	}
    }
	public int getMaxScore() throws IOException {
		factory=GetSession.init();
		SqlSession session = factory.openSession();
		UserMapper mapper=session.getMapper(UserMapper.class);
		User user=mapper.get(name);
		int maxScore=user.getMaxgrade();
		if(hero.getScore()>maxScore) {
			maxScore=hero.getScore();
			user.setMaxgrade(maxScore);
			mapper.update(user);
			session.commit();
		}
		session.close();
		return maxScore;
	}
    //未实现的方法
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
   
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
			//鼠标左键
			int count=0;
			if(count%30==0) {
				initBullet();
			}
			count++;
		}
		if(e.getButton()==MouseEvent.BUTTON3) {
			//鼠标右键
			//判断能量值是否为满
			if(hero.getEnergy()==hero.getAllEnergy()) {
				isActive=true;
				
			}
		}
		if (e.getSource() == bt_rank) {
	           try {
				    new Ranking(name);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			} else if (e.getSource() == bt_restart) { 
				try {
					hero.setBlood(200);
					hero.setEnergy(0);
					hero.setScore(0);
					hero.setTime(0);
					this.setVisible(false);
					start.setJpanel(0, name, 1);
				} catch (Exception e1) {
					e1.printStackTrace();
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
