package fishsimulator;

/**
 * Provide a simple implementation of the FishParams interface
 * @author jdb
 * @version 28/11/08
 */
public class SFPs implements FishParams {
    private double initWeight;
    private double viableWeight;
    private double weightReduce;
    private double planctonEaten;
    private double breedWeight;
    private int breedAge;
    private int maxAge;
    private int huntDistance;
    private double maxEat;
    
    public void initParams(double initWeight, double viableWeight,
                    double weightReduce, double planctonEaten,
                    double breedWeight, int breedAge,
                    int maxAge, int huntDistance, double maxEat) {
        this.initWeight = initWeight;
        this.viableWeight = viableWeight;
        this.weightReduce = weightReduce;
        this.planctonEaten = planctonEaten;
        this.breedWeight = breedWeight;
        this.breedAge = breedAge;
        this.maxAge = maxAge;
        this.huntDistance = huntDistance;
        this.maxEat = maxEat;
    }
    public double getInitWeight() {
        return initWeight;
    }
    public double getViableWeight() {
        return viableWeight;
    }
    public double getWeightReduce() {
        return weightReduce;
    }
    public double getPlanctonEaten() {
        return planctonEaten;
    }
    public double getBreedWeight() {
        return breedWeight;
    }
    public int getBreedAge() {
        return breedAge;
    }
    public int getMaxAge() {
        return maxAge;
    }
    public int getHuntDistance() {
        return huntDistance;
    }
    public double getMaxEat() {
        return maxEat;
    }

}
