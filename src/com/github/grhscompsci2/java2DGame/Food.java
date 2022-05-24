package com.github.grhscompsci2.java2DGame;


public class Food extends Actor {
    private final static String Img = "images/watermelon.png";

    
    
    public Food(){
      
      super(Img, (int)(Math.random()*330), (int)(Math.random()*330));
    //super(Img,(int) Math.random()*Utility.gameWidth, (int)Math.random()*Utility.gameHeight);
    
  }


    public void setInvisible(boolean b) {
        setImage("images/no_sprite.png");
    }
}
