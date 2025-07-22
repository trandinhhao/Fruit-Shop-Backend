package dev.tdh.fruitshop.utils;

import java.text.Normalizer;

public class StringUtils {
    public static String removeAccents(String input) { // xoa dau cho str // wrong
        if (input == null || input.length() == 0) {
            return null;
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }
}
