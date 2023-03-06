import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;

public class MyPanel extends JPanel{
	private Double slope;
	private Double yInt;
	
    public MyPanel(Double s, Double y) {
    	slope = s;
    	yInt = y;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(800,600);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);       
    	Graphics2D g2 = (Graphics2D) g;

    	BasicStroke ax = new BasicStroke(3);
    	BasicStroke grids = new BasicStroke(1);
    	
    	BasicStroke line = new BasicStroke(2);
    	Font slopeEq = new Font("Calibri", Font.ITALIC, 30);
        
        // grids
        g2.setStroke(grids); 
        g2.setColor(Color.LIGHT_GRAY);
        for(int i = 0; i <= 832; i += 10) {
            g2.draw((Shape) new Line2D.Double(i, 0, i, 632));
        }
        
        for(int i = 0; i <= 632; i += 10) {
            g2.draw((Shape) new Line2D.Double(0, i, 832, i));
        }
        
        // x and y axes 
        g2.setColor(Color.black);
        g2.setStroke(ax);
        g2.draw((Shape) new Line2D.Double(0, 300, 832, 300));
        g2.draw((Shape) new Line2D.Double(400, 0, 400, 632));
        
        // grid numbers
        
        for(int i = 400; i <= 832; i += 50) {
        	g.drawString(Integer.toString(i-400), i-5,320);
        }
        
        for(int i = 0; i < 400; i += 50) {
        	g.drawString(Integer.toString(i-400), i-10,320);
        }
        //----
        for(int j = 300; j <= 600; j += 50) {
        	int num = 0;
        	if(j != 300)
        		g.drawString(Integer.toString(num+300-j), 405,j+5);
        }
        
        for(int j = 0; j < 300; j += 50) {
        	int num = 0;
        	if(j != 300)
        		g.drawString(Integer.toString(num+300-j), 405,j+5);
        }
         
        // Draw Graph (Line)
        g2.setColor(Color.blue);
        g2.setStroke(line);
        for(int i = 0; i < 830; i++) {
        	g2.draw((Shape) new Line2D.Double(400, 300-yInt, 400+i, 300-yInt-(slope)*i));
        	g2.draw((Shape) new Line2D.Double(400, 300-yInt, 400-i, 300-yInt+(slope)*i));
        }
        
        // Slope Equation
        g2.setColor(Color.RED);
        g2.setFont(slopeEq);
        String temp = "y = " + slope + "x" + " + " + yInt;
        g.drawString(temp, 10, 25);
    } 
}
