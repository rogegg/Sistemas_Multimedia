
package practicaFinal;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.format.YUVFormat;
import javax.media.util.BufferToImage;

/**
 * Ventana que hereda de <code>JInternalFrame</code> y define los métodos necesarios
 * para la grabación de video con webcam mediante la JMF.
 * @author Rogelio Gil García
 * @version 1.0
 */
public class VentanaInternaCamara extends javax.swing.JInternalFrame {
    private Player p;
    /**
     * Constructor para la ventana de la webcam.
     */
    private VentanaInternaCamara() {
        initComponents();

        try {  
            /*
            CaptureDeviceInfo deviceInfo; 
            List<CaptureDeviceInfo> deviceList =
                  CaptureDeviceManager.getDeviceList(new YUVFormat()); 
            deviceInfo = deviceList.get(0);
            */
             CaptureDeviceInfo deviceInfo; 
             String dName="vfw:Microsoft WDM Image Capture (Win32):0";
             deviceInfo = CaptureDeviceManager.getDevice(dName); 


            MediaLocator ml = deviceInfo.getLocator(); 
            p = Manager.createRealizedPlayer(ml); 
            Component  areaVisual = p.getVisualComponent(); 
            if(areaVisual!=null) this.add(areaVisual, 
                    java.awt.BorderLayout.CENTER);    
            Component  panelControl = p.getControlPanelComponent(); 
            if(panelControl!=null) this.add(panelControl,
                    java.awt.BorderLayout.CENTER);    
            p.start();
        }catch(Exception e){System.err.println("Error al crear la ventana de webcam");}
 
    }
    
    
    /**
     * Método que devuelve la instancia de la clase.
     * Este método llama internamiente al constructor.
     * @return devuelve la instancia que crea el constructor.
     */
    public static VentanaInternaCamara getInstance() {
        VentanaInternaCamara v= new VentanaInternaCamara();
        return v.p!=null?v:null;  
    }
    
    
    
    /**
     * Métodoo para capturar una imagen de una ventana tanto en reproducción
     * de vídeo como de una ventana webcam.
     * @return Imagen capturada.
     */
    public BufferedImage getFrame(){
        FrameGrabbingControl fgc;
        String claseCtr = "javax.media.control.FrameGrabbingControl";
        fgc = (FrameGrabbingControl)p.getControl(claseCtr );
        Buffer bufferFrame = fgc.grabFrame();
        BufferToImage bti;
        bti=new BufferToImage((VideoFormat)bufferFrame.getFormat());
        Image img = bti.createImage(bufferFrame);
        return (BufferedImage)img;
    }
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
         p.stop();
         p.close();
         p.deallocate();
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        p.start();
    }//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
