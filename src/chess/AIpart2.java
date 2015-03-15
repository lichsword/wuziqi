package chess;

import vision.ChessBoard;

public class AIpart2 {
	//其中第一个下标为1时表示黑棋，为2时表示白棋，第二和第三
	//个下标表示(x,y)，第四个下标表示8个方向，最后一个下标为1时表示棋子数，为2时表示距空格的格数
	
	public static int x_Max = ChessBoard.x_Max;
	public static int y_Max = ChessBoard.y_Max;
	protected  static int[][][][][] calculate = new int[3][x_Max][y_Max][8][3];;
	
	protected static final int DIR_UP = 0;
	protected static final int DIR_UPRIGHT = 1;
	protected static final int DIR_RIGHT = 2;
	protected static final int DIR_RIGHTDOWN = 3;
	protected static final int DIR_DOWN = 4;
	protected static final int DIR_DOWNLEFT = 5;
	protected static final int DIR_LEFT = 6;
	protected static final int DIR_LEFTUP = 7;
	
	
	
	
	
	//black[][][] 中第一个下表表示此位置的横坐标，第二个表示纵坐标，第三个
	//表示各个方向的得分，其中 0：竖直  1:斜上右  2：水平3：斜上左
	//white同上
	protected static int[][][] black = new int[x_Max][y_Max][4];
	protected static int[][][] white = new int[x_Max][y_Max][4];
	
	
	public AIpart2(){
	}
	
	//将calculate中数据清除，以便下次使用
	private void calculateClear(){
		for(int i = 0; i < 3; i++){
			for(int k = 0; k < x_Max; k++){
				for(int l = 0; l < y_Max; l++){
					for(int m = 0; m < 8; m++){
						for(int n = 0; n < 3; n++){
							calculate[i][k][l][m][n] = 0;
						}
					}
				}
			}
		}
	}
	
	//将black[][][],white[][][]中数据清除
	private void clearBlackWhite(){
		for(int i = 0; i < x_Max; i ++){
			for(int k = 0; k < y_Max; k++){
				for(int l = 0; l < 4; l++){
					black[i][k][l] = 0;
					white[i][k][l] = 0;
				}
			}
			
		}
	}
	
	//计算calculate
	protected  void getEachDir(int[][] table){
		calculateClear();
		clearBlackWhite();
		for(int x = 0; x < x_Max; x++){
			for(int y = 0; y < y_Max; y++){
				if(table[x][y] == 0){
					
					//计算上方
					for(int i = 1; ; i++){
						if(y - i >=0){
							if(table[x][y - 1] == 0){
								break;
							}
							if(table[x][y - i] == table[x][y - 1]){
								calculate[table[x][y - 1]][x][y][DIR_UP][1] ++;
							}
							else{
								if(table[x][y - i] == 0){
									calculate[table[x][y - 1]][x][y][DIR_UP][2] = i - 1;
									break;
								}
								else{
									calculate[table[x][y - 1]][x][y][DIR_UP][2] = -1;
									break;
								}
							}
						}
						else{
							if(y == 0){
								calculate[1][x][y][DIR_UP][2] = -1;
								calculate[2][x][y][DIR_UP][2] = -1;
								break;
							}
							else
							    calculate[table[x][y - 1]][x][y][DIR_UP][2] = -1;
							break;
						}
					}
					
					
					
					//计算右上方
					for(int i = 1; ; i++){
						if(y - i >=0 && x + i <= x_Max - 1){
							if(table[x + 1][y - 1] == 0){
								break;
							}
							if(table[x + i][y - i] == table[x + 1][y - 1]){
								calculate[table[x + 1][y - 1]][x][y][DIR_UPRIGHT][1] ++;
							}
							else{
								if(table[x + i][y - i] == 0){
									calculate[table[x + 1][y - 1]][x][y][DIR_UPRIGHT][2] = i - 1;
									break;
								}
								else{
									calculate[table[x + 1][y - 1]][x][y][DIR_UPRIGHT][2] = -1;
									break;
								}
							}
						}
						else{
							if(x == x_Max - 1 || y == 0){
								calculate[1][x][y][DIR_UPRIGHT][2] = -1;
								calculate[2][x][y][DIR_UPRIGHT][2] = -1;
								break;
							}
							calculate[table[x + 1][y - 1]][x][y][DIR_UPRIGHT][2] = -1;
							break;
						}
					}
					
					
					//计算右方
					for(int i = 1; ; i++){
						if(x + i <= x_Max - 1){
							if(table[x + 1][y] == 0){
								break;
							}
							if(table[x + i][y] == table[x + 1][y]){
								calculate[table[x + 1][y]][x][y][DIR_RIGHT][1] ++;
							}
							else{
								if(table[x + i][y] == 0){
									calculate[table[x + 1][y]][x][y][DIR_RIGHT][2] = i - 1;
									break;
								}
								else{
									calculate[table[x + 1][y]][x][y][DIR_RIGHT][2] = -1;
									break;
								}
							}
						}
						else{
							if(x == x_Max - 1){
								calculate[1][x][y][DIR_RIGHT][2] = -1;
								calculate[2][x][y][DIR_RIGHT][2] = -1;
								break;
							}
							calculate[table[x + 1][y]][x][y][DIR_RIGHT][2] = -1;
							break;
						}
					}
					
					
					//计算右下方
					for(int i = 1; ; i++){
						if(x + i <= x_Max - 1 && y + i <= y_Max - 1){
							if(table[x + 1][y + 1] == 0){
								break;
							}
							if(table[x + i][y + i] == table[x + 1][y + 1]){
								calculate[table[x + 1][y + 1]][x][y][DIR_RIGHTDOWN][1] ++;
							}
							else{
								if(table[x + i][y + i] == 0){
									calculate[table[x + 1][y + 1]][x][y][DIR_RIGHTDOWN][2] = i - 1;
									break;
								}
								else{
									calculate[table[x + 1][y + 1]][x][y][DIR_RIGHTDOWN][2] = -1;
									break;
								}
							}
						}
						else{
							if(x == x_Max - 1 || y == y_Max - 1){
								calculate[1][x][y][DIR_RIGHTDOWN][2] = -1;
								calculate[1][x][y][DIR_RIGHTDOWN][2] = -1;
								break;
							}
							calculate[table[x + 1][y + 1]][x][y][DIR_RIGHTDOWN][2] = -1;
							break;
						}
					}
					
					//计算下方
					for(int i = 1; ; i++){
						if(y + i <= y_Max - 1){
							if(table[x][y + 1] == 0){
								break;
							}
							if(table[x][y + i] == table[x][y + 1]){
								calculate[table[x][y + 1]][x][y][DIR_DOWN][1] ++;
							}
							else{
								if(table[x][y + i] == 0){
									calculate[table[x][y + 1]][x][y][DIR_DOWN][2] = i - 1;
									break;
								}
								else{
									calculate[table[x][y + 1]][x][y][DIR_DOWN][2] = -1;
									break;
								}
							}
						}
						else{
							if(y == y_Max - 1){
								calculate[1][x][y][DIR_DOWN][2] = -1;
								calculate[1][x][y][DIR_DOWN][2] = -1;
								break;
							}
							calculate[table[x][y + 1]][x][y][DIR_DOWN][2] = -1;
							break;
						}
					}
					
					//计算左下方
					for(int i = 1; ; i++){
						if(x - i >= 0 && y + i <= y_Max - 1){
							if(table[x - 1][y + 1] == 0){
								break;
							}
							if(table[x - i][y + i] == table[x - 1][y + 1]){
								calculate[table[x - 1][y + 1]][x][y][DIR_DOWNLEFT][1] ++;
							}
							else{
								if(table[x - i][y + i] == 0){
									calculate[table[x - 1][y + 1]][x][y][DIR_DOWNLEFT][2] = i - 1;
									break;
								}
								else{
									calculate[table[x - 1][y + 1]][x][y][DIR_DOWNLEFT][2] = -1;
									break;
								}
							}
						}
						else{
							if(x == 0 || y == y_Max - 1){
								calculate[1][x][y][DIR_DOWNLEFT][2] = -1;
								calculate[1][x][y][DIR_DOWNLEFT][2] = -1;
								break;
							}
							calculate[table[x - 1][y + 1]][x][y][DIR_DOWNLEFT][2] = -1;
							break;
						}
					}
					
					//计算左方
					for(int i = 1; ; i++){
						if(x - i >= 0){
							if(table[x - 1][y] == 0){
								break;
							}
							if(table[x - i][y] == table[x - 1][y]){
								calculate[table[x - 1][y]][x][y][DIR_LEFT][1] ++;
							}
							else{
								if(table[x - i][y] == 0){
									calculate[table[x - 1][y]][x][y][DIR_LEFT][2] = i - 1;
									break;
								}
								else{
									calculate[table[x - 1][y]][x][y][DIR_LEFT][2] = -1;
									break;
								}
							}
						}
						else{
							if(x == 0){
								calculate[1][x][y][DIR_LEFT][2] = -1;
								calculate[1][x][y][DIR_LEFT][2] = -1;
								break;
							}
							calculate[table[x - 1][y]][x][y][DIR_LEFT][2] = -1;
							break;
						}
					}
					
					//计算左上方
					for(int i = 1; ; i++){
						if(x - i >=0 && y - i >= 0){
							if(table[x - 1][y - 1] == 0){
								break;
							}
							if(table[x - i][y - i] == table[x - 1][y - 1]){
								calculate[table[x - 1][y - 1]][x][y][DIR_LEFTUP][1] ++;
							}
							else{
								if(table[x - i][y - i] == 0){
									calculate[table[x - 1][y - 1]][x][y][DIR_LEFTUP][2] = i - 1;
									break;
								}
								else{
									calculate[table[x - 1][y - 1]][x][y][DIR_LEFTUP][2] = -1;
									break;
								}
							}
						}
						else{
							if(x == 0 || y == 0){
								calculate[1][x][y][DIR_LEFTUP][2] = -1;
								calculate[1][x][y][DIR_LEFTUP][2] = -1;
								break;
							}
							calculate[table[x - 1][y - 1]][x][y][DIR_LEFTUP][2] = -1;
							break;
						}
					}
					
				}
			}
		}
	}
	
	
	protected int getMaxOneType(int type, int[][] table){
		int maxScoreOneType = 0;
		for(int x = 0; x < x_Max; x++){
			for(int y = 0; y < y_Max; y++){
				if(table[x][y] == 0){
					if(type == 1){
					    if(black[x][y][0] + black[x][y][1] + black[x][y][2] + black[x][y][3] > maxScoreOneType){
					    	maxScoreOneType = black[x][y][0] + black[x][y][1] + black[x][y][2] + black[x][y][3];
					    }
					}
					else if(type == 2){
						if(white[x][y][0] +white[x][y][1] + white[x][y][2] + white[x][y][3] > maxScoreOneType){
							maxScoreOneType = white[x][y][0] +white[x][y][1] + white[x][y][2] + white[x][y][3];
						}
					}
				}
			}
		}
		return maxScoreOneType;
	}
	
	protected void getScore(int[][] table){
		
		for(int x = 0; x < x_Max; x++){
			for(int y = 0; y < y_Max; y++){
				if(table[x][y] == 0){
				    for(int i = 0; i < 4; i++){
				
				    	//在此位置下后生成连五
					    if((calculate[1][x][y][i][1] == 4 && calculate[1][x][y][i + 4][1] == 0) || 
						    	(calculate[1][x][y][i + 4][1] == 4 && calculate[1][x][y][i][1] == 0)||
						    	(calculate[1][x][y][i][1] + calculate[1][x][y][i + 4][1] == 4)){
					    	black[x][y][i] = 8000;
				    	}
				    	//在此位置下后生成活四
					    else if((calculate[1][x][y][i][1] == 3 && calculate[1][x][y][i][2] == 3 && calculate[1][x][y][i + 4][2] == 0)||
						    	(calculate[1][x][y][i + 4][1] == 3 && calculate[1][x][y][i][2] == 0 && calculate[1][x][y][i + 4][2] == 3)||
						    	((calculate[1][x][y][i + 4][1] + calculate[1][x][y][i][1]) ==3 && (calculate[1][x][y][i + 4][2] + calculate[1][x][y][i][2]) ==3)){
						    black[x][y][i] = 2000;
					    }
					    //在此位置下后生成冲四
					    else if((calculate[1][x][y][i][1] == 3 && calculate[1][x][y][i][2] == 3 && calculate[1][x][y][i + 4][1] == 0 && calculate[1][x][y][i + 4][2] == -1) ||
							    (calculate[1][x][y][i + 4][1] == 3 && calculate[1][x][y][i + 4][2] == 3 &&calculate[1][x][y][i][1] == 0 && calculate[1][x][y][i][2] == -1) ||
							    (calculate[1][x][y][i][1] == 3 && calculate[1][x][y][i][2] == -1 && calculate[1][x][y][i + 4][2] == 0 ) ||
							    (calculate[1][x][y][i + 4][1] == 3 && calculate[1][x][y][i + 4][2] == -1 && calculate[1][x][y][i][2] == 0 ) ||
							    ((calculate[1][x][y][i][1] ==2 && calculate[1][x][y][i + 4][1] ==1) && 
							    (calculate[1][x][y][i][2] == 2 ^ calculate[1][x][y][i + 4][2] == 1)) ||
							    ((calculate[1][x][y][i][1] ==1 && calculate[1][x][y][i + 4][1] ==2) && 
							    (calculate[1][x][y][i][2] == 1 ^ calculate[1][x][y][i + 4][2] == 2))){
							black[x][y][i] = 400;	
					    }
					    //在此位置下后为活三
					    else if((calculate[1][x][y][i][1] == 2 && calculate[1][x][y][i][2] == 2 && calculate[1][x][y][i + 4][2] == 0)||
					    		(calculate[1][x][y][i + 4][1] == 2 && calculate[1][x][y][i + 4][2] == 2 && calculate[1][x][y][i][2] == 0) ||
					    		(calculate[1][x][y][i + 4][1] == 1 && calculate[1][x][y][i][1] == 1 && calculate[1][x][y][i + 4][2] == 1 && calculate[1][x][y][i][2] == 1) ){
					    	black[x][y][i] = 400;
					    }
					   //在此位置下后为冲三
					    else if((calculate[1][x][y][i][1] == 2 && calculate[1][x][y][i][2] == -1 && calculate[1][x][y][i + 4][2] == 0)||
					    		(calculate[1][x][y][i][1] == 2 && calculate[1][x][y][i][2] == 0 && calculate[1][x][y][i + 4][2] == -1)||
					    		(calculate[1][x][y][i + 4][1] == 2 && calculate[1][x][y][i + 4][2] == -1 && calculate[1][x][y][i][2] == 0) ||
					    		(calculate[1][x][y][i + 4][1] == 2 && calculate[1][x][y][i + 4][2] == 0 && calculate[1][x][y][i][2] == -1) ||
					    		(calculate[1][x][y][i + 4][1] == 1 && calculate[1][x][y][i][1] == 1 && (calculate[1][x][y][i + 4][2] == -1 ^ calculate[1][x][y][i][2] == -1))){
					    	black[x][y][i] = 20;
					    }
					    //在此位置下后为活二
					    else if((calculate[1][x][y][i][1] == 1 && calculate[1][x][y][i][2] == 1 && calculate[1][x][y][i + 4][2] == 0)||
					    		(calculate[1][x][y][i + 4][1] == 1 && calculate[1][x][y][i + 4][2] == 1 && calculate[1][x][y][i][2] == 0) ){
					    	black[x][y][i] = 15;
					    }
					    //在此位置下后为冲二
					    else if((calculate[1][x][y][i][1] == 1 && calculate[1][x][y][i + 4][1] == 0 && (calculate[1][x][y][i][2] == -1 ^ calculate[1][x][y][i + 4][2] == -1 ))||
					    		(calculate[1][x][y][i + 4][1] == 1 && calculate[1][x][y][i][1] == 0 && (calculate[1][x][y][i + 4][2] == -1 ^ calculate[1][x][y][i][2] == -1))){
					    	black[x][y][i] = 2;
					    }
					    
					    
					    
					    
					    //在此位置下后生成连五
					    if((calculate[2][x][y][i][1] == 4 && calculate[2][x][y][i + 4][1] == 0) || 
						    	(calculate[2][x][y][i + 4][1] == 4 && calculate[2][x][y][i][1] == 0)||
						    	(calculate[2][x][y][i][1] + calculate[2][x][y][i + 4][1] == 4)){
					    	white[x][y][i] = 8000;
				    	}
				    	//在此位置下后生成活四
					    else if((calculate[2][x][y][i][1] == 3 && calculate[2][x][y][i][2] == 3 && calculate[2][x][y][i + 4][2] == 0)||
						    	(calculate[2][x][y][i + 4][1] == 3 && calculate[2][x][y][i][2] == 0 && calculate[2][x][y][i + 4][2] == 3)||
						    	((calculate[2][x][y][i + 4][1] + calculate[2][x][y][i][1]) ==3 && (calculate[2][x][y][i + 4][2] + calculate[2][x][y][i][2]) ==3)){
					    	white[x][y][i] = 2000;
					    }
					    //在此位置下后生成冲四
					    else if((calculate[2][x][y][i][1] == 3 && calculate[2][x][y][i][2] == 3 && calculate[2][x][y][i + 4][1] == 0 && calculate[2][x][y][i + 4][2] == -1) ||
							    (calculate[2][x][y][i + 4][1] == 3 && calculate[2][x][y][i + 4][2] == 3 && calculate[2][x][y][i][1] == 0 && calculate[2][x][y][i][2] == -1) ||
							    (calculate[2][x][y][i][1] == 3 && calculate[2][x][y][i][2] == -1 && calculate[1][x][y][i + 4][1] == 0 ) ||
							    (calculate[2][x][y][i + 4][1] == 3 && calculate[2][x][y][i + 4][2] == -1 && calculate[1][x][y][i][1] == 0 ) ||
							    ((calculate[2][x][y][i][1] ==2 && calculate[2][x][y][i + 4][1] ==1) && 
							    (calculate[2][x][y][i][2] == 2 ^ calculate[2][x][y][i + 4][2] == 1)) ||
							    ((calculate[2][x][y][i][1] ==1 && calculate[2][x][y][i + 4][1] ==2) && 
							    (calculate[2][x][y][i][2] == 1 ^ calculate[2][x][y][i + 4][2] == 2))){
					    	white[x][y][i] = 400;	
					    }
					    //在此位置下后为活三
					    else if((calculate[2][x][y][i][1] == 2 && calculate[2][x][y][i][2] == 2 && calculate[2][x][y][i + 4][2] == 0)||
					    		(calculate[2][x][y][i + 4][1] == 2 && calculate[2][x][y][i + 4][2] == 2 && calculate[2][x][y][i][2] == 0) ||
					    		(calculate[2][x][y][i + 4][1] == 1 && calculate[2][x][y][i][1] == 1 && calculate[2][x][y][i + 4][2] == 1 && calculate[2][x][y][i][2] == 1) ){
					    	white[x][y][i] = 400;
					    }
					   //在此位置下后为冲三
					    else if((calculate[2][x][y][i][1] == 2 && calculate[2][x][y][i][2] == -1 && calculate[2][x][y][i + 4][2] == 0)||
					    		(calculate[2][x][y][i][1] == 2 && calculate[2][x][y][i][2] == 0 && calculate[2][x][y][i + 4][2] == -1)||
					    		(calculate[2][x][y][i + 4][1] == 2 && calculate[2][x][y][i + 4][2] == -1 && calculate[2][x][y][i][2] == 0) ||
					    		(calculate[2][x][y][i + 4][1] == 2 && calculate[2][x][y][i + 4][2] == 0 && calculate[2][x][y][i][2] == -1) ||
					    		(calculate[2][x][y][i + 4][1] == 1 && calculate[2][x][y][i][1] == 1 && (calculate[2][x][y][i + 4][2] == -1 ^ calculate[1][x][y][i][2] == -1))){
					    	white[x][y][i] = 20;
					    }
					    //在此位置下后为活二
					    else if((calculate[2][x][y][i][1] == 1 && calculate[2][x][y][i][2] == 1 && calculate[2][x][y][i + 4][2] == 0)||
					    		(calculate[2][x][y][i + 4][1] == 1 && calculate[2][x][y][i + 4][2] == 1 && calculate[2][x][y][i][2] == 0) ){
					    	white[x][y][i] = 15;
					    	
					    }
					    //在此位置下后为冲二
					    else if((calculate[2][x][y][i][1] == 1 && calculate[2][x][y][i + 4][1] == 0 && (calculate[2][x][y][i][2] == -1 ^ calculate[2][x][y][i + 4][2] == -1 ))||
					    		(calculate[2][x][y][i + 4][1] == 1 && calculate[2][x][y][i][1] == 0 && (calculate[2][x][y][i + 4][2] == -1 ^ calculate[2][x][y][i][2] == -1))){
					    	white[x][y][i] = 2;
					    }
				    
				    
				}
				}
				
			}
		}
			
	}
    
	//返回一个种类棋子的总分
	public int getTotalScoreEach(int type){
		int totalScore = 0;
		if(type == 1){
			for(int x = 0; x < x_Max; x++){
				for(int y = 0; y < y_Max; y++){
					for(int i = 0; i < 4; i++){
						totalScore += black[x][y][i];
					}
				}
				
			}
		}
		else{
			for(int x = 0; x < x_Max; x++){
				for(int y = 0; y < y_Max; y++){
					for(int i = 0; i < 4; i++){
						totalScore += white[x][y][i];
					}
				}
				
			}
		}
		return totalScore;
	}

	
	protected int getOnePointScore(int x, int y, int type){
		if(type == 2){
			return white[x][y][0] + white[x][y][1] + white[x][y][2] + white[x][y][3];
		}
		else if(type == 1){
			return black[x][y][0] + black[x][y][1] + black[x][y][2] + black[x][y][3];
		}
		else{
			return 0;
		}
	}
	
	
	public  boolean getIsOver(int x, int y, int type){
		if(getOnePointScore(x,y,type) >= 8000){
	       return true;
		}
		else
			return false;
    }
	
}
