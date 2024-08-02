package com.demo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {

    @Column(name = "TEST_INT")
    private Integer testInt;

    @Column(name = "TEST_SHORT")
    private Long testShort;

    @Column(name = "TEST_LONG")
    private Long testLong;

    @Column(name = "TEST_FLOAT")
    private Float testFloat;

    @Column(name = "TEST_DOUBLE")
    private Double testDouble;

    @Column(name = "TEST_NUMBER")
    private Number testNumber;

    @Column(name = "TEST_DECIMAL")
    private Decimal testDecimal;

    @Column(name = "TEST_BIGDECIMAL")
    private BigDecimal testBigDecimal;

    @Column(name = "TEST_BOOLEAN")
    private Boolean testBoolean;

    @Column(name = "TEST_STRING")
    private String testString;

    @Column(name = "TEST_TIMESTAMP")
    private Timestamp testTimestamp;

    @Column(name = "TEST_SQL_DATE")
    private java.sql.Date testSqlDate;

    @Column(name = "TEST_JAVA_DATE")
    private java.util.Date testJavaDate;

    @Column(name = "TEST_LOCALDATE")
    private LocalDate testLocalDate;

    @Column(name = "TEST_LOCALDATETIME")
    private LocalDateTime testLocalDateTime;
}
