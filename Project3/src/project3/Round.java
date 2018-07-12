
package project3;

public class Round {
    public int playerScore;
    public int dealerScore;
    public CardList theDeck;
    public CardList playerHand;
    public CardList dealerHand;
    private boolean finalTurn;
    
    public Round() {
        finalTurn = false;
        playerScore = 0;
        dealerScore = 0;
        theDeck = new CardList(52);
        theDeck.shuffle();
        playerHand = new CardList(0);
        dealerHand = new CardList(0);
        playerHand.insertCard(theDeck.drawCard());
        playerHand.insertCard(theDeck.drawCard());
        dealerHand.insertCard(theDeck.drawCard());
        dealerHand.insertCard(theDeck.drawCard());
    }
    
    public void updateScores(){
        Card pTemp = playerHand.getFirstCard();
        playerScore = pTemp.rank;
        Card dTemp = dealerHand.getFirstCard();
        dealerScore = dTemp.rank;
        while(pTemp.getNextCard()!=null){
            pTemp = pTemp.getNextCard();
            playerScore += pTemp.rank;   
        }
        while(dTemp.getNextCard()!=null){
            dTemp = dTemp.getNextCard();
            dealerScore += dTemp.rank;
        }
    }
    
    
    
}
