package com.abc.doge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PurchasePointsController {


    @GetMapping("/purchase-points")
    public String showPurchasePointsPage() {
        // resources/templates/Purchase_points.html 반환
        return "Purchase_points";
    }

    @GetMapping("/Purchase")
    public String showPurchasePage() {
        // resources/templates/Purchase.html 반환
        return "Purchase";
    }
}