/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practicaFinal;

import java.io.File;
import javax.swing.filechooser.FileFilter;


/**
 * Clase que implementa los filtros para los tipos de archivos aceptados por 
 * <code>VentanaPrincipal</code>. Clase que hereda de <code>FileFilter</code>
 * @author Rogelio Gil García
 * @version 1.0
 */
public class RFileFilter extends FileFilter{
    
    final static int IMAGEN = 0;
    final static int SONIDO = 1;
    final static int VIDEO = 2;
    
    private String descripcion;
    private int tipo;
    
    
    /**
     * Constructor con parámetros. Cuando llamamos a este constructor
     * le pasamos una cadena de caracteres que será la descripción de cada
     * filtro. Esta descripción se usará en las pantallas de selección de 
     * archivos por ejemplo.
     * @param d Descripción del filtro.
     */
    public RFileFilter(String d){
        this.descripcion = d;
    }
    
    /**
     * Método que devuelve la descripción del filtro.
     * @return descripción.
     */
    @Override
    public String getDescription(){
        return descripcion;
    }

    
    /**
     * Método obligatorio a implementar de la clase <code>FileFilter</code>.
     * Especifica las reglas para que un fichero sea válido. Tenemos tres
     * grupos de archivos dependiendo de su extensión.
     * Archivos de imagen - jpg, png, jpeg y gif.
     * Archivos de audio - wav, mp3, midi y mid.
     * Archivos de vídeo - avi, mpeg y mp4.
     * Por ejemplo en las pantallas de selección de archivos sólo aparecerán
     * los ficheros que cumplan estas reglas.
     * @param f Fichero del que vamos a obtener la extensión.
     * @return true para ficheros soportados, false para ficheros no soportados.
     */
    @Override
    public boolean accept(File f) {
        
        if(f.isDirectory())return true;
        String name = f.getName().toLowerCase();
        if(descripcion == "Archivos de imagen"){
            tipo = IMAGEN;
            return (name.endsWith("jpg")||
                    name.endsWith("png")||
                    name.endsWith("jpeg")||
                    name.endsWith("gif")
                    );   
        }else if(descripcion == "Archivos de audio"){
            tipo = SONIDO;
            return (name.endsWith("wav")||
                    name.endsWith("mp3")||
                    name.endsWith("midi")||
                    name.endsWith("mid")
                    ); 
        }else if(descripcion == "Archivos de vídeo"){
            tipo = VIDEO;
            return (name.endsWith("avi")||
                    name.endsWith("mpeg")||
                    name.endsWith("mp4")
                    ); 
        }else{return false;}
    }
    
    /**
     * Devuelve el tipo de archivo:
     * 0 - Imagen
     * 1 - Audio
     * 2 - Vídeo
     * @return Entero que representa a un tipo de archivo.
     */
    public int getTipo(){
        return tipo;
    }
}
