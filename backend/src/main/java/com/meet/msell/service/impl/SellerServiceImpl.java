package com.meet.msell.service.impl;

import com.meet.msell.config.JwtProvider;
import com.meet.msell.domain.AccountStatus;
import com.meet.msell.model.common.Address;
import com.meet.msell.model.seller.Seller;
import com.meet.msell.repository.AddressRepository;
import com.meet.msell.repository.SellerRepository;
import com.meet.msell.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;
    private final AddressRepository addressRepository;

    @Override
    public Seller getSellerProfile(String token) {
        String email = jwtProvider.getEmailFromToken(token);
        return getSellerByEmail(email);
    }

    @Override
    public Seller getSellerByEmail(String email) {
        Seller seller = sellerRepository.findByEmail(email);

        if (seller == null)
            throw new UsernameNotFoundException("Seller not found");

        return seller;
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller isExist = sellerRepository.findByEmail(seller.getEmail());

        if (isExist != null)
            throw new Exception("seller already exists, use different email");

        Address savedAddress = addressRepository.save(seller.getPickupAddress());

        seller.setPickupAddress(savedAddress);
        seller.setPassword(encoder.encode(seller.getPassword()));

        return sellerRepository.save(seller);
    }

    @Override
    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Seller not found")
        );
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findByStatus(status);
    }

    @Override
    public Seller updateSeller(Seller seller) {
        Seller updatedSeller = getSellerById(seller.getId());

        if (seller.getSellerName() != null) updatedSeller.setSellerName(seller.getSellerName());
        if (seller.getMobile() != null) updatedSeller.setMobile(seller.getMobile());
        if (seller.getEmail() != null) updatedSeller.setEmail(seller.getEmail());
        if (seller.getBusinessDetails() != null && seller.getBusinessDetails().getBusinessName() != null)
            seller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
        if (
                seller.getBankDetails() != null
                        && seller.getBankDetails().getIfscCode() != null
                        && seller.getBankDetails().getAccountNumber() != null
                        && seller.getBankDetails().getAccountHolderName() != null
        ) {

            updatedSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());
            updatedSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());
            updatedSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());

        }

        if (
                seller.getPickupAddress() != null
                        && seller.getPickupAddress().getAddress() != null
                        && seller.getPickupAddress().getMobile() != null
                        && seller.getPickupAddress().getCity() != null
                        && seller.getPickupAddress().getState() != null
        ) {

            updatedSeller.getPickupAddress().setAddress(seller.getPickupAddress().getAddress());
            updatedSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());
            updatedSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            updatedSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
        }

        if (seller.getGSTIN() != null) updatedSeller.setGSTIN(seller.getGSTIN());

        return sellerRepository.save(updatedSeller);
    }

    @Override
    public void deleteSeller(Long id) {
        Seller seller = getSellerById(id);

        if (seller == null)
            throw new UsernameNotFoundException("Seller not found");
        sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) {
        Seller seller = getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long id, AccountStatus status) {
        Seller seller = getSellerById(id);
        seller.setStatus(status);
        return sellerRepository.save(seller);
    }
}
