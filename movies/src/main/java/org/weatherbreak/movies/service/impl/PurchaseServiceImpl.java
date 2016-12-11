package org.weatherbreak.movies.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.weatherbreak.movies.entity.Purchase;
import org.weatherbreak.movies.repository.PurchaseRepository;
import org.weatherbreak.movies.service.PurchaseService;
import org.weatherbreak.movies.service.UserService;
import org.weatherbreak.movies.service.exception.InvalidFieldException;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private static final int MAX_NUMBER = 10;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Purchase addPurchase(Purchase purchase) {
        if (purchase.getUser() == null) {
            throw new InvalidFieldException("user is required");
        }

        if (purchase.getScreening() == null) {
            throw new InvalidFieldException("screening is required");
        }

        if (StringUtils.isEmpty(purchase.getNumber()) || purchase.getNumber() > MAX_NUMBER) {
            throw new InvalidFieldException("number is invalid");
        }

        long purchaseId = purchaseRepository.addPurchase(purchase);
        return getPurchase(purchaseId);
    }

    @Override
    @Transactional
    public Purchase getPurchase(long purchaseId) {
        return purchaseRepository.getPurchase(purchaseId);
    }

    @Override
    @Transactional
    public List<Purchase> getPurchases() {
        return purchaseRepository.getPurchases();
    }

    @Override
    @Transactional
    public List<Purchase> getPurchasesByUserId(long userId) {
        return purchaseRepository.getPurchasesByUserId(userId);
    }
}
