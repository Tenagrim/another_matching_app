package matcha.constants;

import com.google.common.collect.ImmutableMap;


import java.util.Map;

public class Const {
    private static final Map<String, Object> consts;
    static {
        consts = ImmutableMap.of(
                "basePackage","matcha"
        );
    }
    public static Object get(String key){
        return consts.get(key);
    }
}
