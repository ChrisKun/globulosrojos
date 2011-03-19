package ontologias.interfaz.panel;

import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import java.awt.*;
import java.awt.event.KeyListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private PanelPropiedades padre;

    /**
     * Constructor
     */
    public PanelArbolPropiedades(String ancestor, PanelPropiedades padre) {
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
        selectedConcept = tse.getPath().toString();
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
                if (selRow != -1) {
                    //selectedConcept = selPath.toString();
                    if (Ontologia.getInstance().existsProperty(Ontologia.getInstance().getURI(selPath.getLastPathComponent().toString()))) {
                        // Obtenemos las clases pertenecientes al rango de la propiedad (puede ser mas de una)
                        Iterator<String> rangos = Ontologia.getInstance().listPropertyRange(selPath.getLastPathComponent().toString());

                        String selectedProperty = selPath.getLastPathComponent().toString();

                        // Creamos el panel con las instancias pertenecientes al rango
                        padre.actualizarPanelInstancias(rangos, ancestor, selectedProperty);
                    }
                }
            }
        });

        ontologyTree.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_DELETE) {
                    String[] splittedSelectedConcept = selectedConcept.substring(1, selectedConcept.length() - 1).split(", ");
                    String propertyName = splittedSelectedConcept[1];
                    String destInstance = splittedSelectedConcept[2];
                    if (Ontologia.getInstance().existsInstance(destInstance)) {
                        Ontologia.getInstance().deleteOntProperty(ancestor, propertyName, destInstance);

                        readOntology();
                    }
                }
            }

            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
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
        Iterator<String> properties = null;
        try {
            root.removeAllChildren();
            if(Ontologia.getInstance().isInstanceOf(ancestor, "Imagen"))
                properties = Ontologia.getInstance().listProperties("Imagen");
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
            for (int i = 0; i < ontologyTree.getRowCount(); i++)
                ontologyTree.expandRow(i);

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
