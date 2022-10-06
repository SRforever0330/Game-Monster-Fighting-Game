package cn.sxau.zy.bean;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Bullet2 extends Bullet{
  private int x;
  private int y;
  private int r;
  private Image bu_img=new ImageIcon("E:\\Java\\eclipse\\masterGame2\\src\\main\\resources\\img\\fire2.png").getImage();
  
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
  public Image getImg() {
	  return bu_img;
  }
  public void setImg(Image img) {
	  this.bu_img=img;
  }
public void paint(Graphics g) {
	g.drawImage(bu_img, x , y , null);
	
}
  
}
