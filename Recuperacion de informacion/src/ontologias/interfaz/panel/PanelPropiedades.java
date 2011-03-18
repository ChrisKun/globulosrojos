/*
 * PnlPropiedades.java
 *
 * Created on 12-mar-2011, 3:23:28
 */

package ontologias.interfaz.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Iterator;
import javax.swing.JFrame;
import ontologias.utils.Ontologia;

/**
 *
 * @author Sergio
 */
public class PanelPropiedades extends javax.swing.JPanel {

    public static void main(String [] args) {
        PanelPropiedades p = new PanelPropiedades("Persona");
        javax.swing.JFrame window = new javax.swing.JFrame("");
        window.getContentPane().add(p);
        window.pack();
        //window.setSize(300, 600);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /** Creates new form PnlPropiedades */
    public PanelPropiedades(String instanceName) {
        this.instanceName = instanceName;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPnlPropiedades = new PanelArbolPropiedades(instanceName, this);
        jPnlInstancias = new PanelArbolInstancias(null, "", "", this);

        FlowLayout experimentLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);
        this.setLayout(experimentLayout);

        jPnlPropiedades.setPreferredSize(new Dimension(250, 400));
        jPnlInstancias.setPreferredSize(new Dimension(250, 400));

        this.add(jPnlPropiedades);
        this.add(jPnlInstancias);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private PanelArbolInstancias jPnlInstancias;
    private PanelArbolPropiedades jPnlPropiedades;
    // End of variables declaration//GEN-END:variables

    private String instanceName;

    void actualizarPanelPropiedades() {
        ((PanelArbolPropiedades)jPnlPropiedades).actualizarPanelPropiedades();
    }

    void actualizarPanelInstancias(Iterator<String> ancestors, String propertyName, String sourceInstance) {
    	this.remove(jPnlInstancias);
    	this.jPnlInstancias = new PanelArbolInstancias(ancestors, propertyName, sourceInstance, this);
    	jPnlInstancias.setPreferredSize(new Dimension(250, 400));
    	this.add(jPnlInstancias);
        //((PanelArbolInstancias)jPnlInstancias).actualizarPanelInstancias(ancestors, propertyName, sourceInstance);
        ((JFrame)this.getParent().getParent().getParent().getParent()).pack();
        this.updateUI();
    }
}