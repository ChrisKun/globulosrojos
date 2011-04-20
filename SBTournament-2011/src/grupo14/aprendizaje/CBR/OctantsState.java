package grupo14.aprendizaje.CBR;

import java.util.ArrayList;

import jcolibri.connector.TypeAdaptor;

/**
 * Describes the state of the match giving the amount of players located in each octant of the
 * field. Each octant is 0,38125 * 1.37
 * @author markel
 *
 */
public class OctantsState implements TypeAdaptor {

	public ArrayList<Integer> octants;

	public OctantsState() {
		this.octants = new ArrayList<Integer>();
	}

	/**
	 * Converts a String with the format [pos1Number, pos2Number..., posNNumber]
	 * to an OctantsStateObject
	 */
	@Override
	public void fromString(String content) throws Exception {
		if (!content.startsWith("[") || !content.endsWith("]")) {
			String message = "Invalid String format. "
					+ "Must be [integer,integer,integer,integer,integer,integer,integer,integer]. "
					+ "There must be 8 integer values mandatory";
			throw new Exception(message);
		}
		content = content.replace("[", "");
		content = content.replace("]", "");
		String[] numbers = content.split("-");
		if (numbers.length != 8) {
			String message = "Invalid String format. "
					+ "Must be [integer,integer,integer,integer,integer,integer,integer,integer]. "
					+ "There must be 8 integer values mandatory";
			throw new Exception(message);
		}
		if (this.octants.size() > 0)
			this.octants = new ArrayList<Integer>();
		for (int i = 0; i < numbers.length; i++)
			this.octants.add(new Integer(Integer.parseInt(numbers[i])));
	}

	/**
	 * Converts a OctantsState object to an string with the format: [pos1Number,
	 * pos2Number..., posNNumber]
	 */
	@Override
	public String toString() {
		String stringObject = "[";
		for (int i = 0; i < this.octants.size() - 1; i++)
			stringObject += this.octants.get(i).toString() + "-";
		stringObject += this.octants.get(this.octants.size() - 1) + "]";
		return stringObject;
	}

	/**
	 * Returns true if the received object is an ArrayList and all the elements
	 * of the objects are equal with the elements of the same position in
	 * octants
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ArrayList))
			return false;
		else {
			ArrayList<Integer> object = (ArrayList<Integer>) o;
			for (int i = 0; i < this.octants.size(); i++) {
				if (!octants.get(i).equals(object.get(i)))
					return false;
			}
			return true;
		}
	}
}
