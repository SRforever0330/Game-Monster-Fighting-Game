package cn.sxau.zy.bean;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Master { 
   private int x;  
   private int y;
   private int r;  
   private int speed;
   private Image masImg=null;
   private Image bombImg=null;
   private int masNum;
   private int state;
   public int position=0;
   public Master(){
	   state=1; 
	   masNum=(int)(Math.random()*12)+1;
	   String mas=""+masNum;
	   int bombNum=(int)(Math.random()*1);
	   masImg=new ImageIcon("E:\\Java\\eclipse\\masterGame2\\src\\main\\resources\\img\\mas"+mas+".png").getImage();
	   bombImg=new ImageIcon("E:\\Java\\eclipse\\masterGame2\\src\\main\\resources\\img\\bomb"+bombNum+".png").getImage();
   }
   
   public void changeImg() {
	   masNum=(int)(Math.random()*12)+1;
	   String mas=""+masNum;
	   masImg=new ImageIcon("E:\\Java\\eclipse\\masterGame2\\src\\main\\resources\\img\\mas"+mas+".png").getImage();
   }
   
   public int getSpeed() {
	return speed;
   }
   public void setSpeed(int speed) {
	this.speed = speed;
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
   
   public int getState() {
	return state;
}
   public void setState(int state) {
	this.state = state;
}
//���Ƶл�
   public void paint(Graphics g) {
	   if(this.state==1) {
		   g.drawImage(masImg,x-r,y-r,null);
	   }
	   if(this.state==0) {
		   g.drawImage(bombImg,x-r,y,null);
	   }
   }
}
