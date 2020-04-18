package com.akash.coronavirustracker.DTO;

import lombok.Data;

@Data
public class RequestReport {

    private String state;
    private String country;
    private Integer latestTotalCases;
    private Integer diffFromPrevDay;

}
