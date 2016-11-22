package eg.edu.guc.atomix.engine;

public class Bond {

	private String[] dirNames = new String[8];
	private int[] bondDir = new int[8];
	int north;
	int south;
	int east;
	int west;
	int northeast;
	int northwest;
	int southeast;
	int southwest;

	public Bond(String s) {
		int i = 1;
		int j = 0;
		while (i < s.length()) {
			String c = "" + s.charAt(i);

			bondDir[j] = Integer.parseInt(c);
			i = i + 2;
			j++;
		}

		i = 0;
		while (i < bondDir.length) {
			if (bondDir[i] == 1)
				dirNames[i] = direction(i);
			i++;
		}
		fill();

	}

	public void fill() {
		for (int i = 0; i < bondDir.length; i++) {
			switch (i) {
			// North
			case 0:
				north = bondDir[i];

				break;
			// NorthEast
			case 1:

				northeast = bondDir[i];
				break;

			// East
			case 2:
				east = bondDir[i];

				break;

			// SouthEast

			case 3:

				southeast = bondDir[i];

				break;

			// South

			case 4:

				south = bondDir[i];

				break;

			// SouthWest

			case 5:

				southwest = bondDir[i];
				// West
			case 6:

				west = bondDir[i];

				break;
			// NorthWest

			case 7:

				northwest = bondDir[i];

			default:
				break;

			}

		}

	}

	public String getType(int x) {
		if (x == 1) {
			return "single";
		}
		if (x == 2) {
			return "double";
		}
		if (x == 3) {
			return "triple";
		}
		return null;
	}

	public Bond() {

	}

	public int[] bondArray() {
		return bondDir;
	}

	public String[] directions() {
		return dirNames;
	}

	public String direction(int x) {

		switch (x) {
		case 0:
			return "north";
		case 1:
			return "northeast";
		case 2:
			return "east";
		case 3:
			return "southeast";
		case 4:
			return "south";
		case 5:
			return "southwest";
		case 6:
			return "west";
		case 7:
			return "northwest";
		default:
			return null;
		}

	}


	
	public void printBondArray() {
		for (int i = 0; i < bondDir.length; i++) {
			System.out.print(bondDir[i]);
		}

	}
}
