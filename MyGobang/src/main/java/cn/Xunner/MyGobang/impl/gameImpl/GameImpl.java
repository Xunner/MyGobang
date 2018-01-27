package cn.Xunner.MyGobang.impl.gameImpl;

import java.util.HashMap;

import cn.Xunner.MyGobang.service.GameService;
import cn.Xunner.MyGobang.util.GameState;
import cn.Xunner.MyGobang.util.Player;

/**
 * Created on 2018/1/26
 * 
 * @author 巽
 *
 */
public class GameImpl implements GameService {
	/**
	 * 棋盘格子数
	 */
	private final static int NUM = 15;
	/**
	 * 棋盘方向维度（上下、左右、左斜、右斜共四个）
	 */
	private final static int DIMENSION = 4;
	/**
	 * 棋盘
	 */
	private Player[][] chessboard;
	/**
	 * 当前可以落子的一方
	 */
	private Player player;
	/**
	 * 当前对局状态
	 */
	private GameState gameState;
	/**
	 * 各方技能是否冷却完毕
	 */
	private HashMap<Player, Boolean> isSkillReady = new HashMap<>();
	/**
	 * 判断对局状态的检查者
	 */
	private Checker checker = new Checker();

	public GameImpl() {
		this.init();
	}

	@Override
	public void init() {
		chessboard = new Player[NUM][NUM];
		player = Player.BLACK;
		gameState = GameState.CONTINUE;
		isSkillReady.put(Player.BLACK, true);
		isSkillReady.put(Player.WHITE, true);
	}

	@Override
	public boolean normalStep(int x, int y, Player player) {
		if (this.player == player && chessboard[x][y] == null) {
			chessboard[x][y] = player;
			isSkillReady.put(player, true);
			this.changeTurn();
			if (checker.isWin(x, y, player)) {	// 检查是否胜利
				gameState = GameState.WIN;
			}
			else if (checker.isDraw(x, y, player)) {	// 检查是否平局
				gameState = GameState.DRAW;
			}
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean specialStep(int x, int y, Player player) {
		if (this.player == player && chessboard[x][y] == null && !checker.isCheck(x, y, player)) {
			chessboard[x][y] = player;
			if (isSkillReady.get(player)) {	// 是谋略棋的第一步
				isSkillReady.put(player, false);
			}
			else {	// 是谋略棋的第二步
				this.changeTurn();
			}
			if (checker.isDraw(x, y, player)) {	// 检查是否平局
				gameState = GameState.DRAW;
			}
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Player isWhoseTurn() {
		return player;
	}

	@Override
	public GameState getGameState() {
		return gameState;
	}

	@Override
	public boolean getSkillStateByPlayer(Player player) {
		return isSkillReady.get(player);
	}

	@Override
	public int[][] undo(Player player) {
		// TODO
		return null;
	}

	/**
	 * 交换落子权
	 */
	private void changeTurn() {
		if (this.player == Player.BLACK) {
			player = Player.WHITE;
		}
		else {
			player = Player.BLACK;
		}
	}

	private class Checker {
		/**
		 * 方向维度向量
		 */
		private final int[][] VECTOR = {
				{1, 0}, {-1, 0},
				{0, 1}, {0, -1},
				{1, 1}, {-1, -1},
				{1, -1}, {-1, 1},
		};
		
		private boolean isCheck(int x, int y, Player player) {
			for (int i = 0; i < DIMENSION; i++) {
				if (checker.hasNPiecesInAlignment(x, y, 3, i, player)) {	// 3子连珠则将军
					return true;
				}
			}
			return false;
		}
		
		private boolean isWin(int x, int y, Player player) {
			for (int i = 0; i < DIMENSION; i++) {
				if (checker.hasNPiecesInAlignment(x, y, 5, i, player)) {	// 5子连珠则胜利
					return true;
				}
			}
			return false;
		}
		
		private boolean isDraw(int x, int y, Player player) {
			for (Player[] line : chessboard) {
				for (Player p : line) {
					if (p == null) {
						return false;
					}
				}
			}
			return true;
		}

		private boolean hasNPiecesInAlignment(int x, int y, int n, int direction, Player player) {
			int count = 1;	// (x, y)自身算1子
			for (int i = 0; i < 2; i++) {	// 一个方向维度有正反2个方向
				for (int j = 1; j < n; j++) {
					int nextX = x + VECTOR[direction + i][0] * j;
					int nextY = y + VECTOR[direction + i][1] * j;
					if (chessboard[nextX][nextY] == player) {
						count++;
					}
					else {
						break;
					}
				}
			}
			if (count >= n) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
