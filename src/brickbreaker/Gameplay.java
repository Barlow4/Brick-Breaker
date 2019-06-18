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
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

/**
 *
 * @author thbar7035
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener{
    
    private boolean play = false;
    private boolean restart = false;
    private int score = 0;
    private int maxlives = 3;
    private int lives = 3;
    
    
    private Timer timer;
    private int delay = 8;
    
    private int playerX = 310;
    private int playerY = 550;
    private int playerWidth = 100;
    private int playerHeight = 3;
    private int playerXspeed = 8;
    private int playerDirection = 0;
    private int speedincr=0;
    
    private int ballposX = (int)(Math.random()*600 + 50);
    private int ballposY = 350;
    private int ballXdir = -3;
    private int ballYdir = -3;
    
    private int maxx = 681;
    private int maxy = 561;
    
    private int ballradius = 20;
    private int borderwidth = 3;
    
    private int brickWidth = 75;
    private int brickHeight = 20;
    
    private int numRowsBricks = 7;
    private int numBricksPerRow = 7;
    private int totalBricks = numRowsBricks * numBricksPerRow;
    private int currentBricks = numRowsBricks * numBricksPerRow;
    private int brickWidthSpacing = 10;
    private int brickHeightSpacing = 10;
    
    String[] brcolours = {"violet", "indigo","blue","green","yellow","orange", "red"};
    
        
    Brick[] bricks = new Brick[totalBricks];
    
    
    int n=0;
    
    public Gameplay(){
     
        this.lives = this.maxlives;

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        for(int i =0; i<numRowsBricks; i++)
        {
            for(int j=0; j <numBricksPerRow; j++)
            {
                bricks[i*numBricksPerRow + j] = new Brick(brcolours[i],50+ (brickWidth+brickWidthSpacing)*j, 50+(brickHeight+brickHeightSpacing)*i);
                
                
            }    
        
        }    
        
        
        
        
        // Start the timer
        TimerTask task = new TimerTask() 
        {
            public void run() 
            {
                n++;
                if(n % 20 == 1)
                    System.out.println("hello world " +n +"   "+ballXdir+","+ballYdir);
                gameStep();
            }
        };
        
        Timer timer = new Timer("GameTimer");
     
        long delay = 25;
        timer.schedule(task, delay, delay);
        

         
        
    }
    
    public void prepGame()
    {
        ballposX = (int)(Math.random()*600 + 50);
        ballposY = 350;
        ballYdir *= -1;
        
    }
    
    public void restartGame()
    {
        prepGame();
        score=0;
        lives=maxlives;
        ballXdir = -2;
        ballYdir = -2;
        
        for(int i =0; i<numRowsBricks; i++)
        {
            for(int j=0; j <numBricksPerRow; j++)
            {
                bricks[i*numBricksPerRow + j] = new Brick(brcolours[i],50+ (brickWidth+brickWidthSpacing)*j, 50+(brickHeight+brickHeightSpacing)*i);
                
                
            }    
        
        }
        
        
        play = true;
    }        
    
    public void paint (Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1, 692, 592);
        
        //border
        g.setColor(Color.yellow);
        g.fillRect(0,0, borderwidth, maxy);
        g.fillRect(0,0, maxx, borderwidth);
        g.fillRect(maxx,0, borderwidth, maxy);
        
        
        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, playerY, playerWidth, playerHeight);
        
        //the ball
        g.setColor(Color.blue);
        g.fillOval(ballposX, ballposY, ballradius, ballradius);
        
        //Draw the bricks
        for (Brick bb : bricks) { 
            bb.drawBrick(g);
        }

        g.setColor(Color.orange);
        g.drawString("Brick-Breaker - Tom Barlow - 2019", 10, 20);
        g.drawString("Score:"+score, 300, 20);
        g.drawString("Lives:"+lives, 500, 20);
        
        
        if(lives <=0){
            g.setColor(Color.white);
            g.drawString("YOU SUCK", 310, 325);
            g.drawString("DO YOU WANT TO TEST YOUR SKILLZ AGAIN? PRESS <Y>", 200, 350);
        }
        
        
        
        if(currentBricks== 0){
            g.setColor(Color.green);
               g.drawString("YOU JUST WASTED SO MUCH TIME", 240, 250); 
               g.drawString("PRESS <Y> TO PLAY AGAIN", 260, 270); 
            play = false;   
        }
        
       
            
            
       if(play == false && lives >0)
       {
           if(lives < maxlives )
               g.drawString("TOO BAD.  YOU HAVE "+lives+" LEFT.  PRESS <SPACE> TO START", 200, 330); 
           
           else
            g.drawString("PRESS <SPACE> TO CONTINUE", 240, 350);
       } 

        g.dispose();

    }
    
    
    
    

     @Override
    public void actionPerformed(ActionEvent e) {
    
        System.out.println("Action perf");
    }
     
    private void gameStep()
    {
        if(play){
            int dir;
            // Move the ball
            ballposX += ballXdir;
            ballposY += ballYdir;
            
            if(score > 400 && speedincr == 0)
            {
               ballXdir *= 1.3;
               ballYdir *= 1.3;
               speedincr = 1;
            }
            if(score > 1000 && speedincr == 1)
            {
               ballXdir *= 1.5;
               ballYdir *= 1.5;
               speedincr = 2;
            }
            if(score > 1800 && speedincr == 2)
            {
               ballXdir *= 1.5;
               ballYdir *= 1.5;
               speedincr = 3;
            }
            
            //Process all bricks
            for (Brick bb : bricks) 
            { 
              dir = bb.isCollision(ballposX, ballposY, ballradius);
              
              if(dir > 0)
              {
                  score += 50;
                  currentBricks--;
                  System.out.println(currentBricks);
                  bb.goawayBrick();
              }    
              
                if(dir == 1)
                {
                    ballYdir= -ballYdir;
                System.out.println("T");

                }    
                else if(dir == 2)
                {
                System.out.println("R");
                    ballXdir = -ballXdir;
                }  
                else if(dir == 3)
                {
                System.out.println("B");
                    ballYdir = -ballYdir;
                } 
                else if(dir == 4)
                {
                System.out.println("L");
                    ballXdir = -ballXdir;
                } 

                  
            }
            
            
            if(ballposX <= borderwidth){
                ballXdir *= -1;
            }
            if(ballposY <= borderwidth){
                ballYdir *= -1;
            }
            if(ballposX + ballradius >= maxx - borderwidth){
                ballXdir *= -1;
            }
            
            //Player bounce
            if(ballposY+ballradius >= playerY &&
                    ballposX + ballradius >= playerX &&
                    ballposX <= playerX + playerWidth ){
               
               if(ballXdir> 0 && ballposX - playerX <= 20  ) 
                   ballXdir *= -1;
               else if(ballXdir <0 && (playerX + playerWidth)-(ballposX + ballradius) <= 20)
                   ballXdir *= -1;

               score += 5;
               ballYdir= -ballYdir;
            }
            
            
            // Ball in fail position
            if(ballposY > maxy){
                lives--;
                play = false;
                prepGame();
            }
            
            if(lives<=0){
                ballposX=330;
                ballposY=400;
                ballXdir = 0;
                ballYdir = 0;
                
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
        //System.out.println(key);
        if(key == 89) // Y
            restartGame();
        else if(key == 78) // N
            System.exit(0);
        if(key == 32 || key == 80)
        {
            if(play == true)
            {
                play = false;
                score -= 10;
                if(score<0)score=0;
            }
            else if(play == false)
            {        
                restart = false;
                play = true;
        }   }
        
            
            
      if(key == KeyEvent.VK_RIGHT){
          if(playerX + playerWidth >= maxx - borderwidth){
              playerX = maxx - borderwidth - playerWidth;
              
          }
          else {
              moveRight();
          }
            
        } 
      if(key == KeyEvent.VK_LEFT){
          if(playerX <=  borderwidth){
              playerX = borderwidth;
              
          }
          else {
              moveLeft();
          }
            
        } 
    }
    public void moveRight() {
          play = true;
          playerX+=playerXspeed;
          playerDirection = 1;
      }
    public void moveLeft() {
          play = true;
          playerX-=playerXspeed;
          playerDirection = -1;
      }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

   
    class Brick {
       private String colour;
       private int x;
       private int y;
       private int visible;
       private int width, height;
       private int marginx, marginy;
       
        // constructor
        public Brick(String colour, int x, int y) {
           this.colour = colour;
           this.x = x;
           this.y = y;
           
           this.visible =1;
           this.width = brickWidth;
           this.height = brickHeight;
           
           this.marginx = this.marginy = 10; //pixels
        
        }

       // getter
       public int getX() { return this.x; }
       public int getY() { return this.y; }
       public int getVisibility() { return this.visible; }
       // setter

       public void goawayBrick(){
           this.visible=0;
       }
       
       public int isCollision(int ballx, int bally, int ballradius) 
       { 
           if(this.visible == 0)return 0;

            // test top
           if(bally+ballradius >= this.y &&
                   bally + ballradius <= this.y + this.marginy &&
                    ballx + ballradius >= this.x &&
                    ballx <= this.x + this.width ){
                
                return 1;
            }
           
           // test right
           if(ballx <= this.x + this.width &&
                   ballx >= this.x + this.width - this.marginx &&
                    bally + ballradius >= this.y &&
                    bally <= this.y + this.height ){
                
                return 2;
            }
       
            // test left
           if(ballx + ballradius >= this.x &&
                   ballx +ballradius < this.x + this.marginx &&
                    bally + ballradius >= this.y &&
                    bally <= this.y + this.height ){
                
                return 4;
            }
            // test bottom
            if(bally <= this.y + this.height &&
                   bally > this.y + this.height - this.marginy &&
                    ballx + ballradius >= this.x &&
                    ballx <= this.x + this.width ){
                
                return 3;
            }

           
           
           return 0;
       }
       public void setVisiblity(int vis) { this.visible = vis; }

       public void drawBrick(Graphics g) { 
            if(this.visible == 1)
            {
                setColour(g, this.colour);
            
                g.fillRect(this.x, this.y, this.width, this.height);
       
            }
       }
       
       public void setColour(Graphics g, String c)
       {
           Color col = new Color(255, 0, 0);
           
           if(c == "red")
               col = new Color(255, 0, 0);
           else if(c == "green")
               col = new Color(0, 255, 0);
           else if(c == "blue")
               col = new Color(0, 0, 255);
           else if(c == "cyan")
               col = new Color(0, 255, 255);
           else if(c == "magenta")
               col = new Color(255, 0, 255);
           else if(c == "white")
               col = new Color(255, 255, 255);
            else if(c == "yellow")
               col = new Color(255, 255, 0);
           else if(c == "violet")
               col = new Color(128, 0, 128);
           else if(c == "orange")
               col = new Color(255, 165, 0);
           else if(c == "indigo")
               col = new Color(75, 0, 130);


           g.setColor(col);    
       }
    }
    
    
    
}
