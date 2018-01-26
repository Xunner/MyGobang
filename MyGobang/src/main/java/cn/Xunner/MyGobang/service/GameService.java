package cn.Xunner.MyGobang.service;

import cn.Xunner.MyGobang.util.GameState;
import cn.Xunner.MyGobang.util.Player;

/**
 * Created on 2018/1/25
 * 
 * @author 巽
 *
 */
public interface GameService {
	/**
	 * 初始化游戏，开始一盘对局。
	 */
	public void init();

	/**
	 * 某方选手下一步普通棋。
	 * 
	 * @param x x坐标（从0开始，下同）
	 * @param y y坐标
	 * @param player 黑/白方
	 * @return 是否成功在该位置下棋。true：成功，false：失败（当前位置已有旗子，或已下第一步谋略棋）
	 */
	public boolean normalStep(int x, int y, Player player);

	/**
	 * 某方选手下一步谋略棋。
	 * 
	 * @param x x坐标
	 * @param y y坐标
	 * @param player 黑/白方
	 * @return 是否成功在该位置下棋。true：成功，false：失败（当前位置已有旗子或会导致将军）
	 */
	public boolean specialStep(int x, int y, Player player);
	
	/**
	 * 轮到哪一方落子。
	 * 
	 * @return 当前可以落子的一方
	 */
	public Player isWhoseTurn();

	/**
	 * 得到本次对局的当前状态。
	 * 
	 * @return WIN：player胜利<br>
	 *         CONTINUE：对局未结束<br>
	 *         DRAW：平局
	 */
	public GameState getGameState();

	/**
	 * 得到某方选手的技能状态。
	 * 
	 * @param player 黑/白方
	 * @return true：技能可用，false：技能冷却中
	 */
	public boolean getSkillStateByPlayer(Player player);

	/**
	 * 某方选手悔棋。若为最近一次落子的选手，则撤销一次落子；否则撤销两次。
	 * 
	 * @param 需要撤销落子的所有位置。a[i][j]表示坐标为(i, j)的格子的落子需要被撤销
	 */
	public int[][] undo(Player player);
}
