
package project3;

import java.awt.Image;

class Card extends Link {
  private Image cardimage;

  public Card (int cardnum) {
    cardimage = Project3.load_picture("images/gbCard" + cardnum + ".gif");
    // code ASSUMES there is an images sub-dir in your project folder
    if (cardimage == null) {
      System.out.println("Error - image failed to load: images/gbCard" + cardnum + ".gif");
      System.exit(-1);
    }
  }
  public Card getNextCard() {
    return (Card)next;
  }
  public Image getCardImage() {
    return cardimage;
  }
}  //end class Card
