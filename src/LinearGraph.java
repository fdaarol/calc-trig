import javax.swing.SwingUtilities;

import java.awt.event.MouseListener;

import javax.swing.JFrame;


public class LinearGraph{
	private JFrame frame;
	private Double slope;
	private Double yInt;
	public LinearGraph(Double s, Double y) {
		slope = s;
		yInt = y;
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            GUI(); 
	        }
	    });
	    	
	}

    public void GUI() {
		frame = new JFrame("Linear Graph");
		frame.setSize(800, 600);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
        frame.add(new MyPanel(slope, yInt));
        frame.pack();
    }
}



