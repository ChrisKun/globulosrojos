package compra;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PonImagen extends JFrame { 

JPanel jp = new JPanel(); 
Container conta = getContentPane(); 
Toolkit tk = Toolkit.getDefaultToolkit(); 
Image img = tk.getImage("tomcat.gif"); 

public PonImagen() { 
conta.add(jp); 
} 

public static void main(String[] args) { 
PonImagen ponimg = new PonImagen(); 
ponimg.setSize(300, 200); 
ponimg.setTitle("Prueba de imagenes"); 
ponimg.setVisible(true); 
ponimg.addWindowListener(new WindowAdapter() { 
public void windowClosing(WindowEvent e)
{ 
System.exit(0); 
} 
}); 
} 

public void paint(Graphics g) { 
g.drawImage(img, 10, 50, this); 
} 

} 