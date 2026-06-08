package com.rm.ums.common.helpers;

import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class JpaHelper {

    private final EntityManager em;

    public Class<?> findEntityClass(String tableName) {
        return em
                .getMetamodel()
                .getEntities()
                .stream()
                .filter(entity -> {
                    Table table =
                            entity.getJavaType()
                                    .getAnnotation(Table.class);
                    return table != null
                            && table.name()
                            .equalsIgnoreCase(tableName);
                })
                .findFirst()
                .map(Type::getJavaType)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No entity found for table: "
                                        + tableName));
    }

    public Field findFieldByColumnName(Class<?> entityClass, String columnName) {
        Class<?> currentClass = entityClass;
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                Column column =
                        field.getAnnotation(Column.class);
                if (column != null &&
                        column.name().equalsIgnoreCase(columnName)) {
                    field.setAccessible(true);
                    return field;
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        throw new IllegalArgumentException(
                "Field not found for column: " + columnName
        );
    }

    public Object convert(String value,Class<?> targetType) {
        if (value == null) return null;
        if (targetType == String.class) return value;
        if (targetType == Long.class) return Long.valueOf(value);
        if (targetType == Integer.class) return Integer.valueOf(value);
        if (targetType == Boolean.class) return Boolean.valueOf(value);
        if (targetType == LocalDate.class) return LocalDate.parse(value);
        if (targetType == Instant.class) return Instant.parse(value);
        throw new IllegalArgumentException("Unsupported type: " + targetType.getName());
    }

}
