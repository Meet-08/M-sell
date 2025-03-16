package com.meet.msell.service;

import com.meet.msell.domain.AccountStatus;
import com.meet.msell.model.seller.Seller;

import java.util.List;

public interface SellerService {

    Seller getSellerProfile(String token);

    Seller createSeller(Seller seller) throws Exception;

    Seller getSellerById(Long id);

    Seller getSellerByEmail(String email);

    List<Seller> getAllSellers(AccountStatus status);

    Seller updateSeller(Seller seller);

    void deleteSeller(Long id);

    Seller verifyEmail(String email, String otp);

    Seller updateSellerAccountStatus(Long id, AccountStatus status);


}
