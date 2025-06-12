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
}

