package chess;

import java.awt.*;

public class ElementaryLevel extends AIpart2{
	public ElementaryLevel(){
	}
	public Point getMaxScorePoint(int type,int[][] table){
		getEachDir(table);
		getScore(table);
		Point point = new Point();
		int maxScore = 0;
		int whiteScoreMax = getMaxOneType(2, table);
		int blackScoreMax = getMaxOneType(1, table);
		for(int x = 0; x < x_Max; x++){
			for(int y = 0; y < y_Max; y++){
				if(table[x][y] == 0){
					int blackScore = black[x][y][0] + black[x][y][1] + black[x][y][2] + black[x][y][3];
					int whiteScore = white[x][y][0] +white[x][y][1] + white[x][y][2] + white[x][y][3];
					if(type == 1 && blackScore >= 8000){
						point.x = x; point.y = y;
						return point;
					}
					else if(type == 2 && whiteScore >= 8000){
						point.x = x; point.y = y;
						return point;
					}
					if(type == 1 && blackScore >= 2000 && whiteScoreMax < 8000){
						point.x = x; point.y = y;
						return point;
					}
					else if(type == 2 && whiteScore >= 2000 && blackScoreMax < 8000){
						point.x = x; point.y = y;
						return point;
					}
					
					if(maxScore < blackScore + whiteScore){
						point.x = x; point.y = y;
						maxScore = blackScore + whiteScore;
					}
				}
			}
		}
		return point;
	}

}
