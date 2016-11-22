package eg.edu.guc.atomix.engine;

import java.io.InputStreamReader;
import java.util.Collection;

/* THE CLASS IMPLEMENTING THIS INTERFACE MUST IMPLEMENT   
 * AN EMPTY CONSTRUCTOR
 */

public interface BoardInterface {

	public Board makeBoard(InputStreamReader stream);

	public boolean move(Object atom, char direction);

	public boolean gameover();

	public Collection<?> getAtoms();
}
