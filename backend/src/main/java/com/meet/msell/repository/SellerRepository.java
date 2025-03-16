package com.meet.msell.repository;

import com.meet.msell.domain.AccountStatus;
import com.meet.msell.model.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findByEmail(String email);

    List<Seller> findByStatus(AccountStatus status);
    
}
