package chess;

import java.awt.Point;

public class OneStep2 extends AIpart3{
    
	protected static int[][] black2 = new int[x_Max][x_Max];
	protected static int[][] white2 = new int[x_Max][x_Max];
	private static int blackTotal;
	private static int whiteTotal;
	
	public OneStep2(int[][] table) {
		getEachDir(table);
		getScore(table);
		blackTotal = getTotalScoreEach(1);
		whiteTotal = getTotalScoreEach(2);
		getScore2(table);
	}
	
	private void clearScore(){
		for(int i = 0; i < x_Max; i++){
			for(int p = 0; p < x_Max; p++){
				black2[i][p] = 0;
				white2[i][p] = 0;
			}
		}
	}
	protected void getScore2(int[][] table){
		clearScore();
	    for(int x = 0; x < x_Max; x++){
	    	for(int y = 0; y < x_Max; y++){
	    		if(table[x][y] == 0){
	    			//先算黑的分数
	    			getEachDir(table);
	    			getScore(table);
	    			
	    			//在此位置下后生成连五
	    		    if(getOnePointScore(x, y, 1) >= 100000){
	    		    	black2[x][y] = 120000;
	    		    }
	    		    else{
	    		    	table[x][y] = 1;
	    		    	getEachDir(table);
	    		    	getScore(table);
	    		    	int blackIncrease = getTotalScoreEach(1) - blackTotal;
	    		    	//在此位置下后生成单冲四
	    		    	if(blackIncrease >= 100000 - 1000 && blackIncrease < 100000 + 40){
	    		    		black2[x][y] = 1000;
	    		    	}
	    		    	//生成活四，双冲四
	    		    	else if(blackIncrease >= 200000 -20000){
	    		    		black2[x][y] = 100000;
	    		    	}
	    		    	//在此位置下后生成冲四
	    		    	else if(blackIncrease >= 100000 + 20000 - 2000){
	    		    		black2[x][y] = 20000;
	    		    		
	    		    	}
	    		    	//在此位置下后生成双或三
	    		    	else if(blackIncrease >= 30000 - 600){
	    		    		black2[x][y] = 20000;
	    		    		
	    		    	}
	    		    	//在此位置下后生成活三冲三
	    		    	else if(blackIncrease >= 20000 - 360){
	    		    		black2[x][y] = 5000;
	    		    	}
	    		    	//在此位置下后生成单活三
	    		    	else if(blackIncrease >= 20000 - 400){
	    		    		black2[x][y] = 200;
	    		    	}
	    		    	//在此位置下后生成冲三
	    		    	else if(blackIncrease >= 1000 -40){
	    		    		black2[x][y] = 50;
	    		    	}
	    		    	//在此位置下后生成双活二
	    		    	else if(blackIncrease >= 800 - 96){
	    		    		black2[x][y] = 1100;
	    		    	}
	    		    	//在此位置下后生成活二
	    		    	else if(blackIncrease >= 400 - 12){
	    		    		black2[x][y] = 10;
	    		    	}
	    		    	//在此位置下后生成冲2
	    		    	else if(blackIncrease >= 40 - 1){
	    		    		black2[x][y] =2;
	    		    	}
	    		    	table[x][y] = 0;
	    		    	getEachDir(table);
	    		    	getScore(table);
	    		    }
	    		
	    			//在此位置下后生成连五
	    		    if(getOnePointScore(x, y, 2) >= 100000){
	    		    	white2[x][y] = 120000;
	    		    }
	    		    else{
	    		    	table[x][y] = 2;
	    		    	getEachDir(table);
	    		    	getScore(table);
	    		    	int whiteIncrease = getTotalScoreEach(2) - whiteTotal;
	    		    	//在此位置下后生成单冲四
	    		    	if(whiteIncrease >= 100000 - 1000 && whiteIncrease < 10000 + 40){
	    		    		white2[x][y] = 1000;
	    		    	}
	    		    	//生成活四，双冲四
	    		    	else if(whiteIncrease >= 200000 -20000){
	    		    		white2[x][y] = 100000;
	    		    	}
	    		    	//在此位置下后生成冲死活三
	    		    	else if(whiteIncrease >= 100000 + 20000 - 2000){
	    		    		white2[x][y] = 20000;
	    		    	}
	    		    	//在此位置下后生成双活三
	    		    	else if(whiteIncrease >= 30000 - 400){
	    		    		white2[x][y] = 20000;
	    		    	}
	    		    	//在此位置下后生成活三冲三
	    		    	else if(whiteIncrease >= 20000 - 360){
	    		    		white2[x][y] = 5000;
	    		    	}
	    		    	//在此位置下后生成单活三
	    		    	else if(whiteIncrease >= 20000 - 400){
	    		    		white2[x][y] = 200;
	    		    	}
	    		    	//在此位置下后生成冲三
	    		    	else if(whiteIncrease >= 1000 -40){
	    		    		white2[x][y] = 50;
	    		    	}
	    		    	//在此位置下后生成双活二
	    		    	else if(whiteIncrease >= 800 - 96){
	    		    		white2[x][y] = 1100;
	    		    	}
	    		    	//在此位置下后生成活二
	    		    	else if(whiteIncrease >= 400 - 12){
	    		    		white2[x][y] = 10;
	    		    	}
	    		    	//在此位置下后生成冲2
	    		    	else if(whiteIncrease >= 40 - 1){
	    		    		white2[x][y] =2;
	    		    	}
	    		        table[x][y] = 0;
	    		    }
	    		}
	    	}
	    }
	}
	
	public Point getMaxScorePoint(int type){
		int maxScore = 0;
		int blackMax = getMaxScoreOneType(1);
		int whiteMax = getMaxScoreOneType(2);
		Point point = new Point();
		
        if(blackMax == 120000 || whiteMax == 120000){
        	for(int x = 0; x < x_Max; x++){
				for(int y = 0; y < x_Max; y++){
					if(type == 1 && black2[x][y] == 120000){
						point.x = x;
						point.y = y;
						return point;
					}
					if(type == 2 && white2[x][y] == 120000){
						point.x = x;
						point.y = y;
						return point;
					}
					if(black2[x][y] == 120000 || white2[x][y] == 120000){
						point.x = x;
						point.y = y;
						return point;
					}
				}
        	}
        }
        if(type == 1 && blackMax == 100000){
        	for(int x = 0; x < x_Max; x++){
				for(int y = 0; y < x_Max; y++){
					if(black2[x][y] == 100000){
						point.x = x;
						point.y = y;
						return point;
				    }
			    }
           }
        }
        if(type == 2 && whiteMax == 100000){
        	for(int x = 0; x < x_Max; x++){
				for(int y = 0; y < x_Max; y++){
					if(white2[x][y] == 100000){
						point.x = x;
						point.y = y;
						return point;
				    }
			    }
           }
        }
        if( whiteMax == 100000 || blackMax == 100000){
        	for(int x = 0; x < x_Max; x++){
				for(int y = 0; y < x_Max; y++){
					if(white2[x][y] == 100000 || black2[x][y] == 100000){
						point.x = x;
						point.y = y;
						return point;
				    }
			    }
           }
        }
        if(type == 1 && blackMax == 20000){
        	for(int x = 0; x < x_Max; x++){
				for(int y = 0; y < x_Max; y++){
					if(black2[x][y] == 20000){
						point.x = x;
						point.y = y;
						return point;
				    }
			    }
           }
        }
        if(type == 2 && whiteMax == 20000){
        	for(int x = 0; x < x_Max; x++){
				for(int y = 0; y < x_Max; y++){
					if(white2[x][y] == 20000){
						point.x = x;
						point.y = y;
						return point;
				    }
			    }
           }
        }
		for(int x = 0; x < x_Max; x++){
			for(int y = 0; y < x_Max; y++){
					
				if((black2[x][y] + white2[x][y])>= maxScore){
					maxScore = black2[x][y] + white2[x][y];
					point.x = x;
					point.y = y;
				}
			}
		}
		return point;
	}
	
	protected int getMaxScoreOneType(int type){
		int maxScore = 0;
		if(type == 1){
		    for(int i = 0; i < x_Max; i++){
			    for(int k = 0; k < x_Max; k++){
			    	if(maxScore <= black2[i][k]){
			    		maxScore = black2[i][k];
			    	}
			    }
		    }
		}
		else{
			 for(int i = 0; i < x_Max; i++){
				    for(int k = 0; k < x_Max; k++){
				    	if(maxScore <= white2[i][k]){
				    		maxScore = white2[i][k];
				    	}
				    }
			    }
		}
		return maxScore;
	}
	
	protected int getTotalScoreEach2(int type){
		int score = 0;
		if(type == 1){
			for(int x = 0; x < x_Max; x++){
				for(int y = 0; y < x_Max; y++){
					score += black2[x][y];
				}
			}
		}
		else{
			for(int x = 0; x < x_Max; x++){
				for(int y = 0; y < x_Max; y++){
					score += white2[x][y];
				}
			}
		}
		return score;
	}

}
