package ontologias.interfaz.panel;

import com.hp.hpl.jena.reasoner.test.ManualExample;
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
import javax.swing.JComponent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import ontologias.MenuPrincipal;
import ontologias.utils.MenuContextual;
import ontologias.utils.Ontologia;
import javax.swing.JPopupMenu;

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
    private MenuPrincipal padre;
    private JPopupMenu menuContextual;

    /**
     * Constructor
     */
    public PanelArbolClasesInstancias(MenuPrincipal padre) {
        super();
        createComponents();
        readOntology();
        this.padre = padre;
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
        titleBorder = BorderFactory.createTitledBorder(lineBorder, "Ontologia");
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

                if(menuContextual != null && menuContextual.isVisible())
                    menuContextual.setVisible(false);

                int selRow = ontologyTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = ontologyTree.getPathForLocation(e.getX(), e.getY());
                try {
                    String nombreSeleccion = selPath.getLastPathComponent().toString();
                    if (selRow != -1
                            && e.getClickCount() == 2
                            && Ontologia.getInstance().existsInstance(nombreSeleccion)
                            && Ontologia.getInstance().isInstanceOf(nombreSeleccion, "Imagen")) {
                        padre.mostrarImagen(nombreSeleccion);
                    }
                    else //Si se ha hecho click dereco...
                        if (selRow != -1 && e.isPopupTrigger())
                        {//TODO Puede que esto de error si hay una instancia que se llama igual que una clase
                            String nombreSeleccionado = selPath.getLastPathComponent().toString();
                            if(Ontologia.getInstance().existsClass(nombreSeleccion))
                                menuContextual = MenuContextual.getPopupMenu(MenuContextual.caller.clase, nombreSeleccionado);
                            else if(Ontologia.getInstance().existsInstance(nombreSeleccion))
                                menuContextual = MenuContextual.getPopupMenu(MenuContextual.caller.instancia, nombreSeleccionado);
                            else if(Ontologia.getInstance().existsProperty(nombreSeleccion))
                                menuContextual = MenuContextual.getPopupMenu(MenuContextual.caller.propiedad, nombreSeleccionado);
                            menuContextual.show((JComponent)e.getSource(), e.getX(), e.getY());
                    }
                } catch (NullPointerException ex) {
                    return;
                }
            }
        });

        scrPnl = new JScrollPane(ontologyTree);
        scrPnl.setViewportView(ontologyTree);

        // Boton para crear una instancia en la clase seleccionada
        JButton botonCrear = new JButton("Actualizar");
        //botonCrear.setBounds(new Rectangle(50,50,0,100));
        botonCrear.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                updatePanel();
                ontologyTree.updateUI();
                ontologyTree.repaint();
                
            }
        });

        setLayout(new BorderLayout());
        add(scrPnl, BorderLayout.CENTER);
        add(botonCrear, BorderLayout.SOUTH);
    }

    public void updatePanel() {
        readOntology();
    }

    /**
     * Read the ontology classes.
     *
     */
    protected void readOntology() {
        try {
            root.removeAllChildren();
            Iterator<String> rc = Ontologia.getInstance().listRootClasses();
            while (rc.hasNext()) {
                String nextRC = rc.next();
                DefaultMutableTreeNode node = createNode(nextRC, Ontologia.getInstance(), 0);
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
                DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) value;
                Object o = dmtn.getUserObject();
                if (drawnInstances.contains(o)) {
                    setIcon(INSTANCE);
                } else {
                    setIcon(CONCEPT);
                }
            } catch (Exception e) {
                org.apache.commons.logging.LogFactory.getLog(this.getClass()).error(e);
            }

            return this;
        }
    }
}
