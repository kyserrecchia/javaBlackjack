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

  private JButton shuffleButton,exitButton, newButton, playButton, newGameButton, hitButton, standButton;
  private JLabel header;
  private CardList theDeck = null;
  private JPanel northPanel;
  private JPanel buttonPanel;
  private MyPanel centerPanel;
  private boolean gameOn = false;
  private static JFrame myFrame = null;

  ////////////              MAIN      ////////////////////////
  public static void main(String[] args) {
     Project3 tpo = new Project3();
  }

  ////////////            CONSTRUCTOR   /////////////////////
  public Project3 ()
  {
        myFrame = this;                 // need a static variable reference to a JFrame object
        setDefaultCloseOperation(myFrame.DISPOSE_ON_CLOSE);
        
        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
//        northPanel.setBackground(Color.white);
        header = new JLabel("Welcome to Blackjack!");
        header.setFont(new Font("Verdana",1,20));
        northPanel.add(header, "North");
        playButton = new JButton("PLAY");
        playButton.addActionListener(this);
        northPanel.add(playButton, "Center");
        
        buttonPanel = new JPanel();
        
        newGameButton = new JButton("NEW GAME");
        newGameButton.addActionListener(this);
        hitButton = new JButton("HIT");
        hitButton.addActionListener(this);
        standButton = new JButton("STAND");
        standButton.addActionListener(this);
        northPanel.add(buttonPanel, "South");
        buttonPanel.add(newGameButton);
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        newGameButton.setVisible(false);
        hitButton.setVisible(false);
        standButton.setVisible(false);

        
        //og buttons
//        shuffleButton = new JButton("Shuffle");
//        northPanel.add(shuffleButton);
//        shuffleButton.addActionListener(this);
//        newButton = new JButton("New Deck");
//        northPanel.add(newButton);
//        newButton.addActionListener(this);
//        exitButton = new JButton("Exit");
//        northPanel.add(exitButton);
//        exitButton.addActionListener(this);
        add("North",northPanel);
        
        centerPanel = new MyPanel();
//        centerPanel = new MyPanel();
        add("Center",centerPanel);

        theDeck = new CardList(52);
        theDeck.shuffle();
        setSize(800,700);
        setLocation(winxpos,winypos);
        
        setVisible(true);
   }


  ////////////   BUTTON CLICKS ///////////////////////////
  public void actionPerformed(ActionEvent e) {

      if (e.getSource()== exitButton) {
        dispose(); System.exit(0);
      }
       if (e.getSource()== shuffleButton) {
        theDeck.shuffle();
        repaint();
      }
       if (e.getSource()== newButton) {
        theDeck = new CardList(52);
        repaint();
      }
       
       if (e.getSource()== playButton) {
        header.setText("Blackjack");
        playButton.setVisible(false);
        newGameButton.setVisible(true);
        hitButton.setVisible(true);
        standButton.setVisible(true);
        gameOn = true;
        repaint();
      }
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
        if(gameOn==true){
            int xpos = 25, ypos = 25;
//            if (theDeck == null) return;
//            Card current = theDeck.getFirstCard();
//            while (current!=null) {
//               Image tempimage = current.getCardImage();
//               g.drawImage(tempimage, xpos, ypos, this);
//               // note: tempimage member variable must be set BEFORE paint is called
//               xpos += 80;
//               if (xpos > 700) {
//                  xpos = 25; ypos += 105;
//               }
//               current = current.getNextCard();
//            }

            
            Card current = theDeck.getFirstCard();
            Image tempimage = current.getCardImage();
            g.drawImage(tempimage,xpos,ypos,this);
            xpos+=80;
            current = current.getNextCard();
            tempimage = current.getCardImage();
            g.drawImage(tempimage,xpos,ypos,this);
            xpos+=120;
            
            Card enemyFaceDown = current.getNextCard();
            Image faceDown = Project3.load_picture("images/gbCard52.gif");
            g.drawImage(faceDown,xpos,ypos,this);
            xpos+=80;
            current = enemyFaceDown.getNextCard();
            tempimage = current.getCardImage();
            g.drawImage(tempimage,xpos,ypos,this);
            
        } else{
            int xpos = 5, ypos = 25;
    //        Image prebanner = Project3.load_picture("images/banner.jpg");
            final BufferedImage banner = new ImageResizer().scaleImage(790,600,"images/banner.jpg");

    //        prebanner = prebanner.getScaledInstance(200, 100, Image.SCALE_DEFAULT);

    //        Image tempimage = current.getCardImage();
            g.drawImage(banner, xpos, ypos, this);
        }
            
      }
    }
    
    
    
//    class MyPanel extends JPanel {
//
//     ////////////    PAINT   ////////////////////////////////
//      public void paintComponent (Graphics g) {
//        //
//        int xpos = 25, ypos = 5;
//        if (theDeck == null) return;
//        Card current = theDeck.getFirstCard();
//        while (current!=null) {
//           Image tempimage = current.getCardImage();
//           g.drawImage(tempimage, xpos, ypos, this);
//           // note: tempimage member variable must be set BEFORE paint is called
//           xpos += 80;
//           if (xpos > 700) {
//              xpos = 25; ypos += 105;
//           }
//           current = current.getNextCard();
//        } //while
//      }
//    }

}    