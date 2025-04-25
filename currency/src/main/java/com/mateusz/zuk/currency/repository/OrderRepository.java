package com.mateusz.zuk.currency.repository;

import com.mateusz.zuk.currency.model.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o " +
            "WHERE (:productName is null or o.productName LIKE %:productName% ) " +
            "and (:postDate is null or o.postDate = :postDate)")
    List<Order> findOrdersByCriteria(String productName, LocalDate postDate, Sort sort);
}
