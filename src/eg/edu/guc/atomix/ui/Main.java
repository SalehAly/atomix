package eg.edu.guc.atomix.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.Timer;

import eg.edu.guc.atomix.engine.Atom;
import eg.edu.guc.atomix.engine.Board;
import eg.edu.guc.atomix.engine.Position;

public class Main implements ActionListener, ItemListener {

	FileWriter file;
	ArrayList<Position> undoList = new ArrayList<Position>();
	ArrayList<Position> redoList = new ArrayList<Position>();
	ArrayList<Object> undoAtomList = new ArrayList<Object>();
	ArrayList<Object> redoAtomList = new ArrayList<Object>();
	int time = 0;
	int Level;
	JFrame window;
	AtomButton mainAtom;
	JButton Solve;
	JComboBox ChangeLevel;
	Board x;
	ImagePanel atomixPanel;
	ImagePanel gamePanel;
	Object[][] board;
	JButton undo;
	JButton redo;
	JButton generate;
	JButton Barrier;
	BarrierButton k;
	JLabel label;
	JLabel nrmoves;
	Timer timer;
	BufferedWriter out;

	public Main(JFrame window, int Level, FileWriter file) throws IOException {
		out = new BufferedWriter(file);

		this.file = file;
		this.Level = Level;
		this.window = window;

		x = new Board();
		x.makeBoard(new InputStreamReader(x.getLevel(Level)));
		board = x.getBoard();

		gamePanel = new ImagePanel("Game/bg.jpg");
		gamePanel.setLayout(null);
		gamePanel.setBackground(Color.WHITE);
		gamePanel.setSize(1000, 650);
		window.setContentPane(gamePanel);

		atomixPanel = new ImagePanel("Game/bg.jpg");
		atomixPanel.setBounds(0, 0, (45 * x.BoardWidth()), (45 * x
				.BoardLength()));
		atomixPanel.setBackground(Color.WHITE);
		GridLayout g = new GridLayout(x.BoardLength(), x.BoardWidth());
		atomixPanel.setLayout(g);

		x.fillPanel(atomixPanel, this);

		gamePanel.add(atomixPanel);

		ImagePanel image = new ImagePanel(
				"Game/solutions/Level_"
						+ Level + ".jpg");
		image.setLayout(null);
		image.setBounds(620, 75, 250, 200);
		image.setBackground(Color.BLACK);
		gamePanel.add(image);

		JButton cheat = new JButton("Cheat");

		cheat.addActionListener(this);
		cheat.setBounds(620, 330, 75, 25);
		cheat.setVisible(true);
		gamePanel.add(cheat);
		String[] Names = new String[11];
		Names[0] = "Change Level";
		for (int i = 1; i < 11; i++) {

			Names[i] = "Level" + i;
		}

		ChangeLevel = new JComboBox(Names);
		// ChangeLevel.addActionListener(this);
		ChangeLevel.setBounds(620, 360, 125, 25);
		ChangeLevel.setVisible(false);
		ChangeLevel.addItemListener(this);
		gamePanel.add(ChangeLevel);

		Solve = new JButton("Solve");
		Solve.addActionListener(this);
		Solve.setBounds(620, 390, 75, 25);
		Solve.setVisible(false);
		gamePanel.add(Solve);

		undo = new JButton("Undo");
		undo.addActionListener(this);
		undo.setBounds(750, 360, 75, 25);
		undo.setVisible(true);
		gamePanel.add(undo);
		undo.setEnabled(false);

		redo = new JButton("Redo");
		redo.addActionListener(this);
		redo.setBounds(750, 390, 75, 25);
		redo.setVisible(true);
		gamePanel.add(redo);
		redo.setEnabled(false);

		generate = new JButton("Generate");
		generate.addActionListener(this);
		generate.setBounds(750, 420, 100, 25);
		generate.setVisible(true);
		gamePanel.add(generate);
		generate.setEnabled(true);

		Barrier = new JButton("Barrier");
		Barrier.addActionListener(this);
		Barrier.setBounds(750, 450, 75, 25);
		Barrier.setVisible(true);
		gamePanel.add(Barrier);

		Barrier.setEnabled(true);

		timer = new Timer(1000, this);
		label = new JLabel("0:0:0");
		label.setBounds(800, 100, 300, 300);
		label.setVisible(true);

		// "Comic Sans MS","Monotype Corsiva","Kristen ITC"

		label.setFont(new Font("French Script MT", Font.BOLD, 25));
		label.setForeground(Color.black);
		gamePanel.add(label);

		nrmoves = new JLabel("0");
		nrmoves.setBounds(900, 50, 100, 100);
		nrmoves.setVisible(true);

		// "Comic Sans MS","Monotype Corsiva","Kristen ITC"

		nrmoves.setFont(new Font("French Script MT", Font.BOLD, 25));
		nrmoves.setForeground(Color.black);
		gamePanel.add(nrmoves);

		window.repaint();
		window.validate();
		window.invalidate();
	}

	public void solveCheat() {
		ChangeLevel.setVisible(true);
		Solve.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof Timer) {
			time++;
			label.setText(time2(time));

		} else {

			if (e.getActionCommand().equals("Cheat")) {

				Cheat c = new Cheat(this);

			} else {
				if (e.getActionCommand().equals("atom")) {
					timer.start();

					mainAtom = (AtomButton) e.getSource();
					Atom a = mainAtom.getAtom();

					x.availableMoves(mainAtom, atomixPanel, this);

					gamePanel.repaint();
					gamePanel.validate();
					gamePanel.invalidate();

				} else {
					if (e.getActionCommand().equals("move")) {

						directionButton button = (directionButton) e
								.getSource();

						char c = button.getDir();

						if (undoList.size() < 1) {
							redoAtomList.clear();
							redoList.clear();
						}

						if (button.getObj() instanceof AtomButton) {
							Atom a = mainAtom.getAtom();

							undoList.add(a.getIP());
							undoAtomList.add(mainAtom);

							x.move(mainAtom, c, atomixPanel, this);

							undoList.add(a.getIP());

							if (undoList.size() > 1) {
								undo.setEnabled(true);
							} else {
								undo.setEnabled(false);
							}

							if (redoList.size() > 1) {
								redo.setEnabled(true);
							} else {
								redo.setEnabled(false);
							}
							x.availableMoves(mainAtom, atomixPanel, this);
							nrmoves.setText(x.getMoves() + "");
							if (x.gameover()) {
								try {
									out.write(x.getMoves() + "/n");
									out.flush();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								GameOver();
							}

						} else {
							BarrierButton b = (BarrierButton) button.getObj();
							Position p = b.getIP();
							undoList.add(p);
							undoAtomList.add(b);

							x.move2(b, c, atomixPanel, this);
							x.availableMoves(b, atomixPanel, this);
							window.repaint();
							window.validate();
							window.invalidate();
							p = b.getIP();

							undoList.add(p);

							if (undoList.size() > 1) {
								undo.setEnabled(true);
							} else {
								undo.setEnabled(false);
							}

							if (redoList.size() > 1) {
								redo.setEnabled(true);
							} else {
								redo.setEnabled(false);
							}
							nrmoves.setText(x.getMoves() + "");

						}
					} else {
						if (e.getActionCommand().equals("Undo")) {

							Undo();
							nrmoves.setText(x.getMoves() + "");

						} else {
							if (e.getActionCommand().equals("Redo")) {
								Redo();
								nrmoves.setText(x.getMoves() + "");
							} else {

								if (e.getActionCommand().equals("Solve")) {
									try {
										Solve();
									} catch (FileNotFoundException e1) {

										e1.printStackTrace();
									}

								} else {
									if (e.getActionCommand().equals("Generate")) {
										try {
											x.generate(new InputStreamReader(x
													.getSolved(Level)), 100);
										} catch (FileNotFoundException e1) {

											e1.printStackTrace();
										}
										atomixPanel.removeAll();
										x.fillPanel(atomixPanel, this);
										undoList.clear();
										redoList.clear();
										undoAtomList.clear();
										redoAtomList.clear();
										undo.setEnabled(false);
										redo.setEnabled(false);
										Barrier.setEnabled(true);
									} else {
										if (e.getActionCommand().equals(
												"Barrier")) {
											x.removeDirections(atomixPanel,
													this);
											window.repaint();
											Barrier.setEnabled(false);
											x.enableBarriers(atomixPanel, true);

										} else {
											if (e.getActionCommand().equals("")) {

												JButton k = (JButton) (e
														.getSource());
												int z = (k.getX() / 45);
												int y = (k.getY() / 45);
												x.Barrier(y, z);
												k = new BarrierButton(y, z);
												k.addActionListener(this);
												atomixPanel.remove((y * x
														.BoardWidth())
														+ z);
												atomixPanel.add(k, (y * x
														.BoardWidth())
														+ z);

												window.repaint();
												window.validate();
												x.enableBarriers(atomixPanel,
														false);

											} else {
												if (e.getActionCommand()
														.equals("Bar")) {
													BarrierButton b = (BarrierButton) e
															.getSource();
													x.availableMoves(b,
															atomixPanel, this);

												}
											}
										}
									}
								}

							}

						}
					}
				}
			}
		}
	}

	public void GameOver() {
		if (Level == 10) {
			Level = 0;
		}
		JFrame f = new JFrame("GameOver");
		f.setBounds(400, 100, 300, 200);
		f.setLayout(null);
		JLabel text = new JLabel("GameOver " + "In " + label.getText());
		text.setBounds(0, 20, 300, 30);
		text.setVisible(true);
		JLabel text2 = new JLabel("Number of moves = " + x.getMoves());
		text2.setBounds(0, 60, 300, 30);
		text2.setVisible(true);

		f.add(text);
		f.add(text2);
		f.setVisible(true);
		f.repaint();
		f.validate();
		f.invalidate();

		try {
			Main main = new Main(window, Level + 1, file);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void Undo() {
		x.removeDirections(atomixPanel, this);

		Position intial = undoList.remove(undoList.size() - 1);
		redoList.add(intial);

		Position fin = undoList.remove(undoList.size() - 1);
		redoList.add(fin);

		Object object = undoAtomList.get(undoAtomList.size() - 1);
		if (object instanceof AtomButton) {

			AtomButton target = (AtomButton) undoAtomList.remove(undoAtomList
					.size() - 1);
			redoAtomList.add(target);
			x.setMoves(-1);
			x.move(target, intial, fin, atomixPanel, this);
		} else {
			BarrierButton target = (BarrierButton) undoAtomList
					.remove(undoAtomList.size() - 1);
			redoAtomList.add(target);
			x.setMoves(-5);
			x.move(target, intial, fin, atomixPanel, this);
		}

		if (undoList.size() > 1) {
			undo.setEnabled(true);
		} else {
			undo.setEnabled(false);
		}

		if (redoList.size() > 1) {
			redo.setEnabled(true);
		} else {
			redo.setEnabled(false);
		}

	}

	public void Redo() {
		x.removeDirections(atomixPanel, this);

		Position intial = redoList.remove(redoList.size() - 1);
		undoList.add(intial);

		Position fin = redoList.remove(redoList.size() - 1);
		undoList.add(fin);

		Object object = redoAtomList.get(redoAtomList.size() - 1);
		if (object instanceof AtomButton) {

			AtomButton target = (AtomButton) redoAtomList.remove(redoAtomList
					.size() - 1);
			undoAtomList.add(target);
			x.setMoves(1);
			x.move(target, intial, fin, atomixPanel, this);
		} else {
			BarrierButton target = (BarrierButton) redoAtomList
					.remove(redoAtomList.size() - 1);
			undoAtomList.add(target);
			x.setMoves(5);
			x.move(target, intial, fin, atomixPanel, this);
		}

		if (redoList.size() > 1) {
			redo.setEnabled(true);
		} else {
			redo.setEnabled(false);
		}

		if (undoList.size() > 1) {
			undo.setEnabled(true);
		} else {
			undo.setEnabled(false);
		}

	}

	public void Solve() throws FileNotFoundException {

		x.makeBoard(new InputStreamReader(x.getSolved(Level)));
		atomixPanel.removeAll();
		x.fillPanel(atomixPanel, this);
	}

	public String time2(int t) {
		int hours = 0;
		int minutes = 0;
		int seconds = 0;

		if (t / 60 > 0) {
			minutes = t / 60;
			seconds = t % 60;
		} else {
			seconds = t % 60;
		}

		return "" + hours + ":" + minutes + ":" + seconds;
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getStateChange() == ItemEvent.SELECTED) {

			try {
				gamePanel.removeAll();
				atomixPanel.removeAll();
				Main m = new Main(window, (ChangeLevel.getSelectedIndex()),
						file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
