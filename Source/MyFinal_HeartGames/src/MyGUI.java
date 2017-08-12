import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import View.CardLayoutMenu;

public class MyGUI extends JFrame{
	
	/*private int mode = -1;
	private JRadioButton jbt1 = new JRadioButton("Init as host");
	private JRadioButton jbt2 = new JRadioButton("Connect to host");
	private JRadioButton jbt3 = new JRadioButton("Play with bot");
	
	public Integer getMode()
	{
		return this.mode;
	}
	
	public MyGUI()
	{
		JPanel jp = new JPanel(new GridLayout(3, 1));
		ButtonGroup bg = new ButtonGroup();
		bg.add(jbt1);
		bg.add(jbt2);
		bg.add(jbt3);
		
		jp.add(jbt1);
		jp.add(jbt2);
		jp.add(jbt3);
		
		int res = JOptionPane.showConfirmDialog(null, jp, "Choose mode", JOptionPane.OK_CANCEL_OPTION);
		if (res == JOptionPane.OK_OPTION)
		{
			if (jbt1.isSelected())
			{
				mode = 0;
			}
			
			if (jbt2.isSelected())
			{
				mode = 1;
			}
			
			if (jbt3.isSelected())
			{
				mode = 2;
			}
		}
	}
*/	
	
	private int mode = -1;
	private JRadioButton jbt1 = new JRadioButton("Init as host");
	private JRadioButton jbt2 = new JRadioButton("Connect to host");
	private JRadioButton jbt3 = new JRadioButton("Play with bot");
	private CardLayoutMenu menu;
	private String name = "";
	private int port = -1;
	private String address = "";
	private JFrame frame;
	private int point = 0;
	
	public Integer getMode()
	{
		return this.mode;
	}
	
	public CardLayoutMenu getMenu()
	{
		return this.menu;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Integer getPort()
	{
		return port;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public int getPoint()
	{
		return this.point;
	}
	
	public MyGUI()
	{
		 
		try 
	    { 
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
	    } 
	    catch(Exception e){ 
	    }
		frame = new JFrame("Hearts");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500, 200);
        ImageIcon imgMain = new ImageIcon("image/icons8-Hearts-16.png");
        frame.setIconImage(imgMain.getImage());
        
        frame.setSize(280, 380);
        //Create and set up the content pane.
        menu = new CardLayoutMenu();
        menu.addComponentToPane(frame.getContentPane());
//        while ((mode = menu.getMode()) == -1)
//        {
        	
//        }
       
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
	public void getInfo()
	{
		 mode = menu.getMode();
         name = menu.getName();
         port = menu.getPort();
         address = menu.getIP();
         point = menu.getPoint();
	}
	
	public void setVisible(boolean flag)
	{
		frame.setVisible(flag);
		frame.setEnabled(false);
	}
}
