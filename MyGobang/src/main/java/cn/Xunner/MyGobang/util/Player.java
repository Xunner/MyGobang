package cn.Xunner.MyGobang.util;

/**
 * Created on 2018/1/25
 * 
 * @author 巽
 *
 */
public enum Player {
	BLACK("黑方"), WHITE("白方");

	private String value;

	private Player(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
