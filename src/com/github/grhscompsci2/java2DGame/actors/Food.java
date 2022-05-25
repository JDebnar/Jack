package com.github.grhscompsci2.java2DGame.actors;


public class Food extends Actor {
    private final static String img = "watermelon.png";

    
    
    public Food(){
      
      super(img, (int)(Math.random()*330), (int)(Math.random()*330),0, Type.food);
    //super(Img,(int) Math.random()*Utility.gameWidth, (int)Math.random()*Utility.gameHeight);
    
  }


    public void setInvisible(boolean b) {
        setImage("no_sprite.png");
    }


    @Override
    public void hitEdge() {
      // TODO Auto-generated method stub
      
    }


    @Override
    public void hitActor(Actor actor) {
      if(actor.getType()==Type.player){
        setInvisible(true);
        die();
      }  
    }
}
