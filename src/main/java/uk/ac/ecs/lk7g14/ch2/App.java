package uk.ac.ecs.lk7g14.ch2;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.geometry.shape.Ellipse;

import java.net.URL;

/**
 * OpenIMAJ Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        try {
            MBFImage image = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/sinaface.jpg"));
            System.out.println(image.colourSpace);
            // FImage - greyscale image, each pixel a value between 0 and 1
            // RGB image with 3 FImages - 1 FImage for each red, green, blue colour

            DisplayUtilities.createNamedWindow("sinaface");

            DisplayUtilities.displayName(image, "sinaface");
            DisplayUtilities.displayName(image.getBand(0), "sinaface");

            MBFImage clone = image.clone();
            for(int y=0; y<image.getHeight(); y++) {
                for(int x=0; x<image.getWidth(); x++) {
                    clone.getBand(1).pixels[y][x] = 0;
                    clone.getBand(2).pixels[y][x] = 0;
                }
            }
            DisplayUtilities.displayName(clone, "sinaface");

            clone.getBand(1).fill(0f);
            clone.getBand(2).fill(0f);

            image.processInplace(new CannyEdgeDetector());
            DisplayUtilities.displayName(image, "sinaface");


            image.drawShapeFilled(new Ellipse(700f, 450f, 20f, 10f, 0f), RGBColour.WHITE);
            image.drawShape(new Ellipse(700, 450, 20, 10, 0f), 5, RGBColour.MAGENTA);
            image.drawShapeFilled(new Ellipse(650f, 425f, 25f, 12f, 0f), RGBColour.WHITE);
            image.drawShape(new Ellipse(650f, 425f, 25f, 12f, 0f), 5, RGBColour.MAGENTA);
            image.drawShapeFilled(new Ellipse(600f, 380f, 30f, 15f, 0f), RGBColour.WHITE);
            image.drawShape(new Ellipse(600f, 380f, 30f, 15f, 0f), 5, RGBColour.MAGENTA);
            image.drawShapeFilled(new Ellipse(500f, 300f, 100f, 70f, 0f), RGBColour.WHITE);
            image.drawShape(new Ellipse(500f, 300f, 100f, 70f, 0f), 5, RGBColour.MAGENTA);
            image.drawText("OpenIMAJ is", 425, 300, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
            image.drawText("Awesome", 425, 330, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
            DisplayUtilities.displayName(image, "sinaface");
        } catch(Exception e) {
            System.err.println("Error fetching image URL");
        }
    }
}

