package Data;

import java.awt.*;

//---------------------- Client ---------------------------//



public class MyCard {
	private int deckCard;
	private int deckSuit;
	public Image image;
	public int x = 480;
	public int y = 200;
	public int moveX;
	public int moveY;
	public int number;
	public boolean status = false;
	public int side;
	public MyCard() {
		
	}
     public MyCard(Image image) {
    	 this.image = image;
     }

     public void drawCard(Graphics g) {
    	 if(side == 0 | side == 2)
         g.drawImage(image, x, y, 90, 140, null);
    	 else {
    		 g.drawImage(image, x, y, 140, 90, null);
    	 }
         
     }
	public void setCard(int deckCard)
	{
		this.deckCard = deckCard;
	}
	
	public void setSuit(int deckSuit)
	{
		this.deckSuit = deckSuit;
	}
	
	public int getCard()
	{
		return deckCard;
	}
	
	public int getSuit()
	{
		return deckSuit;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}

	
}
