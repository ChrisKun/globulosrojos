package ontologias.interfaz.panel;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import java.awt.*;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import ontologias.utils.Ontologia;

/**
 *
 * @author Sergio
 */
public class PanelArbolPropiedades extends JPanel implements TreeSelectionListener {
    private static final long serialVersionUID = 1L;
    private JTree ontologyTree;
    private DefaultMutableTreeNode root;
    private static Icon CONCEPT = new javax.swing.ImageIcon(PanelArbolPropiedades.class.getResource("/es/ucm/fdi/gaia/ontobridge/test/gui/class-orange.gif"));
    private static Icon PROPERTY = new javax.swing.ImageIcon(PanelArbolPropiedades.class.getResource("/es/ucm/fdi/gaia/ontobridge/test/gui/property.gif"));
    private static Icon INSTANCE = new javax.swing.ImageIcon(PanelArbolPropiedades.class.getResource("/es/ucm/fdi/gaia/ontobridge/test/gui/instance.gif"));
    private static Icon DATATYPE = new javax.swing.ImageIcon(PanelArbolPropiedades.class.getResource("/es/ucm/fdi/gaia/ontobridge/test/gui/datatype.gif"));
    private static ArrayList<String> drawnProperties = new ArrayList<String>();
    private static ArrayList<String> drawnInstances = new ArrayList<String>();
    private static ArrayList<String> drawnDataTypes = new ArrayList<String>();
    
    private String ancestor;
    private PnlPropiedades padre;
    
    /**
     * Constructor
     */
    public PanelArbolPropiedades(String ancestor, PnlPropiedades padre) {
        super();
        this.ancestor = ancestor;
        this.padre = padre;
        createComponents();
        readOntology();
    }

    public String getSelectedInstance() {
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
                if (selRow != -1 && e.getClickCount() == 2 && Ontologia.getInstance().existsProperty(selPath.getLastPathComponent().toString())) {
                    selectedConcept = selPath.toString();              
                    // Obtenemos las clases pertenecientes al rango de la propiedad (puede ser mas de una)
                    Iterator<String> rangos = Ontologia.getInstance().listPropertyRange(selPath.getLastPathComponent().toString());
                    
                    String selectedProperty = selPath.getLastPathComponent().toString();
                    
                    // Creamos el panel con las instancias pertenecientes al rango
                    padre.actualizarPanelInstancias(rangos, ancestor, selectedProperty); 
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
    protected void readOntology() {
        try {
            root.removeAllChildren();

            Iterator<String> properties = Ontologia.getInstance().listInstanceProperties(ancestor);
            while (properties.hasNext()) {
                String propName = Ontologia.getInstance().getShortName(properties.next());
                if (!propName.contains("rdf") && !propName.contains("owl")) {
                	DefaultMutableTreeNode propNode = new DefaultMutableTreeNode(propName);
                	
                	// Dibujamos los valores de las propiedades si existen
                	Iterator <String> values = Ontologia.getInstance().listPropertyValue(ancestor, propName);
                	while (values.hasNext()) {
                		String valueName = Ontologia.getInstance().getShortName(values.next());
                		propNode.add(new DefaultMutableTreeNode(valueName));
                		if (Ontologia.getInstance().existsInstance(valueName))
                			drawnInstances.add(valueName);
                		else
                			drawnDataTypes.add(valueName);
                	}
                	
                    root.add(propNode);
                    drawnProperties.add(propName);                    
                }
            }
            
            ontologyTree.updateUI();
            ontologyTree.expandRow(0);

        } catch (Exception e) {
            org.apache.commons.logging.LogFactory.getLog(this.getClass()).error(e);
        }
    }

    /**
     * @param ancestor the ancestor to set
     */
    public void setAncestor(String ancestor) {
        this.ancestor = ancestor;
    }

    void actualizarPanelPropiedades() {
        readOntology();
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
                else if (drawnInstances.contains(o))
                    setIcon(INSTANCE);
                else if (drawnDataTypes.contains(o))
                	setIcon(DATATYPE);
                else 
                	setIcon(CONCEPT);
            } catch (Exception e) {
                org.apache.commons.logging.LogFactory.getLog(this.getClass()).error(e);
            }

            return this;
        }
    }
}
