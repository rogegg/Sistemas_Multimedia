package practicaFinal;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Clase que define una línea recta a partir de dos puntos,
 * el inicial y el final.
 * @author Rogelio Gil García
 * @version 1.0
 */
public class RLine extends Line2D.Double implements IRShape{
    private Rectangle2D marco;
    private Color colorBorde;
    private Stroke sk;
    private float[] disc;
    private boolean isDisc;
    private int grosor;
    private boolean isSelect;
    private Stroke skSelect;
    
    
    
    /**
     * Constructor para la clase <code>RLine</code>, una línea recta.
     * @param p1 - punto inicial.
     * @param p2 - punto final.
     */
    public RLine(Point2D p1, Point2D p2){
        colorBorde = Color.BLACK;
        sk = new BasicStroke(1.0f);
        disc= new float[]{5,5,5,5};
        isDisc = false;
        grosor = 1;
        isSelect = false;
        skSelect = new BasicStroke(
                        grosor,                    // grosor
                        BasicStroke.CAP_BUTT,      // terminación: recta
                        BasicStroke.JOIN_ROUND,    // unión: redondeada 
                        1f,                        // ángulo: 1 grado
                        disc, // línea, espacio, línea , espacio
                        2                          // fase
             );
    }
    
    @Override
    public void updateStroke(){
        if(isDisc){
            sk = new BasicStroke(
                        grosor,                    // grosor
                        BasicStroke.CAP_BUTT,      // terminación: recta
                        BasicStroke.JOIN_ROUND,    // unión: redondeada 
                        1f,                        // ángulo: 1 grado
                        disc, // línea, espacio, línea , espacio
                        2                          // fase
             );
        }else
            sk = new BasicStroke(grosor);
    }
      
    @Override
    public void setColorRelleno(Color c) {
    }
    
    @Override
    public void setColorBorde(Color c) {
        colorBorde = c;
    }
    
    @Override
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        updateStroke();
        if(isSelect){
            marco = this.getBounds2D();
            g2d.setPaint(Color.YELLOW);
            g2d.setStroke(skSelect);
            g2d.draw(marco);
        }
        
       
        g2d.setPaint(colorBorde);
        g2d.setStroke(sk);
        g2d.draw(this);
    }

    @Override
    public boolean contains(Point2D p) {
        if (this.getP1().equals(this.getP2()))
            return this.getP1().distance(p)<=5;
        else
            return ptLineDist(p)<= 5;
    }

    @Override
    public void setLocation(Point2D p) {
       double dx = p.getX() - this.getX1();
       double dy = p.getY() - this.getY1();
       Point2D p2 = new Point2D.Double(this.getX2()+dx, this.getY2()+dy);
       setLine(p,p2);
    }

    @Override
    public double getX1() { return super.getX1();}

    @Override
    public double getY1() { return super.getY1();}
    
    @Override
    public double getX2() { return super.getX2();}

    @Override
    public double getY2() { return super.getY2();}

    @Override
    public void setRelleno(boolean r) {
    }

    @Override
    public void update(Point2D p1, Point2D p2) {
        super.setLine(p1,p2);
    }

    @Override
    public void update(Point2D p1, Point2D p2, Point2D p3) {
    }

    @Override
    public void setGrosor(int x) {
        grosor = x;
    }

    @Override
    public void setDiscontinuidad(boolean x) {
        
        if(x){
            isDisc = true;
            disc[0] = 5; disc[2]= 5;
            disc[1] = 5; disc[3]= 5;
        }else{
            isDisc = false;
            disc[0] = 5; disc[2]= 5;
            disc[1] = 0; disc[3]=0;
        }
    }

    @Override
    public void setDegradado(int x) {
    }

    @Override
    public void setIsSelect(boolean x) {
        isSelect = x;
    }

}
