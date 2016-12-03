package com.quickjam2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by libri on 12/3/16.
 */
public class PlayerController extends VectorizedSprite {

    private float gravityAccel = 5;

    public PlayerController(Texture img) {
		super(img);
	}

	public void update(float dt) {
        if(!(this.getY() < this.getHeight() && gravityAccel > 0 || this.getY() > 568 && gravityAccel < 0)) {
            Vector2 movement = super.movement;
            movement.add(0, gravityAccel * dt);
        }

        super.update(dt);
    }

    public void flipGravity() {
        gravityAccel *= -1;
    }

}
