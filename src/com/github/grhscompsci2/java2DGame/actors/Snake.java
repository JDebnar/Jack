package com.github.grhscompsci2.java2DGame.actors;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.lang.model.element.VariableElement;

import com.github.grhscompsci2.java2DGame.Utility;

public class Snake extends Actor {
  public ArrayList<SnakeBody> bodies = new ArrayList<>();
  private final static String rightImg = "snake_head_right.png";
  private final static String leftImg = "snake_head_left.png";
  private final static String upImg = "snake_head_up.png";
  private final static String downImg = "snake_head_down.png";
  private int dist = 0;
  private SnakeBody nextBody = null;
  private double timer = 0;

  public Snake() {
    super(rightImg, 33, 33, 22, Type.player);
  }

  public void act(double deltaTime) {
    timer += deltaTime;
    //if decreased, time goes faster.
    if (timer > .2) {
      timer = 0;
      if (Utility.LEFT_ARROW) {
        setImage(leftImg);
        // super.setSpeed(0);
        setDX(-1 * getSpeed());
        setDY(0);
      } else if (Utility.RIGHT_ARROW) {
        setImage(rightImg);
        // super.setSpeed(0);
        setDX(getSpeed());
        setDY(0);
        // setSnakePos((int)getX()+22,(int) getY());
      } else if (Utility.DOWN_ARROW) {
        setImage(downImg);
        // super.setSpeed(0);
        setDX(0);
        setDY(getSpeed());
      } else if (Utility.UP_ARROW) {
        setImage(upImg);
        // super.setSpeed(0);
        setDX(0);
        setDY(-1 * getSpeed());
      }

      if (Utility.SPACE) {
        grow();
      }
      if (nextBody != null) {
        nextBody.update(getX(), getY());
      }
      setX(getX() + getDX());
      setY(getY() + getDY());
    }
    // super.act(deltaTime);
  }

  public void setSnakePos(int i, int j) {
    super.setX(i);
    super.setY(i);
  }

  public double getSnakeXPosition() {
    return super.getX();
  }

  public void grow() {
    SnakeBody tmp = null;
    switch (getFileName()) {
      case upImg:
        tmp = new SnakeBody((getX()), getY() + super.getHeight());
        Utility.addActor(tmp);
        bodies.add(tmp);
        break;
      case downImg:
        tmp = new SnakeBody((getX()), getY() - super.getHeight());
        Utility.addActor(tmp);
        bodies.add(tmp);
        break;
      case leftImg:
        tmp = new SnakeBody((getX() + super.getWidth()), getY());
        Utility.addActor(tmp);
        bodies.add(tmp);
        break;
      case rightImg:
        tmp = new SnakeBody((getX() - super.getWidth()), getY());
        Utility.addActor(tmp);
        bodies.add(tmp);
        break;
    }
    if(nextBody != null){
      nextBody.addBody(tmp);
    }else{
      nextBody=tmp;
    }
  }

  public ArrayList<SnakeBody> getBodies() {
    return bodies;
  }

  @Override
  public void hitEdge() {
    setSnakePos(165, 165);
    setDX(0);
    setDY(0);
    setImage("gameover.png");
    die();

  }

  @Override
  public void hitActor(Actor actor) {
    // TODO Auto-generated method stub
    if (actor.getType() == Type.food) {
      grow();
      Utility.addActor(new Food());
    }
    if(actor.getType() == Type.enemy){
    }
  }
}
