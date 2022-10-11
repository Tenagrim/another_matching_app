package matcha.base_repo.repo;


import matcha.base_repo.annotations.Id;
import matcha.base_repo.annotations.Table;
import matcha.base_repo.annotations.Transient;
import matcha.base_repo.mappers.SimpleRowMapper;
import matcha.base_repo.models.Entity;
import matcha.base_repo.utils.EntityUtils;
import matcha.base_repo.utils.ReflectionUtils;
import matcha.base_repo.utils.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


public abstract class BaseRepository<T extends Entity> {

    @Value("${app.datasource.schema}")
    private String schema;

    protected JdbcTemplate jdbcTemplate;
    private String idField;
    private String tableName;
    private final RowMapper<T> rowMapper;
    private final Class<T> entityClass;
    protected  String getTableName(){
        if (tableName == null)
            throw new IllegalStateException("Entity class must be annotated with @Table or repository must override getTableName method");
        return tableName;
    };
    protected  String getIdField(){
        if (idField == null)
            throw new IllegalStateException("Entity class must have field annotated with @Id or repository must override getIdField method");
        return idField;
    };

    protected  RowMapper<T> getRowMapper(){
        return rowMapper;
    };

    @SuppressWarnings("unchecked")
    public BaseRepository() {
        Class<T> entityClass = (Class<T>) ReflectionUtils.getGenericParameterClass(this.getClass(),0);
        this.rowMapper = SimpleRowMapper.of(entityClass);
        this.entityClass = entityClass;
        setTableName(entityClass);
        setIdField(entityClass);
    }

    Optional<T> queryForObject(String sql, Object[] params, RowMapper<T> mapper) throws DataAccessException{
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, params, mapper));
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int update(String sql, Object... args) throws DataAccessException {
        try{
            return jdbcTemplate.update(sql, args);
        }
        catch (DuplicateKeyException e){
            return 0;
        }
    }

    public Optional<T> getById(Long id){
        return getByField(getIdField(), id);
    }

    public Optional<T> getByField(String field, Object value){
        return queryForObject(String.format("SELECT * FROM %s.%s WHERE %s = ?" , schema, getTableName(), field),
                new Object[]{value}, getRowMapper());
    }

    public List<T> getListByField(String field, Object value){
        String format = "SELECT * FROM %s.%s WHERE %s = %s";
        return jdbcTemplate.query(String.format(format, schema, getTableName(), field, getStringValue(value)), getRowMapper());

    }

    private String getStringValue(Object obj){
        if (obj instanceof String)
            return "'" + obj.toString() + "'";
        return obj.toString();
    }

    public int save(T entity){
        Map<String, Object> values = getValuesMap(entity, true, false); //TODO дописать вариант не игнорить id
        String insertString = getInsertString(values);
        return update(insertString, values.values().toArray());
    }

    public int update(T entity){
        Map<String, Object> values = getValuesMap(entity, false, true);
        Object id = values.get(getIdField());
        if (id == null)
            throw new IllegalStateException("Primary Key is empty");
        values.remove(getIdField());
        if (values.size() ==0)
            return 0;
        String updateString = getUpdateString(values);
        Object[] args = new Object[values.size() + 1];
        Iterator<Object> it = values.values().iterator();
        for (int i = 0; i < values.size(); i++)
            args[i] = it.next();
        args[values.size()] = id;
        return update(updateString, args);
    }

    public Object saveAndReturnKey(T entity){
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withSchemaName(schema);
        insert.withTableName(getTableName());
        insert.usingGeneratedKeyColumns(getIdField());
        Map<String, Object> values = getValuesMap(entity, true, false);
        return insert.executeAndReturnKey(values);
    }

    @SneakyThrows
    private Map<String, Object> getValuesMap(T entity, boolean ignoreId, boolean ignoreNull){
        Map<String, Object> res = new LinkedHashMap<>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class) && ignoreId ||
                    field.isAnnotationPresent(Transient.class) ||
                    java.lang.reflect.Modifier.isStatic(field.getModifiers()))
                continue;
            String name = EntityUtils.getColumnName(field);
            field.setAccessible(true);
            Object value = field.get(entity);
            if (!(value == null && ignoreNull))
                res.put(name, value);
        }
        return res;
    }

    private String getInsertString(Map<String, Object> valuesMap){
        String format = "INSERT INTO %s.%s (%s) VALUES (%s)";
        String colNames = String.join(", ", valuesMap.keySet());
        String values = String.join(", ", StringUtils.getNTimes("?", valuesMap.keySet().size()));
        return String.format(format, schema, getTableName(), colNames, values);
    }

    private String getUpdateString(Map<String, Object> valuesMap){
        String format = "UPDATE %s.%s SET %s WHERE %s=?";
        String colNames = valuesMap.keySet().stream()
                .map(el->el + "=?")
                .collect(Collectors.joining(", "));
        return String.format(format, schema, getTableName(), colNames, getIdField());
    }

    private void setIdField(Class<T> entityClass){
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)){
                idField = EntityUtils.getColumnName(field);
                return;
            }
        }
    }

    private void setTableName(Class<T> entityClass){
        if (entityClass.isAnnotationPresent(Table.class)){
            Table table = entityClass.getAnnotation(Table.class);
            tableName = table.name();
        }
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
