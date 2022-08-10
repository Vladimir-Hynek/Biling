package com.phonecompany.billing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneLog {

    private String number;
    private LocalDateTime startCall;
    private LocalDateTime endCall;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static PhoneLog parse (@NonNull String callLine) {
        String[] callItems = callLine.split(",");

        PhoneLog phoneLog = new PhoneLog();
        phoneLog.number = callItems[0];
        phoneLog.startCall = LocalDateTime.parse(callItems[1], dateTimeFormatter);
        phoneLog.endCall = LocalDateTime.parse(callItems[2], dateTimeFormatter);

        return phoneLog;
    }
}
