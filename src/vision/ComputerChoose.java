package vision;
import javax.swing.*;

import java.awt.Point;
import java.awt.event.*;
public class ComputerChoose {
	    JDialog  jg;
	public ComputerChoose(JFrame owner, boolean modal, int type){
		jg = new JDialog(owner, modal);
		jg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jg.setLayout(null);
		jg.setSize(240, 240);
		JLabel jl = new JLabel("����ѡ����壡");
		if(type == 2){
			jl.setText("�籨ѡ�����");
		}
		jg.add(jl);
		jl.setBounds(20, 40, 200, 80);
		JButton jb = new JButton("ȷ��");
		jg.add(jb);
		jb.setBounds(80, 180, 80, 20);
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				jg.dispose();
			}
		});
		jg.setVisible(true);
		if(type == 2){
			Point point = ChessBoard.points.get(1);
			if(point.x + 1 < 15){
			    ChessBoard.chessboard.setTable(point.x + 1, point.y, 1);
			}
			else{
				ChessBoard.chessboard.setTable(point.x - 1, point.y, 1);
			}
			point = ChessBoard.points.get(0);
			if(point.x - 1 > 0){
				ChessBoard.chessboard.setTable(point.x - 1, point.y, 2);
			}
			else{
				ChessBoard.chessboard.setTable(point.x + 1, point.y, 2);
			}
			jg.dispose();
			new ChooseColor(3, owner, true);
		}
	}
}
