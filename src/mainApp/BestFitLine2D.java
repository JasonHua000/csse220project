package mainApp;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class BestFitLine2D {
    private double bestFitness, avgFitness, lowFitness;

    public double getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }

    public double getAvgFitness() {
        return avgFitness;
    }

    public void setAvgFitness(double avgFitness) {
        this.avgFitness = avgFitness;
    }

    public double getLowFitness() {
        return lowFitness;
    }

    public void setLowFitness(double lowFitness) {
        this.lowFitness = lowFitness;
    }

    public BestFitLine2D(double bestFitness, double avgFitness, double lowFitness) {
        this.bestFitness = bestFitness;
        this.avgFitness = avgFitness;
        this.lowFitness = lowFitness;
    }

}
