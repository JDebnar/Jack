package com.github.grhscompsci2.java2DGame.actors;

public class GameOver extends Actor {
    private final static String img = "gameover.png";
    
    public GameOver(){
        super(img, 165, 165, 0, Type.obstacle);
    }

    @Override
    public void hitEdge() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hitActor(Actor actor) {
        // TODO Auto-generated method stub
        
    }
}
