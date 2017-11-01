// TODO Excercise 1
package uk.ac.ecs.lk7g14.ch3;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.connectedcomponent.GreyscaleConnectedComponentLabeler;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.segmentation.FelzenszwalbHuttenlocherSegmenter;
import org.openimaj.image.segmentation.SegmentationUtilities;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        try {
            MBFImage input = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/sinaface.jpg"));
            input = ColourSpace.convert(input, ColourSpace.CIE_Lab);

            FloatKMeans cluster = FloatKMeans.createExact(2);
            float[][] imageData = input.getPixelVectorNative(new float[input.getWidth() * input.getHeight()][3]);
            FloatCentroidsResult result = cluster.cluster(imageData);

            float[][] centroids = result.centroids;
            for (float[] fs : centroids) {
                System.out.println(Arrays.toString(fs));
            }

            HardAssigner<float[],?,?> assigner = result.defaultHardAssigner();
            for (int y=0; y<input.getHeight(); y++) {
                for (int x=0; x<input.getWidth(); x++) {
                    float[] pixel = input.getPixelNative(x, y);
                    int centroid = assigner.assign(pixel);
                    input.setPixelNative(x, y, centroids[centroid]);
                }
            }

            input = ColourSpace.convert(input, ColourSpace.RGB);

            GreyscaleConnectedComponentLabeler labeler = new GreyscaleConnectedComponentLabeler();
            List<ConnectedComponent> components = labeler.findComponents(input.flatten());
            int i = 0;
            for (ConnectedComponent comp : components) {
                if (comp.calculateArea() < 50)
                    continue;
                input.drawText("Point:" + (i++), comp.calculateCentroidPixel(), HersheyFont.TIMES_MEDIUM, 20);
            }

            /*FelzenszwalbHuttenlocherSegmenter labeler = new FelzenszwalbHuttenlocherSegmenter();
            List<ConnectedComponent> components = labeler.segment(input);
            SegmentationUtilities.renderSegments(input, components);*/

            DisplayUtilities.display(input);
        } catch(Exception e) {
            System.err.println(e);
        }
    }
}
