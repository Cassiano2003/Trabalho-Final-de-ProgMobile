package com.example.trabalhofinal.TabelasDao;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<Integer> fromStringToIntList(String value) {
        if (value == null || value.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        String[] parts = value.split(",");
        for (String part : parts) {
            try {
                list.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @TypeConverter
    public static String fromIntListToString(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i : list) {
            result.append(i).append(",");
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }

    @TypeConverter
    public static String fromStringArray(String[] array) {
        return array != null ? String.join(",", array) : null;
    }

    @TypeConverter
    public static String[] toStringArray(String data) {
        return data != null ? data.split(",") : new String[]{};
    }

    @TypeConverter
    public static int[] fromStringToIntArray(String value) {
        if (value == null || value.isEmpty()) {
            return new int[0];
        }
        String[] parts = value.split(",");
        int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            try {
                result[i] = Integer.parseInt(parts[i].trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                result[i] = 0;
            }
        }
        return result;
    }

    @TypeConverter
    public static String fromIntArrayToString(int[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i : array) {
            result.append(i).append(",");
        }
        result.setLength(result.length() - 1);
        return result.toString();
    }

}

