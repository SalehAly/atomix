package eg.edu.guc.atomix.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class directionButton extends JButton {

	char direction;
	Object obj;

	public directionButton(char c, Object k) {
		obj = k;
		direction = c;
		this.setLabel("move");
		this.setLayout(null);
		this.setBackground(Color.gray);
		this.setVisible(true);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);

	}

	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		BufferedImage buffImg = new BufferedImage(45, 45,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D gbi = buffImg.createGraphics();

		ImageIcon image = new ImageIcon(
				"Game/" + direction + ".png");

		gbi.drawImage(image.getImage(), 0, 0, null);

		g2d.drawImage(buffImg, 0, 0, null);

	}

	public Object getObj() {
		return obj;
	}

	public char getDir() {
		return direction;
	}

}