package demo.model;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.Arrays;
import java.util.Collections;

/**
 * Represents the list of valid formations
 */
public enum Formation {

    FIVE_FOUR_ONE,
    FIVE_THREE_TWO,
    FIVE_TWO_THREE,
    FOUR_FIVE_ONE,
    FOUR_FOUR_TWO,
    FOUR_THREE_THREE,
    THREE_FIVE_TWO,
    THREE_FOUR_THREE;

    /**
     * We always have one substitute keeper. The columns in the array are defence, midfield, strikers
     * respectively.
     *
     * @return
     */
    public int[] getValidSubs() {
        switch (this) {
            case FIVE_FOUR_ONE:
                return new int[]{0, 1, 2};
            case FIVE_THREE_TWO:
                return new int[]{0, 2, 1};
            case FIVE_TWO_THREE:
                return  new int[]{0,3,0};
            case FOUR_FIVE_ONE:
                return new int[]{1,0,2};
            case FOUR_FOUR_TWO:
                return new int[]{1,1,1};
            case FOUR_THREE_THREE:
                return new int[]{1,2,0};
            case THREE_FIVE_TWO:
                return new int[]{2,0,1};
            case THREE_FOUR_THREE:
                return  new int[]{2,1,0};
        };

        // Keep the compiler happy, and it will be clear if there's a bug
        return new int[]{0,0,0};
    }

    public int getDefenderSubs() {
            return this.getValidSubs()[0];
    }

    public int getMidfielderSubs() {
        return this.getValidSubs()[1];
    }

    public int getAttackerSubs() {
        return this.getValidSubs()[2];
    }

    public static Formation fromString(String formation) {
        switch(formation)
        {
            case "541": return Formation.FIVE_FOUR_ONE;
            case "532": return Formation.FIVE_THREE_TWO;
            case "523": return Formation.FIVE_TWO_THREE;
            case "451": return Formation.FOUR_FIVE_ONE;
            case "442": return Formation.FOUR_FOUR_TWO;
            case "433": return Formation.FOUR_THREE_THREE;
            case "352": return Formation.THREE_FIVE_TWO;
            default : return Formation.THREE_FOUR_THREE;
        }
    }

    public String toString(Formation formation) {
        switch (this)
        {
            case FIVE_FOUR_ONE:
                return "541";
            case FIVE_THREE_TWO:
                return "532";
            case FIVE_TWO_THREE:
                return "523";
            case FOUR_FIVE_ONE:
                return "451";
            case FOUR_FOUR_TWO:
                return "442";
            case FOUR_THREE_THREE:
                return "433";
            case THREE_FIVE_TWO:
                return "352";
            default:
                return "343";
        }
    }


}
