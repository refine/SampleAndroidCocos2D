package com.tiennt.android.samplecocos2dandroid;

import java.util.Random;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor4B;

public class GameLayer extends CCColorLayer {
	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCColorLayer layer = new GameLayer(ccColor4B.ccc4(255, 255, 255, 255));
		scene.addChild(layer);

		return scene;
	}

	public GameLayer(ccColor4B color) {
		super(color);
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		CCSprite player = CCSprite.sprite("Player.png");
		player.setPosition(CGPoint.ccp(player.getContentSize().width / 2.0f,
				winSize.height / 2.0f));

		addChild(player);
		this.schedule("gameLogic", 1.0f);
	}

	public void gameLogic(float dt) {
		addTarget();
	}

	protected void addTarget() {
		Random rand = new Random();
		CCSprite target = CCSprite.sprite("Target.png");

		CGSize winSize = CCDirector.sharedDirector().displaySize();

		int minY = (int) (target.getContentSize().height / 2.0f);
		int maxY = (int) (winSize.height - target.getContentSize().height / 2.0f);
		int rangeY = maxY - minY;
		int actualY = rand.nextInt(rangeY) + minY;

		target.setPosition(CGPoint.ccp(winSize.width
				+ (target.getContentSize().width / 2.0f), actualY));
		addChild(target);

		int minDuration = 2;
		int maxDuration = 4;
		int rangeDuration = maxDuration - minDuration;
		int actualDuration = rand.nextInt(rangeDuration) + minDuration;

		CCMoveTo actionMove = CCMoveTo.action(actualDuration,
				CGPoint.ccp(-target.getContentSize().width / 2.0f, actualY));

		CCCallFuncN actionMoveDone = CCCallFuncN.action(actionMove,
				"spriteMoveFinished");
		CCSequence action = CCSequence.actions(actionMove, actionMoveDone);
		target.runAction(action);
	}

	public void spriteMoveFinished(Object sender) {
		CCSprite sprite = (CCSprite) sender;
		this.removeChild(sprite, true);
	}
}
