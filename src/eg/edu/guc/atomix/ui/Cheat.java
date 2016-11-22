package eg.edu.guc.atomix.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class Cheat implements ActionListener {
	JFrame cheat;
	JButton Enter;
	Main main;
	JPasswordField text;

	public Cheat(Main main) {
		this.main = main;
		cheat = new JFrame();

		cheat.setLayout(new FlowLayout());
		cheat.setBounds(600, 225, 200, 100);

		Enter = new JButton("Enter");
		Enter.setVisible(true);
		Enter.addActionListener(this);
		Enter.setBounds(100, 25, 50, 50);

		JLabel label = new JLabel("Enter Cheat");
		cheat.add(label);
		cheat.add(Enter);

		text = new JPasswordField(10);

		text.setBounds(10, 10, 50, 50);
		cheat.add(text);

		cheat.setVisible(true);
		cheat.validate();
		cheat.repaint();
		cheat.invalidate();

		// String s = text.getText();
		// System.out.println(s);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Enter")) {

			if (text.getText().equals("Solve")) {
				cheat.setVisible(false);
				main.solveCheat();

			}

		}

	}
}
