package cn.Xunner.MyGobang.impl.gameImpl;

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

	public GameImpl() {
		this.init();
	}

	@Override
	public void init() {
		player = Player.BLACK;
		chessboard = new Player[15][15];
	}

	@Override
	public boolean normalStep(int x, int y, Player player) {
		if (this.player == player && chessboard[x][y] == null) {
			chessboard[x][y] = player;
			this.changeTurn();
			this.updateGameState(x, y, player);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean specialStep(int x, int y, Player player) {
		// TODO
		return false;
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
		// TODO
		return false;
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

	private void updateGameState(int x, int y, Player player) {
		// TODO
	}
}
