package com.prasad.banking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "MobileNumber")
@AllArgsConstructor
@NoArgsConstructor
public class MobileNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String countryCode;
    private String number;

    @Override
    public String toString() {
        return "MobileNumber{" +
                "id=" + id +
                ", countryCode='" + countryCode + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
