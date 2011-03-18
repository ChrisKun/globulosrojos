package ontologias.utils;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import ontologias.interfaz.panel.PanelArbolClasesInstancias;
import ontologias.interfaz.panel.PanelIntroducirNombre;
import ontologias.interfaz.panel.PanelPropiedades;

/**
 *
 * @author markel
 */
public class MenuContextual {

    public enum caller{
        clase,
        instancia,
        propiedad
    }

    public static JPopupMenu getPopupMenu(caller llamante, String nombreLlamante){
        if(llamante == caller.clase){
            return new menuContextualClase(nombreLlamante);
        }
        else if(llamante == caller.instancia)
        {
            return new menuContextualInstancia(nombreLlamante);
        }
        else if(llamante == caller.propiedad)
        {
            return new menuContextualPropiedad();
        }
        return null;
    }
}

class menuContextualClase extends JPopupMenu {

    private String llamante;

    public menuContextualClase(String nombreLlamante) {
        this.llamante = nombreLlamante;
        
        JMenuItem item1 = new JMenuItem("Crear instancia");
        JMenuItem item2 = new JMenuItem("Eliminar clase");
        item1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                PanelIntroducirNombre p = new PanelIntroducirNombre(llamante);
                javax.swing.JFrame window = new javax.swing.JFrame("");
                window.getContentPane().add(p);
                window.pack();
                window.setVisible(true);
            }
        });
        item2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                Ontologia.getInstance().delete(llamante);
            }
        });
        this.add(item1);
        this.addSeparator();
        this.add(item2);
        this.pack();
    }
}

class menuContextualInstancia extends JPopupMenu {

    private String llamante;

    public menuContextualInstancia(String nombreLlamante) {
        this.llamante = nombreLlamante;
        JMenuItem item1 = new JMenuItem("Editar propiedades");
        JMenuItem item2 = new JMenuItem("Eliminar instancia");
        item1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                PanelPropiedades p = new PanelPropiedades(llamante);
                javax.swing.JFrame window = new javax.swing.JFrame("");
                window.getContentPane().add(p);
                window.pack();
                window.setVisible(true);
            }
        });
        item2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                Ontologia.getInstance().delete(llamante);
            }
        });
        this.add(item1);
        this.addSeparator();
        this.add(item2);
        this.pack();
    }
}

class menuContextualPropiedad extends JPopupMenu{

    public menuContextualPropiedad() {
        JMenuItem item1 = new JMenuItem("Kaixo");
        JMenuItem item2 = new JMenuItem("Agur");
        item1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.out.println("pulsado Kaixo");
            }
        });
        item1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.out.println("pulsado Agur");
            }
        });
        this.add(item1);
        this.add(item2);
        this.pack();
    }
}
