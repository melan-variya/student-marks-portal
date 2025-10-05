package com.example.studentportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;


@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 50)
    @Column(length = 50, unique = true)
    private String contact; // We'll use this as the login username for STUDENTs

    @Size(max = 50)
    private String city;

    @Min(0) @Max(100)
    private int math = 0;

    @Min(0) @Max(100)
    private int science = 0;

    @Min(0) @Max(100)
    private int english = 0;

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getMath() { return math; }
    public void setMath(int math) { this.math = math; }

    public int getScience() { return science; }
    public void setScience(int science) { this.science = science; }

    public int getEnglish() { return english; }
    public void setEnglish(int english) { this.english = english; }

    // Calculated fields
    @Transient
    public int getTotal() {
        return math + science + english;
    }

    @Transient
    public BigDecimal getPercentage() {
        BigDecimal total = BigDecimal.valueOf(getTotal());
        BigDecimal max = BigDecimal.valueOf(300);
        BigDecimal percent = total.multiply(BigDecimal.valueOf(100)).divide(max, 4, RoundingMode.HALF_UP);
        return percent.setScale(2, RoundingMode.HALF_UP);
    }
}
