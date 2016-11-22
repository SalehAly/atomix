package eg.edu.guc.atomix.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.*;

import eg.edu.guc.atomix.engine.*;

import javax.swing.*;

public class UserInterface implements ActionListener {

	JFrame window;
	ImagePanel panel;
	FileWriter file = new FileWriter(
			"Game/hof.txt");

	public UserInterface() throws IOException {

		Window();

	}

	public void Window() throws IOException {

		panel = new ImagePanel("Game/Pensive Parakeet.jpg");
		panel.setBounds(0, 0, 1000, 650);
		panel.setVisible(true);

		window = new JFrame("Atomix");

		window.setBounds(150, 50, 1000, 650);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // To Make The
		// Program
		// Terminate

		ImageIcon im = new ImageIcon(
				"Game/bg.jpg");

		window.setIconImage(im.getImage());
		window.setLayout(null);

		JButton start = new JButton("Start a New Game ");
		JButton load = new JButton("About Atomix");
		JButton exit = new JButton("Exit");

		start.setBounds(20, 100, 250, 50);
		load.setBounds(20, 180, 200, 50);
		exit.setBounds(20, 260, 150, 50);

		start.addActionListener(this);
		load.addActionListener(this);
		exit.addActionListener(this);

		start.setFont(new Font("Monotype Corsiva", Font.CENTER_BASELINE, 20));
		start.setForeground(Color.white);
		load.setFont(new Font("Monotype Corsiva", Font.CENTER_BASELINE, 20));
		load.setForeground(Color.white);
		exit.setFont(new Font("Monotype Corsiva", Font.CENTER_BASELINE, 20));
		exit.setForeground(Color.white);

		start.setBorderPainted(false);
		start.setContentAreaFilled(false);

		load.setBorderPainted(false);
		load.setContentAreaFilled(false);

		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);

		start.setVisible(true);
		load.setVisible(true);
		exit.setVisible(true);

		panel.add(start);
		panel.add(load);
		panel.add(exit);

		window.add(panel);

		window.setResizable(false);

		window.repaint();
		window.validate();
		window.invalidate();

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Start a New Game ")) {

			try {
				Main main = new Main(window, 1, file);
			} catch (IOException e1) {

				e1.printStackTrace();
			}

		} else {
			if (e.getActionCommand().equals("About Atomix")) {
				final String[] cmd = new String[4];
				cmd[0] = "cmd.exe";
				cmd[1] = "/C";
				cmd[2] = "start";
				cmd[3] = "http://en.wikipedia.org/wiki/Atomix_(computer_game)";
				try {
					final Process p = Runtime.getRuntime().exec(cmd);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				if (e.getActionCommand().equals("Exit")) {

					System.exit(0);
				}

			}
		}
	}

	public static void main(String[] args) throws IOException {

		UserInterface ui = new UserInterface();

	}
}
