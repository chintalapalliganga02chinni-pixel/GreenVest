/*package com.greenvest.controller;


import com.greenvest.model.CreditListing;
import com.greenvest.model.Transaction;
import com.greenvest.service.MarketplaceService;

import java.util.List;

public class MarketplaceController {

    private final MarketplaceService marketplaceService;

    public MarketplaceController(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    public List<CreditListing> getMarketplaceListings() {
        return marketplaceService.getActiveListings();
    }

    public Transaction purchase(String buyerId, String listingId, int quantity) {
        return marketplaceService.purchase(buyerId, listingId, quantity);
    }
}

 */


package com.greenvest.controller;

import com.greenvest.model.CreditListing;
import com.greenvest.model.Transaction;
import com.greenvest.service.MarketplaceService;
import com.greenvest.service.ReceiptService;

import java.util.List;

public class MarketplaceController {

    private final MarketplaceService marketplaceService;
    private final ReceiptService receiptService;

    public MarketplaceController(MarketplaceService marketplaceService,
                                 ReceiptService receiptService) {
        this.marketplaceService = marketplaceService;
        this.receiptService = receiptService;
    }

    public List<CreditListing> getAvailableListings() {
        return marketplaceService.getAvailableListings();
    }

    public Transaction purchase(String buyerId, String listingId, int quantity) {
        return marketplaceService.purchase(buyerId, listingId, quantity);
    }

    // Used by BuyerMenu to print the formatted receipt text
    public String getReceiptText(Transaction transaction) {
        return receiptService.generateReceipt(transaction).getRawText();
    }
}

