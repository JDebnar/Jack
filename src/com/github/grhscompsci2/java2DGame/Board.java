package com.github.grhscompsci2.java2DGame;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;

/**
 * This class controls the JPanel where the game logic and rendering happen. You
 * will need to edit the attributes to add your enemies and player classes so
 * they can draw and act here.
 */
public class Board extends JPanel implements Runnable {
  private final int DELAY = 25000000;
  private final String BACKGROUND_FILE_NAME = "images/background4.png";
  private BufferedImage background;
  private Thread animator;

  // Initialize all of your actors here: players, enemies, obstacles, etc.
  private Snake eater;
  private Food apple;

  /**
   * Initialize the board
   */
  public Board() {
    initBoard();
  }

  /**
   * This method will initialize the key listener, load the background image, set
   * the window size to the size of the image, and initialize an actor. When you
   * are modifying this for your game, you should initialize all of your actors
   * that need to be used in the game.
   */
  private void initBoard() {
    // add the custom key adapter to the panel
    addKeyListener(new TAdapter());
    // load the background image
    loadBackground();
    // set the size of the panel to the size of the background
    setPreferredSize(getPreferredSize());
    setFocusable(true);
    // Initialize all actors below here
    eater = new Snake();
    apple = new Food();
  }

  /**
   * This method will assign the BACKGROUND_FILE_NAME as the background of the
   * JPanel. The background.png file will determine the resolution of your screen.
   * All of your other sprites should match the resolution of the background.
   */
  private void loadBackground() {
    // Load the image
    try {
      this.background = ImageIO.read(Board.class.getResource(BACKGROUND_FILE_NAME));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Returns the preferred size of the background. Used to set the starting size
   * of the JPanel window.
   */
  @Override
  public Dimension getPreferredSize() {
    // if there is no image, give a default size
    if (background == null) {
      return new Dimension(400, 300);
    }
    // give a size based on the background image
    return new Dimension(background.getWidth(), background.getHeight());
  }

  /**
   * This method will draw everything in JPanel. It will update the scale so when
   * the JFrame is resized, the image will resize to maintain the aspect ratio.
   */
  @Override
  public void paintComponent(Graphics g) {
    // Graphics 2D offers greater control than Graphics, so use it instead.
    Graphics2D g2d = (Graphics2D) g;
    // Update the scale based on the size of the JPanel
    Utility.updateScale(new Dimension(background.getWidth(), background.getHeight()), getSize());

    // Always draw this first so it will be on the bottom
    g2d.drawImage(background, 0, 0, Utility.scale(background.getWidth()), Utility.scale(background.getHeight()), this);

    // call other drawing stuff here
    eater.draw(g2d, this);
    apple.draw(g2d,this);
    // get the array list of bullets
    ArrayList<SnakeBody> bodies = eater.getBodies();
    
    // draw them all
    for (SnakeBody body : bodies) {
      switch (eater.getFileName()){
        case "images/snake_head_up.png":
        body = new SnakeBody(eater.getX(), eater.getY()+(eater.getHeight()/2), eater.getDX(), eater.getDY());
          break;
        case "images/snake_head_down.png":
        body = new SnakeBody(eater.getX(), eater.getY()-(eater.getHeight()/2), eater.getDX(), eater.getDY());
          break;
        case "images/snake_head_left.png":
        body = new SnakeBody(eater.getX()+(eater.getWidth()/2)/*+(bodies.size()*22)*/, eater.getY(), eater.getDX(), eater.getDY());
          break;
        case "images/snake_head_right.png":
          body = new SnakeBody(eater.getX()-(eater.getWidth()/2)/*-(bodies.size()*22)*/, eater.getY(), eater.getDX(), eater.getDY());

      }
      body.draw(g2d, this);
    }

    // This method will ensure that the display is up to date
    Toolkit.getDefaultToolkit().sync();
  }

  /**
   * This method is called once per frame. This will allow us to advance the game
   * logic every frame so the actors move and react to input.
   * @param deltaTime
   */
  private void act(float deltaTime) {
    // Check for collisions between actors. Do it before they act so you can handle
    // death and other cases appropriately
    checkCollisions();
    // Have all of your actor attributes act here.
    eater.act(deltaTime);
    // Manage your bullets
    //manageBullets();
  }

  /**
   * This method will start the thread for the animation.
   */
  @Override
  public void addNotify() {
    super.addNotify();

    animator = new Thread(this);
    animator.start();
  }

  /**
   * This method is called once by the animator thread. It regulates the call to
   * Thread.sleep so that each cycle is very close to the same length, unless
   * something goes horribly wrong.
   */
  @Override
  public void run() {
    long beforeTime;
    long lastTime=System.nanoTime();;
    long timeDiff=0;
    long sleep;
    while (true) {
      // Sample the current time before act() and repaint() are called
      beforeTime = System.nanoTime();
      
      float deltaTime=(beforeTime-lastTime)/1000000000f;
      // update all actors
      act(deltaTime);
      // force a repaint
      repaint();
      
      // how long did that take?
      timeDiff = System.nanoTime() - beforeTime;
      // we want to wait a consistent amount of time, so subtract timeDiff from DELAY
      sleep = (DELAY - timeDiff)/1000000;

      // If we went too long, only wait 2 milliseconds for garbage collection.
      if (sleep < 0) {
        sleep = 2;
      }

      // go to sleep
      try {
        Thread.sleep(sleep);
      } catch (InterruptedException e) {
        // Show a message if something goes wrong
        String msg = String.format("Thread interrupted: %s", e.getMessage());
        JOptionPane.showMessageDialog(this, msg, "Error",
            JOptionPane.ERROR_MESSAGE);
      }
      lastTime=System.nanoTime();
    }
  }

  /**
   * This method will check each actor to see if the bounding box overlaps the
   * bounding box of any other actor. Not the most efficient, but will work for
   * small games.
   */
  public void checkCollisions() {
    // check player against all other objects
    /*
     * Use this code as a sample. You will need an ArrayList of things to run into
     * Rectangle r3 = actor.getBounds();
     * for (Alien alien : aliens) {
     * 
     * Rectangle r2 = alien.getBounds();
     * 
     * if (r3.intersects(r2)) {
     * 
     * spaceship.setVisible(false);
     * alien.setVisible(false);
     * ingame = false;
     * }
     * }
     * }
     */
    if (eater.getX() < 0 || eater.getX() > Utility.gameWidth
          || eater.getY() < 0 || eater.getY() > Utility.gameHeight) {
          eater.setSnakePos(50,50);
          eater.setDX(0);
          eater.setDY(0);
        JOptionPane.showMessageDialog(this,"Game Over");
      }

    Rectangle r1 = eater.getBounds();
    Rectangle r3= apple.getBounds();
    if( r1.intersects(r3)){
      apple.setInvisible(true);
      eater.grow();
      apple = new Food();
    }

  }

  /**
   * This class is used to handle the keyEvents. If you want an actor to respond
   * to the keyboard, they need to be called in this class.
   */
  private class TAdapter extends KeyAdapter {

    /**
     * When a key is released, this method is called and passed the ID of the key
     * that was released.
     * 
     * @param e the KeyEvent object generated by the KeyListener
     */
    @Override
    public void keyReleased(KeyEvent e) {
      // add all objects that care about keys being released here
      eater.keyPressed(e);
    }

    /**
     * When a key is pressed, this method is called and passed the ID of the key
     * that was pressed.
     * 
     * @param e the KeyEvent object generated by the KeyListener
     */
    @Override
    public void keyPressed(KeyEvent e) {
      // add all objects that care about keys being pressed here
      eater.keyPressed(e);
    }
  }
}