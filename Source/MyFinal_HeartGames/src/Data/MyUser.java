package Data;
//---------------------- Server ---------------------------//

import java.awt.*;
import java.util.Vector;

import javax.swing.Icon;

public class MyUser {
	private String name;
	private Vector<MyCard> listCard;
	private MyCard firstCard = new MyCard();
	private int point;
	private int number;
	private Icon img;
	private int turn;
	
	
	public void addPoint(int point)
	{
		this.point = this.point + point;
	}
	
	public void setNumber(int number)
	{
		this.number = number;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setPoint(int point)
	{
		this.point = point;
	}
	
	public void setIcon(Icon img)
	{
		this.img = img;
	}
	
	public void setCard(MyCard mc, int pos)
	{
		listCard.set(pos, mc);
	}
	
	public void setListCard(Vector<MyCard> listCard)
	{
		this.listCard = listCard;
	}
	
	public void setTurn(int turn)
	{
		this.turn = turn;
	}
	
	public void setFirstCard(MyCard mc)
	{
		firstCard.setCard(mc.getCard());
		firstCard.setSuit(mc.getSuit());
	}
	
	public void setFirstCard(int card, int suit)
	{
		this.firstCard.setCard(card);
		this.firstCard.setSuit(suit);
	}
	
	public String getName()
	{
		return name;
	}
	
	public int 	getPoint()
	{
		return point;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public Icon getIcon()
	{
		return img;
	}
	
	public MyCard getCard(int pos)
	{
		return listCard.get(pos);
	}
	
	public Vector<MyCard> getListCard()
	{
		return listCard;
	}
	
	public Integer getTurn()
	{
		return this.turn;
	}
	
	public MyCard getFirstCard()
	{
		return this.firstCard;
	}
	
	public void sortDeckCard()
	{
		for (int i = 0; i < listCard.size(); i++)
		{
			for (int j = i + 1; j < listCard.size(); j++)
			{
				if (listCard.get(j).getSuit() < listCard.get(i).getSuit())
				{
					MyCard temp = listCard.get(i);
					listCard.set(i, listCard.get(j));
					listCard.set(j, temp);
				}
			}
		}
		

		int count = 0;
		
		for (int i = 0; i < listCard.size(); i++)
		{
			if ((listCard.get(i).getSuit() != listCard.get(count).getSuit()) || (i == (listCard.size() - 1)))
			{
				if (i == (listCard.size() - 1))
				{
					if (listCard.get(i).getSuit() == listCard.get(count).getSuit())
					{
						for (int m = count; m <= i; m++)
						{
							for (int n = m + 1; n <= i; n++)
							{
								if (listCard.get(m).getCard() > listCard.get(n).getCard())
								{
									MyCard temp = listCard.get(m);
									listCard.set(m, listCard.get(n));
									listCard.set(n, temp);
								}
							}
						}
					}
					else
					{
						for (int m = count; m < i; m++)
						{
							for (int n = m + 1; n < i; n++)
							{
								if (listCard.get(m).getCard() > listCard.get(n).getCard())
								{
									MyCard temp = listCard.get(m);
									listCard.set(m, listCard.get(n));
									listCard.set(n, temp);
								}
							}
						}
					}
				}
				else
				{
					for (int m = count; m < i; m++)
					{
						for (int n = m + 1; n < i; n++)
						{
							if (listCard.get(m).getCard() > listCard.get(n).getCard())
							{
								MyCard temp = listCard.get(m);
								listCard.set(m, listCard.get(n));
								listCard.set(n, temp);
							}
						}
					}
				}
				count = i;
			}
			
		}

	}
}
