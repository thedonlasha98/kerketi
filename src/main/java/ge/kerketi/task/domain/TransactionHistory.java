package ge.kerketi.task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "trans_id")
    private Integer transId;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "initiator")
    private String initiator;

    @Column(name = "from_acct")
    private String from;

    @Column(name = "to_acct")
    private String to;

}