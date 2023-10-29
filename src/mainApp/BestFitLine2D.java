package mainApp;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class BestFitLine2D {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int x1avg;
    private int y1avg;
    private int x2avg;
    private int y2avg;
    private int x1low;
    private int y1low;
    private int x2low;
    private int y2low;

    public BestFitLine2D(int x1, int y1, int x2, int y2, int x1avg, int y1avg, int x2avg, int y2avg, int x1low,
            int y1low, int x2low, int y2low) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x1avg = x1avg;
        this.y1avg = y1avg;
        this.x2avg = x2avg;
        this.y2avg = y2avg;
        this.x1low = x1low;
        this.y1low = y1low;
        this.x2low = x2low;
        this.y2low = y2low;
    }

    public int getX1avg() {
        return x1avg;
    }

    public void setX1avg(int x1avg) {
        this.x1avg = x1avg;
    }

    public int getY1avg() {
        return y1avg;
    }

    public void setY1avg(int y1avg) {
        this.y1avg = y1avg;
    }

    public int getX2avg() {
        return x2avg;
    }

    public void setX2avg(int x2avg) {
        this.x2avg = x2avg;
    }

    public int getY2avg() {
        return y2avg;
    }

    public void setY2avg(int y2avg) {
        this.y2avg = y2avg;
    }

    public int getX1low() {
        return x1low;
    }

    public void setX1low(int x1low) {
        this.x1low = x1low;
    }

    public int getY1low() {
        return y1low;
    }

    public void setY1low(int y1low) {
        this.y1low = y1low;
    }

    public int getX2low() {
        return x2low;
    }

    public void setX2low(int x2low) {
        this.x2low = x2low;
    }

    public int getY2low() {
        return y2low;
    }

    public void setY2low(int y2low) {
        this.y2low = y2low;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
}
