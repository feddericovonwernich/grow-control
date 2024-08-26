package com.fg.grow_control.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SimpleTimestampConverter implements AttributeConverter<SimpleTimestamp, String> {

    @Override
    public String convertToDatabaseColumn(SimpleTimestamp attribute) {
        if (attribute == null) {
            return null;
        }
        // Format the SimpleTimestamp object as a single string (e.g., "yyyy-MM-dd HH:mm:ss")
        return String.format("%04d-%02d-%02d %02d:%02d:%02d",
                attribute.getYear(),
                attribute.getMonth(),
                attribute.getDay(),
                attribute.getHour(),
                attribute.getMinutes(),
                attribute.getSeconds());
    }

    @Override
    public SimpleTimestamp convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        // Parse the string back into a SimpleTimestamp object
        String[] dateTimeParts = dbData.split(" ");
        String[] dateParts = dateTimeParts[0].split("-");
        String[] timeParts = dateTimeParts[1].split(":");

        return new SimpleTimestamp(
                Integer.parseInt(dateParts[2]), // day
                Integer.parseInt(dateParts[1]), // month
                Integer.parseInt(dateParts[0]), // year
                Integer.parseInt(timeParts[0]), // hour
                Integer.parseInt(timeParts[1]), // minutes
                Integer.parseInt(timeParts[2])  // seconds
        );
    }
}