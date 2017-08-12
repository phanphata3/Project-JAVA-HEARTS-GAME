package Data;
//---------------------- Server ---------------------------//


import java.util.Random;
import java.util.Vector;

import javax.swing.plaf.SliderUI;

public class MyRollCard {
	private Vector<MyCard> listCard;
	
	
	public MyRollCard() throws InterruptedException
	{
		listCard = new Vector<>();
		
		for (int i = 0; i < 13; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				MyCard mc = new MyCard();
				mc.setCard(i);
				mc.setSuit(j);
				
				listCard.add(mc);
			}
		}
		
		Random rand = new Random();
		int pos;
		
		for (int i = 0; i < 52; i++)
		{
			pos = rand.nextInt(52);
			MyCard temp = listCard.get(pos);
			listCard.set(pos, listCard.get(i));
			listCard.set(i, temp);
			Thread.sleep(20);
		}
		
	}
	
	public Vector<MyCard> getCard()
	{
		return listCard;
	}
	
	
}
