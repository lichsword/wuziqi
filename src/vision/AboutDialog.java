package vision;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AboutDialog{
	JDialog aboutus;
	JLabel aboutpicture;
	JButton aboutexit;
	JLabel aboutl1;
	JLabel aboutl2;
	Font font1 = new Font("����ƽ�ͼ���",Font.ITALIC,18);
	Font font2 = new Font("���ǰ������",Font.BOLD,20);
	
	public AboutDialog(JFrame owner, boolean modal){
		
		aboutus = new JDialog(owner, modal);
		aboutus.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		aboutus.setSize(400,240);
		aboutus.setLayout(null);
		aboutus.setLocation((owner.getWidth()- 400) / 2, (owner.getHeight() - 240) / 2);
		
		aboutpicture = new JLabel(new ImageIcon("image/aboutBackground.png"));
		aboutus.add(aboutpicture);
		aboutpicture.setBounds(0,0,400,240);
		aboutpicture.setVisible(true);			
		
		aboutl1 = new JLabel("     ����Ʒ���ϴ���ԺBlack-Hole��Ʒ");
		aboutpicture.add(aboutl1);
		aboutl1.setFont(font1);
		aboutl1.setForeground(Color.blue);
		aboutl1.setVisible(true);
		aboutl1.setBounds(30,20,340,30);
		
		aboutl2 = new JLabel("��Ա�����٣�����������������������");
		aboutpicture.add(aboutl2);
		aboutl2.setFont(font1);
		aboutl2.setForeground(Color.blue);
		aboutl2.setVisible(true);
	    aboutl2.setBounds(30,70,340,60);
		
		aboutexit = new JButton("�˳�");
		aboutpicture.add(aboutexit);
		aboutexit.setFont(font2);
		aboutexit.setForeground(Color.red);
		aboutexit.setContentAreaFilled(false);
		aboutexit.setBorder(null);
		aboutexit.setVisible(true);
		aboutexit.setBounds(150,135,100,60);
		aboutexit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				aboutus.dispose();
			}
		});
		aboutus.setVisible(true);
	}
}