package mainApp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class EvolutionComponent extends JComponent {
    public Population population;
    private int populationSize;
    private int generations = 100;
    private int elitism;
    private int genomeLength;
    private double mutationRate;
    private String selection;
    private Boolean crossover;
    private int x,y,xLimit,yLimit,xLimitClone,yLimitClone;
    public int generationCount;
    public ArrayList<BestFitLine2D> lineArray = new ArrayList<BestFitLine2D>();
    

    public EvolutionComponent() {
		this.population = new Population();
    }

    public int getPopulationSize() {
      return populationSize;
    }

    public void setPopulationSize(int populationSize) {
      this.populationSize = populationSize;
    }

    public int getGenerations() {
      return generations;
    }

    public void setGenerations(int generations) {
      this.generations = generations;
    }

    public int getElitism() {
      return elitism;
    }

    public void setElitism(int elitism) {
      this.elitism = elitism;
    }

    public int getGenomeLength() {
      return genomeLength;
    }

    public void setGenomeLength(int genomeLength) {
      this.genomeLength = genomeLength;
    }

    public double getMutationRate() {
      return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
      this.mutationRate = mutationRate;
    }

    public String getSelection() {
      return selection;
    }

    public void setSelection(String selection) {
      this.selection = selection;
    }

    public Boolean getCrossover() {
      return crossover;
    }

    public void setCrossover(Boolean crossover) {
      this.crossover = crossover;
    }

    public void setAll(String populationSize, String selection, String mutationRate, boolean crossover, String generations, String genomeLength, String elitism){
      this.setPopulationSize(Integer.parseInt(populationSize));
      this.setSelection(selection);
      this.setMutationRate(Integer.parseInt(mutationRate));
      this.setCrossover(crossover);
      this.setGenerations(Integer.parseInt(generations));
      this.setGenomeLength(Integer.parseInt(genomeLength));
      this.setElitism(Integer.parseInt(elitism));
      this.lineArray.removeAll(lineArray);
      this.population = new Population(this.populationSize, this.genomeLength);
    }

    public void handleSelection(){
      String s = selection;
      if (s.equals("Truncation")){
        this.population.truncationSelection(mutationRate);
      }
      else if (s.equals("Roulette")){
        //TODO Add roulette selection
      }
      else if (s.equals("Rank")){
        //TODO Add rank selection
      }
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      x = (int)(0.04*this.getWidth());
      y = (int)(0.08*this.getHeight());
      xLimit = (int)(this.getWidth()*0.92);
      yLimit = (int)(this.getHeight()*0.82);  
      this.drawOn(g2);
      this.storeLines(g2);
    }

    public void drawOn(Graphics2D g2){
      drawAxes(g2);
    }

    public void drawAxes(Graphics2D g2) {
        g2.drawRect(x, y, xLimitClone, yLimitClone);
        drawXDivisions(g2);
        drawYDivisions(g2);
    }


    public void drawXDivisions(Graphics2D g2){
      // TODO FIGURE OUT WHY IT DOESNT UPDATE WHEN NUM OF GENERATIONS CHANGES
      xLimitClone = xLimit - x;
      yLimitClone = yLimit + y;
      g2.translate(x, yLimitClone);
      int num = 0;
      for (int i = 0; i <= xLimitClone; i+= xLimitClone/10){
        String sNum = Integer.toString(num);
        g2.drawLine(i, -5, i, 5);
        g2.drawString(sNum, i, 20);
        num+=generations/10;
        if ((i+(xLimitClone/10))>=xLimitClone){
          xLimitClone = i;
        }
      }
      g2.translate(-x, -yLimitClone);
    }

    public void drawYDivisions(Graphics2D g2){
      yLimitClone = yLimit - y;
      g2.translate(x, y);
      int num = 100;
      for (int i = 0; i <= yLimitClone; i+= yLimitClone/10){
        String sNum = Integer.toString(num);
        g2.drawLine(5, i, -5, i);
        g2.drawString(sNum, -25, i+5);
        num-=10;
        if ((i+yLimitClone/10)>=yLimitClone){
          yLimitClone = i;
        }
        //System.out.println(i+","+yLimitClone);
      }
      g2.translate(-x, -y);
    }

    public void storeLines(Graphics2D g2){
      g2.translate(x, y);
      if (this.population.prevC!=null && this.population.nextC!=null){
        int pX = (int)((double)(generationCount)*(((double)xLimitClone)/(double)generations));
        int nX = (int)((double)(generationCount+1)*(((double)xLimitClone)/(double)generations));
        int pY = (int)((double)yLimitClone-(double)((double)this.population.prevC.getFitnessScore()*(((double)yLimitClone)/100.0)));
        int nY = (int)((double)yLimitClone-(double)((double)this.population.nextC.getFitnessScore()*(((double)yLimitClone)/100.0)));
        if (nX<=xLimitClone){
          lineArray.add(new BestFitLine2D(pX, pY, nX, nY));
          g2.setStroke(new BasicStroke(1));
          g2.setColor(Color.black);
          g2.drawRect(0,0,xLimitClone,yLimitClone);
        }
      }
      g2.translate(-x,-y);
      g2.setColor(Color.green);
      g2.setStroke(new BasicStroke(5));
      drawLines(g2);
      // if (generationCount>=generations){
      //   g2.setColor(g2.getBackground());
      //   g2.drawPolyline(xPoints, yPoints, xPoints.length);
      // }
    }
    // public void drawBestLine(Graphics2D g2){
    //   g2.translate(x, y);
    //   if (generationCount!=-1 && this.population.prevC!=null){
    //     int pX = generationCount*((xLimitClone)/generations);
    //     int nX = (generationCount+1)*((xLimitClone)/generations);
    //     int pY = yLimitClone-(int)(this.population.prevC.getFitnessScore()*((yLimitClone)/100));
    //     int nY = yLimitClone-(int)(this.population.nextC.getFitnessScore()*((yLimitClone)/100));
    //     xPoints[generationCount]=pX;
    //     xPoints[generationCount+1]=nX;
    //     yPoints[generationCount]=pY;
    //     yPoints[generationCount+1]=nY;
    //     g2.drawPolyline(xPoints, yPoints, this.generations);
    //   }
    //   g2.translate(-x,-y);
    // }

    public void drawLines(Graphics2D g2){
      g2.translate(x, y);
      if (this.population.prevC!=null && this.population.nextC!=null){
        for (int i = 0; i < lineArray.size(); i++){
          int pX = lineArray.get(i).getX1();
          int pY = lineArray.get(i).getY1();
          int nX = lineArray.get(i).getX2();
          int nY = lineArray.get(i).getY2();
          g2.drawLine(pX, pY, nX, nY);
        }
      }
      g2.translate(-x,-y);
    }
}