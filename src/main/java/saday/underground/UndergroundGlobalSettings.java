package saday.underground;

public class UndergroundGlobalSettings {

    private static boolean UNDERGROUND_ENABLED = true;
    private static int GLOBAL_FROST_HEIGHT = 50;
    private static int MAX_COLD_TICKS = 60 * 5;

    public static boolean isUndergroundEnabled() {
        return UNDERGROUND_ENABLED;
    }

    public static int getGlobalFrostHeight() {
        return GLOBAL_FROST_HEIGHT;
    }

    public static int getMaxColdTicks() { return MAX_COLD_TICKS; }

}
