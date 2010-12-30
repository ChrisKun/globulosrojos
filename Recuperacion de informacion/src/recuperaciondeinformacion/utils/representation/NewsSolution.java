package recuperaciondeinformacion.utils.representation;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class NewsSolution implements CaseComponent {

	String id;
	String category;
	String imgURL;
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getImgURL() {
		return imgURL;
	}



	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}


	@Override
	public String toString() {
		return "NewsSolution [\n\tid=" + id + "\n\tcategory=" + category + "\n\timgURL=" + imgURL + "\n]";
	}



	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", this.getClass());
	}

}
