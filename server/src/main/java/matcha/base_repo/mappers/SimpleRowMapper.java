package matcha.base_repo.mappers;


import matcha.base_repo.annotations.Transient;
import matcha.base_repo.models.Entity;
import matcha.base_repo.utils.EntityUtils;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SimpleRowMapper<T extends Entity> implements RowMapper<T> {

    private Class<T> entityClass;
    private static Map<Class<?>, Method> methodMapping;
    static {
        methodMapping = new HashMap<>();
        try {
            methodMapping.put(Long.class, ResultSet.class.getDeclaredMethod("getLong", String.class));
            methodMapping.put(String.class, ResultSet.class.getDeclaredMethod("getString", String.class));
            methodMapping.put(Date.class, ResultSet.class.getDeclaredMethod("getTimestamp", String.class));
            methodMapping.put(java.util.Date.class, ResultSet.class.getDeclaredMethod("getTimestamp", String.class));
            // TODO добавить больше дефолтных типов и возможность добавлять кастомные мапперы
        }catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    private SimpleRowMapper(Class<T> clazz) {
        entityClass = clazz;
    }
    private SimpleRowMapper(){}

    public static <T extends Entity> SimpleRowMapper<T> of(Class<T> clazz){
        return new SimpleRowMapper<>(clazz);
    }

    @SneakyThrows
    @Override
    public T mapRow(ResultSet resultSet, int i) throws SQLException {
        T entity;
        try {
            entity = entityClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalStateException("Entity object must have public default constructor to create an instance");
        }
        for (Field field : entity.getClass().getDeclaredFields()) {

            if (field.isAnnotationPresent(Transient.class) ||
                    java.lang.reflect.Modifier.isStatic(field.getModifiers()))
                continue;
            field.setAccessible(true);
            field.set(entity, methodMapping.get(field.getType()).invoke(resultSet, EntityUtils.getColumnName(field)));
        }
        return entity;
    }



}
