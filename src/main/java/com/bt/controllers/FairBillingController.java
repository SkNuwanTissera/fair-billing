package com.bt.controllers;

import com.bt.models.Session;
import com.bt.records.UserBilling;
import com.bt.utils.FairBillingUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FairBillingController {

    @GetMapping("/api/fair-billing")
    public List<UserBilling> getFairBilling(@RequestParam String logFilePath) {
        List<Session> userSessions = FairBillingUtils.readLogFile(logFilePath);
        return FairBillingUtils.generateUserBills(userSessions);
    }
}