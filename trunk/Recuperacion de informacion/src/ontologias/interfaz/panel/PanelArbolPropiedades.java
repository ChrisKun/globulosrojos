package ontologias.interfaz.panel;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import java.awt.*;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Sergio
 */
public class PanelArbolPropiedades extends JPanel implements TreeSelectionListener {
    private static final long serialVersionUID = 1L;
    private JTree ontologyTree;
    private DefaultMutableTreeNode root;
    private static Icon CONCEPT = new javax.swing.ImageIcon(PanelArbolPropiedades.class.getResource("/es/ucm/fdi/gaia/ontobridge/test/gui/class-orange.gif"));
    private static Icon PROPERTY = new javax.swing.ImageIcon(PanelArbolPropiedades.class.getResource("/es/ucm/fdi/gaia/ontobridge/test/gui/datatype.gif"));
    private static int maxdepth = 20; //Constant to avoid cycles;
    private static ArrayList<String> drawnProperties = new ArrayList<String>(); //avoid cycles between instances
    private static Set<String> datatypes = new java.util.HashSet<String>();

    /**
     * Constructor
     */
    public PanelArbolPropiedades(OntoBridge ob, String ancestor) {
        super();
        createComponents(ancestor);
        readOntology(ob, ancestor);
    }

    public String getSelectedInstance() {
        return selectedConcept;
    }
    private String selectedConcept = null;

    public void valueChanged(TreeSelectionEvent tse) {
        selectedConcept = ontologyTree.getLastSelectedPathComponent().toString();
    }

    protected void createComponents(String ancestor) {
        JScrollPane scrPnl;
        Border lineBorder, titleBorder, emptyBorder, compoundBorder;

        //set border and layout
        emptyBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        lineBorder = BorderFactory.createLineBorder(Color.BLACK);
        titleBorder = BorderFactory.createTitledBorder(lineBorder, "Properties of " + ancestor);
        compoundBorder = BorderFactory.createCompoundBorder(titleBorder,
                emptyBorder);
        setBorder(compoundBorder);

        //set Ontology
        root = new DefaultMutableTreeNode(ancestor);

        ontologyTree = new JTree(root);
        ontologyTree.setCellRenderer(new MyRenderer());
        ontologyTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        ontologyTree.addTreeSelectionListener(this);

        ontologyTree.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                int selRow = ontologyTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = ontologyTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    selectedConcept = selPath.toString();
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

            Iterator<String> properties = ob.listInstanceProperties(ancestor);
            while (properties.hasNext()) {
                String propName = ob.getShortName(properties.next());
                if (!propName.contains("rdf") && !propName.contains("owl")) {
                    root.add(new DefaultMutableTreeNode(propName));
                    drawnProperties.add(propName);
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

        Iterator<String> instances = ob.listInstanceProperties(nodeName);
        while (instances.hasNext()) {
            String instanceName = ob.getShortName(instances.next());
            if (!instanceName.equals("owl:Nothing")) {
                node.add(new DefaultMutableTreeNode(instanceName));
                drawnProperties.add(instanceName);
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
                DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)value;
                Object o = dmtn.getUserObject();
                if (drawnProperties.contains(o))
                    setIcon(PROPERTY);
                else
                    setIcon(CONCEPT);
            } catch (Exception e) {
                org.apache.commons.logging.LogFactory.getLog(this.getClass()).error(e);
            }

            return this;
        }
    }
}
