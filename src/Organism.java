import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    Edit the functionToTest variable to change which function you want to use.
    The functions themselves can be edited themselves in the methods functionOne and functionTwo.

    All other variables that can be edited are found in the main method.

    Brayden Waugh

 */

public class Organism implements Comparable<Organism> {
    private String organismCode;
    private double mutationProbability = 0.05;
    private int leftOfDecimal = 8;
    private int rightOfDecimal = 4;
    private int functionToTest = 1; // Change to 2 to test second function

    public Organism() {
        organismCode = generateOrganism(12);
    }

    public Organism(int size, int leftOfDecimal, int rightOfDecimal, double mutationProbability) {
        // Ensure that leftOfDecimal + rightOfDecimal = organismCode
        organismCode = generateOrganism(size);
        this.leftOfDecimal = leftOfDecimal;
        this.rightOfDecimal = rightOfDecimal;
        this.mutationProbability = mutationProbability;
    }

    private String generateOrganism(int length) {
        StringBuilder organismBinary = new StringBuilder();

        for (int i = 0; i < length; i++) { // 50/50 chance of organismCode bit to be 0 or 1
            if (Math.random() > 0.5) {
                organismBinary.append("1");
            } else {
                organismBinary.append("0");
            }
        }
        return organismBinary.toString();
    }

    private double decodeOrganism() {
        /*
        Divides the organism code based on the values of leftOfDecimal and rightOfDecimal and uses the formula
        to convert a binary value to a decimal.
         */
        double leftTotal = 0;
        double rightTotal = 0;
        int sign;

        for (int i = 0; i < leftOfDecimal; i++) {
            leftTotal += Integer.parseInt(String.valueOf(organismCode.charAt(i))) *
                    (Math.pow(2, (leftOfDecimal - (i + 1))));
        }

        for (int i = 0; i < (rightOfDecimal - 1); i++) {
            rightTotal += Integer.parseInt(String.valueOf(organismCode.charAt(i + leftOfDecimal))) *
                    (Math.pow(2, (-(i + 1))));
        }

        if (Integer.parseInt(String.valueOf(organismCode.charAt((leftOfDecimal + rightOfDecimal) - 1))) == 1) {
            sign = 1;
        } else {
            sign = -1;
        }

        return ((leftTotal + rightTotal) * sign);
    }

    public List<Organism> mateOrganisms(Organism secondParent) {
        Organism firstChild = new Organism();
        Organism secondChild = new Organism();
        // Pick a random point in the organisms to determine which bits they will swap
        int matePoint = (int) (Math.random() * ((this.organismCode.length() - 1) - 1)) + 1;
        //System.out.println(matePoint);
        StringBuilder firstParentCode = new StringBuilder(this.organismCode);
        StringBuilder secondParentCode = new StringBuilder(secondParent.getOrganismCode());
        StringBuilder tempCode = new StringBuilder();

        for (int i = matePoint; i < this.organismCode.length(); i++) {
            tempCode.append(firstParentCode.charAt(i));
            firstParentCode.setCharAt(i, secondParentCode.charAt(i));
            secondParentCode.setCharAt(i, tempCode.charAt(i - matePoint));
        }

        firstChild.setOrganismCode(firstParentCode.toString());
        secondChild.setOrganismCode(secondParentCode.toString());

        List<Organism> organismChildren = new ArrayList<>();
        organismChildren.add(firstChild);
        organismChildren.add(secondChild);

        return organismChildren;
    }

    private void mutateOrganism() {
        StringBuilder organismCode = new StringBuilder(this.organismCode);

        for (int i = 0; i < this.organismCode.length(); i++) {
            double mutateChance = Math.random();
            // Each bit in organismCode has a random chance to change value from 0 to 1 or vice versa
            if (mutateChance < this.mutationProbability) {
                if (organismCode.charAt(i) == '1') {
                    organismCode.setCharAt(i, '0');
                } else if (organismCode.charAt(i) == '0') {
                    organismCode.setCharAt(i, '1');
                }
            }
        }
        this.organismCode = organismCode.toString();
    }

    private double testFitness(int whichFunction) {
        if (whichFunction == 1) {
            return functionOne(this.decodeOrganism());
        } else if (whichFunction == 2) {
            return functionTwo(this.decodeOrganism());
        }
        return 0.0;
    }

    private double functionOne(double x) {
        return 4 - Math.pow(x, 2);
    }

    private double functionTwo(double x) {
        return Math.pow(x, 2) - (4 * x) - 9;
    }

    public void setLeftOfDecimal(int leftOfDecimal) {
        this.leftOfDecimal = leftOfDecimal;
    }

    public void setRightOfDecimal(int rightOfDecimal) {
        this.rightOfDecimal = rightOfDecimal;
    }

    private void setOrganismCode(String organismCode) {
        this.organismCode = organismCode;
    }

    private String getOrganismCode() {
        return organismCode;
    }

    public int getFunctionToTest() {
        return functionToTest;
    }

    @Override
    public int compareTo(Organism o) {
        // 0 - equal, -1 if o1 < o2, 1 if o1 > o2
        // FITNESS IS SORTED IN MINIMIZATION ORDER
        if (this.testFitness(functionToTest) == o.testFitness(functionToTest)) {
            return 0;
        } else if (this.testFitness(functionToTest) < o.testFitness(functionToTest)) {
            return -1;
        } else if (this.testFitness(functionToTest) > o.testFitness(functionToTest)) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        // Customize the organism generation here
        int organismsPerGeneration = 10;
        int numberOfGenerations = 10;
        boolean maximization = true; // False for minimization

        // Customize the organism characteristics here
        int organismSize = 12; // Ensure that leftOfDecimal + rightOfDecimal = organismSize
        int leftOfDecimal = 8;
        int rightOfDecimal = 4;
        double mutationProbability = 0.05;

        ArrayList<Organism> currentGeneration = new ArrayList<>();
        ArrayList<Organism> nextGeneration = new ArrayList<>();
        ArrayList<Organism> childrenMutation = new ArrayList<>();

        for (int i = 0; i < organismsPerGeneration; i++) {
            currentGeneration.add(new Organism());
        }

        if (maximization) {
            currentGeneration.sort(Collections.reverseOrder()); // Maximize function - use descending order
        } else {
            Collections.sort(currentGeneration); // Minimize function - use ascending order
        }

        int generationCounter = 0;

        while (generationCounter < numberOfGenerations) {
            int organismCounter = 0;
            int topFiftyPercent = 0; // Choose the top 50% of organisms to mate with the strongest
            while (organismCounter < organismsPerGeneration) {
                Organism parentOne = currentGeneration.get(0);
                Organism parentTwo = currentGeneration.get(topFiftyPercent);
                childrenMutation.addAll(parentOne.mateOrganisms(parentTwo));
                for (Organism child : childrenMutation) {
                    child.mutateOrganism();
                }
                nextGeneration.addAll(childrenMutation); // add potentially mutated children to next generation
                childrenMutation.clear(); // dispose of the children from holding array
                topFiftyPercent++;
                organismCounter += 2;
            }

            if (maximization) {
                nextGeneration.sort(Collections.reverseOrder());
            } else {
                Collections.sort(nextGeneration);
            }
            System.out.println("===== GENERATION " + generationCounter + " =====");
            for (Organism organism : nextGeneration) {
                System.out.println(organism.getOrganismCode());
            }
            System.out.println("\nThe fitness of the strongest organism is: "
                    + nextGeneration.get(0).testFitness(nextGeneration.get(0).getFunctionToTest()));
            System.out.println("The decoded value of the strongest organism is: "
                    + nextGeneration.get(0).decodeOrganism());

            currentGeneration.clear();
            currentGeneration.addAll(nextGeneration); // Set the current generation to the next generation
            nextGeneration.clear();
            generationCounter++;
        }
        System.out.println("\n====== FINAL RESULT ======");
        System.out.println("The fitness of the strongest organism is: "
                + currentGeneration.get(0).testFitness(currentGeneration.get(0).getFunctionToTest()));
        System.out.println("The decoded value of the strongest organism is: "
                + currentGeneration.get(0).decodeOrganism());
    }
}