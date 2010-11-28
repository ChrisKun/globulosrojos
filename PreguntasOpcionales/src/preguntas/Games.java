package preguntas;

import jcolibri.cbrcore.CBRQuery;
import jcolibri.extensions.recommendation.ContentBasedProfile.CreateProfile;
import jcolibri.method.gui.formFilling.ObtainQueryWithFormMethod;
import userProfile.User;

public class Games{

	public static void main(String[] args) {
		CBRQuery query = new CBRQuery();
		query.setDescription(new User());
		ObtainQueryWithFormMethod.obtainQueryWithoutInitialValues(query,null,null);
		CreateProfile.createProfile(query, "src/profilesData/profile.xml");
	}

}
