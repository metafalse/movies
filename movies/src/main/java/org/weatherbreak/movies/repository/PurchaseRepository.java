package org.weatherbreak.movies.repository;

import org.weatherbreak.movies.entity.Purchase;

import java.util.List;

public interface PurchaseRepository {

    long addPurchase(Purchase purchase);

    Purchase getPurchase(long purchaseId);

    List<Purchase> getPurchases();

    List<Purchase> getPurchasesByUserId(long userId);
}
