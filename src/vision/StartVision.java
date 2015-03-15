package vision;

import javax.swing.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;

public class StartVision{
	static JFrame f;
	JLabel jLabel;	
	JLabel singlePerson;
	JLabel twoPerson;
	JLabel about;
	JLabel exit;
	boolean soundPlayed = false;
	
	File file = new File("image/01.au");
	AudioClip soundPut;
	
			
	@SuppressWarnings("deprecation")
	public StartVision() throws MalformedURLException{
		
		soundPut = Applet.newAudioClip(file.toURL());
		f = new JFrame();
		
		
		Container contentPane = f.getContentPane();
		contentPane.setLayout(null);
		f.setTitle("Îå×ÓÆå");
		f.setSize(1000,700);
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		
		
		jLabel = new JLabel(new ImageIcon("image//StartVision.jpg"));
		contentPane.add(jLabel);
		jLabel.setBounds(0,0,1000,700);
		
		
		
		
		singlePerson = new JLabel(new ImageIcon("image/startSingle.png"));
		jLabel.add(singlePerson);
		singlePerson.setBounds(620, 200, 300, 85);
		singlePerson.setVisible(false);
		
		twoPerson = new JLabel(new ImageIcon("image/startTwo.png"));
		jLabel.add(twoPerson);
		twoPerson.setBounds(620,285,300,85);
		twoPerson.setVisible(false);
		
		about = new JLabel(new ImageIcon("image/about.png"));
		jLabel.add(about);
		about.setBounds(554,392,300,85);
		about.setVisible(false);
			
		exit = new JLabel(new ImageIcon("image/exit.png"));
		jLabel.add(exit);
		exit.setBounds(550,479,300,85);
		exit.setVisible(false);
		
		contentPane.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int x = e.getX();
				int y = e.getY();
				if(x >= 620 && x <= 920 && y >= 200 && y <= 280){
					f.dispose();
					ChessBoard.main(null);
					ChessBoard.setHasComputer(true);
					try{
					    ChessBoard.chessboard.clearTable();
					    ChessBoard.chessboard.setIsOver(false);
					}catch(Exception ex){}
				}
				else if(x >= 620 && x <= 920 && y >= 290 && y <= 370){
					f.dispose();
					ChessBoard.main(null);
					ChessBoard.setHasComputer(false);
					try{
					ChessBoard.chessboard.clearTable();
					ChessBoard.chessboard.setIsOver(false);
					}catch(Exception ex){}
				}
				else if(x >= 620 && x <= 770 && y >= 392 && y <= 470){
					new AboutDialog(f, true);
				}
				else if(x >= 620 && x <= 770 && y >= 480 && y <= 560){
					System.exit(0);
				}
			}
		});
		
		contentPane.addMouseMotionListener(new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				int x = e.getX();
				int y = e.getY();
				if(x >= 620 && x <= 920 && y >= 200 && y <= 280){
					if(!soundPlayed){
					    soundPut.play();
					    soundPlayed = true;
					    singlePerson.setVisible(true);
					}
				}
				else if(x >= 620 && x <= 920 && y >= 290 && y <= 370){
					if(!soundPlayed){
					    soundPut.play();
					    soundPlayed = true;
					    twoPerson.setVisible(true);
					}
				}
				else if(x >= 620 && x <= 770 && y >= 392 && y <= 470){
					if(!soundPlayed){
					    soundPut.play();
					    soundPlayed = true;
					    about.setVisible(true);
					}
				}
				else if(x >= 620 && x <= 770 && y >= 480 && y <= 560){
					if(!soundPlayed){
					    soundPut.play();
					    soundPlayed = true;
					    exit.setVisible(true);
					}
				}
				else{
					if(soundPlayed){
					    soundPlayed = false;
					    soundPut.stop();
					    singlePerson.setVisible(false);
					    twoPerson.setVisible(false);
					    about.setVisible(false);
					    exit.setVisible(false);
					}
				}
			}
		});
	}
	
	public static void main(String[] args) throws MalformedURLException{
		new StartVision();
	}
}