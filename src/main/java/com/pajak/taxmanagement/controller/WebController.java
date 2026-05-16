package com.pajak.taxmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard.html";
    }

    @GetMapping("/onboarding")
    public String onboarding() {
        return "onboarding.html";
    }

    @GetMapping("/tax-income")
    public String incomePage() { 
        return "tax-income"; }

    @GetMapping("/tax-vehicle")
    public String vehiclePage() { 
        return "tax-vehicle"; }

    @GetMapping("/tax-property")
    public String propertyPage() { 
        return "tax-property"; }

    @GetMapping("/tax-consumption")
    public String consumptionPage() { 
        return "tax-consumption"; }
}