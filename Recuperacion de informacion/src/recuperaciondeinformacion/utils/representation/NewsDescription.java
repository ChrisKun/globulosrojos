package recuperaciondeinformacion.utils.representation;

import java.util.Collection;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;
import jcolibri.datatypes.Text;
import jcolibri.extensions.textual.IE.representation.IEText;

public class NewsDescription implements CaseComponent {
	
	String id;
	IEText text;
	Text title;
	Collection<String> nombres;
	Collection<String> verbos;
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public IEText getText() {
		return text;
	}



	public void setText(IEText text) {
		this.text = text;
	}



	public Text getTitle() {
		return title;
	}



	public void setTitle(Text title) {
		this.title = title;
	}
	
	public Collection<String> getNombres() {
		return nombres;
	}



	public void setNombres(Collection<String> nombres) {
		this.nombres = nombres;
	}



	public Collection<String> getVerbos() {
		return verbos;
	}



	public void setVerbos(Collection<String> verbos) {
		this.verbos = verbos;
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
