package com.javidan.fintech.entity;


import com.javidan.fintech.util.Currency;
import lombok.*;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Column(name = "status")
    private Boolean isActive;
    @Column(name = "accountNo")
    private Integer accountNo;
    @ManyToOne
    @JoinColumn(name="user_id")
    private TechUser user;
}
