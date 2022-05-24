package com.github.grhscompsci2.java2DGame;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.lang.model.element.VariableElement;

public class Snake extends Actor {
  public ArrayList<SnakeBody> bodies = new ArrayList<>();
  private final static String rightImg = "images/snake_head_right.png";
  private final static String leftImg = "images/snake_head_left.png";
  private final static String upImg = "images/snake_head_up.png";
  private final static String downImg = "images/snake_head_down.png";
  private int dist = 0;

  public Snake() {
    super(rightImg, 33, 33,10);
  }

  // Snake will go to far in some cases. Maybe the DX and dYisn't updating fast
  // enough and the snake doesn't turn sometimes
  // when holding down a button, the Snake turns very late compared to pressing it
  // fast.
  // I think the problem occurs when adding the dx and dy when you change between
  // L R and U D
  public void keyPressed(KeyEvent e) {
    int oldDX = getDX();
    int oldDY = getDY();
    dist += Math.abs(getDX()) + Math.abs(getDY());
    super.keyPressed(e);
    int key = e.getKeyCode();
    //if (dist % 22 == 0)
      //dist = 0;
    //if (dist == 0) {

      if (key == KeyEvent.VK_LEFT) {
        setImage(leftImg);
        // super.setSpeed(0);
        setDY(0);
      } else if (key == KeyEvent.VK_RIGHT) {
        setImage(rightImg);
        // super.setSpeed(0);
        setDY(0);
      } else if (key == KeyEvent.VK_DOWN) {
        setImage(downImg);
        // super.setSpeed(0);
        setDX(0);
      } else if (key == KeyEvent.VK_UP) {
        setImage(upImg);
        // super.setSpeed(0);
        setDX(0);
      }

      if (key == KeyEvent.VK_SPACE) {
        grow();
      }
      //super.setSpeed(2);
    //} else {
      //setDX(oldDX);
      //setDY(oldDY);
    //}
  }

  public void setSnakePos(int i, int j) {
    super.setX(i);
    super.setY(i);
  }

  public int getSnakePosition() {
    return super.getX();
    // return super.getY();
  }

  void grow() {
    int vX = 0;
    int vY = 0;

    switch (getFileName()) {
      case upImg:
        vY = -1;
        bodies.add(new SnakeBody((getX()), getY() + super.getHeight(), getDX(), getDY()));
        break;
      case downImg:
        vY = 1;
        bodies.add(new SnakeBody((getX()), getY() - super.getHeight(), getDX(), getDY()));
        break;
      case leftImg:
        vX = -1;
        bodies.add(new SnakeBody((getX() + super.getWidth()), getY(), getDX(), getDY()));
        break;
      case rightImg:
        vX = 1;
        bodies.add(new SnakeBody((getX() - super.getWidth()), getY(), getDX(), getDY()));
        break;
    }
  }

  public ArrayList<SnakeBody> getBodies() {
    return bodies;
  }
}
