package ro.pub.cs.systems.eim.practicaltest01.general;

public interface Constants {
    final public static String FIRST_NUMBER = "firstnumber";
    final public static String SECOND_NUMBER = "second number";

    final public static String[] actionTypes = {
            "ro.pub.cs.systems.eim.practicaltest01.arithmeticmean",
            "ro.pub.cs.systems.eim.practicaltest01.geometricmean"
    };
    final public static String BROADCAST_RECEIVER_EXTRA = "message";
    final public static String BROADCAST_RECEIVER_TAG = "[Message]";

    final public static int SERVICE_STOPPED = 0;
    final public static int SERVICE_STARTED = 1;
    final public static int NUMBER_OF_CLICKS_THRESHOLD = 5;
}
