package test;

import javax.swing.JPanel;

import cn.sxau.zy.controller.MyJpanel;

public class TestChild {
	public static void Method() {
		MyJpanel MyJPanel = null;
		System.out.println(MyJPanel instanceof JPanel);
	}
	
	public static void main(String[] args) {
		Method();
	}
}
