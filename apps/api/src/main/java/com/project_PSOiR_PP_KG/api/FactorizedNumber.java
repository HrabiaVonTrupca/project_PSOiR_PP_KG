package com.project_PSOiR_PP_KG.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FactorizedNumber {
    private Long number;
    private int numberOfDigits;
    private List<Long> factors;
    private boolean isPrime;

    public FactorizedNumber(Long number) {
        this.number = number;
    }

    public Long getNumber() {
        return number;
    }

    public int getNumberOfDigits() {
        return numberOfDigits;
    }

    public void setNumberOfDigits(int numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }

    public List<Long> getFactors() {
        return factors;
    }

    public void setFactors(List<Long> factors) {
        this.factors = factors;
    }

    public boolean isPrime() {
        return isPrime;
    }

    public void setPrime(boolean prime) {
        isPrime = prime;
    }
}
