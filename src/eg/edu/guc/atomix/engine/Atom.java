package eg.edu.guc.atomix.engine;

import eg.edu.guc.atomix.engine.Position;

public class Atom {

	private String name;
	private Position initialPosition;
	private Bond bond;

	public Atom(String name, Bond b, Position p) {

		initialPosition = p;
		this.name = name;
		bond = b;
	}

	public Atom() {

	}

	public String getName() {
		return name;
	}

	public Bond getBond() {
		return bond;
	}

	public void setAtomsPosition(Position x) {
		initialPosition = x;
	}

	public Position getIP() {
		return initialPosition;
	}

}
