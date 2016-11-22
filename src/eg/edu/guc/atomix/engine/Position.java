package eg.edu.guc.atomix.engine;

public class Position {
	private int x;
	private int y;

	public Position(int row, int coloumn) {
		x = row;
		y = coloumn;
	}

	public Position() {

	}

	public int getColumn() {
		return y;
	}

	public int getRow() {
		return x;
	}

	public boolean equals(Position p) {
		return (this.getRow() == p.getRow())
				&& (this.getColumn() == p.getColumn());
	}

}
