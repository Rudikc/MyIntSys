package ga;

import timetable_elements.Group;
import timetable_elements.Subject;
import timetable_elements.Timetable;

import java.util.Arrays;
import java.util.Random;

public class Genetic {

    private int populationSize;
    private int elitismCount;
    private int tournamentSize;
    private double mutationRate;
    private double crossoverRate;

    public Genetic(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }

    public Population initPopulation(Timetable timetable) {
        return new Population(this.populationSize, timetable);
    }

    public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
        return (generationsCount > maxGenerations);
    }

    public boolean isTerminationConditionMet(Population population) {
        return population.getFittest(0).getFitness() == 1.0;
    }

    public Population mutatePopulation(Population population, Timetable timetable) {
        Population newPopulation = new Population(this.populationSize);
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);
            Individual randomIndividual = new Individual(timetable);
            for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                if (populationIndex > this.elitismCount) {
                    if (this.mutationRate > Math.random()) {
                        individual.setGene(geneIndex, randomIndividual.getGene(geneIndex));
                    }
                }
            }
            newPopulation.setIndividual(populationIndex, individual);
        }
        return newPopulation;
    }

    public double calculateFitness(Individual individual, Timetable timetable) {

        Timetable threadTimetable = new Timetable(timetable);
        threadTimetable.createClasses(individual);

        int clashes = threadTimetable.calcClashes();
        double fitness = 1 / (double) (clashes + 1);

        individual.setFitness(fitness);
        return fitness;
    }

    public void evaluatePopulation(Population population, Timetable timetable) {
        double populationFitness = 0;

        for (Individual individual : population.getIndividuals()) {
            populationFitness += this.calculateFitness(individual, timetable);
        }

        population.setPopulationFitness(populationFitness);
    }

    public Individual selectParent(Population population) {
        Population tournament = new Population(this.tournamentSize);

        population.shuffle();
        for (int i = 0; i < this.tournamentSize; i++) {
            Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }
        return tournament.getFittest(0);
    }

    public Population crossoverPopulation(Population population) {
        Population newPopulation = new Population(population.size());
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            if (this.crossoverRate > Math.random() && populationIndex > this.elitismCount) {
                Individual offspring = new Individual(parent1.getChromosomeLength());

                Individual parent2 = selectParent(population);

                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    if (0.5 > Math.random()) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    }
                }
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        return newPopulation;
    }

    public static class Population {
        Individual[] population;
        double populationFitness = -1;

        public Population(int populationSize) {
            this.population = new Individual[populationSize];
        }

        public Population(int populationSize, Timetable timetable) {
            this.population = new Individual[populationSize];

            for (int individualCount = 0; individualCount < populationSize; individualCount++) {
                Individual individual = new Individual(timetable);
                this.population[individualCount] = individual;
            }
        }

        public Individual[] getIndividuals() {
            return this.population;
        }

        public Individual getFittest(int offset) {
            Arrays.sort(this.population, (o1, o2) -> {
                if (o1.getFitness() > o2.getFitness()) {
                    return -1;
                } else if (o1.getFitness() < o2.getFitness()) {
                    return 1;
                }
                return 0;
            });

            return this.population[offset];
        }

        public void setPopulationFitness(double fitness) {
            this.populationFitness = fitness;
        }

        public int size() {
            return this.population.length;
        }

        public void setIndividual(int offset, Individual individual) {
            population[offset] = individual;
        }

        public Individual getIndividual(int offset) {
            return population[offset];
        }

        public void shuffle() {
            Random rnd = new Random();
            for (int i = population.length - 1; i > 0; i--) {
                int index = rnd.nextInt(i + 1);
                Individual a = population[index];
                population[index] = population[i];
                population[i] = a;
            }
        }
    }

    public static class Individual {
        public int[] getChromosome() {
            return this.chromosome;
        }

        public int getChromosomeLength() {
            return this.chromosome.length;
        }

        public void setGene(int offset, int gene) {
            this.chromosome[offset] = gene;
        }

        public int getGene(int offset) {
            return this.chromosome[offset];
        }

        public double getFitness() {
            return this.fitness;
        }

        public void setFitness(double fitness) {
            this.fitness = fitness;
        }

        int[] chromosome;

        double fitness = -1;

        public Individual(Timetable timetable) {
            int numClasses = timetable.getNumOfClasses();
            int chromosomeLength = numClasses * 4;
            int[] newChromosome = new int[chromosomeLength];
            int chromosomeIndex = 0;
            for (Group group : timetable.getGroupsAsArray()) {
                for (int moduleId : group.getModuleIds()) {
                    int timeslotId = timetable.getRandomTimeslot().getTimeslotId();
                    newChromosome[chromosomeIndex] = timeslotId;
                    chromosomeIndex++;
                    int roomId = timetable.getRandomRoom().getRoomId();
                    newChromosome[chromosomeIndex] = roomId;
                    chromosomeIndex++;
                    Subject module = timetable.getModule(moduleId);
                    newChromosome[chromosomeIndex] = module.getRandomLecturerId();
                    chromosomeIndex++;
                    newChromosome[chromosomeIndex] = module.getRandomPractikId();
                    chromosomeIndex++;
                }
            }

            this.chromosome = newChromosome;
        }

        public Individual(int chromosomeLength) {
            int[] individual;
            individual = new int[chromosomeLength];

            for (int gene = 0; gene < chromosomeLength; gene++) {
                individual[gene] = gene;
            }

            this.chromosome = individual;
        }

        public String toString() {
            StringBuilder output = new StringBuilder();
            for (int i : this.chromosome) {
                output.append(i).append(" ");
            }
            return output.toString();
        }

        public boolean containsGene(int gene) {
            for (int value : this.chromosome) {
                if (value == gene) {
                    return true;
                }
            }
            return false;
        }


    }


}
