package vision;

import java.util.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;

@SuppressWarnings("serial")
public class StartUI extends JPanel{
	static File file = new File("start\\");
	static String[] pics = file.list();
	File file2 = new File("image/start.wav");
	AudioClip soundPut;
	ImageIcon icon;
	JButton jb = new JButton("Skip");
	JButton jb2 = new JButton("进入游戏");
    static JFrame jf;
	int n = 0;
	Timer timer;
    @SuppressWarnings("deprecation")
	public StartUI() throws MalformedURLException{
    	soundPut = Applet.newAudioClip(file2.toURL());
    	Arrays.sort(pics, new StringComparator());
    	setLayout(null);
    	jb.setFont(new Font("宋体",2,20));
    	jb.setContentAreaFilled(false);
    	add(jb);
    	jb.setBounds(900, 600, 80, 80);
    	jb.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				n = pics.length - 4;
			}
    		
    	});
    	jb.setBorder(null);
    	jb2.setFont(new Font("宋体",2,20));
    	jb2.setContentAreaFilled(false);
    	jb2.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			soundPut.stop();
    			jf.dispose();
    			try {
					StartVision.main(null);
				} catch (MalformedURLException e1) {}
    		}
    	});
    	jb2.setBorder(new EtchedBorder(EtchedBorder.RAISED));
    	add(jb2);
    	jb2.setBounds(440, 650, 120, 40);
    	jb2.setVisible(false);
    	timer = new Timer(0,new TimerListener());
		timer.start();
    	icon = new ImageIcon("start" + File.separator + pics[n]);
    	repaint();
    	soundPut.loop();
    }
    public void stopTimer(){
    	timer.stop();
    }
    
    public static void main(String[] args) throws MalformedURLException{
        jf =new JFrame();
    	jf.setContentPane(new StartUI());
    	jf.setUndecorated(true);
    	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	jf.setSize(1000, 700);
    	jf.setLocation(200,0);
    	jf.setTitle("五子棋");
    	jf.setVisible(true);
    	
    }
   
    class TimerListener implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		if(n < pics.length){
    			icon = new ImageIcon("start" + File.separator + pics[n]);
    			n++;
    			repaint();
    		}
    		else{
    			jb2.setVisible(true);
    			stopTimer();
    		}
    	}
    }
    protected void paintComponent(Graphics g){
    	g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}



class StringComparator implements Comparator<Object>{
	public StringComparator(){
		
	}
	
	public int compare(Object o1, Object o2) {
		if(o1 instanceof String){
			if(((String) o1).length() < ((String) o2).length()){
				return -1;
			}
			else if(((String) o1).length() == ((String) o2).length()){
				return ((String) o1).compareTo((String) o2);
			}
			else{
				return 1;
			}
		}
		throw new IllegalArgumentException("param o1, o2 must String.");
	}
	
}
