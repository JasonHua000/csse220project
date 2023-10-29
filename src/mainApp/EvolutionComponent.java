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
    private int x,y,xLimit,yLimit,xWidth,yHeight;
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
    // TODO remove magic numbers
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

    public void drawAxes(Graphics2D g2){
      g2.drawRect(x, y, xLimit, yLimit);
      drawXDivisions(g2);
      drawYDivisions(g2);
    }

    public void drawXDivisions(Graphics2D g2){
      // TODO FIGURE OUT WHY IT DOESNT UPDATE WHEN NUM OF GENERATIONS CHANGES
      xWidth = xLimit - x;
      yHeight = yLimit + y;
      g2.translate(x, yHeight);
      int num = 0;
      for (int i = 0; i <= xWidth; i+= xWidth/10){
        String sNum = Integer.toString(num);
        g2.drawLine(i, -5, i, 5);
        g2.drawString(sNum, i, 20);
        num+=generations/10;
        if ((i+(xWidth/10))>=xWidth){
          xWidth = i;
        }
      }
      g2.translate(-x, -yHeight);
    }

    public void drawYDivisions(Graphics2D g2){
      yHeight = yLimit - y;
      g2.translate(x, y);
      int num = 100;
      for (int i = 0; i <= yHeight; i+= yHeight/10){
        String sNum = Integer.toString(num);
        g2.drawLine(5, i, -5, i);
        g2.drawString(sNum, -25, i+5);
        num-=10;
        if ((i+yHeight/10)>=yHeight){
          yHeight = i;
        }
        //System.out.println(i+","+yHeight);
      }
      g2.translate(-x, -y);
    }

    public void storeLines(Graphics2D g2){
      g2.translate(x, y);
      if (this.population.prevC!=null && this.population.nextC!=null){
        // int pX = (int)((double)(generationCount)*(((double)xWidth)/(double)generations));
        // int nX = (int)((double)(generationCount+1)*(((double)xWidth)/(double)generations));

        //Line of best fit
        int pX = calculateX(generationCount);
        int nX = calculateX(generationCount+1);
        int pY = calculateY(this.population.prevC.getFitnessScore()); 
        int nY = calculateY(this.population.nextC.getFitnessScore());

        //Line of average
        int pXAvg = pX;
        int nXAvg = nX;
        int pYAvg = calculateY(this.population.prevCAvg);
        int nYAvg = calculateY(this.population.nextCAvg);

        //Line of lowest
        int pXLow = pX;
        int nXLow = nX;
        int pYLow = calculateY(this.population.prevCLow.getFitnessScore());
        int nYLow = calculateY(this.population.nextCLow.getFitnessScore());

        if (nX<=xWidth){
          lineArray.add(new BestFitLine2D(pX, pY, nX, nY, pXAvg, pYAvg, nXAvg, nYAvg, pXLow, pYLow, nXLow, nYLow));
          g2.setStroke(new BasicStroke(1));
          g2.setColor(Color.black);
          g2.drawRect(0,0,xWidth,yHeight);
        }
      }
      g2.translate(-x,-y);
      drawLines(g2);
      // if (generationCount>=generations){
      //   g2.setColor(g2.getBackground());
      //   g2.drawPolyline(xPoints, yPoints, xPoints.length);
      // }
    }
    // public void drawBestLine(Graphics2D g2){
    //   g2.translate(x, y);
    //   if (generationCount!=-1 && this.population.prevC!=null){
    //     int pX = generationCount*((xWidth)/generations);
    //     int nX = (generationCount+1)*((xWidth)/generations);
    //     int pY = yHeight-(int)(this.population.prevC.getFitnessScore()*((yHeight)/100));
    //     int nY = yHeight-(int)(this.population.nextC.getFitnessScore()*((yHeight)/100));
    //     xPoints[generationCount]=pX;
    //     xPoints[generationCount+1]=nX;
    //     yPoints[generationCount]=pY;
    //     yPoints[generationCount+1]=nY;
    //     g2.drawPolyline(xPoints, yPoints, this.generations);
    //   }
    //   g2.translate(-x,-y);
    // }

    public int calculateY(double y){
      return (int)(yHeight-((double)y*((double)yHeight/100.0)));
    }

    public int calculateX(double x){
      return (int)(x*((double)xWidth/generations));
    }

    public void drawLines(Graphics2D g2){
      g2.translate(x, y);
      if (this.population.prevC!=null && this.population.nextC!=null){
        for (int i = 0; i < lineArray.size(); i++){
          //Line of best fit
          int pX = lineArray.get(i).getX1();
          int pY = lineArray.get(i).getY1();
          int nX = lineArray.get(i).getX2();
          int nY = lineArray.get(i).getY2();
          g2.setColor(Color.green);
          g2.setStroke(new BasicStroke(5));
          g2.drawLine(pX, pY, nX, nY);

          //Line of avg
          pX = lineArray.get(i).getX1avg();
          pY = lineArray.get(i).getY1avg();
          nX = lineArray.get(i).getX2avg();
          nY = lineArray.get(i).getY2avg();
          g2.setColor(Color.orange);
          g2.drawLine(pX, pY, nX, nY);


          //Line of lowest
          //TODO FIGURE OUT LOGIC FOR WHY THIS IS SO JAGGED; Assumably, u can use the array in such a way that the newest chromosome is preserved, and then the previous index where the last chromosome was preserved can be used to find the initial x,y, with the current chromsome being the final x,y. this may require restructuring of linearray rn.
          pX = lineArray.get(i).getX1low();
          pY = lineArray.get(i).getY1low();
          nX = lineArray.get(i).getX2low();
          nY = lineArray.get(i).getY2low();
          g2.setColor(Color.red);
          g2.drawLine(pX, pY, nX, nY);

        }
      }
      g2.translate(-x,-y);
    }
}