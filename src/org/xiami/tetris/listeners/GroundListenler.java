/**
 * 
 */
package org.xiami.tetris.listeners;

import org.xiami.tetris.model.Ground;

/**
 * @author xiami
 *
 */
public interface GroundListenler {
	/**
	 * 游戏死掉的监听
	 * @param ground
	 */
	void gameDie(Ground ground);
	
	void removeListener(Ground ground);
}
