package com.playground.examples.gui.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Drawer extends JComponent {
	private Image image;

	private Graphics2D graphics2D;

	private Stroke stroke = 
			new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL);
	private int currentX, currentY, oldX, oldY, currentWidth, currentHeight;
	
	public Drawer(){
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				oldX = e.getX();
				oldY = e.getY();
			}
		});

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(graphics2D != null) {
					Color oldColor = graphics2D.getColor();
					graphics2D.setPaint(Color.white);
					graphics2D.drawLine(e.getX(), e.getY(), e.getX()+1, e.getY()+1);
					graphics2D.setPaint(oldColor);
					graphics2D.drawLine(e.getX(), e.getY(), e.getX()+1, e.getY()+1);
				}
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}				
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				currentX = e.getX();
				currentY = e.getY();
				
				if (currentX > getSize().width || currentY > getSize().height) {
					return;
				}
				
				if(graphics2D != null) {
					Color oldColor = graphics2D.getColor();
					graphics2D.setPaint(Color.white);
					graphics2D.drawLine(oldX, oldY, currentX, currentY);
					graphics2D.setPaint(oldColor);
					graphics2D.drawLine(oldX, oldY, currentX, currentY);
				}
				repaint();
				oldX = currentX;
				oldY = currentY;
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		if(image == null) {
			image = createImage(getSize().width, getSize().height);
			currentWidth = getSize().width;
			currentHeight = getSize().height;
			graphics2D = (Graphics2D)image.getGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics2D.setStroke(stroke);
			clear();
			graphics2D.setPaint(Color.black);
		}
		else if (getSize().width > currentWidth || getSize().height > currentHeight) {
			currentWidth = getSize().width;
			currentHeight = getSize().height;
			BufferedImage newImg = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_ARGB);
			Color oldColor = graphics2D.getColor();
			graphics2D = newImg.createGraphics();
			graphics2D.setStroke(stroke);
			clear();
			graphics2D.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
			repaint();
			graphics2D.setPaint(oldColor);
			image = newImg;
		}
		
		g.drawImage(image, 0, 0, null);
	}

	public void clear() {
		if (graphics2D != null) {
			Color oldColor = graphics2D.getColor();
			graphics2D.setPaint(Color.white);
			graphics2D.fillRect(0, 0, getSize().width, getSize().height);
			graphics2D.setPaint(oldColor);;
			repaint();
		}
	}
	
	public void setColor(Color color) {
		graphics2D.setPaint(color);
		repaint();
	}
	
	public void setLineWidth(float width) {
		stroke = new BasicStroke(width,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
		graphics2D.setStroke(stroke);
	}
}
