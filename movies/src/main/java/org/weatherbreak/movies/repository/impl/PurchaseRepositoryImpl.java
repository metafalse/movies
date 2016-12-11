package org.weatherbreak.movies.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.weatherbreak.movies.entity.Purchase;
import org.weatherbreak.movies.entity.impl.PurchaseImpl;
import org.weatherbreak.movies.repository.PurchaseRepository;

import java.util.List;

@Repository
public class PurchaseRepositoryImpl implements PurchaseRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public long addPurchase(Purchase purchase) {
        return (Long) this.sessionFactory.getCurrentSession().save(purchase);
    }

    @Override
    public Purchase getPurchase(long purchaseId) {
        return (Purchase) this.sessionFactory.getCurrentSession().get(PurchaseImpl.class, purchaseId);
    }

    @Override
    public List<Purchase> getPurchases() {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PurchaseImpl.class);
        List<Purchase> purchases = crit.list();
        return purchases;
    }

    @Override
    public List<Purchase> getPurchasesByUserId(long userId) {
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(PurchaseImpl.class)
            .add(Restrictions.eq("user.id", userId));
        List<Purchase> purchases = crit.list();
        return purchases;
    }
}
