package com.example.raresm.sdproject1.dtos;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ConsumptionDto implements Serializable {
    @NotBlank(message = "Please include a consumption date")
    private final Date date;
    @NotNull(message = "Please include a consumption start hour")
    @Range(min = 0, max = 23, message = "Please select a number between 0 to 23")
    private final Integer startHour;
    @NotNull(message = "Please include a consumption end hour")
    @Range(min = 1, max = 24, message = "Please select a number between 1 to 24")
    private final Integer endHour;
    @NotNull(message = "Please include a value (kWh)")
    private final Double value;

    public ConsumptionDto(Date date, Integer startHour, Integer endHour, Double value) {
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumptionDto entity = (ConsumptionDto) o;
        return Objects.equals(this.date, entity.date) &&
                Objects.equals(this.startHour, entity.startHour) &&
                Objects.equals(this.endHour, entity.endHour) &&
                Objects.equals(this.value, entity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, startHour, endHour, value);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "date = " + date + ", " +
                "startHour = " + startHour + ", " +
                "endHour = " + endHour + ", " +
                "value = " + value + ")";
    }
}
