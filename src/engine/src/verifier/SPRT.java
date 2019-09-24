package verifier;

import javafx.util.Pair;
import log.Log;
import property.Property;
import property.PropertyChecker;
import simulation.SoSSimulationProgram;

//import simulation.Simulation;

public class SPRT extends Verifier {
    double alpha;
    double beta;
    double delta;
    int minimumSample;

    /**
     * Instantiates a new Sprt.
     *
     * @param checker the checker
     */
    public SPRT(PropertyChecker checker) {
    super(checker);
    this.alpha = 0.05;
    this.beta = 0.05;
    this.delta = 0.01;
    this.minimumSample = 2;
  }

    /**
     * Verify input simulation in GUI (Existence, Absence, Universality Checker).
     *
     * //@param simulation           the simulation
     * @param verificationProperty the verification property
     * @param maxRepeat            the max repeat
     * @param theta                the theta
     * @return the pair
     */
    public Pair<Pair<Integer,Boolean>, String> verifyWithSimulationGUI(//Simulation simulation
                                                                       SoSSimulationProgram simulation,
                                                                       Property verificationProperty, int maxRepeat, double theta) {
        int maxNumSamples = maxRepeat;
        boolean ret = true;
        int numSamples;
        int numTrue;
        numSamples = 0;
        numTrue = 0;
        
        while (this.isSampleNeeded(numSamples, numTrue, theta)) {
            if (!(numSamples < maxNumSamples)) {
                System.out.println("Over maximum repeat: " + maxNumSamples);
                break;
            }
            simulation.setRunning();            // 시뮬레이션 시작 전에 running 변수의 값을 true로 바꿔준다.
            Log log = simulation.run();
//            System.out.println("출력문 확인!!!!!!!!!!!!!!!!!!!!");
//            log.printSnapshot();

            if (this.propertychecker.check(log, verificationProperty)) {
                numTrue += 1;
            }
            numSamples += 1;
        }
        
        ret = this.isSatisfied(numSamples, numTrue, theta);
        
        // TODO Theta가 1일때 true 나오는 이유 확인
        if(theta == 1.00) ret = false;
        
        String verificationResult = "theta: " + Double.parseDouble(String.format("%.2f", theta)) +
            " numSamples: " + numSamples + " numTrue: " + numTrue + " result: " + ret;
        
        
        return new Pair(new Pair(numSamples, ret), verificationResult);
    }
    
    /**
     * Verify input simulation in GUI (Minimum Duration Checker).
     *
     * @param simulation           the simulation
     * @param verificationProperty the verification property
     * @param maxRepeat            the max repeat
     * @param theta                the theta
     * @param t                    specific time period that shows the minimum duration
     * @param T                    whole simulation time
     * @return the pair
     */
    /*
    public Pair<Pair<Integer,Boolean>, String> verifyWithSimulationGUI(Simulation simulation, Property verificationProperty, int maxRepeat, double theta, int t, int T) {
        int maxNumSamples = maxRepeat;
        boolean ret = true;
        int numSamples;
        int numTrue;
        
        numSamples = 0;
        numTrue = 0;
        
        while (this.isSampleNeeded(numSamples, numTrue, theta)) {
            if (!(numSamples < maxNumSamples)) {
                System.out.println("Over maximum repeat: " + maxNumSamples);
                break;
            }
            
            Log log = simulation.runSimulation();
            Log log2 = simulation.runSimulation();
            
            //MinimumDurationChecker
            if(this.propertychecker.check(log, verificationProperty, t, T)) {
                numTrue += 1;
            }
            numSamples += 1;
        }
        
        ret = this.isSatisfied(numSamples, numTrue, theta);
        String verificationResult = "theta: " + Double.parseDouble(String.format("%.2f", theta)) +
            " numSamples: " + numSamples + " numTrue: " + numTrue + " result: " + ret;
        
        
        return new Pair(new Pair(numSamples, ret), verificationResult);
    }
    
    *//**
     * Verify input simulation in GUI (Steady State Checker).
     *
     * @param simulation           the simulation
     * @param verificationProperty the verification property
     * @param maxRepeat            the max repeat
     * @param theta                the theta
     * @param p                    probability of satisfying property in the whole simulation time (t/T = p, where t is a sum of true property ticks)
     * @param T                    whole simulation time
     * @return the pair
     *//*
    public Pair<Pair<Integer,Boolean>, String> verifyWithSimulationGUI(Simulation simulation, Property verificationProperty, int maxRepeat, double theta, double p, int T) {
        int maxNumSamples = maxRepeat;
        boolean ret = true;
        int numSamples;
        int numTrue;
        
        numSamples = 0;
        numTrue = 0;
        
        while (this.isSampleNeeded(numSamples, numTrue, theta)) {
            if (!(numSamples < maxNumSamples)) {
                System.out.println("Over maximum repeat: " + maxNumSamples);
                break;
            }
            
            Log log = simulation.runSimulation();
            Log log2= simulation.runSimulation();
            
            if(this.propertychecker.check(log, verificationProperty, p, T)) {
                numTrue += 1;
            }
            numSamples += 1;
        }
        
        ret = this.isSatisfied(numSamples, numTrue, theta);
        String verificationResult = "theta: " + Double.parseDouble(String.format("%.2f", theta)) +
            " numSamples: " + numSamples + " numTrue: " + numTrue + " result: " + ret;
        
        
        return new Pair(new Pair(numSamples, ret), verificationResult);
    }
    
    *//**
     * Verify input simulation in GUI (Transient State Checker).
     *
     * @param simulation           the simulation
     * @param verificationProperty the verification property
     * @param maxRepeat            the max repeat
     * @param theta                the theta
     * @param p                    probability of satisfying property in the whole simulation time (t/T = p, where t is a sum of true property ticks)
     * @param t                    specific simulation time
     * @param T                    whole simulation time
     * @return the pair
     *//*
    
    public Pair<Pair<Integer,Boolean>, String> verifyWithSimulationGUI(Simulation simulation, Property verificationProperty, int maxRepeat, double theta, double p, int t, int T) {
        int maxNumSamples = maxRepeat;
        boolean ret = true;
        int numSamples;
        int numTrue;
        
        numSamples = 0;
        numTrue = 0;
        
        while (this.isSampleNeeded(numSamples, numTrue, theta)) {
            if (!(numSamples < maxNumSamples)) {
                System.out.println("Over maximum repeat: " + maxNumSamples);
                break;
            }
            
            Log log = simulation.runSimulation();
            Log log2 = simulation.runSimulation();
            
            if(this.propertychecker.check(log, verificationProperty, p, t, T)) {
                numTrue += 1;
            }
            numSamples += 1;
        }
        
        ret = this.isSatisfied(numSamples, numTrue, theta);
        String verificationResult = "theta: " + Double.parseDouble(String.format("%.2f", theta)) +
            " numSamples: " + numSamples + " numTrue: " + numTrue + " result: " + ret;
        
        
        return new Pair(new Pair(numSamples, ret), verificationResult);
    }*/
    
    /**
     * @param numSample
     * @param numTrue
     * @param theta
     * @return true/false
     */
    private boolean isSampleNeeded(int numSample, int numTrue, double theta) {
        if (numSample < this.minimumSample) return true;
        
        double h0Threshold = this.beta / (1-this.alpha);
        double h1Threshold = (1-this.beta) / this.alpha;
        
        double v = this.getV(numSample, numTrue, theta);
        
        if (v <= h0Threshold) {
            return false;
        }
        else if (v >= h1Threshold) {
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * @param numSamples
     * @param numTrue
     * @param theta
     * @return true/false
     */
    private boolean isSatisfied(int numSamples, int numTrue, double theta) {
        double h0Threshold = this.beta/(1-this.alpha);
        
        double v = this.getV(numSamples, numTrue, theta);
        
        if (v <= h0Threshold) {
            return true;
        } else  {
            return false;
        }
    }
    
    /**
     * @param numSample
     * @param numTrue
     * @param theta
     * @return double
     */
    private double getV(int numSample, int numTrue, double theta) {
        double p0 = theta + this.delta;
        double p1 = theta - this.delta;
        
        int numFalse = numSample - numTrue;
        
        double p1m = Math.pow(p1, numTrue) * Math.pow((1-p1), numFalse);
        double p0m = Math.pow(p0, numTrue) * Math.pow((1-p0), numFalse);
        
        if (p0m == 0) {
            p1m = p1m + Double.MIN_VALUE;
            p0m = p0m + Double.MIN_VALUE;
        }
        
        return p1m / p0m;
    }
    
}


