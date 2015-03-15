package vision;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Music extends MouseAdapter{
	JDialog dialog;
	JLabel YinyueYinxiao;
	JLabel left;
	JLabel right;
	
	public Music(JFrame owner, boolean modal){
		dialog = new JDialog(owner, modal);
		dialog.setSize(420,300);
        dialog.setUndecorated(true);
        dialog.setLayout(null);
        dialog.setLocation(230,230);
				
		YinyueYinxiao = new JLabel(new ImageIcon("image/kaiguanbeijing.png"));
		dialog.add(YinyueYinxiao);
		YinyueYinxiao.setBounds(0,0,420,300);
		YinyueYinxiao.addMouseListener(this);
		
		left = new JLabel(new ImageIcon("image/left.png"));
		YinyueYinxiao.add(left);
		left.setBounds(5,50,200,133);
		left.setVisible(true);
		
		right = new JLabel(new ImageIcon("image/right.png"));
		YinyueYinxiao.add(right);
		right.setBounds(185,55,200,133);
		right.setVisible(false);
		
		YinyueYinxiao.setVisible(true);
		dialog.setVisible(true);
	}
	public void mouseClicked(MouseEvent e){		
		int x = e.getX();
		int y = e.getY();
		
		if(x>=54 && x<=120 && y>=104 && y<=186){
			left.setVisible(true);
			right.setVisible(false);
			ChessBoard.chessboard.stopSound1();	
		}
		
		if(x>=268 && x<=355 && y>=94 && y<=195){
			left.setVisible(false);
			right.setVisible(true);
		}
		
		if(x>=164 && x<=228 && y>=194 && y<= 234){
			dialog.dispose();
		}		
	}	
}