package ru.anykeyers.serviceofservices;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class Mapper {

    public static List<Long> parseFromString(String serviceIds) {
        return Arrays.stream(serviceIds.split(","))
                .map(Long::parseLong)
                .toList();
    }

    public static String formatDate(long duration) {
        Date date = new Date(duration);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }

}
