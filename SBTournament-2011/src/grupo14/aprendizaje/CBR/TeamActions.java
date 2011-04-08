package grupo14.aprendizaje.CBR;

import grupo14.players.PlayerActions;

import java.util.ArrayList;

import jcolibri.connector.TypeAdaptor;

public class TeamActions implements TypeAdaptor{

	ArrayList<PlayerActions> playerActions;
	
	public TeamActions()
	{
		this.playerActions = new ArrayList<PlayerActions>();
	}
	
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
		String[] numbers = content.split(",");
		if (numbers.length != 8) {
			String message = "Invalid String format. "
					+ "Must be [integer,integer,integer,integer,integer,integer,integer,integer]. "
					+ "There must be 8 integer values mandatory";
			throw new Exception(message);
		}
		if (this.playerActions.size() > 0)
			this.playerActions = new ArrayList<PlayerActions>();
		for (int i = 0; i < numbers.length; i++)
			this.playerActions.add(PlayerActions.valueOf(numbers[i]));
	}
	
	@Override
	public String toString() {
		String stringObject = "[";
		for (int i = 0; i < this.playerActions.size() - 1; i++)
			stringObject += this.playerActions.get(i) + ",";
		stringObject += this.playerActions.size() - 1 + "]";
		return stringObject;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ArrayList))
			return false;
		else {
			ArrayList<PlayerActions> object = (ArrayList<PlayerActions>) o;
			for (int i = 0; i < this.playerActions.size(); i++) {
				if (!playerActions.get(i).equals(object.get(i)))
					return false;
			}
			return true;
		}
	}

	public ArrayList<PlayerActions> getPlayerActions() {
		return playerActions;
	}

	public void setPlayerActions(ArrayList<PlayerActions> playerActions) {
		this.playerActions = playerActions;
	}
	

}
