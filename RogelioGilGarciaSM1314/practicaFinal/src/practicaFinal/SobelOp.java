
package practicaFinal;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import sm.image.BufferedImageOpAdapter;
import sm.image.BufferedImagePixelIterator;
import sm.image.KernelProducer;

/**
 * Clase que implementa un filtro de imagen, define la operación Sobel sobre imágenes.
 * Esta operación detecta los bordes de las figuras de la imagen,
 * dejando un fondo negro y unas líneas blancas para los bordes.
 * @author Rogelio Gil García
 * @version 1.0
 */
public class SobelOp extends BufferedImageOpAdapter {

    
    /**
     * Filtro de detección de bordes para imágenes.
     * @param src Imagen original fuente.
     * @param dest Imagen resultado de aplicar el filtro.
     * @return Imagen resultado de aplicar el filtro
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            throw new RuntimeException("src a null");
        }
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }

        //GradienteX
        Kernel kx = KernelProducer.createKernel(KernelProducer.TYPE_SOBELX_3x3);
        ConvolveOp cop = new ConvolveOp(kx);
        BufferedImage gx = cop.filter(src, null);

        //GradienteY
        Kernel ky = KernelProducer.createKernel(KernelProducer.TYPE_SOBELY_3x3);
        cop = new ConvolveOp(ky);
        BufferedImage gy = cop.filter(src, null);

        BufferedImagePixelIterator itx = new BufferedImagePixelIterator(gx);
        BufferedImagePixelIterator ity = new BufferedImagePixelIterator(gy);
        int sx = 0;
        int sy = 0;
        int max = 0;
        int magnitud;
        int matriz[][] = new int[gx.getWidth()][gx.getHeight()];
        for (BufferedImagePixelIterator it = new BufferedImagePixelIterator(src); it.hasNext();) {
            BufferedImagePixelIterator.PixelData pixel = it.next();
            BufferedImagePixelIterator.PixelData pixelx = itx.next();
            BufferedImagePixelIterator.PixelData pixely = ity.next();

            sx = pixelx.sample[0] + pixelx.sample[1] + pixelx.sample[2];
            sy = pixely.sample[0] + pixely.sample[1] + pixely.sample[2];

            magnitud = (int) Math.hypot(sx, sy);
            matriz[pixel.col][pixel.row] = magnitud;
            //magnitud = ImageTools.clampRange((int)magnitud, 0, 255);
            //Calculamos max
            if (max < magnitud) {
                max = magnitud;
            }

        }
        if (max != 0) {
            //Normalizamos los valores entre 0 y 255
            for (itx = new BufferedImagePixelIterator(gx); itx.hasNext();) {
                BufferedImagePixelIterator.PixelData pixel = itx.next();
                magnitud = (int) ((matriz[pixel.col][pixel.row] / (max * 1.0)) * 255);
                Color c = new Color(magnitud, magnitud, magnitud);
                dest.setRGB(pixel.col, pixel.row, c.getRGB());
            }
        }
        return dest;
    }

}
