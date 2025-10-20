package com.example.bankcards.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class MoneyCalculator {

    public BigDecimal calculatePercentage(BigDecimal amount, BigDecimal percentage) {
        return amount.multiply(percentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b).setScale(2, RoundingMode.HALF_UP);
    }

    public boolean isGreaterThan(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) > 0;
    }

    public boolean isLessThan(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0;
    }

    public BigDecimal formatMoney(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }
}