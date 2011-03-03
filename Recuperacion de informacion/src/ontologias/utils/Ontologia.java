package ontologias.utils;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import es.ucm.fdi.gaia.ontobridge.OntologyDocument;
import java.util.ArrayList;

/**
 * Clase que proporciona un Singleton para la ontologia
 * @author markel
 */
public class Ontologia {

    private static OntoBridge ob;
    private static OntologyDocument mainOnto;
    private static ArrayList<OntologyDocument> subOntologies;

    public static OntoBridge getInstance(){
        if(ob == null)
        {
            ob = new OntoBridge();
            initializeOntology();
        }
        return ob;
    }

    private static void initializeOntology() {
        ob.initWithPelletReasoner();
        mainOnto = new OntologyDocument("","file:files/Ontologia.owl");
        subOntologies = new ArrayList<OntologyDocument>();
        ob.loadOntology(mainOnto, subOntologies, false);
    }

    public static OntologyDocument getMainOnto() {
        return mainOnto;
    }

}
