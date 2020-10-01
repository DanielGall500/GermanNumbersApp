package ucd.danielgall.klangapp.utilities;

public class LearnPage {

    public static final int ZERO_TO_NINE = 0;
    public static final int TEN_TO_NINETEEN = 1;
    public static final int TWENTY_TO_TWENTY_NINE = 2;
    public static final int THIRTY_TO_THIRTY_NINE = 3;
    public static final int FORTY_TO_FORTY_NINE = 4;
    public static final int FIFTY_TO_FIFTY_NINE = 5;
    public static final int SIXTY_TO_SIXTY_NINE = 6;
    public static final int SEVENTY_TO_SEVENTY_NINE = 7;
    public static final int EIGHTY_TO_EIGHTY_NINE = 8;
    public static final int NINETY_TO_NINETY_NINE = 9;

    public static int[] getMinMax(int id) {
        switch (id) {
            case ZERO_TO_NINE:
                return new int[]{0, 9};
            case TEN_TO_NINETEEN:
                return new int[]{10, 19};
            case TWENTY_TO_TWENTY_NINE:
                return new int[]{20, 29};
            case THIRTY_TO_THIRTY_NINE:
                return new int[]{30, 39};
            case FORTY_TO_FORTY_NINE:
                return new int[]{40, 49};
            case FIFTY_TO_FIFTY_NINE:
                return new int[]{50, 59};
            case SIXTY_TO_SIXTY_NINE:
                return new int[]{60, 69};
            case SEVENTY_TO_SEVENTY_NINE:
                return new int[]{70, 79};
            case EIGHTY_TO_EIGHTY_NINE:
                return new int[]{80, 89};
            case NINETY_TO_NINETY_NINE:
                return new int[]{90, 99};
            default:
                return null;

        }
    }


}
