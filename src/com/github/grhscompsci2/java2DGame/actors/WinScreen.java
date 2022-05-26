package com.github.grhscompsci2.java2DGame.actors;

public class WinScreen extends Actor {
    private final static String img = "gamewin.png";

    public WinScreen(){
        super(img, 165,165,0,Type.obstacle);
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
