package View;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AnimatedCard {

    //public static void main(String[] args) {
        
   // }
	private AnimationPane animationPanel = new AnimationPane();
    public AnimatedCard(JFrame frame) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                    try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
               // frame.setLayout(new BorderLayout())
                animationPanel.setBackground(Color.getHSBColor((float) 0.3, (float) 0.5, 1));    
                frame.add(animationPanel);
                
                
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
            }

        });
    }
	public AnimationPane getAnimationPanel() {
		return animationPanel;
	}
	public void setAnimationPanel(AnimationPane animationPanel) {
		this.animationPanel = animationPanel;
	}
}