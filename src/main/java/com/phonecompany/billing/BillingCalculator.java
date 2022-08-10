package com.phonecompany.billing;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BillingCalculator implements TelephoneBillCalculator{


    @Override
    public BigDecimal calculate(String phoneLog) {
        List<PhoneLog> phoneLogList = read(phoneLog);
        String freeNumber = freeNuber(phoneLogList);
        return null;
    }

    private List<PhoneLog> read(String callLog) {
        return Arrays.stream(callLog.split("[\n\r]+"))
                .map(i -> PhoneLog.parse(i))
                .collect(Collectors.toList());
    }

    private String freeNuber(List<PhoneLog> phoneLogList) {
        return phoneLogList.stream()
                .map(PhoneLog::getNumber)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(i -> i.getKey())
                .get();
    }
    private BigDecimal calculateCallPrice(PhoneLog phoneCall) {
        Long minuten = Long.valueOf(ChronoUnit.MINUTES.between(phoneCall.getStartCall(), phoneCall.getEndCall()));
        BigDecimal price = BigDecimal.ZERO;
        BigDecimal minutePrice;
        if (phoneCall.getStartCall().toLocalTime().isAfter(LocalTime.of(8,0)) && phoneCall.getStartCall().toLocalTime().isBefore(LocalTime.of(16,0))) {
            minutePrice = BigDecimal.ONE;
        } else {
            minutePrice = BigDecimal.valueOf(0.5);
        }
        if(minuten > 5L) {
            price = minutePrice.multiply(BigDecimal.valueOf(ChronoUnit.MINUTES.between(phoneCall.getStartCall(), phoneCall.getEndCall())));
        }
        price = minutePrice.multiply(BigDecimal.valueOf(ChronoUnit.MINUTES.between(phoneCall.getStartCall(), phoneCall.getEndCall())));
        return price;
    }

}
