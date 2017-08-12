package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class View {
	private JFrame mainFrame;
	private Image imageBack;
	// Model
	private AnimatedCard animatedCard;
	public View()
	{
		mainFrame = new JFrame("Hearts");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBackground(Color.getHSBColor((float) 0.3, (float) 0.5, 1));
		
		ImageIcon img = new ImageIcon("image/icons8-Cards-128.png");
		mainFrame.setIconImage(img.getImage());
		mainFrame.setPreferredSize(new Dimension(1000,700 ));
		animatedCard = new AnimatedCard(mainFrame);
	}
	public AnimatedCard getAnimatedCard() {
		return animatedCard;
	}
	public void setAnimatedCard(AnimatedCard animatedCard) {
		this.animatedCard = animatedCard;
	}
	
	public JFrame getMainFrame()
	{
		return this.mainFrame;
	}
}
