package ontologias.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import ontologias.interfaz.panel.PanelArbolSubclases;
import ontologias.interfaz.panel.PanelPropiedades;

public class MenuContextualInstancia extends JPopupMenu {

    private String llamante;

    public MenuContextualInstancia getPadre() {
        return this;
    }

    public void cambiarClaseAInstancia(String nombreSeleccion) {
        ArrayList<String> properties = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();
        //Coger informacion de la instancia seleccionada
        Ontologia.getInstance().listInstancePropertiesValues(llamante, properties, values);
        //Se elimina la instancia antigua
        Ontologia.getInstance().delete(llamante);
        //Se crea la instancia en su nueva clase
        Ontologia.getInstance().createInstance(nombreSeleccion, llamante);
        //Se le asignan los valores que tenian sus propiedades antes del cambio
        for (int i = 0; i < properties.size(); i++) {
            Ontologia.getInstance().createOntProperty(llamante, Ontologia.getInstance().getShortName(properties.get(i)), values.get(i));
        }
        
    }

    public MenuContextualInstancia(String nombreLlamante) {
        this.llamante = nombreLlamante;
        this.setLightWeightPopupEnabled(true);
        JMenuItem item1 = new JMenuItem("Editar propiedades");
        JMenuItem item2 = new JMenuItem("Eliminar instancia");
        JMenuItem item3 = new JMenuItem("Clasificar Instancia");
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
        item3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                //Abrir panel para la elecion de clase destino
                PanelArbolSubclases panel = new PanelArbolSubclases(Ontologia.getInstance(), "Imagen", getPadre());
                panel.setSize(600, 600);
                javax.swing.JFrame window = new javax.swing.JFrame("");
                window.getContentPane().add(panel);
                window.pack();
                window.setVisible(true);
            }
        });
        this.add(item1);
        this.add(item3);
        this.addSeparator();
        this.add(item2);
        this.pack();
    }
}
