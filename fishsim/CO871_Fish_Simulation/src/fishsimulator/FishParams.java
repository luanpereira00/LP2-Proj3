package fishsimulator;

import java.io.Serializable;

/**
 * Interface for classes that provide parameters for newly created fish.
 * Implemented by the basic class SFP but could be imnplemented by
 * a more elaborate class with a GUI panel
 * @author jdb
 * @version 28/11/2008
 */
public interface FishParams extends Serializable {

    public void initParams(double initWeight, double viableWeight,
                    double weightReduce, double planctonEaten,
                    double breedWeight, int breedAge,
                    int maxAge, int huntDistance, double maxEat);
    public double getInitWeight();
    public double getViableWeight();
    public double getWeightReduce();
    public double getPlanctonEaten();
    public double getBreedWeight();
    public int getBreedAge();
    public int getMaxAge();
    public int getHuntDistance();
    public double getMaxEat();
}
