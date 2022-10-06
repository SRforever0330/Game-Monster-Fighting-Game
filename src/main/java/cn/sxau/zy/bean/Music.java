package cn.sxau.zy.bean;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Music extends Thread{
  Player player;
  String musicName;
  public Music(String file) {
	  this.musicName=file;
  }

public void run() {
	  try {
		play();
	} catch (FileNotFoundException | JavaLayerException e) {
		
		e.printStackTrace();
	}
	  
  }
  public void play() throws FileNotFoundException,JavaLayerException{
	BufferedInputStream buffer=new BufferedInputStream(new FileInputStream (musicName));
	player=new Player(buffer);
	player.play();
}
}
