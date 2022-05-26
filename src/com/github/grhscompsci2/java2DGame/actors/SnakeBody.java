package com.github.grhscompsci2.java2DGame.actors;

public class SnakeBody extends Actor {
    public SnakeBody nextBody;

    public SnakeBody(double d, double e) {
        super("snake_body.png", d, e, 0, Type.enemy);
    }

    public void setSnakeBodyPos(int i, int j) {
        super.setX(i);
        super.setY(j);
    }

    public void update(double x, double y) {
        if (nextBody != null) {
            nextBody.update(getX(), getY());
        }
        setX(x);
        setY(y);
    }

    @Override
    public void hitEdge() {

    }

    @Override
    public void hitActor(Actor actor) {
        // TODO Auto-generated method stub

    }

    public void addBody(SnakeBody tmp) {
        if(nextBody!=null){
            nextBody.addBody(tmp);
        }
        else{
            nextBody=tmp;
        }
    }
}
