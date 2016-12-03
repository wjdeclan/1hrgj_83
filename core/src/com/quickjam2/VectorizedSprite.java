package com.quickjam2;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class VectorizedSprite extends Sprite {
	Vector2 movement = new Vector2(0, 0);

	public void update(float dt) {
		this.setPosition(this.getX() + movement.x * dt, this.getY() + movement.y * dt);
	}

	public void setMovementVector(Vector2 movement) {
		this.movement = movement;
	}

}
