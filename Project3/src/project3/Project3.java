/***************************************************************
  Project Number 3 - Comp Sci 182 - Data Structures (w/ Swing)
  Start Code - Build your program starting with this code
               Card Game
  Copyright 2005-2016 Christopher C. Ferguson
  This code may only be used with the permission of Christopher C. Ferguson
***************************************************************/
package project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.RenderingHints;

public class Project3 extends JFrame implements ActionListener {

  private static int winxpos=0,winypos=0;      // place window here

  private JButton newButton, playButton, newGameButton, hitButton, standButton;
  private JLabel header;
  private CardList theDeck = null;
  private JPanel northPanel;
  private JPanel buttonPanel;
  private MyPanel centerPanel;
  private Round round;
  private boolean roundOn, finale;
  private static JFrame myFrame = null;

  ////////////              MAIN      ////////////////////////
  public static void main(String[] args) {
     Project3 tpo = new Project3();
  }

  ////////////            CONSTRUCTOR   /////////////////////
  public Project3 ()
  {
        roundOn = false;
        myFrame = this;                 // need a static variable reference to a JFrame object
        setDefaultCloseOperation(myFrame.DISPOSE_ON_CLOSE);  
    
        loadView();
        
        setSize(800,700);
        setLocation(winxpos,winypos);
        
        setVisible(true);
   }

  public void loadView(){
        header = new JLabel("Welcome to Blackjack!");
        header.setFont(new Font("Verdana",1,20));
        playButton = new JButton("PLAY");
        playButton.addActionListener(this);
        
        newGameButton = new JButton("NEW GAME");
        newGameButton.addActionListener(this);
        hitButton = new JButton("HIT");
        hitButton.addActionListener(this);
        standButton = new JButton("STAND");
        standButton.addActionListener(this);
        
        newGameButton.setVisible(false);
        hitButton.setVisible(false);
        standButton.setVisible(false);
        
        buttonPanel = new JPanel();
        buttonPanel.add(newGameButton);
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        
        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setBackground(Color.white);
        northPanel.add(header, "North");
        northPanel.add(playButton, "Center");
        northPanel.add(buttonPanel, "South");
        add("North",northPanel); 
        centerPanel = new MyPanel();
        add("Center",centerPanel);
  }
  
  ////////////   BUTTON CLICKS ///////////////////////////
  public void actionPerformed(ActionEvent e) {

       if (e.getSource()== newButton) {
        theDeck = new CardList(52);
        repaint();
      }
       
       if (e.getSource()== playButton || e.getSource()==newGameButton) {
        finale = false;
        round = new Round();
        header.setText("Blackjack");
        playButton.setVisible(false);
        newGameButton.setVisible(true);
        hitButton.setVisible(true);
        standButton.setVisible(true);
        roundOn = true;
        repaint();
      }
       
       if(e.getSource()==hitButton){  
         round.playerHand.insertCard(round.theDeck.drawCard());
         secondTurn();
       }
       
       if(e.getSource()==standButton){
         finale = true;
         hitButton.setVisible(false);
         standButton.setVisible(false);
         secondTurn();
       }
      
  }
  
    public void secondTurn(){
        int dealerScore = round.dealerScore;
        if(dealerScore<18){
            round.dealerHand.insertCard(round.theDeck.drawCard());
        }
        round.updateScores();
        if(round.playerScore>20){
            finale = true;
            hitButton.setVisible(false);
            standButton.setVisible(false);
        }
        repaint();
    }


// This routine will load an image into memory
//
    public static Image load_picture(String fname)
    {
            // Create a MediaTracker to inform us when the image has
            // been completely loaded.
            Image image;
            MediaTracker tracker = new MediaTracker(myFrame);


            // getImage() returns immediately.  The image is not
            // actually loaded until it is first used.  We use a
            // MediaTracker to make sure the image is loaded
            // before we try to display it.

            image = myFrame.getToolkit().getImage(fname);

            // Add the image to the MediaTracker so that we can wait
            // for it.
            tracker.addImage(image, 0);
            try { tracker.waitForID(0); }
            catch ( InterruptedException e) { System.err.println(e); }

            if (tracker.isErrorID(0)) { image=null;}
            return image;
    }
// --------------   end of load_picture ---------------------------

    class MyPanel extends JPanel {
        
      public void drawFirstDeal(){
          
      }

     ////////////    PAINT   ////////////////////////////////
      public void paintComponent (Graphics g) {
        //
        if(roundOn==true){
            int xpos = 25, ypos = 25;
    //            
                Card current = round.playerHand.getFirstCard();
                Image tempimage = current.getCardImage();
                g.drawImage(tempimage,xpos,ypos,this);
                xpos+=80;
                current = current.getNextCard();
                tempimage = current.getCardImage();
                g.drawImage(tempimage,xpos,ypos,this);
                current = current.getNextCard();
                while(current!=null){
                    xpos+=80;
                    tempimage = current.getCardImage();
                    g.drawImage(tempimage,xpos,ypos,this);
                    current = current.getNextCard();
                }
                xpos+=120;

                Card enemyFaceDown = round.dealerHand.getFirstCard();
    
                if(finale!=true){
                    tempimage = Project3.load_picture("images/gbCard52.gif");
                }else{
                    tempimage = enemyFaceDown.getCardImage();
                }
                
                g.drawImage(tempimage,xpos,ypos,this);
                
                xpos+=80;
                current = enemyFaceDown.getNextCard();
                tempimage = current.getCardImage();
                g.drawImage(tempimage,xpos,ypos,this);
                while(current!=null){
                    tempimage = current.getCardImage();
                    g.drawImage(tempimage,xpos,ypos,this);
                    xpos+=80;
                    current = current.getNextCard();
                }
                
                round.updateScores();
                if(round.playerScore==21){
                    hitButton.setVisible(false);
                    standButton.setVisible(false);
                }
                
                ////////////////////////////
            
        } else{
            //initial image at start of program
            int xpos = 5, ypos = 25;
    //        Image prebanner = Project3.load_picture("images/banner.jpg");
            final BufferedImage banner = new ImageResizer().scaleImage(790,600,"images/banner.jpg");

    //        prebanner = prebanner.getScaledInstance(200, 100, Image.SCALE_DEFAULT);

    //        Image tempimage = current.getCardImage();
            g.drawImage(banner, xpos, ypos, this);
        }
            
      }
    }
    

}    