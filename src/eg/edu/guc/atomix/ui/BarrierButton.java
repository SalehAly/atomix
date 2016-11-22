package eg.edu.guc.atomix.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import eg.edu.guc.atomix.engine.Position;

public class BarrierButton extends JButton {
	Position p;

	public BarrierButton(int x, int y) {

		p = new Position(x, y);

		this.setLabel("Bar");
		this.setLayout(null);

		this.setBorderPainted(true);
		this.setContentAreaFilled(true);

		this.setEnabled(true);
		this.setVisible(true);
	}

	public Position getIP() {
		return p;
	}

	public void setIP(Position p2) {
		p = p2;
	}

	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		BufferedImage buffImg = new BufferedImage(45, 45,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D gbi = buffImg.createGraphics();

		ImageIcon image = new ImageIcon("C:\\Users\\Aly\\Desktop\\wall1.png");

		gbi.drawImage(image.getImage(), 0, 0, null);

		g2d.drawImage(buffImg, 0, 0, null);

	}
}
