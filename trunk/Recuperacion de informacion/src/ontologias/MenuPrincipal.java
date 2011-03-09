/*
 * MenuPrincipal.java
 *
 * Created on Feb 26, 2011, 7:05:01 PM
 */
package ontologias;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import ontologias.interfaz.panel.PanelArbolClasesInstancias;
import ontologias.interfaz.panel.PanelArbolInstancias;
import ontologias.interfaz.panel.PanelArbolPropiedades;
import ontologias.interfaz.panel.PanelArbolSubclases;
import ontologias.utils.CargadorImagenes;
import ontologias.utils.Ontologia;

/**
 *
 * @author markel
 */
public class MenuPrincipal extends javax.swing.JFrame {

    /** Creates new form MenuPrincipal */
    public MenuPrincipal() {
        //Para iniciarlo en estado maximizado
        //this.setExtendedState(Frame.MAXIMIZED_BOTH);
        CargadorImagenes loader = new CargadorImagenes();
        images = loader.loadImages();
        imageIndex = 0;
        nombre = images.get(imageIndex).toString().split("/");
        initComponents();
        labelFoto.setIcon(images.get(imageIndex));
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelListaNoticias = new PanelArbolClasesInstancias(Ontologia.getInstance());
        panelFoto = new javax.swing.JPanel();
        labelFoto = new javax.swing.JLabel();
        panelInfoFoto = new PanelArbolPropiedades(Ontologia.getInstance(), nombre[3]);
        panelMenu = new javax.swing.JPanel();
        botonAnterior = new javax.swing.JButton();
        botonSiguiente = new javax.swing.JButton();
        botonM = new javax.swing.JButton();
        botonR = new javax.swing.JButton();
        botonT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Marcador Semantico de Imagenes");
        setMinimumSize(new java.awt.Dimension(944, 715));

        this.getContentPane().add(panelListaNoticias);
        this.pack();
        panelListaNoticias.setMinimumSize(new java.awt.Dimension(253, 600));
        panelListaNoticias.setPreferredSize(new java.awt.Dimension(253, 600));

        javax.swing.GroupLayout panelListaNoticiasLayout = new javax.swing.GroupLayout(panelListaNoticias);
        panelListaNoticias.setLayout(panelListaNoticiasLayout);
        panelListaNoticiasLayout.setHorizontalGroup(
            panelListaNoticiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );
        panelListaNoticiasLayout.setVerticalGroup(
            panelListaNoticiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 656, Short.MAX_VALUE)
        );

        labelFoto.setText(" ");

        javax.swing.GroupLayout panelFotoLayout = new javax.swing.GroupLayout(panelFoto);
        panelFoto.setLayout(panelFotoLayout);
        panelFotoLayout.setHorizontalGroup(
            panelFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFotoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFoto)
                .addContainerGap(877, Short.MAX_VALUE))
        );
        panelFotoLayout.setVerticalGroup(
            panelFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFotoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFoto)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        this.getContentPane().add(panelInfoFoto);
        this.pack();

        javax.swing.GroupLayout panelInfoFotoLayout = new javax.swing.GroupLayout(panelInfoFoto);
        panelInfoFoto.setLayout(panelInfoFotoLayout);
        panelInfoFotoLayout.setHorizontalGroup(
            panelInfoFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 890, Short.MAX_VALUE)
        );
        panelInfoFotoLayout.setVerticalGroup(
            panelInfoFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        botonAnterior.setText("Anterior");
        botonAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnteriorActionPerformed(evt);
            }
        });

        botonSiguiente.setText("Siguiente");
        botonSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSiguienteActionPerformed(evt);
            }
        });

        botonM.setText("M");
        botonM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMActionPerformed(evt);
            }
        });

        botonR.setText("R");
        botonR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRActionPerformed(evt);
            }
        });

        botonT.setText("T");
        botonT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(botonAnterior)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonSiguiente)
                .addContainerGap(414, Short.MAX_VALUE))
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonM)
                    .addComponent(botonR)
                    .addComponent(botonT)
                    .addComponent(botonSiguiente)
                    .addComponent(botonAnterior))
                .addContainerGap(104, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelListaNoticias, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelListaNoticias, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelInfoFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSiguienteActionPerformed
        if (imageIndex + 1 == images.size()) {
            imageIndex = 0;
            labelFoto.setIcon(images.get(imageIndex));
            return;
        }
        labelFoto.setIcon(images.get(++imageIndex));
        nombre = images.get(imageIndex).toString().split("/");
        ((PanelArbolPropiedades)panelInfoFoto).setAncestor(nombre[3]);
        panelInfoFoto.revalidate();
        panelInfoFoto.repaint();
        panelInfoFoto.updateUI();
        SwingUtilities.updateComponentTreeUI(panelInfoFoto);
        this.pack();
        this.validateTree();
        this.repaint();
        this.validate();
    }//GEN-LAST:event_botonSiguienteActionPerformed

    private void botonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnteriorActionPerformed
        if (imageIndex - 1 == -1) {
            imageIndex = images.size() - 1;
            labelFoto.setIcon(images.get(imageIndex));
            return;
        }
        labelFoto.setIcon(images.get(--imageIndex));
        nombre = images.get(imageIndex).toString().split("/");
        ((PanelArbolPropiedades)panelInfoFoto).setAncestor(nombre[3]);
        panelInfoFoto.revalidate();
        panelInfoFoto.repaint();
        panelInfoFoto.updateUI();
        this.pack();
        this.repaint();
        this.validate();
    }//GEN-LAST:event_botonAnteriorActionPerformed

    private void botonMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMActionPerformed
        javax.swing.JFrame window = new javax.swing.JFrame(Ontologia.getMainOnto().getURL());
        PanelArbolSubclases subclases = new PanelArbolSubclases(Ontologia.getInstance(), "Noticia");
        window.getContentPane().add(subclases);
        window.pack();
        window.setSize(300, 600);
        window.setVisible(true);
    }//GEN-LAST:event_botonMActionPerformed

    private void botonRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRActionPerformed
        javax.swing.JFrame window = new javax.swing.JFrame(Ontologia.getMainOnto().getURL());
        PanelArbolInstancias subclases = new PanelArbolInstancias(Ontologia.getInstance(), "Persona");
        window.getContentPane().add(subclases);
        window.pack();
        window.setSize(300, 600);
        window.setVisible(true);
    }//GEN-LAST:event_botonRActionPerformed

    private void botonTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTActionPerformed
        javax.swing.JFrame window = new javax.swing.JFrame(Ontologia.getMainOnto().getURL());
        PanelArbolClasesInstancias subclases = new PanelArbolClasesInstancias(Ontologia.getInstance());
        window.getContentPane().add(subclases);
        window.pack();
        window.setSize(300, 600);
        window.setVisible(true);
    }//GEN-LAST:event_botonTActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAnterior;
    private javax.swing.JButton botonM;
    private javax.swing.JButton botonR;
    private javax.swing.JButton botonSiguiente;
    private javax.swing.JButton botonT;
    private javax.swing.JLabel labelFoto;
    private javax.swing.JPanel panelFoto;
    private javax.swing.JPanel panelInfoFoto;
    private javax.swing.JPanel panelListaNoticias;
    private javax.swing.JPanel panelMenu;
    // End of variables declaration//GEN-END:variables
    ArrayList<ImageIcon> images;
    private int imageIndex;
    String[] nombre;
}
