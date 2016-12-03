package com.quickjam2;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

public class QuickJam extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	PlayerController player;

	Texture obstacle;

	HashMap<Integer, Sprite> time = new HashMap<Integer, Sprite>();

	float obstaclesPerSecond = 2;
	float timeToObstacle;
	float currentObstacleTime;

	float timeToGravity;
	float currentGravityTime;

	ArrayList<VectorizedSprite> obstacles = new ArrayList<VectorizedSprite>();

	@Override
	public void create() {
		batch = new SpriteBatch();

		Texture playerImg = new Texture("player.png");
		player = new PlayerController(playerImg);
		player.setSize(48, 48);
		player.setPosition(player.getWidth()+20, player.getHeight()+20);

		obstacle = new Texture("obstacle.png");

		time.put(1, new Sprite(new Texture("one.png")));
		time.put(2, new Sprite(new Texture("two.png")));
		time.put(3, new Sprite(new Texture("three.png")));

		time.get(1).setScale(5);
		time.get(1).setPosition(384 - time.get(1).getWidth() / 2, 278 - time.get(1).getHeight() / 2);
		time.get(2).setScale(5);
		time.get(2).setPosition(384 - time.get(2).getWidth() / 2, 278 - time.get(2).getHeight() / 2);
		time.get(3).setScale(5);
		time.get(3).setPosition(384 - time.get(3).getWidth() / 2, 278 - time.get(3).getHeight() / 2);

		timeToObstacle = 1 / obstaclesPerSecond;
		currentObstacleTime = -3;

		timeToGravity = 5;
		currentGravityTime = -3;

		Gdx.input.setInputProcessor(this);
	}

	public void reset() {
		player.setPosition(player.getWidth()+20, player.getHeight()+20);
		if (!player.gravityDown()) {
			player.flipGravity();
		}
		
		timeToObstacle = 1 / obstaclesPerSecond;
		currentObstacleTime = -3;

		timeToGravity = 4;
		currentGravityTime = -3;

		obstacles = new ArrayList<VectorizedSprite>();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float dt = Gdx.graphics.getDeltaTime();

		currentObstacleTime += dt;
		currentGravityTime += dt;

		System.out.println(currentObstacleTime);

		if (currentObstacleTime > timeToObstacle) {
			VectorizedSprite vs = new VectorizedSprite(obstacle);
			vs.setSize(24, 24);
			vs.setPosition(775, (float) (Math.random() * 768));
			vs.setMovementVector(new Vector2(-200, 0));
			obstacles.add(vs);
			currentObstacleTime -= timeToObstacle;
		}

		if (currentGravityTime > timeToGravity) {
			player.flip(false, true);
			player.flipGravity();
			currentGravityTime -= timeToGravity;
		}
		
		player.update(dt);

		ArrayList<VectorizedSprite> toRemove = new ArrayList<VectorizedSprite>();

		for (VectorizedSprite vs : obstacles) {
			vs.update(dt);
			if (vs.getX() < 0) {
				toRemove.add(vs);
			}
			if (Intersector.overlaps(player.getBoundingRectangle(), vs.getBoundingRectangle())) {
				reset();
			}
		}

		for (VectorizedSprite vs : toRemove) {
			obstacles.remove(vs);
		}

		batch.begin();

		for (VectorizedSprite vs : obstacles) {
			vs.draw(batch);
		}

		player.draw(batch);

		if ((currentGravityTime > timeToGravity - 3 && currentGravityTime < timeToGravity - 2.5)
				|| (currentGravityTime > -3 && currentGravityTime < -2.5)) {
			time.get(3).draw(batch);
		} else if ((currentGravityTime > timeToGravity - 2 && currentGravityTime < timeToGravity - 1.5)
				|| (currentGravityTime > -2 && currentGravityTime < -1.5)) {
			time.get(2).draw(batch);
		} else if ((currentGravityTime > timeToGravity - 1 && currentGravityTime < timeToGravity - 0.5)
				|| (currentGravityTime > -1 && currentGravityTime < -0.5)) {
			time.get(1).draw(batch);
		}

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.LEFT) {
			player.getMovement().set(new Vector2(-100, player.movement.y));
		} else if (keycode == Input.Keys.RIGHT) {
			player.getMovement().set(new Vector2(100, player.movement.y));
		} else if (keycode == Input.Keys.UP) {
			player.jump();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
