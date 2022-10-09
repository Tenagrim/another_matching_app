package base_repo.utils;

import java.util.Iterator;

public class StringUtils {

    public static String camelCaseToSnake(String value){
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return  value
                .replaceAll(regex, replacement)
                .toLowerCase();
    }

    public static Iterable<String> getNTimes(String str, int n){
        return () -> new Iterator<>() {
            int t = 0;
            @Override
            public boolean hasNext() {
                return t < n;
            }
            @Override
            public String next() {
                t += 1;
                return str;
            }
        };
    }
}
