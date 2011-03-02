/*
 * MenuPrincipal.java
 *
 * Created on Feb 26, 2011, 7:05:01 PM
 */
package ontologias;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import ontologias.utils.CargadorImagenes;

/**
 *
 * @author markel
 */
public class MenuPrincipal extends javax.swing.JFrame {

    /** Creates new form MenuPrincipal */
    public MenuPrincipal() {
        initComponents();
        CargadorImagenes loader = new CargadorImagenes();
        images = loader.loadImages();
        imageIndex = 0;
        labelFoto.setIcon(images.get(imageIndex));
        String[] nombre = images.get(imageIndex).toString().split("/");
        Iterator<String> it = loader.LoadImageInfo(nombre[3]);
        System.out.println(nombre[3]);
        System.out.println(images.get(imageIndex).getDescription());
        System.out.println(images.get(imageIndex).toString());
        
        while(it.hasNext())
        {
            labelImageInfo.setText(it.next());
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelListaNoticias = new javax.swing.JPanel();
        panelFoto = new javax.swing.JPanel();
        labelFoto = new javax.swing.JLabel();
        panelInfoFoto = new javax.swing.JPanel();
        labelImageInfo = new javax.swing.JLabel();
        panelMenu = new javax.swing.JPanel();
        botonAnterior = new javax.swing.JButton();
        botonSiguiente = new javax.swing.JButton();
        botonM = new javax.swing.JButton();
        botonR = new javax.swing.JButton();
        botonT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Marcador Semantico de Imagenes");
        setMinimumSize(new java.awt.Dimension(944, 715));

        javax.swing.GroupLayout panelListaNoticiasLayout = new javax.swing.GroupLayout(panelListaNoticias);
        panelListaNoticias.setLayout(panelListaNoticiasLayout);
        panelListaNoticiasLayout.setHorizontalGroup(
            panelListaNoticiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 173, Short.MAX_VALUE)
        );
        panelListaNoticiasLayout.setVerticalGroup(
            panelListaNoticiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 591, Short.MAX_VALUE)
        );

        labelFoto.setText(" ");

        javax.swing.GroupLayout panelFotoLayout = new javax.swing.GroupLayout(panelFoto);
        panelFoto.setLayout(panelFotoLayout);
        panelFotoLayout.setHorizontalGroup(
            panelFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFotoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFoto)
                .addContainerGap(792, Short.MAX_VALUE))
        );
        panelFotoLayout.setVerticalGroup(
            panelFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFotoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFoto)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        labelImageInfo.setText("labelImageInfo");

        javax.swing.GroupLayout panelInfoFotoLayout = new javax.swing.GroupLayout(panelInfoFoto);
        panelInfoFoto.setLayout(panelInfoFotoLayout);
        panelInfoFotoLayout.setHorizontalGroup(
            panelInfoFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoFotoLayout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(labelImageInfo)
                .addContainerGap(512, Short.MAX_VALUE))
        );
        panelInfoFotoLayout.setVerticalGroup(
            panelInfoFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoFotoLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(labelImageInfo)
                .addGap(64, 64, 64))
        );

        labelImageInfo.getAccessibleContext().setAccessibleName("labelImageInfo");

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

        botonR.setText("R");

        botonT.setText("T");

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
                .addContainerGap(329, Short.MAX_VALUE))
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
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelListaNoticias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelInfoFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panelListaNoticias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    }//GEN-LAST:event_botonSiguienteActionPerformed

    private void botonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnteriorActionPerformed
        if (imageIndex - 1 == -1) {
            imageIndex = images.size() - 1;
            labelFoto.setIcon(images.get(imageIndex));
            return;
        }
        labelFoto.setIcon(images.get(--imageIndex));
    }//GEN-LAST:event_botonAnteriorActionPerformed

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
    private javax.swing.JLabel labelImageInfo;
    private javax.swing.JPanel panelFoto;
    private javax.swing.JPanel panelInfoFoto;
    private javax.swing.JPanel panelListaNoticias;
    private javax.swing.JPanel panelMenu;
    // End of variables declaration//GEN-END:variables
    ArrayList<ImageIcon> images;
    private int imageIndex;
}
