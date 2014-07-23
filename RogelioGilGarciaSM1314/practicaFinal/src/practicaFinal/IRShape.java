package practicaFinal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

/**
 * Interface que estandariza clases destinadas a crear figuras 2D
 * @author Rogelio Gil García
 * @version 1.0
 */
interface IRShape {
     
    
    /**
     * Método para dibujar una figura. Antes de llamar a la función debe 
     * actualizar los atributos de la figura sobre el objeto <code>Graphics</code>.
     * @param g objeto <code>Graphics</code> a dibujar.
     */
    public void draw(Graphics g);
    
    
    
    /**
     * Método que actualiza las propiedades de los bordes de las figuras.
     * Este método se pensó para actualizar el borde con las propiedades 
     * establecidas antes de llamar a <code>draw(g)</code>
     */
    public void updateStroke();
    
    
    /**
     * Método que modifica la posición de las figuras que necesitan 2 puntos.
     * @param p1 punto 1 de la nueva posición.
     * @param p2 punto 2 de la nueva posición.
     */
    public void update(Point2D p1,Point2D p2);
    
    /**
     * Método que modifica la posición de las figuras que necesitan 3 puntos.
     * @param p1 punto 1 de la nueva posición.
     * @param p2 punto 2 de la nueva posición.
     * @param p3 punto 3 de la nueva posición.
     */
    public void update(Point2D p1,Point2D p2,Point2D p3);
    
    /**
     * Método que devuelve si un punto está contenido en la figura. La principal
     * función de este método será para seleccionar una figura mediante el ratón.
     * @param p 
     * @return 
     */
    public boolean contains(Point2D p);
    
    /**
     * Método para mover las figuras a partir de un punto de referencia.
     * @param p punto de referencia para mover la figura.
     */
    public void setLocation(Point2D p);
    
    /**
     * Devuelve la coordenada X del primer punto de la figura.
     * @return coordenada X del punto 1 de la figura.
     */
    public double getX1();
    
    /**
     * Devuelve la coordenada Y del primer punto de la figura.
     * @return coordenada Y del punto 1 de la figura.
     */
    public double getY1();
    
    /**
     * Devuelve la coordenada X del segundo punto de la figura.
     * @return coordenada X del punto 2 de la figura.
     */
    public double getX2();
    
    /**
     * Devuelve la coordenada Y del segundo punto de la figura.
     * @return coordenada Y del punto 2 de la figura.
     */
    public double getY2();
    
    /**
     * Método para modificar el color de relleno de la figura.
     * @param c Nuevo color de relleno de la figura.
     */
    public void setColorRelleno(Color c);
    
    /**
     * Método para modificar el color del borde de la figura.
     * @param c Nuevo color del borde de la figura.
     */
    public void setColorBorde(Color c);
    
    /**
     * Método para activar y desactivar la opción de relleno de la figura.
     * true para activar.
     * false para desactivar.
     * @param r booleano que activa o desactiva la opción de relleno.
     */
    public void setRelleno(boolean r);
    
    /**
     * Método para modificar el grosor del trazo de la figura.
     * @param x Entero que indica el nuevo grosor de la figura.
     */
    public void setGrosor(int x);
    
    /**
     * Método para activar o desactivar el trazo discontinuo de la figura.
     * true para activar el trazo discontinuo.
     * false para desactivar el trazo discontinuo.
     * @param x booleano para activar o desactivar la discontinuidad de trazo.
     */
    public void setDiscontinuidad(boolean x);
    
    /**
     * Método para seleccionar el tipo de degradado de la figura.
     * 0 - sin degradado.
     * 1 - degradado horizontal.
     * 2 - degradado vertical.
     * @param x Entero que para indicar el tipo de degradado.
     */
    public void setDegradado(int x);
    
    /**
     * Método que indica si la figura está seleccionada para modificarla o no.
     * @param x true si está seleccionada, false si no está seleccionada.
     */
    public void setIsSelect(boolean x);
    
}
