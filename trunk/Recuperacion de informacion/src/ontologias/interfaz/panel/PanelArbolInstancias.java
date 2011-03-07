package ontologias.interfaz.panel;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
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
public class PanelArbolInstancias extends JPanel implements TreeSelectionListener {
    private static final long serialVersionUID = 1L;
    private JTree ontologyTree;
    private DefaultMutableTreeNode root;
    private static Icon CONCEPT = new javax.swing.ImageIcon(PanelArbolSubclases.class.getResource("/es/ucm/fdi/gaia/ontobridge/test/gui/class-orange.gif"));
    private static Icon INSTANCE = new javax.swing.ImageIcon(PanelArbolInstancias.class.getResource("/es/ucm/fdi/gaia/ontobridge/test/gui/instance.gif"));
    private static int maxdepth = 20; //Constant to avoid cycles;
    private static ArrayList<String> drawnInstances = new ArrayList<String>(); //avoid cycles between instances
    private OntoBridge ob;
    private String propertyName;
    private String sourceInstance;

    /**
     * Constructor
     */
    public PanelArbolInstancias(OntoBridge ob, String ancestor) {
        super();
        this.ob = ob;
        createComponents(ancestor);
        readOntology(ob, ancestor);
    }

    public PanelArbolInstancias(OntoBridge ob, Iterator<String> ancestors, String sourceInstance, String propertyName) {
    	super();
    	this.ob = ob;
    	this.propertyName = propertyName;
    	this.sourceInstance = sourceInstance;
    	createComponents("");
    	readOntology(ob, ancestors);
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
        titleBorder = BorderFactory.createTitledBorder(lineBorder, "Instances of " + ancestor);
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

            public void mousePressed(MouseEvent e) {
                int selRow = ontologyTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = ontologyTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1 && e.getClickCount() == 2) {
                    selectedConcept = selPath.toString();
                    
                    String instanceName = ob.getShortName(selPath.getLastPathComponent().toString());
                    // TODO: Modificar la ontologia anadiendo la instancia seleccionada a la propiedad
                    ob.modifyOntProperty(sourceInstance, propertyName, instanceName);
                    
                    // Cerramos el panel
                    close();
                }
            }
        });

        scrPnl = new JScrollPane(ontologyTree);
        scrPnl.setViewportView(ontologyTree);

        setLayout(new BorderLayout());
        add(scrPnl, BorderLayout.CENTER);
    }
    
    public void close() {
    	// JPanel -> JLPanel -> JRootPanel -> JFrame
    	((JFrame)this.getParent().getParent().getParent().getParent()).dispose();
    }

    /**
     * Read the ontology classes.
     *
     */
    protected void readOntology(OntoBridge ob, String ancestor) {
        try {
            ontologyTree.getModel().getRoot();
            
            DefaultMutableTreeNode node = createNode(ob.getURI(ancestor), ob, 0);
            root.add(node);

            ontologyTree.expandRow(0);

        } catch (Exception e) {
            org.apache.commons.logging.LogFactory.getLog(this.getClass()).error(e);
        }
    }
    
    protected void readOntology(OntoBridge ob, Iterator<String> ancestors) {
        try {
        	while (ancestors.hasNext()) {
        		String ancestor = ancestors.next();
	            ontologyTree.getModel().getRoot();
                DefaultMutableTreeNode node = createNode(ob.getURI(ancestor), ob, 0);
                root.add(node);
                for (int i = 0; i < ontologyTree.getRowCount(); i++)
                	ontologyTree.expandRow(i);
        	}

        } catch (Exception e) {
            org.apache.commons.logging.LogFactory.getLog(this.getClass()).error(e);
        }
    }   

    private DefaultMutableTreeNode createNode(String nodeName, OntoBridge ob, int depth) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(ob.getShortName(nodeName));
        if (depth > maxdepth) {
            return node;
        }

        Iterator<String> instances = ob.listInstances(nodeName);
        while (instances.hasNext()) {
            String instanceName = ob.getShortName(instances.next());
            if (!instanceName.equals("owl:Nothing")) {
                node.add(new DefaultMutableTreeNode(instanceName));
                drawnInstances.add(instanceName);
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
                if (drawnInstances.contains(o))
                    setIcon(INSTANCE);
                else
                    setIcon(CONCEPT);
            } catch (Exception e) {
                org.apache.commons.logging.LogFactory.getLog(this.getClass()).error(e);
            }

            return this;
        }
    }
}
