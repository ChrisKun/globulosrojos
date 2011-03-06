package ontologias.interfaz.panel;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
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
public class PanelArbolClasesInstancias extends JPanel implements TreeSelectionListener {
    private static final long serialVersionUID = 1L;
    private JTree ontologyTree;
    private DefaultMutableTreeNode root;
    private static Icon CONCEPT = new javax.swing.ImageIcon(PanelArbolClasesInstancias.class.getResource("/es/ucm/fdi/gaia/ontobridge/test/gui/class-orange.gif"));
    private static Icon INSTANCE = new javax.swing.ImageIcon(PanelArbolClasesInstancias.class.getResource("/es/ucm/fdi/gaia/ontobridge/test/gui/instance.gif"));
    private static int maxdepth = 20; //Constant to avoid cycles;
    private static ArrayList<String> drawnInstances = new ArrayList<String>(); //avoid cycles between instances

    /**
     * Constructor
     */
    public PanelArbolClasesInstancias(OntoBridge ob) {
        super();
        createComponents();
        readOntology(ob);
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
        titleBorder = BorderFactory.createTitledBorder(lineBorder, "Ontología");
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
                    System.out.println(selectedConcept);
                }
            }
        });

        scrPnl = new JScrollPane(ontologyTree);
        scrPnl.setViewportView(ontologyTree);

        // Botón para crear una instancia en la clase seleccionada
        JButton botonCrear = new JButton("Crear instancia");
        botonCrear.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	System.out.println("<Instancia creada>");
            }       	
        });
        
        setLayout(new BorderLayout());
        add(scrPnl, BorderLayout.CENTER);
        add(botonCrear, BorderLayout.SOUTH);
    }

    /**
     * Read the ontology classes.
     *
     */
    protected void readOntology(OntoBridge ob) {
        try {
            ontologyTree.getModel().getRoot();
            Iterator<String> rc = ob.listRootClasses();
            while (rc.hasNext()) {
                String nextRC = rc.next();
                DefaultMutableTreeNode node = createNode(nextRC, ob, 0);
                root.add(node);
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
