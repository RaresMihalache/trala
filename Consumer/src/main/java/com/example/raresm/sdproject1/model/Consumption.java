package com.example.raresm.sdproject1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Please include a consumption date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @NotNull(message = "Please include a consumption start hour")
    @Range(min = 0, max = 23, message = "Please select a number between 0 to 23")
    private Integer startHour;
    @NotNull(message = "Please include a consumption end hour")
    @Range(min = 1, max = 24, message = "Please select a number between 1 to 24")
    private Integer endHour;
    @NotNull(message = "Please include a consumption value (kWh)")
    @DecimalMin(value = "0.01", message = "Max Consumption is too low") @DecimalMax(value = "4.5", message = "Max Consumption is too big")
    private Double consumptionValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;

    public Consumption() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Double getConsumptionValue() {
        return consumptionValue;
    }

    public void setConsumptionValue(Double consumptionValue) {
        this.consumptionValue = consumptionValue;
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "id=" + id +
                ", date=" + date +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", consumptionValue=" + consumptionValue +
                ", backlog=" + backlog +
                '}';
    }
}
