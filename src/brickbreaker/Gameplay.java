/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package brickbreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

/**
 *
 * @author thbar7035
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener{
    
    private boolean play = false;
    private int score = 0;
    
    private int totalBricks = 21;
    
    private Timer timer;
    private int delay = 8;
    
    private int playerX = 310;
    
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    
    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
       // timer = new Timer(delay. this);
       // timer.start();
    }
    
    public void paint (Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1, 692, 592);
        
        //border
        g.setColor(Color.yellow);
        g.fillRect(0,0, 3, 592);
        g.fillRect(0,0, 692, 5);
        g.fillRect(691,0, 3, 592);
        
        
        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 3);
        
        //the ball
        g.setColor(Color.blue);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        g.dispose();
        
        
    }
    
    

     @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0){
                ballXdir= -ballXdir;
            }
            if(ballposY < 0){
                ballYdir= -ballYdir;
            }
            if(ballposX > 670){
                ballXdir= -ballXdir;
            }
        }
        repaint();
        
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();
      if(key == KeyEvent.VK_RIGHT){
          if(playerX >= 600){
              playerX = 600;
              
          }
          else {
              moveRight();
          }
            
        } 
      if(key == KeyEvent.VK_LEFT){
          if(playerX >= 10){
              playerX = 10;
              
          }
          else {
              moveLeft();
          }
            
        } 
    }
    public void moveRight() {
          play = true;
          playerX+=20;
      }
    public void moveLeft() {
          play = true;
          playerX-=20;
      }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

   
    
    
    
    
}
