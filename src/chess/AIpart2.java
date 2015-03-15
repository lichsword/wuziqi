package chess;

import vision.ChessBoard;

public class AIpart2 {
	//���е�һ���±�Ϊ1ʱ��ʾ���壬Ϊ2ʱ��ʾ���壬�ڶ��͵���
	//���±��ʾ(x,y)�����ĸ��±��ʾ8���������һ���±�Ϊ1ʱ��ʾ��������Ϊ2ʱ��ʾ��ո�ĸ���
	
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
	
	
	
	
	
	//black[][][] �е�һ���±��ʾ��λ�õĺ����꣬�ڶ�����ʾ�����꣬������
	//��ʾ��������ĵ÷֣����� 0����ֱ  1:б����  2��ˮƽ3��б����
	//whiteͬ��
	protected static int[][][] black = new int[x_Max][y_Max][4];
	protected static int[][][] white = new int[x_Max][y_Max][4];
	
	
	public AIpart2(){
	}
	
	//��calculate������������Ա��´�ʹ��
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
	
	//��black[][][],white[][][]���������
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
	
	//����calculate
	protected  void getEachDir(int[][] table){
		calculateClear();
		clearBlackWhite();
		for(int x = 0; x < x_Max; x++){
			for(int y = 0; y < y_Max; y++){
				if(table[x][y] == 0){
					
					//�����Ϸ�
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
					
					
					
					//�������Ϸ�
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
					
					
					//�����ҷ�
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
					
					
					//�������·�
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
					
					//�����·�
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
					
					//�������·�
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
					
					//������
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
					
					//�������Ϸ�
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
				
				    	//�ڴ�λ���º���������
					    if((calculate[1][x][y][i][1] == 4 && calculate[1][x][y][i + 4][1] == 0) || 
						    	(calculate[1][x][y][i + 4][1] == 4 && calculate[1][x][y][i][1] == 0)||
						    	(calculate[1][x][y][i][1] + calculate[1][x][y][i + 4][1] == 4)){
					    	black[x][y][i] = 8000;
				    	}
				    	//�ڴ�λ���º����ɻ���
					    else if((calculate[1][x][y][i][1] == 3 && calculate[1][x][y][i][2] == 3 && calculate[1][x][y][i + 4][2] == 0)||
						    	(calculate[1][x][y][i + 4][1] == 3 && calculate[1][x][y][i][2] == 0 && calculate[1][x][y][i + 4][2] == 3)||
						    	((calculate[1][x][y][i + 4][1] + calculate[1][x][y][i][1]) ==3 && (calculate[1][x][y][i + 4][2] + calculate[1][x][y][i][2]) ==3)){
						    black[x][y][i] = 2000;
					    }
					    //�ڴ�λ���º����ɳ���
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
					    //�ڴ�λ���º�Ϊ����
					    else if((calculate[1][x][y][i][1] == 2 && calculate[1][x][y][i][2] == 2 && calculate[1][x][y][i + 4][2] == 0)||
					    		(calculate[1][x][y][i + 4][1] == 2 && calculate[1][x][y][i + 4][2] == 2 && calculate[1][x][y][i][2] == 0) ||
					    		(calculate[1][x][y][i + 4][1] == 1 && calculate[1][x][y][i][1] == 1 && calculate[1][x][y][i + 4][2] == 1 && calculate[1][x][y][i][2] == 1) ){
					    	black[x][y][i] = 400;
					    }
					   //�ڴ�λ���º�Ϊ����
					    else if((calculate[1][x][y][i][1] == 2 && calculate[1][x][y][i][2] == -1 && calculate[1][x][y][i + 4][2] == 0)||
					    		(calculate[1][x][y][i][1] == 2 && calculate[1][x][y][i][2] == 0 && calculate[1][x][y][i + 4][2] == -1)||
					    		(calculate[1][x][y][i + 4][1] == 2 && calculate[1][x][y][i + 4][2] == -1 && calculate[1][x][y][i][2] == 0) ||
					    		(calculate[1][x][y][i + 4][1] == 2 && calculate[1][x][y][i + 4][2] == 0 && calculate[1][x][y][i][2] == -1) ||
					    		(calculate[1][x][y][i + 4][1] == 1 && calculate[1][x][y][i][1] == 1 && (calculate[1][x][y][i + 4][2] == -1 ^ calculate[1][x][y][i][2] == -1))){
					    	black[x][y][i] = 20;
					    }
					    //�ڴ�λ���º�Ϊ���
					    else if((calculate[1][x][y][i][1] == 1 && calculate[1][x][y][i][2] == 1 && calculate[1][x][y][i + 4][2] == 0)||
					    		(calculate[1][x][y][i + 4][1] == 1 && calculate[1][x][y][i + 4][2] == 1 && calculate[1][x][y][i][2] == 0) ){
					    	black[x][y][i] = 15;
					    }
					    //�ڴ�λ���º�Ϊ���
					    else if((calculate[1][x][y][i][1] == 1 && calculate[1][x][y][i + 4][1] == 0 && (calculate[1][x][y][i][2] == -1 ^ calculate[1][x][y][i + 4][2] == -1 ))||
					    		(calculate[1][x][y][i + 4][1] == 1 && calculate[1][x][y][i][1] == 0 && (calculate[1][x][y][i + 4][2] == -1 ^ calculate[1][x][y][i][2] == -1))){
					    	black[x][y][i] = 2;
					    }
					    
					    
					    
					    
					    //�ڴ�λ���º���������
					    if((calculate[2][x][y][i][1] == 4 && calculate[2][x][y][i + 4][1] == 0) || 
						    	(calculate[2][x][y][i + 4][1] == 4 && calculate[2][x][y][i][1] == 0)||
						    	(calculate[2][x][y][i][1] + calculate[2][x][y][i + 4][1] == 4)){
					    	white[x][y][i] = 8000;
				    	}
				    	//�ڴ�λ���º����ɻ���
					    else if((calculate[2][x][y][i][1] == 3 && calculate[2][x][y][i][2] == 3 && calculate[2][x][y][i + 4][2] == 0)||
						    	(calculate[2][x][y][i + 4][1] == 3 && calculate[2][x][y][i][2] == 0 && calculate[2][x][y][i + 4][2] == 3)||
						    	((calculate[2][x][y][i + 4][1] + calculate[2][x][y][i][1]) ==3 && (calculate[2][x][y][i + 4][2] + calculate[2][x][y][i][2]) ==3)){
					    	white[x][y][i] = 2000;
					    }
					    //�ڴ�λ���º����ɳ���
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
					    //�ڴ�λ���º�Ϊ����
					    else if((calculate[2][x][y][i][1] == 2 && calculate[2][x][y][i][2] == 2 && calculate[2][x][y][i + 4][2] == 0)||
					    		(calculate[2][x][y][i + 4][1] == 2 && calculate[2][x][y][i + 4][2] == 2 && calculate[2][x][y][i][2] == 0) ||
					    		(calculate[2][x][y][i + 4][1] == 1 && calculate[2][x][y][i][1] == 1 && calculate[2][x][y][i + 4][2] == 1 && calculate[2][x][y][i][2] == 1) ){
					    	white[x][y][i] = 400;
					    }
					   //�ڴ�λ���º�Ϊ����
					    else if((calculate[2][x][y][i][1] == 2 && calculate[2][x][y][i][2] == -1 && calculate[2][x][y][i + 4][2] == 0)||
					    		(calculate[2][x][y][i][1] == 2 && calculate[2][x][y][i][2] == 0 && calculate[2][x][y][i + 4][2] == -1)||
					    		(calculate[2][x][y][i + 4][1] == 2 && calculate[2][x][y][i + 4][2] == -1 && calculate[2][x][y][i][2] == 0) ||
					    		(calculate[2][x][y][i + 4][1] == 2 && calculate[2][x][y][i + 4][2] == 0 && calculate[2][x][y][i][2] == -1) ||
					    		(calculate[2][x][y][i + 4][1] == 1 && calculate[2][x][y][i][1] == 1 && (calculate[2][x][y][i + 4][2] == -1 ^ calculate[1][x][y][i][2] == -1))){
					    	white[x][y][i] = 20;
					    }
					    //�ڴ�λ���º�Ϊ���
					    else if((calculate[2][x][y][i][1] == 1 && calculate[2][x][y][i][2] == 1 && calculate[2][x][y][i + 4][2] == 0)||
					    		(calculate[2][x][y][i + 4][1] == 1 && calculate[2][x][y][i + 4][2] == 1 && calculate[2][x][y][i][2] == 0) ){
					    	white[x][y][i] = 15;
					    	
					    }
					    //�ڴ�λ���º�Ϊ���
					    else if((calculate[2][x][y][i][1] == 1 && calculate[2][x][y][i + 4][1] == 0 && (calculate[2][x][y][i][2] == -1 ^ calculate[2][x][y][i + 4][2] == -1 ))||
					    		(calculate[2][x][y][i + 4][1] == 1 && calculate[2][x][y][i][1] == 0 && (calculate[2][x][y][i + 4][2] == -1 ^ calculate[2][x][y][i][2] == -1))){
					    	white[x][y][i] = 2;
					    }
				    
				    
				}
				}
				
			}
		}
			
	}
    
	//����һ���������ӵ��ܷ�
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
