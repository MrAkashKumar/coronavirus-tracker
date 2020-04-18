package com.akash.coronavirustracker.service;


import com.akash.coronavirustracker.DTO.RequestReport;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class CoronaVirusDataService<records> {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<RequestReport> allListReport = new ArrayList<>();

    public List<RequestReport> getAllListReport() {
        return allListReport;
    }

    public CoronaVirusDataService() throws IOException {
    }

    @PostConstruct
    @Scheduled(cron = "* * * * * *")
    public void fetchVirusData() throws IOException {
       //HttpClient httpClient = HttpClient.;
        List<RequestReport> newListReport = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(VIRUS_DATA_URL, String.class);
        //System.out.println(result);
        StringReader csvReader = new StringReader(result);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
        for(CSVRecord record: records) {
            RequestReport requestReport = new RequestReport();

            requestReport.setState(record.get("Province/State"));
            requestReport.setCountry(record.get("Country/Region"));
            Integer latestCases = Integer.parseInt(record.get(record.size()-1));
            Integer prevDayCases = Integer.parseInt(record.get(record.size()-2));
            requestReport.setLatestTotalCases(latestCases);
            requestReport.setDiffFromPrevDay(latestCases-prevDayCases);
            //System.out.println(requestReport);
            newListReport.add(requestReport);
        }
        this.allListReport = newListReport;
    }




}
