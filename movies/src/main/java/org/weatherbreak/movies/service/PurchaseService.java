package org.weatherbreak.movies.service;

import org.weatherbreak.movies.entity.Purchase;

import java.util.List;

public interface PurchaseService {

    Purchase addPurchase(Purchase purchase);

    Purchase getPurchase(long purchaseId);

    List<Purchase> getPurchases();

    List<Purchase> getPurchasesByUserId(long userId);
}
