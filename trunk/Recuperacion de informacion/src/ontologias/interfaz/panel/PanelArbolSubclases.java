package ontologias.interfaz.panel;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import ontologias.utils.MenuContextualInstancia;
import ontologias.utils.Ontologia;

/**
 *
 * @author Sergio
 */
public class PanelArbolSubclases extends JPanel implements TreeSelectionListener {

    private static final long serialVersionUID = 1L;
    private JTree ontologyTree;
    private DefaultMutableTreeNode root;
    private static Icon CONCEPT = new javax.swing.ImageIcon(PanelArbolSubclases.class.getResource("/es/ucm/fdi/gaia/ontobridge/test/gui/class-orange.gif"));
    private static int maxdepth = 20; //Constant to avoid cycles;
    MenuContextualInstancia padre;
    JFrame ventana;

    /**
     * Constructor
     */
    public PanelArbolSubclases(OntoBridge ob, String ancestor) {
        super();
        createComponents();
        readOntology(ob, ancestor);
    }

    /**
     * Constructor
     */
    public PanelArbolSubclases(OntoBridge ob, String ancestor, MenuContextualInstancia padre, JFrame ventana) {
        super();
        this.padre = padre;
        this.ventana = ventana;
        createComponents();
        readOntology(ob, ancestor);
    }

    public String getSelectedConcept() {
        return selectedConcept;
    }
    private String selectedConcept = null;

    public void valueChanged(TreeSelectionEvent tse) {
        selectedConcept = ontologyTree.getLastSelectedPathComponent().toString();
    }

    protected void createComponents() {
        JScrollPane scrPnl;
        Border lineBorder, titleBorder, emptyBorder, compoundBorder;

        //set border and layout
        emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        lineBorder = BorderFactory.createLineBorder(Color.BLACK);
        titleBorder = BorderFactory.createTitledBorder(lineBorder, "Ontology Structure");
        compoundBorder = BorderFactory.createCompoundBorder(titleBorder,
                emptyBorder);
        setBorder(compoundBorder);

        //set Ontology
        root = new DefaultMutableTreeNode("Thing");

        ontologyTree = new JTree(root);
        ontologyTree.setCellRenderer(new MyRenderer());
        ontologyTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        ontologyTree.addTreeSelectionListener(this);

        ontologyTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int selRow = ontologyTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = ontologyTree.getPathForLocation(e.getX(), e.getY());
                String nombreSeleccion = selPath.getLastPathComponent().toString();
                if (selRow != -1 && e.getClickCount() == 2 && Ontologia.getInstance().existsInstance(nombreSeleccion)) {
                    //selectedConcept = selPath.toString();
                    nombreSeleccion = Ontologia.getInstance().getShortName(selPath.getLastPathComponent().toString());
                    padre.cambiarClaseAInstancia(nombreSeleccion);
                    Ontologia.setGuardado(false);
                    ventana.dispose();
                }
            }
        });

        scrPnl = new JScrollPane(ontologyTree);
        scrPnl.setViewportView(ontologyTree);

        setLayout(new BorderLayout());
        add(scrPnl, BorderLayout.CENTER);
    }

    /**
     * Read the ontology classes.
     *
     */
    protected void readOntology(OntoBridge ob, String ancestor) {
        try {
            ontologyTree.getModel().getRoot();
            Iterator<String> rc = ob.listRootClasses();
            while (rc.hasNext()) {
                String nextRC = rc.next();
                if (ob.getShortName(nextRC).equals(ancestor) || ancestor.equals("Thing")) {
                    DefaultMutableTreeNode node = createNode(nextRC, ob, 0);
                    root.add(node);
                }
            }
            ontologyTree.expandRow(0);

        } catch (Exception e) {
            org.apache.commons.logging.LogFactory.getLog(this.getClass()).error(e);
        }
    }

    private DefaultMutableTreeNode createNode(String nodeName, OntoBridge ob, int depth) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(ob.getShortName(nodeName));
        if (depth > maxdepth) {
            return node;
        }

        Iterator<String> subClasses = ob.listSubClasses(nodeName, true);
        while (subClasses.hasNext()) {
            String subClassName = ob.getShortName(subClasses.next());
            if (!subClassName.equals("owl:Nothing")) {
                node.add(createNode(subClassName, ob, depth + 1));
            }
        }

        return node;
    }

    class MyRenderer extends DefaultTreeCellRenderer {

        private static final long serialVersionUID = 1L;

        public MyRenderer() {
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, value, sel, expanded,
                    leaf, row, hasFocus);

            try {
                setIcon(CONCEPT);
            } catch (Exception e) {
                org.apache.commons.logging.LogFactory.getLog(this.getClass()).error(e);
            }

            return this;
        }
    }
}
