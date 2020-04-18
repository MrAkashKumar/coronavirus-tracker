package com.akash.coronavirustracker.controller;

import com.akash.coronavirustracker.DTO.RequestReport;
import com.akash.coronavirustracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String homeController(Model model){
        List<RequestReport> requestReports = coronaVirusDataService.getAllListReport();
        Integer totalReportedCases = requestReports.stream().mapToInt(stat ->stat.getLatestTotalCases()).sum();
        Integer totalReportedNewCases = requestReports.stream().mapToInt(stat ->stat.getDiffFromPrevDay()).sum();
        model.addAttribute("requestReport", requestReports);
        model.addAttribute("latestTotalCases", totalReportedCases);
        model.addAttribute("NewTotalCases", totalReportedNewCases);
        return "home";
    }

}
