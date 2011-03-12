package ontologias.utils;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

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

    public static JPopupMenu getPopupMenu(caller llamante){
        if(llamante == caller.clase){
            return new menuContextualClase();
        }
        else if(llamante == caller.instancia)
        {
            return new menuContextualInstancia();
        }
        else if(llamante == caller.propiedad)
        {
            return new menuContextualPropiedad();
        }
        return null;
    }
}

class menuContextualClase extends JPopupMenu {

    public menuContextualClase() {
        JMenuItem item1 = new JMenuItem("Hola");
        JMenuItem item2 = new JMenuItem("Adios");
        item1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.out.println("pulsado Hola");
            }
        });
        item2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.out.println("pulsado Adios");
            }
        });
        this.add(item1);
        this.add(item2);
        this.pack();
    }
}

class menuContextualInstancia extends JPopupMenu {
    
    public menuContextualInstancia() {
        JMenuItem item1 = new JMenuItem("Hello");
        JMenuItem item2 = new JMenuItem("Bye");
        item1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.out.println("pulsado Hello");
            }
        });
        item1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.out.println("pulsado Bye");
            }
        });
        this.add(item1);
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
