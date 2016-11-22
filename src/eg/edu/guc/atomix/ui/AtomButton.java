package eg.edu.guc.atomix.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import eg.edu.guc.atomix.engine.Atom;

public class AtomButton extends JButton {

	Atom atom;

	public AtomButton(Atom x) {

		atom = x;
		this.setLabel("atom");
		this.setLayout(null);

		this.validate();
		this.repaint();
		this.setVisible(true);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);

	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		BufferedImage buffImg = new BufferedImage(45, 45,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D gbi = buffImg.createGraphics();

		ImageIcon image = new ImageIcon(
				"Game/atoms/"
						+ atom.getName() + ".png");

		gbi.drawImage(image.getImage(), 0, 0, null);

		for (int i = 0; i < atom.getBond().bondArray().length; i++) {

			int[] array = atom.getBond().bondArray();

			int type = array[i];
			ImageIcon image2;

			if (type == 1) {

				image2 = new ImageIcon(
						"Game/bond - Copy/"
								+ i + ".png");
			} else {
				if (type == 2) {

					image2 = new ImageIcon(
							"Game/bond - Copy/2"
									+ i + ".png");
				} else {

					continue;

				}

			}

			gbi.drawImage(image2.getImage(), 0, 0, null);

		}

		g2d.drawImage(buffImg, 0, 0, null);

	}

	public Atom getAtom() {
		return atom;
	}

}
