package com.github.grhscompsci2.java2DGame;

public class SnakeBody extends Actor {
    public SnakeBody(int x, int y, int vX, int vY){
        super("images/snake_body.png", x, y, 4);
        setDX(vX * getSpeed());
        setDY(vY * getSpeed());
    }
    public void setSnakeBodyPos(int i, int j){
        super.setX(i);
        super.setY(i);
    }
    }
