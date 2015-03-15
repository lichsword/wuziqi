package vision;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Exit extends MouseAdapter{
	JDialog frame11;
	JLabel exitpicture;
	
	public void exit(){
		frame11 = new JDialog();
		frame11.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame11.setVisible(true);
		frame11.setSize(400,183);
		frame11.setLayout(null);
        //frame11.setUndecorated(true);
        frame11.setLocation(230,230);
		
		exitpicture = new JLabel(new ImageIcon("image/tuichu.png"));
		frame11.add(exitpicture);
		exitpicture.setBounds(0,0,400,183);
		exitpicture.setVisible(true);
		exitpicture.addMouseListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		if(x>=50 && x<= 230 && y>=95 && y<=120){
			frame11.setVisible(false);
			System.exit(0);
		}
		if(x>=250 && x<= 430 && y>=95 && y<=120){
			frame11.setVisible(false);
		}
	}	
}