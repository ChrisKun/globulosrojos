package ontologias.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import ontologias.interfaz.panel.PanelIntroducirNombre;

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
            return new MenuContextualInstancia(nombreLlamante);
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
                Ontologia.setGuardado(false);
            }
        });
        this.add(item1);
        this.addSeparator();
        this.add(item2);
        this.pack();
    }
}
