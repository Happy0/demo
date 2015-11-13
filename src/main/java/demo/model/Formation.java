package demo.model;

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
}
