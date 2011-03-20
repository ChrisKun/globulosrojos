/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FramePrincipal.java
 *
 * Created on 20-mar-2011, 19:18:39
 */

package ontologias;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import ontologias.interfaz.panel.PanelArbolClasesInstancias;
import ontologias.interfaz.panel.PanelArbolSubclases;
import ontologias.interfaz.panel.PanelConsulta;
import ontologias.interfaz.panel.PanelPropiedades;
import ontologias.utils.CargadorImagenes;
import ontologias.utils.Ontologia;

/**
 *
 * @author sergio
 */
public class MenuPrincipal extends javax.swing.JFrame {

    /** Creates new form FramePrincipal */
    public MenuPrincipal() {
        //Para iniciar la interfaz en estado maximizado
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        CargadorImagenes loader = new CargadorImagenes();
        images = loader.loadImages();
        imageIndex = 0;
        nombre = images.get(imageIndex).getDescription();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        labelFoto = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        botonAnterior = new javax.swing.JButton();
        botonSiguiente = new javax.swing.JButton();
        botonM = new javax.swing.JButton();
        botonModificarOnt = new javax.swing.JButton();
        botonPreguntas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Marcador Semántico de Imágenes");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(210, 143, 76));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(138, 206, 158));

        labelFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(191, 125, 189));

        botonAnterior.setText("<");
        botonAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnteriorActionPerformed(evt);
            }
        });

        botonSiguiente.setText(">");
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

        botonModificarOnt.setText("Modificar Ontología");
        botonModificarOnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarOntActionPerformed(evt);
            }
        });

        botonPreguntas.setText("Preguntas");
        botonPreguntas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPreguntasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonM, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonPreguntas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonModificarOnt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonM)
                    .addComponent(botonModificarOnt)
                    .addComponent(botonSiguiente)
                    .addComponent(botonAnterior)
                    .addComponent(botonPreguntas))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FlowLayout experimentLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);
        jPanel1.setLayout(experimentLayout);
        PanelArbolClasesInstancias paci = new PanelArbolClasesInstancias(this);
        paci.setPreferredSize(new Dimension(290, 420));
        jPanel1.add(paci);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnteriorActionPerformed
        if (imageIndex - 1 == -1) {
            imageIndex = images.size() - 1;
            labelFoto.setIcon(images.get(imageIndex));
            return;
        }
        labelFoto.setIcon(images.get(--imageIndex));
        nombre = images.get(imageIndex).getDescription();
    }//GEN-LAST:event_botonAnteriorActionPerformed

    private void botonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSiguienteActionPerformed
        if (imageIndex + 1 == images.size()) {
            imageIndex = 0;
            labelFoto.setIcon(images.get(imageIndex));
            return;
        }
        labelFoto.setIcon(images.get(++imageIndex));
        nombre = images.get(imageIndex).getDescription();
    }//GEN-LAST:event_botonSiguienteActionPerformed

    private void botonMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMActionPerformed
        javax.swing.JFrame window = new javax.swing.JFrame(Ontologia.getMainOnto().getURL());
        PanelArbolSubclases subclases = new PanelArbolSubclases(Ontologia.getInstance(), "Noticia");
        window.getContentPane().add(subclases);
        window.pack();
        window.setSize(300, 600);
        window.setVisible(true);
    }//GEN-LAST:event_botonMActionPerformed

    private void botonModificarOntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarOntActionPerformed
        javax.swing.JFrame window = new javax.swing.JFrame(Ontologia.getMainOnto().getURL());
        PanelArbolClasesInstancias subclases = new PanelArbolClasesInstancias(this);
        window.getContentPane().add(subclases);
        window.pack();
        window.setSize(300, 600);
        window.setVisible(true);
    }//GEN-LAST:event_botonModificarOntActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(JOptionPane.showConfirmDialog(this, "¿Desea guardar la ontologia?","Guardar",JOptionPane.YES_NO_OPTION) == 0)
            Ontologia.getInstance().save("files/Ontologia.owl");
    }//GEN-LAST:event_formWindowClosing

    private void botonPreguntasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPreguntasActionPerformed
        PanelConsulta p = new PanelConsulta();
        p.pack();
        p.setSize(800, 600);
        p.setVisible(true);
    }//GEN-LAST:event_botonPreguntasActionPerformed

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

    public void mostrarImagen(String imagen) {
        for (int i = 0; i < images.size(); i++) {
            ImageIcon img = images.get(i);
            if (img.getDescription().equals(imagen)) {
                labelFoto.setIcon(img);
                this.imageIndex = i;
                return;
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAnterior;
    private javax.swing.JButton botonM;
    private javax.swing.JButton botonModificarOnt;
    private javax.swing.JButton botonPreguntas;
    private javax.swing.JButton botonSiguiente;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelFoto;
    // End of variables declaration//GEN-END:variables
    ArrayList<ImageIcon> images;
    private int imageIndex;
    String nombre;
    javax.swing.JFrame ventanaPopUp;
    PanelPropiedades panelPropiedadesPopUp;
    
}
