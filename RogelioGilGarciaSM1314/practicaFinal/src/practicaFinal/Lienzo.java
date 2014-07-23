package practicaFinal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
   
/**
 * Clase que implementa el espacio destinado para dibujar. Mediante esta clase
 * se gestionan las figuras dibujadas y la imagen de fondo del lienzo.
 * @author Rogelio Gil García
 * @version 2.0
 */
public class Lienzo extends javax.swing.JPanel {
    
    //Paleta de herramientas
    final static int PUNTO = 0;
    final static int LINEA = 1;
    final static int RECTANGULO = 2;
    final static int ELIPSE = 3;
    final static int CURVA = 4;
    final static int RECTANGULORED = 5;
    final static int DNEAR = 8; //Distancia hasta el objeto a seleccionar-> d > 0
    
    final static int RELLENO = 0;
    final static int BORDE = 1;
    
    final static int DEGRADADOH = 1;
    final static int DEGRADADOV = 2;
     
    private List<IRShape> vShape = new ArrayList();
    private IRShape s;
    
    private static int forma=PUNTO ; //Objeto seleccionado.
    private static boolean relleno=false; //Relleno de objetos.
    private static boolean editar=false; 
    private boolean needPControl;
    private static int selectColor=BORDE; //Borde o Relleno.
    private Point2D p,p2,p3;     //punto
    private Point2D dxy;
    private static Color color = Color.BLACK;
    private static Color colorRelleno = Color.WHITE;
    private static Color colorBorde = Color.BLACK;
    private static Stroke sk = new BasicStroke(1.0f);
    private static int grosor = 1;
    private static boolean disc = false;
    private static int degradado = 0;
    private String extension;

    private BufferedImage img;
    private BufferedImage imgSource;
    
    
    
    
    /**
     * Constructor sin parámetros de la clase <code>Lienzo</code>.
     */
    public Lienzo() {
        initComponents();
        dxy = new Point2D.Double();
        p = new Point2D.Double();
        p2 = new Point2D.Double();
        p3 = new Point2D.Double();
        needPControl = false;
        extension = null;
    }
    
      
    /**
     * Función que crea la forma seleccionada anteriormente a partir de dos 
     * puntos. Para ello, dependiendo de la forma, llamará al constructor de
     * una clase u otra. Seguidamente se le aplican las propiedades a estas figuras
     * @param p1 Punto 1.
     * @param p2 Punto 2.
     * @return devuelve la figura creada <code>IRShape</code>.
     */
    private IRShape createShape(Point2D p1, Point2D p2){
        if(p1==null || (p2==null && forma!=PUNTO)) return null;
        //System.out.println("Forma: "+forma);
        switch(forma){
            case PUNTO:
                this.s = new RLine(p1,p1);break;
            case LINEA:
                this.s = new RLine(p1,p2);break;
            case RECTANGULO:
                this.s = new RRectangle(p1, p2);break;
            case ELIPSE:
                this.s = new REllipse(p1,p2);break;
            case CURVA:
                this.s = new RCurve(p1,p2,p2);break;
            case RECTANGULORED:
                this.s = new RRoundRectangle(p1, p2);
                
                break;
                
        }
        if(s!=null){  
            s.setColorRelleno(colorRelleno);
            s.setColorBorde(colorBorde);
            s.setRelleno(relleno);
            s.setGrosor(grosor);
            s.setDiscontinuidad(disc);
            s.setDegradado(degradado);
        }
        return this.s;
    }

    
    
    /**
     * Función que devuelve la figura seleccionada. La figura contendrá
     * un punto, el cual se toma como referencia para la selección.
     * <p> Cuando hay varias figuras superpuestas se seleccionará la que 
     * queda encima.
     * @param p Punto contenido por la figura.
     * @return Figura seleccionada.
     */
    private IRShape getSelectedShape(Point2D p){
         //recorrer hacia atrás
        for(int i=vShape.size()-1;i>=0;i--){
                //if(x.contains(p))
            if(vShape.get(i).contains(p))
                    return vShape.get(i);
        }
        return null;
    }
           
    /**
     * Método que devuelve la figura seleccionada en este momento.
     * @return Devuelve la figura seleccionada.
     */
    public int getSelect(){ 
        return forma;
    }
    
    /**
     * Método que modifica la figura seleccionada.
     * @param s Nueva figura seleccionada.
     */
    public static void setSelect(int s){
        forma = s;
    }
    
    /**
     * Método que indica si el relleno está activado.
     * @return Devuelve true si el relleno está activo, false si no lo está.
     */
    public static boolean getRelleno(){
        return relleno;
    } 
    
    /**
     * Método para modificar el relleno.
     * @param x True si queremos activar el relleno, false si queremos desactivarlo.
     */
    public static void setRelleno(boolean x){
        relleno = x;
        degradado = 0;
    }
    
    /**
     * Método que selecciona el tipo de degradado. Tenemos varios tipos de 
     * degradado, son los siguientes:
     * 0 - sin degradado
     * 1 - degradado horizontal
     * 2 - degradado vertical
     * @param x Nuevo tipo de degradado.
     */
    public static void setDegradado(int x){
        degradado = x;
        relleno = true;
    }
    
    /**
     * Función para seleccionar la casilla de color a modificar:
     * 0 - Color de relleno.
     * 1 - Color de borde.
     * @param x Casilla de color seleccionada.
     */
    public static void setSelectColor(int x){
        selectColor = x;
    }
    
    /**
     * Devuelve la casilla de color seleccionada
     * @return 0 para color de relleno, 1 para color de borde.
     */
    public static int getSelectColor(){return selectColor;}
       
    /**
     * Devuelve el color usado en el lienzo.
     * @return color para usar en el lienzo.
     */
    public Color getColor(){ return color;}
    
    /**
     * Modifica el color de relleno o color de borde, dependiendo de la 
     * casilla que esté seleccionada en ese momento.
     * @param x Color con el que se va a dibujar la casilla.
     */
    public static void setColor(Color x){
        switch(selectColor){
            default: colorBorde = x; break;
            case RELLENO: colorRelleno = x; break;
            case BORDE: colorBorde = x; break;
        }
    }
    
    /**
     * Modifica el grosor del borde de las figuras.
     * @param x grosor del borde.
     */
    public static void setGrosor(int x){
        grosor = x;
    } 
    
    /**
     * Activa o desactiva el borde discontinuo.
     * @param x true para activar, false para desactivar.
     */
    public static void setDiscontinuo(boolean x){
        disc = x;
    }
    
    /**
     * Devuelve el elemento <code>Stroke</code> que se está usando en el lienzo.
     * @return Stroke en uso.
     */
    public Stroke getStroke(){
        return sk;
    }
    
    /**
     * Activa o desactiva el modo de edición, que nos permite modificar las figuras.
     * @param x true para activar el modo edición, false para desactivarlo.
     */
    public static void setEditar(boolean x){
        editar = x;
    }  
    /**
     * Método para consultar el estado del modo edición.
     * @return true si está activado el modo edición, false si está desactivado.
     */
    public static boolean getEditar(){
        return editar;
    }
    
    /**
     * Devuelve la imagen actual de fondo del lienzo.
     * @return Imagen actual.
     */
    public BufferedImage getImage(){
        return this.img;
    }
    
    /**
     * Modifica la imagen actual y se adecúa el lienzo al tamaño de esta imagen.
     * @param img Nueva imagen actual.
     */
    public void setImage(BufferedImage img){
        this.img = img;
        this.setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
    }
    
    /**
     * Devuelve la imagen original que no ha sido modificada aún por filtros 
     * o efectos.
     * @return Imagen original.
     */
    public BufferedImage getImageSource(){
        return imgSource;
    }
    
    /**
     * Modifica la imagen original y se adecúa el lienzo al tamaño de esta imagen.
     * @param img Nueva imagen original.
     */
    public void setImageSource(BufferedImage img){
        if(img!=null){
            this.setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
            this.imgSource = img;
            setImage(img);
        }
    }
        
    
    
    /**
     * Función que dibuja sobre el lienzo todas las figuras y atributos
     * seleccionados anteriormente.
     * Primero se pinta la imagen de fondo con <code>drawImage()</code> de
     * <code>Graphics2D</code>. Luego se recorre un vector de <code>IRShape</code>
     * dibujando cada una de las figuras con su propio método <code>draw</code>
     * @param g 
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        /*Pintamos imagen*/
        if(img!=null) 
            g2d.drawImage(img,0,0,this);
        
        /*Pintamos figuras*/
        for(IRShape s:vShape) {
            s.draw(g);                
        }          
    }
    
    /**
     * Función que crea una imagen con la imagen de fondo del lienzo y las 
     * figuras dibujadas en él. 
     * <p> Recibe la imagen de fondo como parámetro y devuelve la imagen ya
     * creada con las figuras.
     * @param image imagen de fondo.
     * @return imagen con las figuras ya pintadas.
     */
    public BufferedImage guardarShape(BufferedImage image){
        Graphics2D g2d = image.createGraphics();
        
        for(IRShape s:vShape) {
            s.draw(g2d);
        }
        return image;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        Point2D point = evt.getPoint();
        
        if(editar){
            //Seleccionamos la figura más cercana al punto. 
            this.s = getSelectedShape(point);
            if(this.s != null){
                double x=0,y=0;
                x = s.getX1();
                y = s.getY1();
                dxy.setLocation(x-evt.getX(), y-evt.getY());
                s.setIsSelect(true);
            }            
        }else{
             if(this.s != null){
                 s.setIsSelect(false);
             }
            if(getSelect()==CURVA && needPControl){
                if(vShape.get(vShape.size()-1) instanceof RCurve){
                    vShape.get(vShape.size()-1).update(p, evt.getPoint(), p2);
                }
            }
            else{
                p = point;
                this.s = createShape(this.p,this.p);
                this.vShape.add(this.s);
            }
        }
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if(editar){
            if(this.s != null){
                Point2D pt= new Point2D.Double(evt.getX()+dxy.getX(),evt.getY()+dxy.getY());
                s.setLocation(pt);
            }
        }else  
            if(this.s != null){
                if(forma == PUNTO){
                    s.update(p,p);
                }else{
                    if(getSelect()==CURVA && needPControl){
                        s.update(p,evt.getPoint(),p2);
                    }else if(getSelect()==CURVA && !needPControl){
                        this.p2 = evt.getPoint();
                        s.update(p,p2,p2);
                    }else{
                        this.p2 = evt.getPoint();
                        s.update(p,p2);
                        needPControl = false;
                    }       
                }
            }
        repaint();
                
    }//GEN-LAST:event_formMouseDragged

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if(editar){
             if(this.s != null)
                 s.setIsSelect(false);
        }else{
            if(getSelect()==CURVA && needPControl){
                if(s!=null){
                    s.update(p,evt.getPoint(),p2);  
                    needPControl = false;
                    repaint();
                }
            }else{
                needPControl = true;
                formMouseDragged(evt);            
            }
        }
    }//GEN-LAST:event_formMouseReleased

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
