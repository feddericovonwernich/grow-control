package com.fg.grow_control.entity;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FieldDescription;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.ParameterClass;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.RequiredField;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@ParameterClass
@ToString
public class SimpleTimestamp {

    @RequiredField
    @FieldDescription(description = "The day of the month")
    @NotNull
    Integer day;

    @RequiredField
    @FieldDescription(description = "The month of the year")
    @NotNull
    Integer month;

    @RequiredField
    @FieldDescription(description = "The year")
    @NotNull
    Integer year;

    @RequiredField
    @FieldDescription(description = "The hour of the day")
    @NotNull
    Integer hour;

    @RequiredField
    @FieldDescription(description = "The minute of the hour")
    @NotNull
    Integer minutes;

    @RequiredField
    @FieldDescription(description = "The second of the minute")
    @NotNull
    Integer seconds;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SimpleTimestamp that = (SimpleTimestamp) obj;
        return Objects.equals(this.day, that.day) &&
                Objects.equals(this.month, that.month) &&
                Objects.equals(this.year, that.year) &&
                Objects.equals(this.hour, that.hour) &&
                Objects.equals(this.minutes, that.minutes) &&
                Objects.equals(this.seconds, that.seconds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year, hour, minutes, seconds);
    }
    
    public static SimpleTimestamp fromSqlTimestamp(java.sql.Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);

        return SimpleTimestamp.builder()
                .day(calendar.get(Calendar.DAY_OF_MONTH))
                .month(calendar.get(Calendar.MONTH) + 1) // Calendar.MONTH is zero-based
                .year(calendar.get(Calendar.YEAR))
                .hour(calendar.get(Calendar.HOUR_OF_DAY))
                .minutes(calendar.get(Calendar.MINUTE))
                .seconds(calendar.get(Calendar.SECOND))
                .build();
    }

    public Timestamp toSqlTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.year);
        calendar.set(Calendar.MONTH, this.month - 1); // Calendar.MONTH is zero-based
        calendar.set(Calendar.DAY_OF_MONTH, this.day);
        calendar.set(Calendar.HOUR_OF_DAY, this.hour);
        calendar.set(Calendar.MINUTE, this.minutes);
        calendar.set(Calendar.SECOND, this.seconds);
        calendar.set(Calendar.MILLISECOND, 0); // Assuming no milliseconds, set it to zero

        return new Timestamp(calendar.getTimeInMillis());
    }


}
