package uk.ac.ecs.lk7g14.ch4;

import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.pixel.statistics.HistogramModel;
import org.openimaj.math.statistics.distribution.MultidimensionalHistogram;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            URL[] imageURLs = new URL[]{
                new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist1.jpg"),
                new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist2.jpg"),
                new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist3.jpg")
            };

            List<MultidimensionalHistogram> histograms = new ArrayList<MultidimensionalHistogram>();
            HistogramModel model = new HistogramModel(4, 4, 4);

            for (URL u : imageURLs) {
                model.estimateModel(ImageUtilities.readMBF(u));
                histograms.add(model.histogram.clone());
            }

            double closestDistance = Double.MAX_VALUE;
            URL[] closestImages = new URL[2];
            for(int i=0; i < histograms.size(); i++) {
                for(int j=(i+1); j < histograms.size(); j++) {
                    double distance = histograms.get(i).compare(histograms.get(j), DoubleFVComparison.EUCLIDEAN);
                    //double distance = histograms.get(i).compare(histograms.get(j), DoubleFVComparison.INTERSECTION);
                    if(distance < closestDistance) {
                        closestDistance = distance;
                        closestImages[0] = imageURLs[i];
                        closestImages[1] = imageURLs[j];
                    }
                }
            }

            System.out.println(closestDistance);
            for(URL imgURL : closestImages) {
                DisplayUtilities.display(ImageUtilities.readMBF(imgURL));
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }
}
