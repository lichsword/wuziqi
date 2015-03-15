package vision;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Setup{
	static JDialog dialog;
	JRadioButton c1;
	JRadioButton c2;
	ButtonGroup g1;
	
	JRadioButton c3;
	JRadioButton c4;
	JRadioButton c5;
	JRadioButton c6;
	ButtonGroup g2;
	
	JRadioButton c7;
	
	JButton exitsetupqueding;
	JButton exitsetupquxiao;
	
	JLabel YinyueYinxiao;
	JLabel YinyueChange;
	JLabel YinxiaoChange;
	
	static boolean change1;
	     JFrame owner2;
	public Setup(JFrame owner, boolean modal){
		owner2 = owner;
		dialog = new JDialog(owner, modal);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		dialog.setSize(240,320);
		dialog.setLayout(null);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1,2));
		panel1.setBorder(BorderFactory.createTitledBorder("先后手设置"));
		panel1.setBounds(10, 10, 200, 60);
		
		c1 = new JRadioButton("玩家先手",true);
		c2 = new JRadioButton("电脑先手");
		g1 = new ButtonGroup();
		g1.add(c1);
		g1.add(c2);
		panel1.add(c1);
		panel1.add(c2);
		dialog.add(panel1);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(2,2));
		panel2.setBorder(BorderFactory.createTitledBorder("难度选择"));
		panel2.setBounds(10,80,200,100);
		
		c3 = new JRadioButton("简单",true);
		c4 = new JRadioButton("中等");
		c5 = new JRadioButton("困难");
		c6 = new JRadioButton("大师");
		g2 = new ButtonGroup();
		g2.add(c3);
		g2.add(c4);
		g2.add(c5);
		g2.add(c6);
		panel2.add(c3);
		panel2.add(c4);
		panel2.add(c5);
		panel2.add(c6);
		dialog.add(panel2);
		switch(ChessBoard.chessboard.getLevel()){
		case 1:c3.setSelected(true);break;
		case 2:c4.setSelected(true);break;
		case 3:c5.setSelected(true);break;
		case 4:c6.setSelected(true);break;
		}
		if(ChessBoard.chessboard.getPlayerFirst()){
		    c1.setSelected(true);
		}
		else{
		    c2.setSelected(true);
		}
		
		
		c7 = new JRadioButton("SWAP2");
		dialog.add(c7);
		c7.setBounds(10, 190, 80, 20);
		if(ChessBoard.chessboard.getSwap2()){
			c7.setSelected(true);
		}
		
		exitsetupqueding = new JButton("确定");
	    dialog.add(exitsetupqueding);
		exitsetupqueding.setBounds(15,230,80,30);
		exitsetupqueding.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ChessBoard.setTime();
				if(c1.isSelected()){
					ChessBoard.chessboard.setPlayerFirst(true);
				}
				else{
					ChessBoard.chessboard.setPlayerFirst(false);
				}
				if(c3.isSelected()){
					ChessBoard.chessboard.setLevel(1);
				}
				else if(c4.isSelected()){
					ChessBoard.chessboard.setLevel(2);
				}
				else if(c5.isSelected()){
					ChessBoard.chessboard.setLevel(3);
				}
				else if(c6.isSelected()){
					ChessBoard.chessboard.setLevel(4);
				}
				if(c7.isSelected()){
					ChessBoard.chessboard.setSwap2(true);
				}
				else{
					ChessBoard.chessboard.setSwap2(false);
				}
				ChessBoard.chessboard.clearTable();
				ChessBoard.chessboard.setIsOver(false);
				if(c2.isSelected() && ChessBoard.chessboard.gethasComputer() && !ChessBoard.chessboard.getSwap2()){
					ChessBoard.chessboard.setTable(7, 7, 1);
				}
				else if(c2.isSelected() && ChessBoard.chessboard.gethasComputer() && ChessBoard.chessboard.getSwap2()){
					ChessBoard.chessboard.setTable(7, 7, 1);
					ChessBoard.chessboard.setTable(7, 6 , 2);
					ChessBoard.chessboard.setTable(7, 9, 1);
					dialog.dispose();
					new ChooseColor(2, owner2, true);
				}
				else if(!c2.isSelected() &&  ChessBoard.chessboard.gethasComputer() && ChessBoard.chessboard.getSwap2()){
					ChessBoard.setHasComputer(false);
				}
				dialog.dispose();
			}
		});
		
		exitsetupquxiao = new JButton("取消");
		dialog.add(exitsetupquxiao);
		exitsetupquxiao.setBounds(115,230,80,30);
		exitsetupquxiao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.dispose();
			}
		});
		dialog.setVisible(true);
	}
		

}