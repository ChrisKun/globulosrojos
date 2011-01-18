package recuperaciondeinformacion.utils.representation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
    // Caracterï¿½sticas
    ArrayList<String> Politicos = new ArrayList<String>();
    ArrayList<String> JugadoresRealMadrid = new ArrayList<String>();
    ArrayList<String> JugadoresBarcelona = new ArrayList<String>();
    ArrayList<String> PresidentesFutbol = new ArrayList<String>();
    ArrayList<String> EntrenadoresFutbol = new ArrayList<String>();
    ArrayList<String> JugadoresTenis = new ArrayList<String>();
    ArrayList<String> CorredoresFormula1 = new ArrayList<String>();
    ArrayList<String> Paises = new ArrayList<String>();
    ArrayList<String> Bancos = new ArrayList<String>();

    public ArrayList<String> getBancos() {
        return Bancos;
    }

    public void setBancos(ArrayList<String> Bancos) {
        this.Bancos = Bancos;
    }

    public ArrayList<String> getCorredoresFormula1() {
        return CorredoresFormula1;
    }

    public void setCorredoresFormula1(ArrayList<String> CorredoresFormula1) {
        this.CorredoresFormula1 = CorredoresFormula1;
    }

    public ArrayList<String> getEntrenadoresFutbol() {
        return EntrenadoresFutbol;
    }

    public void setEntrenadoresFutbol(ArrayList<String> EntrenadoresFutbol) {
        this.EntrenadoresFutbol = EntrenadoresFutbol;
    }

    public ArrayList<String> getJugadoresBarcelona() {
        return JugadoresBarcelona;
    }

    public void setJugadoresBarcelona(ArrayList<String> JugadoresBarcelona) {
        this.JugadoresBarcelona = JugadoresBarcelona;
    }

    public ArrayList<String> getJugadoresRealMadrid() {
        return JugadoresRealMadrid;
    }

    public void setJugadoresRealMadrid(ArrayList<String> JugadoresRealMadrid) {
        this.JugadoresRealMadrid = JugadoresRealMadrid;
    }

    public ArrayList<String> getJugadoresTenis() {
        return JugadoresTenis;
    }

    public void setJugadoresTenis(ArrayList<String> JugadoresTenis) {
        this.JugadoresTenis = JugadoresTenis;
    }

    public ArrayList<String> getPaises() {
        return Paises;
    }

    public void setPaises(ArrayList<String> Paises) {
        this.Paises = Paises;
    }

    public ArrayList<String> getPresidentesFutbol() {
        return PresidentesFutbol;
    }

    public void setPresidentesFutbol(ArrayList<String> PresidentesFutbol) {
        this.PresidentesFutbol = PresidentesFutbol;
    }

    public ArrayList<String> getPoliticos() {
        return Politicos;
    }

    public void setPoliticos(ArrayList<String> politicos) {
        Politicos = politicos;
    }

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
    
    public HashMap<String, ArrayList<String>> getPropiedades() {
    	HashMap<String, ArrayList<String>> l = new HashMap<String, ArrayList<String>>();
    	l.put("Politicos", this.Politicos);
    	l.put("Jugadores Real Madrid", this.JugadoresRealMadrid);
    	l.put("Jugadores Barcelona", this.JugadoresBarcelona);
    	l.put("Presidentes de Fútbol", this.PresidentesFutbol);
    	l.put("Entrenadores de Fútbol", this.EntrenadoresFutbol);
    	l.put("Jugadores de Tenis", this.JugadoresTenis);
    	l.put("Corredores de Fórmula 1", this.CorredoresFormula1);
    	l.put("Paises", this.Paises);
    	l.put("Bancos", this.Bancos);
    	return l;
    }

    @Override
    public String toString() {
        return "NewsDescription [\n\tid=" + id + "\n\ttitle=" + title + "\n\ttext=" + text
                + "\n\tnombres=" + nombres + "\n\tverbos=" + verbos + "\n\tpoliticos=" + Politicos + "]";
    }

    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id", this.getClass());
    }
}
