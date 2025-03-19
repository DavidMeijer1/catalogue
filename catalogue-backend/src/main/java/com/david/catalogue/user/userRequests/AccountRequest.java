package com.david.catalogue.user.userRequests;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Jacksonized
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ACCOUNT_REQUESTS")
public class AccountRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_REQUESTS_ID_SEQ")
    @SequenceGenerator(name = "ACCOUNT_REQUESTS_ID_SEQ", sequenceName = "ACCOUNT_REQUESTS_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;
    @NonNull
    private String username;
//    @Column(columnDefinition = "TIMESTAMP(0)")
//    private LocalDateTime dateRequested;
}