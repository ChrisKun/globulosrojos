package ontologias;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import es.ucm.fdi.gaia.ontobridge.OntologyDocument;
import es.ucm.fdi.gaia.ontobridge.test.gui.PnlConceptsTree;
import java.util.ArrayList;
import ontologias.interfaz.panel.PanelArbolInstancias;
import ontologias.interfaz.panel.PanelArbolSubclases;

/**
 *
 * @author Sergio
 */
public class NewClass {
    public static void main (String[] args) {
        OntoBridge ob = new OntoBridge();
        ob.initWithPelletReasoner();

        OntologyDocument mainOnto = new OntologyDocument("","file:Ontologia.owl");

        ArrayList<OntologyDocument> subOntologies = new ArrayList<OntologyDocument>();

        ob.loadOntology(mainOnto, subOntologies, false);

        javax.swing.JFrame window = new javax.swing.JFrame(mainOnto.getURL());
        PanelArbolInstancias tree = new PanelArbolInstancias(ob, "Equipo");
        window.getContentPane().add(tree);
        window.pack();
        window.setSize(300, 600);
        window.setVisible(true);
    }
}
