package ontologias;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import es.ucm.fdi.gaia.ontobridge.OntologyDocument;
import java.util.ArrayList;
import ontologias.interfaz.panel.PanelArbolPropiedades;

/**
 *
 * @author Sergio
 */
public class NewClass {
    public static void main (String[] args) {
        OntoBridge ob = new OntoBridge();
        ob.initWithPelletReasoner();

        OntologyDocument mainOnto = new OntologyDocument("","file:files/Ontologia.owl");

        ArrayList<OntologyDocument> subOntologies = new ArrayList<OntologyDocument>();

        ob.loadOntology(mainOnto, subOntologies, false);

        javax.swing.JFrame window = new javax.swing.JFrame(mainOnto.getURL());
        PanelArbolPropiedades tree = new PanelArbolPropiedades(ob, "Pau_Gasol");
        window.getContentPane().add(tree);
        window.pack();
        window.setSize(300, 600);
        window.setVisible(true);
    }
}
