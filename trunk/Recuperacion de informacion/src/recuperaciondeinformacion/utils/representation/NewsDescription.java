package recuperaciondeinformacion.utils.representation;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;
import jcolibri.datatypes.Text;

public class NewsDescription implements CaseComponent {

	String id;
	Text text;
	Text title;
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Text getText() {
		return text;
	}



	public void setText(Text text) {
		this.text = text;
	}



	public Text getTitle() {
		return title;
	}



	public void setTitle(Text title) {
		this.title = title;
	}



	@Override
	public String toString() {
		return "NewsDescription [\n\tid=" + id + "\n\ttitle=" + title + "\n\ttext="	+ text + "\n]";
	}



	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", this.getClass());
	}

}
