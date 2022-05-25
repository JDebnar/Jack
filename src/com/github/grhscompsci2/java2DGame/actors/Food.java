package com.github.grhscompsci2.java2DGame.actors;

import com.github.grhscompsci2.java2DGame.Utility;

public class Food extends Actor {
    private final static String img = "watermelon.png";

    
    
    public Food(){
      super(img, roundToNearest11((int)(Math.random()*330))
                  ,roundToNearest11((int)(Math.random()*330)) 
                     ,0, Type.food);    
  }

    public void setInvisible(boolean b) {
        setImage("no_sprite.png");
    }

    public static int roundToNearest11(int i){
      int rem;
      rem=i%11;
      
      if(rem>5)
      {
        i=i+(11-rem);
      }
      else
      {
       i=i-rem; 
      }
      if(i%22==0){
        if (i>311){
          i-=33;
        }else{
          i+=11;
        }

      }
      return i;
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
      }else if(actor.getType()==Type.enemy){
        die();
        Utility.addActor(new Food());
    }
}
}

