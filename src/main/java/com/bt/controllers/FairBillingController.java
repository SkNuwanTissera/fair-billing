package com.bt.controllers;

import com.bt.records.UserBilling;
import com.bt.utils.FairBillingUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FairBillingController {

    /**
     * This method reads a log file containing user sessions and calculates the total billable time for each user.
     * @param logFilePath the path to the log file containing user sessions.
     * @return a list of UserBilling objects containing the user ID, the number of sessions, and the total billable time in seconds for each user.
     */
    @GetMapping("/api/fair-billing")
    public List<UserBilling> getFairBilling(@RequestParam String logFilePath) {
        return FairBillingUtils.generateUserBills(FairBillingUtils.readLogFile(logFilePath));
    }
}