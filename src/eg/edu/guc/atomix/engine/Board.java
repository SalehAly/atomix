package eg.edu.guc.atomix.engine;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import eg.edu.guc.atomix.engine.Atom;
import eg.edu.guc.atomix.ui.AtomButton;
import eg.edu.guc.atomix.ui.BarrierButton;
import eg.edu.guc.atomix.ui.ImagePanel;
import eg.edu.guc.atomix.ui.Main;
import eg.edu.guc.atomix.ui.directionButton;

public class Board implements BoardInterface {
	private Object[][] board;

	private ArrayList<Atom> atomsList;
	private FileInputStream[] levelFiles = new FileInputStream[10];
	private String levelName;
	int boardLength;
	int boardWidth;
	int moves = 0;

	public int BoardLength() {
		return boardLength;
	}

	public int BoardWidth() {
		return boardWidth;
	}

	public Board() {
		try {

			for (int i = 0; i < levelFiles.length; i++){
				levelFiles[i] = new FileInputStream("Levels/level_"+(i+1)+".txt");

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in intializing Files");
		}

		// FileInputStream x = chooseLevel();
		// makeBoard(new InputStreamReader(x));

	}

	public FileInputStream getLevel(int i) {

		return levelFiles[i - 1];
	}

	public FileInputStream chooseLevel() {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		String s = "";
		int level = 0;
		System.out.println("Please Enter Level");
		try {
			s = bf.readLine();

			level = Integer.parseInt(s);

		} catch (Exception e) {
			System.out.println("Error");
		}

		return levelFiles[level - 1];
	}

	public Board makeBoard(InputStreamReader stream) {

		ArrayList<String> store = new ArrayList<String>();

		BufferedReader br1 = new BufferedReader(stream);

		String s = "";

		try {
			s = br1.readLine();

			levelName = s;

			s = br1.readLine();

			int atomsNr = Integer.parseInt(s);
			atomsList = new ArrayList<Atom>(atomsNr);

			int i = 0;
			while (i < atomsNr) {
				s = br1.readLine();

				Atom x = new Atom("" + s.charAt(0), new Bond(s.substring(1)),
						new Position());

				atomsList.add(x);
				i++;
			}

			int length = 1;

			s = br1.readLine();

			int width = s.length();
			while (s != null) {
				store.add(s);
				length++;
				s = br1.readLine();

			}

			board = new Object[length - 1][width];
			boardLength = length - 1;
			boardWidth = width;
			i = 0;

			while (i < store.size()) {
				s = store.get(i);

				int j = 0;
				while (j < s.length()) {

					if (s.charAt(j) == '#') {

						board[i][j] = new Integer(1);

					} else {
						if (s.charAt(j) == '.') {
							board[i][j] = null;
						} else {

							String k = "" + s.charAt(j);
							try {
								int x = Integer.parseInt(k) - 1;
								atomsList.get(x).setAtomsPosition(
										new Position(i, j));
								board[i][j] = atomsList.get(x);
							} catch (Exception e) {

								int number = s.charAt(j);
								int index = number - 55 - 1;
								atomsList.get(index).setAtomsPosition(
										new Position(i, j));
								board[i][j] = atomsList.get(index);

							}

						}

					}

					j++;

				}

				i++;

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Error100000000000000");
		}
		return this;

	}

	public void removeDirections(JPanel panel, Main main) {
		for (int i = 0; i < panel.getComponentCount(); i++) {

			if (panel.getComponent(i) instanceof directionButton) {

				panel.remove(i);
				JButton label = new JButton();
				label.setLabel("");
				label.setName("");
				label.setLayout(null);

				label.setBorderPainted(false);
				label.setContentAreaFilled(false);

				label.setEnabled(false);
				label.setVisible(true);

				label.addActionListener(main);
				panel.add(label, i);
			}
		}
	}

	public void repeat() {
		if (!gameover()) {

			Atom c = chooseAtom();
			char c2 = chooseDir();
			move(c, c2);
		}
		if (!gameover())
			repeat();
	}

	public Atom chooseAtom() {
		BufferedReader buf = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.println("Please Enter Which Atom do u Want to Move");
		String s = "";
		try {
			s = buf.readLine();
		} catch (Exception e) {
			System.out.println("Error");

		}

		return atomsList.get(Integer.parseInt(s) - 1);

	}

	public char chooseDir() {
		BufferedReader buf = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.println("which dir " + "\n" + "L) Left");
		System.out.println("R) Rigth");
		System.out.println("U) UP");
		System.out.println("D)Down");

		String s = "";
		try {
			s = buf.readLine();

		} catch (Exception e) {

		}
		return s.charAt(0);
	}

	public boolean move(Object k, char s) {

		Atom y = (Atom) k;

		Position p = y.getIP();

		int row = p.getRow();
		int col = p.getColumn();
		int i;
		Position x;
		switch (s) {

		case 'L':

			if (board[row][col - 1] != null) {
				return false;
			} else {
				for (i = col - 1; board[row][i] == null && (i != 0); i--) {

				}

				board[row][i + 1] = y;
				board[row][col] = null;
				x = new Position(row, i + 1);
				y.setAtomsPosition(x);
			}
			break;

		case 'R':
			if (board[row][col + 1] != null) {
				return false;
			} else {

				for (i = col + 1; board[row][i] == null
						&& (i != board[col].length - 1); i++) {

				}
				board[row][i - 1] = y;
				board[row][col] = null;
				x = new Position(row, i - 1);
				y.setAtomsPosition(x);
			}
			break;

		case 'U':
			if (board[row - 1][col] != null) {
				return false;

			} else {

				for (i = row - 1; board[i][col] == null && (i != 0); i--) {

				}
				board[i + 1][col] = y;
				board[row][col] = null;
				x = new Position(i + 1, col);
				y.setAtomsPosition(x);
			}

			break;

		case 'D':
			if (board[row + 1][col] != null) {
				return false;
			} else {

				for (i = row + 1; board[i][col] == null
						&& (i != board.length - 1); i++) {

				}
				board[i - 1][col] = y;
				board[row][col] = null;
				x = new Position(i - 1, col);
				y.setAtomsPosition(x);
			}
			break;

		}
		return true;

	}

	public void printboard() {

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == null) {
					System.out.print("     ");
				} else {
					if (board[i][j] instanceof Atom) {
						Atom x = (Atom) board[i][j];
						System.out.print("  " + (atomsList.indexOf(x) + 1)
								+ "  ");

					} else {

						System.out.print("  " + "#" + "  ");
					}

				}

			}
			System.out.println();
			System.out.println();
		}

	}

	public Collection<?> getAtoms() {
		Atom[] a = new Atom[atomsList.size()];
		for (int i = 0; i < atomsList.size(); i++) {
			a[i] = atomsList.get(i);
		}
		return java.util.Arrays.asList(a);
		// return java.util.Arrays.asList(atomsList);
	}

	public boolean gameover() {
		int i = 0;
		boolean flag = false;

		while (i < atomsList.size()) {
			flag = false;
			Atom x = atomsList.get(i);
			int[] array = x.getBond().bondArray();
			Position p = x.getIP();
			int row = p.getRow();
			int col = p.getColumn();

			int j = 0;

			while (j < array.length) {

				if (array[j] == 0) {

				} else {

					switch (j) {
					// North
					case 0:

						if (board[row - 1][col] instanceof Atom ) {
								Atom k = (Atom)board[row - 1][col];
							if(k.getBond().south== 2 || k.getBond().south== 1 ){
							flag = true;
							}else{
								flag=false;
							}
							} else {

							flag = false;

						}

						break;
					// NorthEast
					case 1:

						if (board[row - 1][col + 1] instanceof Atom) {
							Atom k = (Atom)board[row - 1][col+1];
							if(k.getBond().northwest==1 || k.getBond().northwest==2){
							flag = true;
							}else{
								flag=false;
							}
							} else {

							flag = false;

						}

						break;

					// East
					case 2:
						if (board[row][col + 1] instanceof Atom) {
							Atom k = (Atom)board[row][col+1];
							if(k.getBond().west==1 || k.getBond().west==2){
							flag = true;
							}else{
								flag=false;
							}
							} else {

							flag = false;

						}

						break;

					// SouthEast

					case 3:

						if (board[row + 1][col + 1] instanceof Atom) {
							Atom k = (Atom)board[row +1][col+1];
							if(k.getBond().northwest==1 || k.getBond().northwest==2){
							flag = true;
							}else{
								flag=false;
							}
							} else {

							flag = false;

						}

						break;

					// South

					case 4:

						if (board[row + 1][col] instanceof Atom) {
							Atom k = (Atom)board[row + 1][col];
							if(k.getBond().north==1 || k.getBond().north==2){
							flag = true;
							}else{
								flag=false;
							}
							} else {

							flag = false;

						}

						break;

					// SouthWest

					case 5:

						if (board[row + 1][col - 1] instanceof Atom) {
							Atom k = (Atom)board[row + 1][col-1];
							if(k.getBond().northeast==1 || k.getBond().northeast==2){
							flag = true;
							}else{
								flag=false;
							}
							} else {

							flag = false;

						}
						break;
					// West
					case 6:

						if (board[row][col - 1] instanceof Atom) {
							Atom k = (Atom)board[row][col - 1 ];
							if(k.getBond().east==1 || k.getBond().east==2){
							flag = true;
							}else{
								flag=false;
							}
							} else {

							flag = false;

						}

						break;
					// NorthWest

					case 7:

						if (board[row - 1][col - 1] instanceof Atom) {
							Atom k = (Atom)board[row - 1][col-1];
							if(k.getBond().southeast==1 || k.getBond().southeast==2){
							flag = true;
							}else{
								flag=false;
							}
							} else {

							flag = false;

						}
						break;

					default:
						break;

					}

				}
				j++;
			}
			if (flag == false)
				return false;

			i++;
		}

		return flag;

	}

	public Object[][] getBoard() {
		return board;
	}

	/*
	 * public static void main(String[] args) throws FileNotFoundException {
	 * Board b = new Board();
	 * 
	 * }
	 */
	public boolean move(Object k, char s, JPanel panel, Main main) {

		AtomButton button = (AtomButton) k;
		Atom y = button.getAtom();

		Position p = y.getIP();

		int row = p.getRow();
		int col = p.getColumn();
		int i;
		Position x;

		switch (s) {

		case 'L':

			if (board[row][col - 1] != null) {
				return false;
			} else {
				for (i = col - 1; board[row][i] == null && (i != 0); i--) {

				}

				board[row][i + 1] = y;
				board[row][col] = null;
				x = new Position(row, i + 1);
				y.setAtomsPosition(x);

				JButton label = new JButton();
				label.setLabel("");
				label.setName("");
				label.setLayout(null);

				label.setBorderPainted(false);
				label.setContentAreaFilled(false);

				label.setEnabled(false);
				label.setVisible(true);

				label.addActionListener(main);

				panel.remove((row * board[0].length) + col);
				panel.add(label, (row * board[0].length) + col);

				AtomButton atom = new AtomButton(y);
				atom.addActionListener(main);

				panel.remove((row * board[0].length) + i + 1);
				panel.add(atom, (row * board[0].length) + i + 1);

			}
			break;

		case 'R':
			if (board[row][col + 1] != null) {
				return false;
			} else {

				for (i = col + 1; board[row][i] == null
						&& (i != board[col].length - 1); i++) {

				}
				board[row][i - 1] = y;
				board[row][col] = null;
				x = new Position(row, i - 1);
				y.setAtomsPosition(x);

				JButton label = new JButton();
				label.setLabel("");
				label.setName("");
				label.setLayout(null);

				label.setBorderPainted(false);
				label.setContentAreaFilled(false);

				label.setEnabled(false);
				label.setVisible(true);

				label.addActionListener(main);

				panel.remove((row * board[0].length) + col);
				panel.add(label, (row * board[0].length) + col);

				panel.remove((row * board[0].length) + i - 1);
				AtomButton atom = new AtomButton(y);
				atom.addActionListener(main);
				panel.add(atom, (row * board[0].length) + i - 1);

			}
			break;

		case 'U':
			if (board[row - 1][col] != null) {
				return false;

			} else {

				for (i = row - 1; board[i][col] == null && (i != 0); i--) {

				}
				board[i + 1][col] = y;
				board[row][col] = null;
				x = new Position(i + 1, col);
				y.setAtomsPosition(x);

				JButton label = new JButton();
				label.setLabel("");
				label.setName("");
				label.setLayout(null);

				label.setBorderPainted(false);
				label.setContentAreaFilled(false);

				label.setEnabled(false);
				label.setVisible(true);
				label.addActionListener(main);

				AtomButton atom = new AtomButton(y);
				atom.addActionListener(main);

				panel.remove(((row) * board[0].length) + col);
				panel.add(label, (row * board[0].length) + col);

				panel.remove(((i + 1) * board[0].length) + col);
				panel.add(atom, ((i + 1) * board[0].length) + col);

			}

			break;

		case 'D':
			if (board[row + 1][col] != null) {
				return false;
			} else {

				for (i = row + 1; board[i][col] == null
						&& (i != board.length - 1); i++) {

				}
				board[i - 1][col] = y;
				board[row][col] = null;
				x = new Position(i - 1, col);
				y.setAtomsPosition(x);

				JButton label = new JButton();
				label.setLabel("");
				label.setName("");
				label.setLayout(null);

				label.setBorderPainted(false);
				label.setContentAreaFilled(false);

				label.setEnabled(false);
				label.setVisible(true);

				label.addActionListener(main);
				panel.remove((row * board[0].length) + col);
				panel.add(label, (row * board[0].length) + col);
				AtomButton atom = new AtomButton(y);
				atom.addActionListener(main);
				panel.remove(((i - 1) * board[0].length) + col);
				panel.add(atom, ((i - 1) * board[0].length) + col);

			}
			break;

		}

		removeDirections(panel, main);
		panel.repaint();
		panel.validate();
		panel.revalidate();
		moves++;
		return true;

	}

	public boolean move(Object k, Position intial, Position fin, JPanel panel,
			Main main) {

		if (k instanceof AtomButton) {
			AtomButton button = (AtomButton) k;
			Atom y = button.getAtom();

			int rowI = intial.getRow();
			int colI = intial.getColumn();

			int rowF = fin.getRow();
			int colF = fin.getColumn();

			Position x;
			board[rowF][colF] = y;
			board[rowI][colI] = null;

			y.setAtomsPosition(fin);

			JButton space = new JButton();
			space.setLabel("");
			space.setName("");
			space.setLayout(null);

			space.setBorderPainted(false);
			space.setContentAreaFilled(false);

			space.setEnabled(false);

			space.setVisible(true);
			space.setEnabled(false);

			space.addActionListener(main);

			panel.remove((rowI * board[0].length) + colI);
			panel.add(space, (rowI * board[0].length) + colI);

			// AtomButton atom = new AtomButton(y);
			// atom.addActionListener(main);
			button.addActionListener(main);
			panel.remove((rowF * board[0].length) + colF);
			panel.add(button, (rowF * board[0].length) + colF);

		} else {
			BarrierButton button = (BarrierButton) k;
			int rowI = intial.getRow();
			int colI = intial.getColumn();

			int rowF = fin.getRow();
			int colF = fin.getColumn();

			Position x;
			board[rowF][colF] = new Integer(1);
			board[rowI][colI] = null;

			button.setIP(fin);

			JButton space = new JButton();
			space.setLabel("");
			space.setName("");
			space.setLayout(null);

			space.setBorderPainted(false);
			space.setContentAreaFilled(false);

			space.setEnabled(false);

			space.setVisible(true);
			space.setEnabled(false);

			space.addActionListener(main);

			panel.remove((rowI * board[0].length) + colI);
			panel.add(space, (rowI * board[0].length) + colI);

			button.addActionListener(main);
			panel.remove((rowF * board[0].length) + colF);
			panel.add(button, (rowF * board[0].length) + colF);

		}

		// removeDirections(panel);
		panel.repaint();
		panel.validate();
		panel.revalidate();
		return true;

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("atom")) {

		}
	}

	public void availableMoves(Object obj, JPanel panel, Main m) {

		removeDirections(panel, m);

		panel.repaint();
		panel.validate();
		panel.invalidate();

		int row = 0;
		int col = 0;
		if (obj instanceof AtomButton) {
			AtomButton a = (AtomButton) obj;
			Atom atom = a.getAtom();

			row = atom.getIP().getRow();
			col = atom.getIP().getColumn();
		} else {
			if (obj instanceof BarrierButton) {

				BarrierButton a = (BarrierButton) obj;
				Position p = a.getIP();
				row = p.getRow();
				col = p.getColumn();

			}
		}

		if (board[row][col + 1] == null) {

			panel.remove((row * board[0].length) + col + 1);
			directionButton k = new directionButton('R', obj);
			panel.add(k, (row * board[0].length) + col + 1);
			k.addActionListener(m);

		}

		if (board[row][col - 1] == null) {
			panel.remove((row * board[0].length) + col - 1);
			directionButton l = new directionButton('L', obj);
			panel.add(l, (row * board[0].length) + col - 1);

			l.addActionListener(m);
		}

		if (board[row + 1][col] == null) {
			panel.remove(((row + 1) * board[0].length) + col);
			directionButton p = new directionButton('D', obj);
			panel.add(p, ((row + 1) * board[0].length) + col);
			p.addActionListener(m);
		}
		if (board[row - 1][col] == null) {
			panel.remove(((row - 1) * board[0].length) + col);
			directionButton f = new directionButton('U', obj);
			panel.add(f, ((row - 1) * board[0].length) + col);
			f.addActionListener(m);
		}
		panel.repaint();
		panel.validate();
		panel.invalidate();
	}

	public void fillPanel(JPanel atomixPanel, Main m) {
		int x = (int) (Math.random() * 6);

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {

				if (board[i][j] == null) {
					JButton space = new JButton();
					space.setLabel("");
					space.setName("");
					space.setLayout(null);

					space.setBorderPainted(false);
					space.setContentAreaFilled(false);

					space.setEnabled(false);
					space.setVisible(true);
					// space.setEnabled(true);

					space.addActionListener(m);
					atomixPanel.add(space);
				} else {
					if (board[i][j] instanceof Atom) {
						AtomButton atomm = new AtomButton((Atom) board[i][j]);
						atomm.addActionListener(m);
						atomixPanel.add(atomm);

					} else {
						// JButton wall = new JButton("",new
						// ImageIcon("Game/wall.jpg"));
						// wall.setSize(30,30);
						// wall.setVisible(true);
						JLabel wall = new JLabel();

						// wall.setEnabled(true);
						wall.setVisible(true);
						wall.setIcon(new ImageIcon(
								"Game/wall"
										+ x + ".jpg"));

						atomixPanel.add(wall);
					}

				}
			}
		}
		atomixPanel.repaint();
		atomixPanel.validate();
		atomixPanel.invalidate();
	}

	public FileInputStream getSolved(int level) throws FileNotFoundException {

		return new FileInputStream(
				"SolvedLevels/SolvedLevel_" + level
						+ ".txt");
	}

	public int getMoves() {
		return moves;
	}

	public Board generate(InputStreamReader stream, int moves) {
		makeBoard(stream);
		char[] array = { 'U', 'D', 'R', 'L' };

		int i = 0;
		while (i < moves) {

			int charIndex = (int) (Math.random() * 4);
			int atomIndex = (int) (Math.random() * atomsList.size());

			Atom atom = atomsList.get(atomIndex);
			char c = array[charIndex];

			Position intial = atom.getIP();

			move(atom, c);

			Position p = atom.getIP();

			switch (c) {
			case 'U':
				move(atom, 'D');
				break;

			case 'D':
				move(atom, 'U');
				break;

			case 'R':
				move(atom, 'L');
				break;

			case 'L':
				move(atom, 'R');
				break;

			}

			Position fin = atom.getIP();

			if (!fin.equals(intial)) {

				board[fin.getRow()][fin.getColumn()] = null;
				board[intial.getRow()][intial.getColumn()] = atom;
				atom.setAtomsPosition(intial);

			} else {

				board[intial.getRow()][intial.getColumn()] = null;
				board[p.getRow()][p.getColumn()] = atom;
				atom.setAtomsPosition(p);
			}

			i++;

		}
		return this;

	}

	public void enableBarriers(ImagePanel panel, Boolean flag) {

		for (int i = 0; i < panel.getComponentCount(); i++) {

			if (panel.getComponent(i) instanceof JButton
					&& panel.getComponent(i).getName() != null) {
				panel.getComponent(i).setEnabled(flag);

			}
		}

	}

	public boolean move2(Object k, char s, ImagePanel panel, Main main) {

		BarrierButton y = (BarrierButton) k;

		Position p = y.getIP();

		int row = p.getRow();
		int col = p.getColumn();
		int i;
		Position x;
		JButton space = new JButton();
		space.setLabel("");
		space.setName("");
		space.setLayout(null);

		space.setBorderPainted(false);
		space.setContentAreaFilled(false);

		space.setEnabled(false);
		space.setVisible(false);

		switch (s) {

		case 'L':

			if (board[row][col - 1] != null) {
				return false;
			}
			board[row][col - 1] = new Integer(1);
			board[row][col] = null;
			x = new Position(row, col - 1);

			y.setIP(x);

			panel.remove((row * board[0].length) + col);
			panel.add(space, (row * board[0].length) + col);

			panel.remove(row * board[0].length + col - 1);
			panel.add(y, (row * board[0].length) + col - 1);
			y.addActionListener(main);

			break;

		case 'R':

			if (board[row][col + 1] != null) {
				return false;
			}

			board[row][col + 1] = new Integer(1);
			board[row][col] = null;
			x = new Position(row, col + 1);
			y.setIP(x);

			panel.remove((row * board[0].length) + col);
			panel.add(space, (row * board[0].length) + col);

			panel.remove((row * board[0].length) + col + 1);
			panel.add(y, (row * board[0].length) + col + 1);
			y.addActionListener(main);

			break;

		case 'U':
			if (board[row - 1][col] != null) {
				return false;

			}
			board[row - 1][col] = new Integer(1);
			board[row][col] = null;
			x = new Position(row - 1, col);
			y.setIP(x);

			panel.remove((row * board[0].length) + col);
			panel.add(space, (row * board[0].length) + col);

			panel.remove(((row - 1) * board[0].length) + col);
			panel.add(y, ((row - 1) * board[0].length) + col);
			y.addActionListener(main);
			break;

		case 'D':
			if (board[row + 1][col] != null) {
				return false;
			}

			board[row + 1][col] = new Integer(1);
			board[row][col] = null;
			x = new Position(row + 1, col);
			y.setIP(x);

			panel.remove((row * board[0].length) + col);
			panel.add(space, (row * board[0].length) + col);

			panel.remove(((row + 1) * board[0].length) + col);
			panel.add(y, ((row + 1) * board[0].length) + col);
			y.addActionListener(main);

			break;

		}
		moves = moves + 5;
		return true;

	}

	public void setMoves(int x) {
		moves = moves + x;
	}

	public void Barrier(int x, int y) {

		board[x][y] = new Integer(1);

	}

}
