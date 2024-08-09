package com.demo.util;

import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Component
public class SQLUtil {

    @PersistenceContext
    private EntityManager entityManager;

    public <T> List<T> findList(String sqlStr, Class<T> clazz) {

        Query query = entityManager.createNativeQuery(sqlStr);
        List resultList = query.unwrap(NativeQueryImpl.class)
                .setResultTransformer(new CustomResultTransformer(clazz))
                .getResultList();
        return resultList;
    }

    /**
     * 自定義轉換器
     */
    private class CustomResultTransformer implements ResultTransformer {

        private Class<?> clazz;
        public CustomResultTransformer(Class<?> clazz) {
            this.clazz = clazz;
        }

        @Override
        public Object transformTuple(Object[] tuple, String[] aliases) {

            try {
                Object obj = this.clazz.getDeclaredConstructor().newInstance();

                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    // 物件設定對應DB欄位名稱
                    Column column = field.getAnnotation(Column.class);
                    field.setAccessible(true);

                    for (int i = 0; i < aliases.length; i++) {
                        // 依照標籤寫入
                        if (StringUtils.equals(aliases[i], column.name())) {
                            field.set(obj, customConverter(tuple[i], field.getType()));
                        }
                    }
                }

                return obj;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public List transformList(List tuples) {
            return tuples;
        }

        /**
         * 客製化欄位轉換
         *
         * @param dbValue   DB資料
         * @param fieldType java物件型別
         * @return
         */
        private Object customConverter(Object dbValue, Class fieldType) {
            if (Objects.isNull(dbValue)) {
                return null;
            } else if (dbValue instanceof Timestamp timestamp) {
                return convertTimestamp(timestamp, fieldType);
            } else if (dbValue instanceof Date date) {
                return convertDate(date, fieldType);
            }
            return dbValue;
        }

        private Object convertDate(Date date, Class fieldType) {
            if (fieldType == LocalDate.class) {
                return date.toLocalDate();
            }  else if (fieldType == java.util.Date.class) {
                return Date.from(date.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            } else if (fieldType == Date.class) {
                return date;
            } else {
                return date;
            }
        }

        private Object convertTimestamp(Timestamp timestamp, Class fieldType) {
            if (fieldType == LocalDateTime.class) {
                return timestamp.toLocalDateTime();
            } else if (fieldType == Timestamp.class) {
                return timestamp;
            } else {
                return timestamp;
            }
        }
    }
}
