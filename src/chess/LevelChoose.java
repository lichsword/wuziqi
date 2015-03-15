package chess;

import java.awt.Point;

public class LevelChoose {
	public static Point getPoint(int level, int type, int[][] table){
		switch(level){
		case 1: return (new ElementaryLevel()).getMaxScorePoint(type, table);
		case 2: return (new OneStep2(table)).getMaxScorePoint(type);
		case 3: return (new OneStep(table)).getMaxScorePoint(type, table);
		default:return (new AIpart()).putOne(table, type);
		}
	}
    
}
