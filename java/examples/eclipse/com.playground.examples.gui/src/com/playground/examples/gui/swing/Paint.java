package com.playground.examples.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Paint {	
	private Color[] colors = {
		Color.blue, Color.cyan, Color.green, Color.yellow,
		Color.orange, Color.red, Color.pink, Color.magenta,
		Color.gray, Color.black, Color.white
	};
	
	private String title = "Java Paint";
	private JFrame frame;
	
	public Paint(String title, Color[] colors) {
		if (title != null) {
			this.title = title;
		}
		
		if (colors != null) {
			this.colors = new Color[colors.length];
			
			for (int i = 0; i < colors.length; i++) {
				this.colors[i] = colors[i];
			}
		}
	}
	
	private void init() {		
		frame = new JFrame(title);
		
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		
		final Drawer drawer = new Drawer();
		content.add(drawer, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(70, 0));
		panel.setMinimumSize(new Dimension(70, 0));
		panel.setMaximumSize(new Dimension(70, 0));
				
		for (int i = 0; i < colors.length; i++) {
			Color color = colors[i];

			BufferedImage image = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			g.setColor(color);
			g.fillRect(0, 0, 16, 16);
			
			JButton button = new JButton(new ImageIcon(image));
			button.setPreferredSize(new Dimension(16, 16));
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					drawer.setColor(color);
				}
			});
			panel.add(button);
		}
		
		JButton button = new JButton("Clear");
		button.setPreferredSize(new Dimension(64, 16));
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawer.clear();
			}
		});
		panel.add(button);
		
		JLabel label = new JLabel("Line Width");
		panel.add(label);
		
		JSlider slider = new JSlider(SwingConstants.VERTICAL, 1, 40, 1);
		slider.setPreferredSize(new Dimension(16, 300));
		slider.setPaintTicks(true);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				float width = ((JSlider)e.getSource()).getValue();
				drawer.setLineWidth(width);
			}	
		});
		panel.add(slider);
		
		content.add(panel, BorderLayout.WEST);
	}
	
	public void open(int width, int height) {
		init();
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		Color[] colors = null;
		
		colors = new Color[14];
		colors[0] = new Color(1f, 0f, 0f, 0.3f);
		colors[1] = new Color(1f, 0f, 0f, 1f);
		colors[2] = new Color(0f, 1f, 0f, 0.3f);
		colors[3] = new Color(0f, 1f, 0f, 1f);
		colors[4] = new Color(0f, 0f, 1f, 0.3f);
		colors[5] = new Color(0f, 0f, 1f, 1f);
		colors[6] = new Color(0f, 0f, 0f, 0.3f);
		colors[7] = new Color(0f, 0f, 0f, 1f);
		colors[8] = new Color(1f, 1f, 0f, 0.3f);
		colors[9] = new Color(1f, 1f, 0f, 1f);
		colors[10] = new Color(1f, 0f, 1f, 0.3f);
		colors[11] = new Color(1f, 0f, 1f, 1f);
		colors[12] = new Color(0f, 1f, 1f, 0.3f);
		colors[13] = new Color(0f, 1f, 1f, 1f);

		Paint paint = new Paint(null, colors);
		paint.open(1000, 1000);
	}
}
