package com.test.graduation;

import java.awt.Cursor;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

class MyradioButton extends JRadioButton{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MyradioButton(String type){
		super();
		this.setOpaque(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if(type.equals("监听")){		
			setSelectedIcon(new ImageIcon("src\\chat\\icon\\lis_1.png"));			
			setIcon(new ImageIcon("src\\chat\\icon\\lis_0.png"));
		}else if (type.equals("连接")) {		
			setSelectedIcon(new ImageIcon("src\\chat\\icon\\con_1.png"));		
			setIcon(new ImageIcon("src\\chat\\icon\\con_0.png"));			
		}else if (type.equals("断开")) {
			setSelectedIcon(new ImageIcon("src\\chat\\icon\\dis_1.png"));
			setIcon(new ImageIcon("src\\chat\\icon\\dis_0.png"));			
			}
	}
}