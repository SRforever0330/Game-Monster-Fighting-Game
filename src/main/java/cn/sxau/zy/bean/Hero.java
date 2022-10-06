package cn.sxau.zy.bean;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import cn.sxau.zy.memto.Memento;

public class Hero {
   private int x;
   private int y;
   private int r;
   private int speed=7;
   private int level;
   private int AllBlood;
   private int Blood;
   private int score;
   private int maxScore;
   private int scoreX;
   private int scoreY;
   private int bloodY;
   private int AllEnergy;
   private int Energy;
   private int time=0;
   private int lastTime=0;
   private String player="";
   private Image heroImg=new ImageIcon("E:\\Java\\eclipse\\masterGame2\\src\\main\\resources\\img\\hero.png").getImage();
   private static Hero hero=new Hero();
   private Hero(){
   }
   public Memento createMeneto() {
		Memento memento = new Memento();
		memento.setLevel(this.level);
		memento.setGrade(this.score);
		memento.setBlood(this.Blood);
		memento.setEnergy(this.Energy);
		memento.setMaxScore(this.maxScore);
		memento.setTime(this.time);
		return memento;

	}

	public void restoreFromtoMeneto(Memento mem) {
		level = mem.getLevel();
		score = mem.getGrade();
		maxScore = mem.getMaxScore();
		Blood = mem.getBlood();
		Energy = mem.getEnergy();
		time = mem.getTime();
	}
	
   public static Hero getHero() {
	   return hero;
   }
   
   public int getLevel() {
	return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getX() {
		   return x;
   }
   public int getY() {
	   return y;
   }
   public int getR() {
	   return r;
   }
   public void setX(int x) {
	   this.x=x;
   }
   public void setY(int y) {
	   this.y=y;
   }
   public void setR(int r) {
	   this.r=r;
   }
   
   public int getTime() {
	return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getSpeed() {
	   return speed;
   }
   public int getAllBlood() {
	   return AllBlood;
   }
   public int getBlood() {
	   return Blood;
   }
   public int getScore() {
	   return score;
   }
   public int getMaxScore() {
	   return maxScore;
   }
   public void setMaxScore(int maxScore) {
	   this.maxScore=maxScore;
   }
   public void setSpeed(int speed) {
	  this.speed = speed;
   }
   public void setAllBlood(int allBlood) {
	  this.AllBlood = allBlood;
   }
   public void setBlood(int blood) {
	  this.Blood = blood;
   }
   public void setScore(int score) {
	  this.score = score;
   }
   
   public void setScoreX(int scoreX) {
	this.scoreX = scoreX;
  }
   public void setScoreY(int scoreY) {
	this.scoreY = scoreY;
   }
   
   public void setPlayer(String player) {
	this.player = player;
    }
   
   public void setBloodY(int bloodY) {
	this.bloodY = bloodY;
    }
   public void changeImg() {
	   heroImg=new ImageIcon("img/bomb0.png").getImage();
   }

   public void setLive() {
	   heroImg=new ImageIcon("img/hero.png").getImage();
   }
   
   public int getAllEnergy() {
	return AllEnergy;
  }
   public int getEnergy() {
	return Energy;
  }
   public void setAllEnergy(int allEnergy) {
	AllEnergy = allEnergy;
  }
   public void setEnergy(int energy) {
	Energy = energy;
  }
   
   public int getLastTime() {
	return lastTime;
   }
   public void setLastTime(int lastTime) {
	this.lastTime = lastTime;
   }
public void paint(Graphics g) {
	  g.drawImage(heroImg, x-r, y, null);
	  g.setColor(Color.ORANGE);
	  g.fillRect(100, bloodY, AllBlood, 30);
	  g.setColor(Color.RED);
	  g.fillRect(100, bloodY, Blood, 30);
	  g.setColor(Color.GRAY);
	  g.fillRect(100, 60, AllEnergy, 30);
	  g.setColor(Color.BLUE);
	  g.fillRect(100, 60, Energy, 30);
	  g.setFont(new Font("楷体",Font.BOLD,20));
	  g.drawString(this.player+"分数:"+score, scoreX, scoreY);
   }






}
