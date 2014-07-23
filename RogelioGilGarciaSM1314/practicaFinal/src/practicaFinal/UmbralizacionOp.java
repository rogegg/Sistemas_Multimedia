/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practicaFinal;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;
import sm.image.BufferedImageSampleIterator;

/**
 * Clase que define la operación de umbralización sobre imágenes. 
 * Esta operación satura las bandas de colores de la imagen de la 
 * siguiente forma: 
 *   valor (menor que) U ; valor = 0;
 *   valor (mayor o igual que) U ; valor = 255;
 * Se define U como el valor umbral.
 * @author Rogelio Gil García
 * @version 1.0
 */
public class UmbralizacionOp extends BufferedImageOpAdapter{
 
    private int umbral;

    /**
     * Constructor con el valor del umbral.
     * @param umbral valor umbral.
     */
    public UmbralizacionOp(int umbral) {
        this.umbral = umbral;
    }
    
     /**
     * Filtro de umbralización de imagen.
     * @param src Imagen original fuente.
     * @param dest Imagen resultado de aplicar el filtro.
     * @return Imagen resultado de aplicar el filtro
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    //Código de umbralización 
         if (dest == null) { 
            dest = createCompatibleDestImage(src, null); 
        } 
        WritableRaster destRaster = dest.getRaster(); 
 
         for (BufferedImageSampleIterator it = new BufferedImageSampleIterator(src); it.hasNext();) { 
                    BufferedImageSampleIterator.SampleData sample = it.next()  ;
                    if(sample.value<umbral) sample.value = 0;
                    else    sample.value = 255;
                    destRaster.setSample(sample.col, sample.row, sample.band, sample.value);                  
            } 
        return dest;        
    } 


}
