package com.meet.msell.model.seller;

import com.meet.msell.domain.AccountStatus;
import com.meet.msell.domain.USER_ROLE;
import com.meet.msell.model.common.Address;
import com.meet.msell.model.business.BankDetails;
import com.meet.msell.model.business.BusinessDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sellerName;

    private String mobile;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    @OneToOne(cascade = CascadeType.ALL)
    private Address pickupAddress = new Address();

    private String GSTIN;

    private USER_ROLE role;

    private boolean isEmailVerified = false;

    private AccountStatus status = AccountStatus.PENDING_VERIFICATION;

}
