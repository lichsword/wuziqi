package vision;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import chess.*;
import javax.swing.*;
import vision.StartVision;

@SuppressWarnings("serial")
public class ChessBoard extends JPanel {
	
	File file1 = new File("image/1248.wav");
	AudioClip soundPut1;
	File file2 = new File("image/start.wav");
	AudioClip soundPut2;
	
	//棋盘的规格
	public static int x_Max = 15;
	public static int y_Max = 15;
	
	//棋盘左上角的坐标
	private static int x_top_left = 40;
	private static int y_top_left = 60;

	//表示是否为黑棋下
    private static boolean isBlack = true;  
    private static boolean hasComputer = true;
    private boolean swap2 = false;
    
    public static ArrayList<Point> points = new ArrayList<Point>();
    
    //默认玩家先手
    private static boolean playerFirst = false;
    
    private static boolean isOver = false;  //表示是否结束
    
    //当table[][]为零时，此处无棋，一时为黑棋，二时为白棋
    private static int[][] table = new int[x_Max][x_Max];
    private static int[][] table2 = new int[x_Max][x_Max];
    private int x;
    private int y;
    
    private static int level = 4;
    
    //限制时间3分钟
    private static int time = 180;
    
    public boolean Yinyuekaiguan = true;
    public boolean Yinxiaokaiguan = true;
    
    public Point pointLast = new Point();
    
    static JFrame mainBoard;	
	
	
	JButton jbRestart;  //
	JButton jbUndo;
	JButton jbBackplay;	
	JButton jbSetUp;
	JButton jbExit; 
	JButton jbMusic;     
	
	JLabel JLTimer;
	
	JButton exitjbSetUpqueding;
	JButton exitjbSetUpquxiao;
	
	ActionListener buttonListener = new ButtonListener();
    
    StartVision startvision;
    public static ChessBoard chessboard;
    
    //构造器，监测并计算下棋的位置
	@SuppressWarnings("deprecation")
	public ChessBoard() throws MalformedURLException {
		
		soundPut1 = Applet.newAudioClip(file1.toURL());
		soundPut2 = Applet.newAudioClip(file2.toURL());
		
		setLayout(null);
		
		jbRestart = new JButton("重新开始");
		jbRestart.setBounds(90, 572, 80, 80);
		jbRestart.setContentAreaFilled(false);
		jbRestart.setBorder(null);
		jbRestart.addActionListener(buttonListener);
		add(jbRestart);
		
		jbUndo = new JButton("悔棋");
		jbUndo.setBounds(260, 570, 80, 80);
		jbUndo.setContentAreaFilled(false);
		jbUndo.setBorder(null);
		jbUndo.addActionListener(buttonListener);
		add(jbUndo);
		
		jbBackplay = new JButton("回放");
		jbBackplay.setBounds(435, 570, 80, 80);
		jbBackplay.setContentAreaFilled(false);
		jbBackplay.setBorder(null);
		jbBackplay.addActionListener(buttonListener);
		add(jbBackplay);
		
		jbMusic = new JButton("音乐/音效");
        jbMusic.setVisible(true);
		jbMusic.setBounds(620, 570, 80, 80);
		jbMusic.setContentAreaFilled(false);
		jbMusic.setBorder(null);
		jbMusic.addActionListener(buttonListener);
		add(jbMusic);
		
		jbSetUp = new JButton("规则设置");
		jbSetUp.setContentAreaFilled(false);
		jbSetUp.setBorder(null);
		jbSetUp.setBounds(795, 575, 80, 80);
		jbSetUp.addActionListener(buttonListener);
		add(jbSetUp);
		
		jbExit = new JButton("退出");
		jbExit.setContentAreaFilled(false);
		jbExit.setBorder(null);
		jbExit.setBounds(930, 595, 50, 50);
		jbExit.addActionListener(buttonListener);		
		add(jbExit);
		
		JLTimer = new JLabel();
		JLTimer.setBounds(150, 0, 300, 60);
		JLTimer.setFont(new Font("隶书",Font.BOLD,30));
		JLTimer.setText("剩余180秒");
		JLTimer.setOpaque(false);
		add(JLTimer);	
		
		Thread thread = new Thread(new Runnable(){
			public void run(){
				while(true){
				    if(time > 0){
				    	JLTimer.setText("时间剩余" + time + "秒");
						time--;
				    }
				    else{
				    	JLTimer.setText("时间到!");
				    }
				    try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		thread.start();
		if(hasComputer && !playerFirst){
			if(table[7][7] == 0){
			    table[7][7] = 1;
			    table2[7][7] = 1;
			}
			points.add(new Point(7, 7));
		}
		repaint();
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				x = (int)((e.getX() - x_top_left) / 30.0 + 0.5);
				y = (int)((e.getY() - y_top_left) / 30.0 + 0.5);
				if(x < 0 || x > x_Max || y < 0 || y > y_Max || isOver || table[x][y] != 0){
					return;
				}
				if(!hasComputer){
					AIpart ai = new AIpart();
					ai.getEachDir(table2);
					table[x][y] = isBlack? 1: 2;
					table2[x][y] = table[x][y];
					points.add(new Point(x,y));
					
					isBlack = !isBlack;
					if(ai.getIsOver(x, y, table2[x][y])){
						isOver = true;
					}
					repaint();
					time = 180;
					if(points.size() == 3 && swap2){
						hasComputer = true;
						playerFirst = false;
						if(Math.abs(points.get(0).x - points.get(1).x) + Math.abs(points.get(0).y - points.get(1).y) > 2
								&& Math.abs(points.get(2).x - points.get(1).x) + Math.abs(points.get(2).y - points.get(1).y) > 2
								&& Math.abs(points.get(2).x - points.get(0).x) + Math.abs(points.get(2).y - points.get(0).y) > 2){
							new ComputerChoose(mainBoard, true, 2);
						}
						else{
							new ComputerChoose(mainBoard, true, 1);
						}
					}
					else if(points.size() == 5 && swap2){
						hasComputer = true;
						playerFirst = false;
						new ComputerChoose(mainBoard, true, 1);
					}
				}
				else if(hasComputer && !playerFirst){
					if(points.size() % 2 == 0){
						return;
					}
					AIpart ai = new AIpart();
					ai.getEachDir(table2);
					
					table[x][y] = 2;
					table2[x][y] = 2;
					points.add(new Point(x, y));
					if(ai.getIsOver(x, y, 2)){
						isOver = true;
						repaint();
						time = 0;
			            return;
					}
					
					
					
					Thread thread = new Thread(new Runnable(){
						public void run() {
							Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
							repaint();
							time = 180;
							Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
						}
					});
					
					Thread thread2 = new Thread(new Runnable(){
						public void run() {
							Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
							AIpart ai2 = new AIpart();
						    ai2.getEachDir(table2);
							Point point = LevelChoose.getPoint(level, 1, table2);
							table[point.x][point.y] = 1;
							table2[point.x][point.y] = 1;
							points.add(point);
							if(ai2.getIsOver(point.x, point.y, 1)){
								isOver = true;
								time = 0;
					            return;
							}
							repaint();
							time = 180;
							Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
						}
					});
					
					thread.start();
					thread2.start();
				}
				else if(hasComputer && playerFirst){
					if(points.size() % 2 != 0){
						return;
					}
					
					AIpart ai = new AIpart();
					ai.getEachDir(table2);
					
					table[x][y] = 1;
					table2[x][y] = 1;
					points.add(new Point(x, y));
					if(ai.getIsOver(x, y, 1)){
						isOver = true;
						time = 0;
						repaint();
			            return;
					}
					Thread thread = new Thread(new Runnable(){
						public void run() {
							Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
							repaint();
							time = 180;
							Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
						}
					});
					Thread thread2 = new Thread(new Runnable(){
						public void run() {
							Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
							AIpart ai2 = new AIpart();
						    ai2.getEachDir(table2);
							Point point = LevelChoose.getPoint(level, 2, table2);
							table[point.x][point.y] = 2;
							table2[point.x][point.y] = 2;
							points.add(point);
							if(ai2.getIsOver(point.x, point.y, 2)){
								time = 0;
								isOver = true;
					            return;
							}
							repaint();
							time = 180;
							Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
						}
					});
					
					thread.start();
					thread2.start();
				}
			}
		});
	}	
		
	private void backplayActionPerformed(){
		//清空table
		for(int x = 0; x < x_Max; x++){
			for(int y = 0; y < y_Max; y++){
				table[x][y] = 0;
			}
		}
		Thread thread = new Thread(new Runnable(){
			public void run(){
		    for (int j = 0; j < points.size(); j++){
			    int k = j % 2 ;
			    if (k == 0) {
				    int x = points.get(j).x;
				    int y = points.get(j).y;
			    	table[x][y] = 1;
			    	pointLast = new Point(x, y);
		    	}
			    else {
				    int x = points.get(j).x;
				    int y = points.get(j).y;
				    table[x][y] = 2;
				    pointLast = new Point(x, y);
			    }
			    repaint();
			    try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
		    }
			}
		});
		thread.start();
	}
	
	public void clearTable(){
		points.clear();
		for(int x = 0; x < x_Max; x++){
			for(int y = 0; y < y_Max; y++){
				table[x][y] = 0;
				table2[x][y] = 0;
				repaint();
			}
		}
	}
	
	public int getLevel(){
		return level;
	}
	public boolean getPlayerFirst(){
		return playerFirst;
	}
	public boolean gethasComputer(){
		return hasComputer;
	}
	public static void setHasComputer(boolean hasAI){
		hasComputer = hasAI;
	}
	
	public void setPlayerFirst(boolean playerFirst){
		ChessBoard.playerFirst = playerFirst;
	}
	
	public void setLevel(int level){
	    ChessBoard.level = level;
	    clearTable();
	}
	public int[][] getTable(){
	    return ChessBoard.table2;
	}
	
	public static void setTime(){
		time = 180;
	}

	public void setTable(int x, int y, int type){
		table[x][y] = type;
		table2[x][y] = type;
		if(type != 0){
			points.add(new Point(x, y));
		}
		repaint();
	}
	public void reverseIsBlack(){
		isBlack = !isBlack;
	}
	
	public void putSound1(){
		soundPut1.loop();
	}
	
	public void stopSound1(){
		soundPut1.stop();
	}
	public void setIsBlack(boolean isBlack){
		ChessBoard.isBlack = isBlack;
	}
	
	public void setIsOver(boolean isOver){
		ChessBoard.isOver = isOver;
	}
	public void setSwap2(boolean swap2){
		chessboard.swap2 = swap2;
	}
	
	public boolean getSwap2(){
		return chessboard.swap2;
	}
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
		    public void run() {
		        try {
					chessboard = new ChessBoard();
				} catch (MalformedURLException e) {}
			
		        mainBoard = new JFrame("ChessBoardTest");
			    mainBoard.setTitle("五子棋");
				mainBoard.setSize(1000,700);
				mainBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainBoard.add(chessboard);
				mainBoard.setResizable(false);
				mainBoard.setVisible(true);
				chessboard.putSound1();
		   }
	    });		
	}
	
	
	
	protected void paintComponent(Graphics g){
		Image image = (new ImageIcon("image/back.jpg")).getImage();
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		
		for(int i = 0; i < x_Max - 1; i++){
			for(int k = 0; k < y_Max - 1; k++){
				g.drawRect(x_top_left + i * 30, y_top_left + k * 30, 30, 30);
			}
		}
        
		for(int i = 0; i < x_Max; i++){
        	for(int k = 0; k < y_Max; k++){
        		if(table[i][k] != 0){
                    if(table[i][k] == 1){			                            	
                    	Image imageBlack = (new ImageIcon("image/black.png")).getImage();
			            g.drawImage(imageBlack, x_top_left -14 + i * 30, y_top_left -14 + k * 30, 28, 28, this);			            
                    }
			            
                    else{		                
                    	Image imageBlack = (new ImageIcon("image/white.png")).getImage();
			            g.drawImage(imageBlack, x_top_left -14 + i * 30, y_top_left -14 + k * 30, 28, 28, this);		            
                    }
        		}
        	}
		
	    }  
	}
	
	
	class ButtonListener implements ActionListener{
	    public void actionPerformed(ActionEvent e){
		    if(e.getSource() == jbRestart){
			    Restart.showDialog(mainBoard, true);
	    	}		
	    	if(e.getSource() == jbUndo){
	    		if(points.size() == 0){
	    			return;
	    		}
	   		    if(chessboard.gethasComputer()){
	   		    	setTime();
	   		    	if(chessboard.getPlayerFirst()){
	   		    		if(points.size() % 2 == 1){
	   		    			Point point = points.get(points.size() - 1);
	   		    			points.remove(points.size() - 1);
	   		    			chessboard.setTable(point.x, point.y, 0);
	   		    		}
	   		    		else{
	   		    			Point point = points.get(points.size() - 1);
	   		    			points.remove(points.size() - 1);
	   		    			chessboard.setTable(point.x, point.y, 0);
	   		    			point = points.get(points.size() - 1);
	   		    			points.remove(points.size() - 1);
	   		    			chessboard.setTable(point.x, point.y, 0);
	   		    		}
	   		    		chessboard.setIsOver(false);
	   		    	}
	   		    	else{
		   		    	if(points.size() == 1){
		   		    		return;
		   		    	}
	   		    		Point point = points.get(points.size() - 1);
   		    			points.remove(points.size() - 1);
   		    			chessboard.setTable(point.x, point.y, 0);
   		    			point = points.get(points.size() - 1);
   		    			points.remove(points.size() - 1);
   		    			chessboard.setTable(point.x, point.y, 0);
   		    			chessboard.setIsOver(false);
		   		    }
	   		    }
	   		    else{
	   		    	setTime();
	   		    	Point point = points.get(points.size() - 1);
		    		points.remove(points.size() - 1);
		    		chessboard.setTable(point.x, point.y, 0);
		    		chessboard.reverseIsBlack();
		    		chessboard.setIsOver(false);
	   		    }
	    	}
				
	    	if(e.getSource() == jbBackplay){
	    		System.out.println("sfdfs");
	    		backplayActionPerformed();
			
	    	}		
	    	if(e.getSource() == jbMusic){
		    	new Music(mainBoard, true);
		    	
	    	}
		
	    	if(e.getSource() == jbSetUp){
	    		new Setup(mainBoard, true);
	    	}
		
		    if(e.getSource() == jbExit){
			    mainBoard.dispose();
			    soundPut1.stop();
			    try {
					StartVision.main(null);
				} catch (MalformedURLException e1) {}
		    }
        }
	}
	
}