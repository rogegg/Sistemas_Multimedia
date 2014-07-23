package practicaFinal;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;


/**
 * Clase que define una curva cuadrática paramétrica. Esta curva necesitará 
 * tres puntos, un punto inicial, un punto final y un punto de control que 
 * indicará la curva de esta figura.
 * @author Rogelio Gil García
 * @version 1.0
 */
public class RCurve extends QuadCurve2D.Double implements IRShape{
    private Rectangle2D marco;
    private Color colorBorde,colorRelleno;
    private boolean relleno;
    private Stroke sk;
    private float[] disc;
    private boolean isDisc;
    private float grosor;
    private int degradado;
    private boolean isSelect;
    private Stroke skSelect;
    
    
    /**
     * Constructor para la clase <code>RCurve</code>, una curva cuadrática.
     * @param p1 - punto inicial.
     * @param p2 - punto final.
     * @param p3 - punto de control.
     */
    public RCurve(Point2D p1, Point2D p2, Point2D p3){
        colorBorde = Color.BLACK;
        colorRelleno = Color.BLACK;
        relleno = false;
        sk = new BasicStroke(1.0f);
        disc = new float[]{5,5,5,5};
        isDisc = false;
        grosor = 1;
        degradado = 0;
        isSelect = false;
        skSelect = new BasicStroke(
                        grosor,                         // grosor
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
                        grosor,                         // grosor
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
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        updateStroke();
        if(isSelect){
            marco = this.getBounds2D();
            g2d.setPaint(Color.YELLOW);
            g2d.setStroke(skSelect);
            g2d.draw(marco);
        }
        if (relleno){
            g2d.setPaint(colorRelleno);
            if (degradado==1){
                Point2D pg1= new Point2D.Double(this.getX1(),0);
                Point2D pg2= new Point2D.Double(
                        this.getX1()+this.getBounds2D().getWidth(),0);
                GradientPaint gradient = new GradientPaint(pg1, colorBorde,
                                                            pg2, colorRelleno);
                g2d.setPaint(gradient);
            }else if(degradado == 2){
                Point2D pg1= new Point2D.Double(0,this.getBounds2D().getY());
                Point2D pg2= new Point2D.Double(0,
                        this.getBounds2D().getY()+getBounds2D().getHeight());
                GradientPaint gradient = new GradientPaint(pg1, colorBorde,
                                                            pg2, colorRelleno);
                g2d.setPaint(gradient);
            }
            g2d.fill(this);
        }
        g2d.setPaint(colorBorde);
        g2d.setStroke(sk);
        g2d.draw(this);
    }

    @Override
    public boolean contains(Point2D p) {
        return super.contains(p);
    }

    @Override
    public void setLocation(Point2D p) {  
       double dx = p.getX() - this.getX1();
       double dy = p.getY() - this.getY1();
       Point2D p2 = new Point2D.Double(this.getX2()+dx, this.getY2()+dy);
       Point2D pc = new Point2D.Double(this.getCtrlPt().getX()+dx, this.getCtrlPt().getY()+dy);
        setCurve(p,pc, p2);
    }

    @Override
    public double getX1() { return super.getX1();}

    @Override
    public double getY1() {return super.getY1();}

    @Override
    public double getX2() {return super.getX2();}
        
    @Override
    public double getY2(){return super.getY2();}

    @Override
    public void setColorRelleno(Color c) {
        colorRelleno=c;
    }

    @Override
    public void setColorBorde(Color c) {
        colorBorde=c;
    }

    @Override
    public void setRelleno(boolean r) {
        relleno = r;
    }

    @Override
    public void update(Point2D p1, Point2D p2) {       
    }
    
    /**
     * Actualizamos la localización de los puntos de la curva.
     * @param p1 Punto inicial.
     * @param p2 Punto de control.
     * @param p3 Punto final.
     */
    @Override
    public void update(Point2D p1, Point2D p2, Point2D p3) {      
        setCurve(p1, p2, p3);
    }

    @Override
    public void setGrosor(int x) {
        grosor = x;
    }

    
    @Override
    public void setDiscontinuidad(boolean x) {
        if(x){
            isDisc=true;
            disc[1] = 5; disc[3]=5;
        }else{
            isDisc=false;
            disc[1] = 0; disc[3]=0;
        }
    }

    @Override
    public void setDegradado(int x) {
        degradado = x;
    }

    @Override
    public void setIsSelect(boolean x) {
         isSelect = x;
    }
    
}
