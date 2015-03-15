package vision;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;

import chess.LevelChoose;

public class ChooseColor extends MouseAdapter{
	int a = 0;
	JDialog choose;
	JLabel choosepicture;
	JLabel choosetitle;
	JLabel choosetitle2;
	JLabel chooseblack;
	JLabel choosewhite;
	
	String s;
	int color;
	public ChooseColor(int a, JFrame owner, boolean modal){
		this.a = a;
		if(a == 1){
			s ="单人模式初始状态";			
		}
		else if(a == 2){
			s = "三手后后手选色";
		}
		else if(a ==3){
			s = "五手后后手选色";
		}
		
		choose = new JDialog(owner, modal);
		choose.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		choose.setSize(418,300);
		choose.setLayout(null);
		
		choosepicture = new JLabel(new ImageIcon("image/StartTwojiemian.jpg"));
		choose.add(choosepicture);
		choosepicture.setBounds(0,0,400,293);
		choosepicture.setVisible(true);
		
		Font font1 = new Font("汉仪雪君体繁",Font.BOLD,24);
		Font font2 = new Font("汉仪柏青体简",Font.BOLD,18);
		
		choosetitle = new JLabel("请选择你的颜色");
		choosetitle.setFont(font1);
		choosetitle.setForeground(Color.BLUE);
		choosepicture.add(choosetitle);
		choosetitle.setBounds(20,5,200,50);
		
		choosetitle2 = new JLabel(s);
		choosetitle2.setFont(font2);
		choosetitle2.setForeground(Color.RED);
		choosepicture.add(choosetitle2);
		choosetitle2.setBounds(95,50,160,40);
		
		chooseblack = new JLabel(new ImageIcon("image/black.png"));
		choosepicture.add(chooseblack);
		chooseblack.setBounds(30,120,150,100);
		
		choosewhite = new JLabel(new ImageIcon("image/white.png"));
		choosepicture.add(choosewhite);
		choosewhite.setBounds(190,120,150,100);
		
		chooseblack.addMouseListener(this);
		choosewhite.addMouseListener(this);
		choose.setVisible(true);
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == chooseblack && a == 2){
			choose.dispose();
			ChessBoard.setTime();
			Thread thread = new Thread(new Runnable(){
				public void run(){
			        ChessBoard.chessboard.setTable(6, 5, 2);
			        ChessBoard.chessboard.setPlayerFirst(true);
				}
			});
			thread.start();
			ChessBoard.setTime();
			
		}
		else if(e.getSource() == choosewhite && a == 2){
			choose.dispose();
			ChessBoard.setTime();
			ChessBoard.setHasComputer(true);
			ChessBoard.chessboard.setPlayerFirst(false);
		}
		else if(e.getSource() == chooseblack && a == 3){
			choose.dispose();
			ChessBoard.setTime();
			Thread thread = new Thread(new Runnable(){
				public void run(){
			        ChessBoard.chessboard.setTable(6, 5, 2);
			        ChessBoard.chessboard.setPlayerFirst(true);
				}
			});
			thread.start();
			ChessBoard.setTime();
		}
		else if(e.getSource() == choosewhite && a == 3){
			ChessBoard.setHasComputer(true);
			ChessBoard.chessboard.setIsBlack(false);
			ChessBoard.chessboard.setPlayerFirst(false);
			choose.dispose();
		}
		
	}
}