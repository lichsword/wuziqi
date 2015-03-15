package chess;

import java.awt.*;
import java.util.Arrays;

public class OneStep extends AIpart2{
	private boolean skip = false;
    private int whiteTotal = 0;
    private int blackTotal =0;
	public OneStep(int[][] table) {
		getEachDir(table);
		getScore(table);
		whiteTotal = this.getTotalScoreEach(2);
		blackTotal = this.getTotalScoreEach(1);
	}	
	public Point getMaxScorePoint(int type,int[][] table){
		Point point = new Point();
		int maxScore = 0;
		int[][] score = new  int[x_Max * y_Max][3];
		int n = 0;
		for(int x = 0; x < x_Max; x++){
			for(int y = 0; y < y_Max; y++){
				if(table[x][y] == 0){
					getEachDir(table);
					getScore(table);
					int whiteScoreMax = getMaxOneType(2, table);
					int blackScoreMax = getMaxOneType(1, table);
					int blackScore = black[x][y][0] + black[x][y][1] + black[x][y][2] + black[x][y][3];
					int whiteScore = white[x][y][0] + white[x][y][1] + white[x][y][2] + white[x][y][3];
					if(whiteScoreMax >= 8000 || blackScoreMax >= 8000){
					    if(type == 1 && blackScore >= 8000){
						    score[n][0] = x;
						    score[n][1] = y;
						    score[n][2] += 100000;
						    skip = true;
					    }
					    else if(type ==1 && whiteScore >= 8000 && blackScoreMax < 8000){
					    	score[n][0] = x;
						    score[n][1] = y;
						    score[n][2] += 50000;
						    skip = true;
					    }
					    else if(type == 2 && whiteScore >= 8000){
					    	score[n][0] = x;
						    score[n][1] = y;
						    score[n][2] += 100000;
						    skip = true;
					    }
					    else if(type == 2 && blackScore >= 8000 && whiteScoreMax < 8000){
					    	score[n][0] = x;
						    score[n][1] = y;
						    score[n][2] += 50000;
						    skip = true;
					    }
					}
					if(type == 1 && blackScore >= 2000 && whiteScoreMax < 8000){
						 score[n][0] = x;
						 score[n][1] = y;
						 score[n][2] += 10000;
						 skip = true;
					}
					else if(type == 2 && whiteScore >= 2000 && blackScoreMax < 8000){
						 score[n][0] = x;
						 score[n][1] = y;
						 score[n][2] += 10000;
						 skip = true;
					}
					if(skip){
						score[n][2] += (getOnePointScore(x, y, 1) + getOnePointScore(x, y, 2));
						n++;
						if(x == x_Max -1 && y == y_Max -1){
							Arrays.sort(score, new ArrComparator());
							point.x = score[0][0];
							point.y = score[0][1];
							return point;
						}
					}
					table[x][y] = 1;
					getEachDir(table);
					getScore(table);
					int blackScoreTotal = getTotalScoreEach(1) - blackTotal;
					if(maxScore <= blackScoreTotal && (blackScoreTotal < 7600 || blackScoreTotal >= 8000)){
						 maxScore = blackScoreTotal;
						 point.x = x; point.y = y;
					}
					table[x][y] = 2;
					getEachDir(table);
					getScore(table);
		            int whiteScoreTotal = getTotalScoreEach(2) - whiteTotal;
					if(maxScore <= whiteScoreTotal && (whiteScoreTotal < 7600 || whiteScoreTotal >= 8000)){
						maxScore = whiteScoreTotal;
						point.x = x; point.y = y;
					}
					table[x][y] = 0;
				}
			}
		}
		return point;
	}
}
