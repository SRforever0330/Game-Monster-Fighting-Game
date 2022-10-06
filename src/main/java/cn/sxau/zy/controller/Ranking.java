package cn.sxau.zy.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.sxau.zy.mapper.UserMapper;
import cn.sxau.zy.pojo.User;
import cn.sxau.zy.tool.GetSession;

public class Ranking extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SqlSessionFactory factory;
	private List<User> userList;//从数据库中获取到的用户列表信息
	private Object[][] o;//用来存显示数据的数组
	private JScrollPane scrollPane;//定义一个滚动面板，用来放表格
	private int rowNum;
	public Ranking(String name) throws IOException {
		//database
		factory=GetSession.init();
		SqlSession session = factory.openSession();
		UserMapper mapper=session.getMapper(UserMapper.class);
		userList=mapper.getUsers();
		o=new Object[userList.size()][2];
	    //创建显示数据	
			for(int i=0;i<userList.size();i++) {
				o[i][0]=userList.get(i).getUsername();
				o[i][1]=userList.get(i).getMaxgrade();
				if(userList.get(i).getUsername().equals(name)) {
					rowNum=i;
				}
			}
		
		//创建表头
        String[] columnNames = { "用户名", "成绩"};
 
       /*
         * JTable还提供了一个重载的构造方法,传入两个Vector
         * JTable(Vector rowData, Vector columnNames)
         * 
         */
         //初始化表格
         final JTable table = new JTable(o, columnNames){ 
			private static final long serialVersionUID = 1L;

				public Component prepareRenderer(TableCellRenderer renderer, int row, int column) 
        	    { 
        	     Component c = super.prepareRenderer(renderer, row, column); 
        	     // 如果是该用户则更改颜色 
        	     c.setBackground(row == rowNum ? Color.YELLOW : Color.WHITE); 
        	     return c; 
        	    } 
        	}; 
         //设置字体居中
         DefaultTableCellRenderer render = new DefaultTableCellRenderer();
         render.setHorizontalAlignment(SwingConstants.CENTER);
         table.getColumn("用户名").setCellRenderer(render);
         table.getColumn("成绩").setCellRenderer(render);
         table.setRowHeight(25);
		 //初始化滚动面板
         scrollPane= new JScrollPane(table);
         add(scrollPane,BorderLayout.CENTER);
         Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
         this.setTitle("排行榜");
 		 this.setBounds((dim.width - 350) / 2, (dim.height - 180) / 2, 350, 180);// 界面居中
 		 this.setResizable(false);// 大小不可改变
         setVisible(true);
	}
	
}
