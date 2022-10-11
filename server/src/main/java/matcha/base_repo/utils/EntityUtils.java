package matcha.base_repo.utils;



import matcha.base_repo.annotations.Column;

import java.lang.reflect.Field;

public class EntityUtils {
    public static String getColumnName(Field field){
        if(field.isAnnotationPresent(Column.class)) {
            Column name = field.getAnnotation(Column.class);
            return name.name();
        }
        return field.getName();
    }
}
