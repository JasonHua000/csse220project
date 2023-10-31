package mainApp;

import javax.swing.SwingWorker;
import java.util.List;

public class EvolutionWorker extends SwingWorker<Void, Void> {
    private EvolutionComponent evComponent;
    private int generations;

    public EvolutionWorker(EvolutionComponent evComponent, int generations) {
        this.evComponent = evComponent;
        this.generations = generations;
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Perform time-consuming operations here, like genetic algorithm evolution
        for (int generationCount = 0; generationCount <= generations; generationCount++) {
            evComponent.handleSelection();
            evComponent.generationCount = generationCount;
            publish(); // This triggers the process method
            System.out.println("Generation " + generationCount + " completed"); // Print a status message
        }
        System.out.println("Evolution process completed"); // Print when the process is finished
        return null;
    }

    @Override
    protected void process(List<Void> chunks) {
        // Update UI during or after each iteration (e.g., repaint)
        evComponent.repaint();
    }
}
