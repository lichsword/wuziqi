package chess;

import java.util.*;
import vision.*;
import java.awt.*;

public class AIpart {
	// 其中第一个下标为1时表示黑棋，为2时表示白棋，第二和第三
	// 个下标表示(x,y)，第四个下标表示8个方向，最后一个下标为1时表示棋子数，为2时表示距空格的格数

	public static int x_Max = ChessBoard.x_Max;
	public static int y_Max = ChessBoard.y_Max;
	protected static int[][][][][] calculate = new int[3][x_Max][y_Max][8][3];

	protected static final int DIR_UP = 0;
	protected static final int DIR_UPRIGHT = 1;
	protected static final int DIR_RIGHT = 2;
	protected static final int DIR_RIGHTDOWN = 3;
	protected static final int DIR_DOWN = 4;
	protected static final int DIR_DOWNLEFT = 5;
	protected static final int DIR_LEFT = 6;
	protected static final int DIR_LEFTUP = 7;

	private static final int weight = 10;
	private static int deep = 6;

	// black[][][] 中第一个下表表示此位置的横坐标，第二个表示纵坐标，第三个
	// 表示各个方向的得分，其中 0：竖直 1:斜上右 2：水平3：斜上左
	// white同上
	protected static int[][][] black = new int[x_Max][y_Max][4];
	protected static int[][][] white = new int[x_Max][y_Max][4];

	public AIpart() {
	}

	// 将calculate中数据清除，以便下次使用
	private void calculateClear() {
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < x_Max; k++) {
				for (int l = 0; l < y_Max; l++) {
					for (int m = 0; m < 8; m++) {
						for (int n = 0; n < 3; n++) {
							calculate[i][k][l][m][n] = 0;
						}
					}
				}
			}
		}
	}

	private boolean randomTest(int kt) {
		Random rm = new Random();
		return rm.nextInt() % kt == 0;
	}

	public Point putOne(int[][] table, int type) {
		getEachDir(table);
		int x, y, mx = -100000000;
		x = y = -1;
		// 搜索最优下棋点
		int[][] bests = getBests(table, type);
		for (int k = 0; k < bests.length; k++) {
			int i = bests[k][0];
			int j = bests[k][1];
			// 有成5,则直接下子,并退出循环..没有,则思考对方情况
			if (getIsOver(i, j, type)) {
				x = i;
				y = j;
				mx = 200000000;
				break;
			}
			if (getIsOver(i, j, 3 - type)) {
				x = i;
				y = j;
				if (mx != 100000000) {
					mx = 90000000;
				}
			}
			
			// 预设己方下棋,并更新边界值
			table[i][j] = type;
			getEachDir(table);
			// 预测未来
			int t = findMin(-100000000, 100000000, deep, table, 3 - type);
			
			// 还原预设下棋位置以及边界值
			table[i][j] = 0;
			getEachDir(table);
			// 差距小于1000，50%概率随机选取
			if (t - mx > 1000 || (Math.abs(t - mx) < 1000 && randomTest(weight))) {
				x = i;
				y = j;
				mx = t;
			}

		}
	
		if(x == -1 && y == -1){
			return (new Point(bests[0][0], bests[0][1]));
		}
		return (new Point(x, y));
	}

	protected int findMax(int alpha, int beta, int step, int[][] table, int type) {
		int max = alpha;
		if (step == 0) {
			return evaluate(type);
		}
		int[][] rt = getBests(table, type);
		for (int i = 0; i < rt.length; i++) {
			int x = rt[i][0];
			int y = rt[i][1];
			if (getIsOver(x, y, type)) // 电脑可取胜
				return 100 * (1000000 + step * 1000);
			table[x][y] = type;
			getEachDir(table);
			//
			int t = findMin(max, beta, step - 1, table, 3 - type);
			table[x][y] = 0;
			getEachDir(table);
			if (t > max)
				max = t;
			// beta 剪枝
			if (t >= beta)
				return max;
		}
		return max;
	}

	protected int findMin(int alpha, int beta, int step, int[][] table, int type) {
		int min = beta;
		if (step == 0) {
			return evaluate(3 - type);
		}
		int[][] rt = getBests(table, type);
		for (int i = 0; i < rt.length; i++) {
			int x = rt[i][0];
			int y = rt[i][1];
			if (getIsOver(x, y, type)) // 玩家成5
				return -100 * (1000000 + step * 1000);
			// 预存当前边界值
			table[x][y] = type;
			getEachDir(table);
			int t = findMax(alpha, min, step - 1, table, 3 - type);
			table[x][y] = 0;
			getEachDir(table);
			if (t < min)
				min = t;
			// alpha 剪枝
			if (t <= alpha) {
				return min;
			}
		}
		return min;
	}

	// 将black[][][],white[][][]中数据清除
	private void clearBlackWhite() {
		for (int i = 0; i < x_Max; i++) {
			for (int k = 0; k < y_Max; k++) {
				for (int l = 0; l < 4; l++) {
					black[i][k][l] = 0;
					white[i][k][l] = 0;
				}
			}

		}
	}

	// 计算calculate
	public void getEachDir(int[][] table) {
		calculateClear();
		clearBlackWhite();
		for (int x = 0; x < x_Max; x++) {
			for (int y = 0; y < y_Max; y++) {
				if (table[x][y] == 0) {

					// 计算上方
					for (int i = 1;; i++) {
						if (y - i >= 0) {
							if (table[x][y - 1] == 0) {
								break;
							}
							if (table[x][y - i] == table[x][y - 1]) {
								calculate[table[x][y - 1]][x][y][DIR_UP][1]++;
							} else {
								if (table[x][y - i] == 0) {
									calculate[table[x][y - 1]][x][y][DIR_UP][2] = i - 1;
									break;
								} else {
									calculate[table[x][y - 1]][x][y][DIR_UP][2] = -1;
									break;
								}
							}
						} else {
							if (y == 0) {
								calculate[1][x][y][DIR_UP][2] = -1;
								calculate[2][x][y][DIR_UP][2] = -1;
								break;
							} else
								calculate[table[x][y - 1]][x][y][DIR_UP][2] = -1;
							break;
						}
					}

					// 计算右上方
					for (int i = 1;; i++) {
						if (y - i >= 0 && x + i <= x_Max - 1) {
							if (table[x + 1][y - 1] == 0) {
								break;
							}
							if (table[x + i][y - i] == table[x + 1][y - 1]) {
								calculate[table[x + 1][y - 1]][x][y][DIR_UPRIGHT][1]++;
							} else {
								if (table[x + i][y - i] == 0) {
									calculate[table[x + 1][y - 1]][x][y][DIR_UPRIGHT][2] = i - 1;
									break;
								} else {
									calculate[table[x + 1][y - 1]][x][y][DIR_UPRIGHT][2] = -1;
									break;
								}
							}
						} else {
							if (x == x_Max - 1 || y == 0) {
								calculate[1][x][y][DIR_UPRIGHT][2] = -1;
								calculate[2][x][y][DIR_UPRIGHT][2] = -1;
								break;
							}
							calculate[table[x + 1][y - 1]][x][y][DIR_UPRIGHT][2] = -1;
							break;
						}
					}

					// 计算右方
					for (int i = 1;; i++) {
						if (x + i <= x_Max - 1) {
							if (table[x + 1][y] == 0) {
								break;
							}
							if (table[x + i][y] == table[x + 1][y]) {
								calculate[table[x + 1][y]][x][y][DIR_RIGHT][1]++;
							} else {
								if (table[x + i][y] == 0) {
									calculate[table[x + 1][y]][x][y][DIR_RIGHT][2] = i - 1;
									break;
								} else {
									calculate[table[x + 1][y]][x][y][DIR_RIGHT][2] = -1;
									break;
								}
							}
						} else {
							if (x == x_Max - 1) {
								calculate[1][x][y][DIR_RIGHT][2] = -1;
								calculate[2][x][y][DIR_RIGHT][2] = -1;
								break;
							}
							calculate[table[x + 1][y]][x][y][DIR_RIGHT][2] = -1;
							break;
						}
					}

					// 计算右下方
					for (int i = 1;; i++) {
						if (x + i <= x_Max - 1 && y + i <= y_Max - 1) {
							if (table[x + 1][y + 1] == 0) {
								break;
							}
							if (table[x + i][y + i] == table[x + 1][y + 1]) {
								calculate[table[x + 1][y + 1]][x][y][DIR_RIGHTDOWN][1]++;
							} else {
								if (table[x + i][y + i] == 0) {
									calculate[table[x + 1][y + 1]][x][y][DIR_RIGHTDOWN][2] = i - 1;
									break;
								} else {
									calculate[table[x + 1][y + 1]][x][y][DIR_RIGHTDOWN][2] = -1;
									break;
								}
							}
						} else {
							if (x == x_Max - 1 || y == y_Max - 1) {
								calculate[1][x][y][DIR_RIGHTDOWN][2] = -1;
								calculate[1][x][y][DIR_RIGHTDOWN][2] = -1;
								break;
							}
							calculate[table[x + 1][y + 1]][x][y][DIR_RIGHTDOWN][2] = -1;
							break;
						}
					}

					// 计算下方
					for (int i = 1;; i++) {
						if (y + i <= y_Max - 1) {
							if (table[x][y + 1] == 0) {
								break;
							}
							if (table[x][y + i] == table[x][y + 1]) {
								calculate[table[x][y + 1]][x][y][DIR_DOWN][1]++;
							} else {
								if (table[x][y + i] == 0) {
									calculate[table[x][y + 1]][x][y][DIR_DOWN][2] = i - 1;
									break;
								} else {
									calculate[table[x][y + 1]][x][y][DIR_DOWN][2] = -1;
									break;
								}
							}
						} else {
							if (y == y_Max - 1) {
								calculate[1][x][y][DIR_DOWN][2] = -1;
								calculate[1][x][y][DIR_DOWN][2] = -1;
								break;
							}
							calculate[table[x][y + 1]][x][y][DIR_DOWN][2] = -1;
							break;
						}
					}

					// 计算左下方
					for (int i = 1;; i++) {
						if (x - i >= 0 && y + i <= y_Max - 1) {
							if (table[x - 1][y + 1] == 0) {
								break;
							}
							if (table[x - i][y + i] == table[x - 1][y + 1]) {
								calculate[table[x - 1][y + 1]][x][y][DIR_DOWNLEFT][1]++;
							} else {
								if (table[x - i][y + i] == 0) {
									calculate[table[x - 1][y + 1]][x][y][DIR_DOWNLEFT][2] = i - 1;
									break;
								} else {
									calculate[table[x - 1][y + 1]][x][y][DIR_DOWNLEFT][2] = -1;
									break;
								}
							}
						} else {
							if (x == 0 || y == y_Max - 1) {
								calculate[1][x][y][DIR_DOWNLEFT][2] = -1;
								calculate[1][x][y][DIR_DOWNLEFT][2] = -1;
								break;
							}
							calculate[table[x - 1][y + 1]][x][y][DIR_DOWNLEFT][2] = -1;
							break;
						}
					}

					// 计算左方
					for (int i = 1;; i++) {
						if (x - i >= 0) {
							if (table[x - 1][y] == 0) {
								break;
							}
							if (table[x - i][y] == table[x - 1][y]) {
								calculate[table[x - 1][y]][x][y][DIR_LEFT][1]++;
							} else {
								if (table[x - i][y] == 0) {
									calculate[table[x - 1][y]][x][y][DIR_LEFT][2] = i - 1;
									break;
								} else {
									calculate[table[x - 1][y]][x][y][DIR_LEFT][2] = -1;
									break;
								}
							}
						} else {
							if (x == 0) {
								calculate[1][x][y][DIR_LEFT][2] = -1;
								calculate[1][x][y][DIR_LEFT][2] = -1;
								break;
							}
							calculate[table[x - 1][y]][x][y][DIR_LEFT][2] = -1;
							break;
						}
					}

					// 计算左上方
					for (int i = 1;; i++) {
						if (x - i >= 0 && y - i >= 0) {
							if (table[x - 1][y - 1] == 0) {
								break;
							}
							if (table[x - i][y - i] == table[x - 1][y - 1]) {
								calculate[table[x - 1][y - 1]][x][y][DIR_LEFTUP][1]++;
							} else {
								if (table[x - i][y - i] == 0) {
									calculate[table[x - 1][y - 1]][x][y][DIR_LEFTUP][2] = i - 1;
									break;
								} else {
									calculate[table[x - 1][y - 1]][x][y][DIR_LEFTUP][2] = -1;
									break;
								}
							}
						} else {
							if (x == 0 || y == 0) {
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
		getScore(table);
	}

	public void getScore(int[][] table) {
		for (int x = 0; x < x_Max; x++) {
			for (int y = 0; y < y_Max; y++) {
				if (table[x][y] == 0) {
					for (int i = 0; i < 4; i++) {
						// 在此位置下后生成连五
						if ((calculate[1][x][y][i][1] == 4 && calculate[1][x][y][i + 4][1] == 0)
								|| (calculate[1][x][y][i + 4][1] == 4 && calculate[1][x][y][i][1] == 0)
								|| (calculate[1][x][y][i][1]
										+ calculate[1][x][y][i + 4][1] == 4)) {
							black[x][y][i] = 1;
						}
						// 在此位置下后生成活四
						else if ((calculate[1][x][y][i][1] == 3
								&& calculate[1][x][y][i][2] == 3 && calculate[1][x][y][i + 4][2] == 0)
								|| (calculate[1][x][y][i + 4][1] == 3
										&& calculate[1][x][y][i][2] == 0 && calculate[1][x][y][i + 4][2] == 3)
								|| ((calculate[1][x][y][i + 4][1] + calculate[1][x][y][i][1]) == 3 && (calculate[1][x][y][i + 4][2] + calculate[1][x][y][i][2]) == 3)) {
							black[x][y][i] = 2;
						}
						// 在此位置下后生成冲四
						else if ((calculate[1][x][y][i][1] == 3
								&& calculate[1][x][y][i][2] == 3
								&& calculate[1][x][y][i + 4][1] == 0 && calculate[1][x][y][i + 4][2] == -1)
								|| (calculate[1][x][y][i + 4][1] == 3
										&& calculate[1][x][y][i + 4][2] == 3
										&& calculate[1][x][y][i][1] == 0 && calculate[1][x][y][i][2] == -1)
								|| (calculate[1][x][y][i][1] == 3
										&& calculate[1][x][y][i][2] == -1 && calculate[1][x][y][i + 4][2] == 0)
								|| (calculate[1][x][y][i + 4][1] == 3
										&& calculate[1][x][y][i + 4][2] == -1 && calculate[1][x][y][i][2] == 0)
								|| ((calculate[1][x][y][i][1] == 2 && calculate[1][x][y][i + 4][1] == 1) && (calculate[1][x][y][i][2] == 2 ^ calculate[1][x][y][i + 4][2] == 1))
								|| ((calculate[1][x][y][i][1] == 1 && calculate[1][x][y][i + 4][1] == 2) && (calculate[1][x][y][i][2] == 1 ^ calculate[1][x][y][i + 4][2] == 2))) {
							black[x][y][i] = 3;
						}
						// 在此位置下后为活三
						else if ((calculate[1][x][y][i][1] == 2
								&& calculate[1][x][y][i][2] == 2 && calculate[1][x][y][i + 4][2] == 0)
								|| (calculate[1][x][y][i + 4][1] == 2
										&& calculate[1][x][y][i + 4][2] == 2 && calculate[1][x][y][i][2] == 0)
								|| (calculate[1][x][y][i + 4][1] == 1
										&& calculate[1][x][y][i][1] == 1
										&& calculate[1][x][y][i + 4][2] == 1 && calculate[1][x][y][i][2] == 1)) {
							black[x][y][i] = 4;
						}
						// 在此位置下后为冲三
						else if ((calculate[1][x][y][i][1] == 2
								&& calculate[1][x][y][i][2] == -1 && calculate[1][x][y][i + 4][2] == 0)
								|| (calculate[1][x][y][i][1] == 2
										&& calculate[1][x][y][i][2] == 0 && calculate[1][x][y][i + 4][2] == -1)
								|| (calculate[1][x][y][i + 4][1] == 2
										&& calculate[1][x][y][i + 4][2] == -1 && calculate[1][x][y][i][2] == 0)
								|| (calculate[1][x][y][i + 4][1] == 2
										&& calculate[1][x][y][i + 4][2] == 0 && calculate[1][x][y][i][2] == -1)
								|| (calculate[1][x][y][i + 4][1] == 1
										&& calculate[1][x][y][i][1] == 1 && (calculate[1][x][y][i + 4][2] == -1 ^ calculate[1][x][y][i][2] == -1))) {
							black[x][y][i] = 5;
						}
						// 在此位置下后为活二
						else if ((calculate[1][x][y][i][1] == 1
								&& calculate[1][x][y][i][2] == 1 && calculate[1][x][y][i + 4][2] == 0)
								|| (calculate[1][x][y][i + 4][1] == 1
										&& calculate[1][x][y][i + 4][2] == 1 && calculate[1][x][y][i][2] == 0)) {
							black[x][y][i] = 6;
						}
						// 在此位置下后为冲二
						else if ((calculate[1][x][y][i][1] == 1
								&& calculate[1][x][y][i + 4][1] == 0 && (calculate[1][x][y][i][2] == -1 ^ calculate[1][x][y][i + 4][2] == -1))
								|| (calculate[1][x][y][i + 4][1] == 1
										&& calculate[1][x][y][i][1] == 0 && (calculate[1][x][y][i + 4][2] == -1 ^ calculate[1][x][y][i][2] == -1))) {
							black[x][y][i] = 7;
						}

						// 在此位置下后生成连五
						if ((calculate[2][x][y][i][1] == 4 && calculate[2][x][y][i + 4][1] == 0)
								|| (calculate[2][x][y][i + 4][1] == 4 && calculate[2][x][y][i][1] == 0)
								|| (calculate[2][x][y][i][1]
										+ calculate[2][x][y][i + 4][1] == 4)) {
							white[x][y][i] = 1;
						}
						// 在此位置下后生成活四
						else if ((calculate[2][x][y][i][1] == 3
								&& calculate[2][x][y][i][2] == 3 && calculate[2][x][y][i + 4][2] == 0)
								|| (calculate[2][x][y][i + 4][1] == 3
										&& calculate[2][x][y][i][2] == 0 && calculate[2][x][y][i + 4][2] == 3)
								|| ((calculate[2][x][y][i + 4][1] + calculate[2][x][y][i][1]) == 3 && (calculate[2][x][y][i + 4][2] + calculate[2][x][y][i][2]) == 3)) {
							white[x][y][i] = 2;
						}
						// 在此位置下后生成冲四
						else if ((calculate[2][x][y][i][1] == 3
								&& calculate[2][x][y][i][2] == 3
								&& calculate[2][x][y][i + 4][1] == 0 && calculate[2][x][y][i + 4][2] == -1)
								|| (calculate[2][x][y][i + 4][1] == 3
										&& calculate[2][x][y][i + 4][2] == 3
										&& calculate[2][x][y][i][1] == 0 && calculate[2][x][y][i][2] == -1)
								|| (calculate[2][x][y][i][1] == 3
										&& calculate[2][x][y][i][2] == -1 && calculate[1][x][y][i + 4][1] == 0)
								|| (calculate[2][x][y][i + 4][1] == 3
										&& calculate[2][x][y][i + 4][2] == -1 && calculate[1][x][y][i][1] == 0)
								|| ((calculate[2][x][y][i][1] == 2 && calculate[2][x][y][i + 4][1] == 1) && (calculate[2][x][y][i][2] == 2 ^ calculate[2][x][y][i + 4][2] == 1))
								|| ((calculate[2][x][y][i][1] == 1 && calculate[2][x][y][i + 4][1] == 2) && (calculate[2][x][y][i][2] == 1 ^ calculate[2][x][y][i + 4][2] == 2))) {
							white[x][y][i] = 3;
						}
						// 在此位置下后为活三
						else if ((calculate[2][x][y][i][1] == 2
								&& calculate[2][x][y][i][2] == 2 && calculate[2][x][y][i + 4][2] == 0)
								|| (calculate[2][x][y][i + 4][1] == 2
										&& calculate[2][x][y][i + 4][2] == 2 && calculate[2][x][y][i][2] == 0)
								|| (calculate[2][x][y][i + 4][1] == 1
										&& calculate[2][x][y][i][1] == 1
										&& calculate[2][x][y][i + 4][2] == 1 && calculate[2][x][y][i][2] == 1)) {
							white[x][y][i] = 4;
						}
						// 在此位置下后为冲三
						else if ((calculate[2][x][y][i][1] == 2
								&& calculate[2][x][y][i][2] == -1 && calculate[2][x][y][i + 4][2] == 0)
								|| (calculate[2][x][y][i][1] == 2
										&& calculate[2][x][y][i][2] == 0 && calculate[2][x][y][i + 4][2] == -1)
								|| (calculate[2][x][y][i + 4][1] == 2
										&& calculate[2][x][y][i + 4][2] == -1 && calculate[2][x][y][i][2] == 0)
								|| (calculate[2][x][y][i + 4][1] == 2
										&& calculate[2][x][y][i + 4][2] == 0 && calculate[2][x][y][i][2] == -1)
								|| (calculate[2][x][y][i + 4][1] == 1
										&& calculate[2][x][y][i][1] == 1 && (calculate[2][x][y][i + 4][2] == -1 ^ calculate[1][x][y][i][2] == -1))) {
							white[x][y][i] = 5;
						}
						// 在此位置下后为活二
						else if ((calculate[2][x][y][i][1] == 1
								&& calculate[2][x][y][i][2] == 1 && calculate[2][x][y][i + 4][2] == 0)
								|| (calculate[2][x][y][i + 4][1] == 1
										&& calculate[2][x][y][i + 4][2] == 1 && calculate[2][x][y][i][2] == 0)) {
							white[x][y][i] = 6;

						}
						// 在此位置下后为冲二
						else if ((calculate[2][x][y][i][1] == 1
								&& calculate[2][x][y][i + 4][1] == 0 && (calculate[2][x][y][i][2] == -1 ^ calculate[2][x][y][i + 4][2] == -1))
								|| (calculate[2][x][y][i + 4][1] == 1
										&& calculate[2][x][y][i][1] == 0 && (calculate[2][x][y][i + 4][2] == -1 ^ calculate[2][x][y][i][2] == -1))) {
							white[x][y][i] = 7;
						}

					}
				}

			}
		}
	}

	//
	public void Ceshi() {
		for (int x = 0; x < x_Max; x++) {
			for (int y = 0; y < y_Max; y++) {
				System.out.println(x + "    " + y + " black" + " "
						+ black[x][y][0] + " " + black[x][y][1] + " "
						+ black[x][y][2] + " " + black[x][y][3] + " ");
				System.out.println(x + "    " + y + " white" + " "
						+ white[x][y][0] + " " + white[x][y][1] + " "
						+ white[x][y][2] + " " + white[x][y][3] + " ");

			}
		}
	}

	public int evaluate(int type) {
		int rt = 0;
		if (type == 1) {
			for (int x = 0; x < x_Max; x++) {
				for (int y = 0; y < y_Max; y++) {
					for (int i = 0; i < 4; i++) {
						switch (black[x][y][i]) {
						case 1:
							rt += 1000000;
							break;
						case 2:
							rt += 200000;
							break;
						case 3:
							rt += 40000;
							break;
						case 4:
							rt += 39000;
							break;
						case 5:
							rt += 800;
							break;
						case 6:
							rt += 900;
							break;
						case 7:
							rt += 200;
							break;
						}
						switch (white[x][y][i]) {
						case 1:
							rt -= 1000000;
							break;
						case 2:
							rt -= 200000;
							break;
						case 3:
							rt -= 40000;
							break;
						case 4:
							rt -= 39000;
							break;
						case 5:
							rt -= 800;
							break;
						case 6:
							rt -= 900;
							break;
						case 7:
							rt -= 200;
							break;
						}
					}
				}
			}
		} else {
			for (int x = 0; x < x_Max; x++) {
				for (int y = 0; y < y_Max; y++) {
					for (int i = 0; i < 4; i++) {
						switch (black[x][y][i]) {
						case 1:
							rt -= 1000000;
							break;
						case 2:
							rt -= 200000;
							break;
						case 3:
							rt -= 40000;
							break;
						case 4:
							rt -= 39000;
							break;
						case 5:
							rt -= 800;
							break;
						case 6:
							rt -= 900;
							break;
						case 7:
							rt -= 200;
							break;
						}
						switch (white[x][y][i]) {
						case 1:
							rt += 1000000;
							break;
						case 2:
							rt += 200000;
							break;
						case 3:
							rt += 40000;
							break;
						case 4:
							rt += 39000;
							break;
						case 5:
							rt += 800;
							break;
						case 6:
							rt += 900;
							break;
						case 7:
							rt += 200;
							break;
						}
					}
				}
			}
		}
		return rt;
	}

	public int[][] getBests(int[][] table, int type) {
		int[][] rt = new int[x_Max * y_Max][3];
		int n = 0;
		for (int x = 0; x < x_Max; x++) {
			for (int y = 0; y < y_Max; y++) {
				if (table[x][y] == 0) {
					rt[n][0] = x;
					rt[n][1] = y;
					if(type == 1){
					for (int i = 0; i < 4; i++) {
						switch (black[x][y][i]) {
						case 1:
							rt[n][2] += 10000000;
							break;// 连五
						case 2:
							rt[n][2] += 200000;
							break;// 活四
						case 3:
							rt[n][2] += 40000;
							break;// 冲四
						case 4:
							rt[n][2] += 39000;
							break;// 活三
						case 5:
							rt[n][2] += 800;
							break;// 冲三
						case 6:
							rt[n][2] += 900;
							break;// 火二
						case 7:
							rt[n][2] += 200;
							break;// 冲二
						}
						switch (white[x][y][i]) {
						case 1:
							rt[n][2] += 9000000;
							break;
						case 2:
							rt[n][2] += 190000;
							break;
						case 3:
							rt[n][2] += 39000;
							break;
						case 4:
							rt[n][2] += 38000;
							break;
						case 5:
							rt[n][2] += 700;
							break;
						case 6:
							rt[n][2] += 800;
							break;
						case 7:
							rt[n][2] += 100;
							break;
						}
					}
					}
					else if(type == 2){
						for (int i = 0; i < 4; i++) {
							switch (white[x][y][i]) {
							case 1:
								rt[n][2] += 10000000;
								break;// 连五
							case 2:
								rt[n][2] += 200000;
								break;// 活四
							case 3:
								rt[n][2] += 40000;
								break;// 冲四
							case 4:
								rt[n][2] += 39000;
								break;// 活三
							case 5:
								rt[n][2] += 800;
								break;// 冲三
							case 6:
								rt[n][2] += 900;
								break;// 火二
							case 7:
								rt[n][2] += 200;
								break;// 冲二
							}
							switch (black[x][y][i]) {
							case 1:
								rt[n][2] += 9000000;
								break;
							case 2:
								rt[n][2] += 190000;
								break;
							case 3:
								rt[n][2] += 39000;
								break;
							case 4:
								rt[n][2] += 38000;
								break;
							case 5:
								rt[n][2] += 700;
								break;
							case 6:
								rt[n][2] += 800;
								break;
							case 7:
								rt[n][2] += 100;
								break;
							}
						}
					
					}
					n++;
				}
			}
		}

		Arrays.sort(rt, new ArrComparator());
		int size = weight > n ? n : weight;
		int[][] bests = new int[size][3];
		System.arraycopy(rt, 0, bests, 0, size);
		
		return bests;
	}

	public boolean getIsOver(int x, int y, int type) {
		if (type == 1
				&& (black[x][y][0] == 1 || black[x][y][1] == 1
						|| black[x][y][2] == 1 || black[x][y][3] == 1)) {
			return true;
		} else if (type == 2
				&& (white[x][y][0] == 1 || white[x][y][1] == 1
						|| white[x][y][2] == 1 || white[x][y][3] == 1)) {
			return true;
		} else {
			return false;
		}
	}

}