package vision;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Restart extends JPanel{
	JButton jbMakeSure;
	JButton jbCancel;
	JLabel restartLabel;
	
	ImageIcon icon = new ImageIcon("image/exit.jpg");
	private static JDialog dialog;
	
	Font font1 = new Font("方正平和简体",Font.BOLD,20);
	Font font2 = new Font("汉仪白棋体简",Font.ITALIC,24);
	public Restart(){
		repaint();
		setLayout(null);
		jbMakeSure = new JButton("重新开始");		
		jbMakeSure.setFont(font1);	
		jbMakeSure.setBounds(20,95,180,25);
		jbMakeSure.setContentAreaFilled(false);
		jbMakeSure.setBorder(null);
		add(jbMakeSure);
		jbMakeSure.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.dispose();
				ChessBoard.chessboard.clearTable();
				ChessBoard.chessboard.setIsOver(false);
				if(ChessBoard.chessboard.gethasComputer() && !ChessBoard.chessboard.getPlayerFirst()){
					ChessBoard.chessboard.setTable(7, 7, 1);
				}
				ChessBoard.setTime();
			}
		});
		
		jbCancel = new JButton("我再试试");			
		jbCancel.setFont(font1);
		jbCancel.setBounds(220,95,180,25);
		jbCancel.setContentAreaFilled(false);
		jbCancel.setBorder(null);
		add(jbCancel);
		
		restartLabel = new JLabel("你真的要重新开始吗？");
		restartLabel.setFont(font2);
		restartLabel.setForeground(Color.BLUE);
		add(restartLabel);
		restartLabel.setBounds(70,20,250,50);
		
		jbCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				dialog.dispose();
			}
		});
	}
	public static void showDialog(JFrame owner, boolean modal){
		dialog = new JDialog(owner, modal);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setSize(400, 180);
		dialog.setContentPane(new Restart());
		dialog.setVisible(true);
	}
	
	public static void closeDialog(){
		dialog.dispose();
	}
	protected void paintComponent(Graphics g){
		g.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	    
	}
	
}
		
	
