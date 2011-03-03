package ontologias.utils;

import es.ucm.fdi.gaia.ontobridge.OntoBridge;
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

/**
 * Clase que carga todas las imagenes de la carpeta de noticias en distintos tipos de datos
 * @author markel
 */
public class CargadorImagenes {

    /**
     * Lee los nombres de las imagenes desde el fichero de nombres
     * @return ArrayList de ImageIcons para introducir en un jLabel
     */
    public ArrayList<ImageIcon> loadImages() {

        ArrayList<ImageIcon> img = new ArrayList<ImageIcon>();

        try {
            img = readFileNamesFromFile("data/noticias/img/nombres.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CargadorImagenes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Imposible cargar imagenes. Fichero de nombres de imagenes no encontrado");
            return null;
        }
        return img;
    }

    public ArrayList<String> loadImageNames() {

        ArrayList<String> img = new ArrayList<String>();

        try {
            img = readImageNames("data/noticias/img/nombres.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CargadorImagenes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Imposible cargar imagenes. Fichero de nombres de imagenes no encontrado");
            return null;
        }
        return img;
    }

    private ArrayList<ImageIcon> readFileNamesFromFile(String fileName) throws FileNotFoundException {
        File archivo = new File(fileName);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<ImageIcon> names = new ArrayList<ImageIcon>();
        try {
            String linea = br.readLine();
            while(linea != null)
            {
                names.add(new ImageIcon("data/noticias/img/"+linea));
                linea = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(CargadorImagenes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return names;
    }

    private ArrayList<String> readImageNames(String fileName) throws FileNotFoundException {
        File archivo = new File(fileName);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> names = new ArrayList<String>();
        try {
            String linea = br.readLine();
            while(linea != null)
            {
                names.add(linea);
                linea = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(CargadorImagenes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return names;
    }

    /**
     * Introduce las imagenes recibidas en la ontologia, de forma que estas sean instancias de la clase Imagen
     * @param images
     */
    public void loadImagesInOntology(ArrayList<String> images){

        OntoBridge ob = Ontologia.getInstance();

        for(String img: images)
        {
            ob.createInstance("Imagen", img);
        }

        ob.save("files/Ontologia.owl");
    }

    public Iterator<String> LoadImageInfo(String image)
    {
        OntoBridge ob = Ontologia.getInstance();

        Iterator<String> it = ob.listBelongingClasses(image);
        return it;

    }

//    public static void main(String[] args){
//        CargadorImagenes loader = new CargadorImagenes();
//        loader.loadImagesInOntology(loader.loadImageNames());
//    }
}
