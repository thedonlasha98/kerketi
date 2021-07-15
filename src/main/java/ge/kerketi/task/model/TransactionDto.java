package ge.kerketi.task.model;

import ge.kerketi.task.domain.TransactionHistory;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDto {
    private LocalDateTime date;

    private BigDecimal amount;

    private String initiator;

    private String from;

    private String to;

    public static TransactionDto toDto(TransactionHistory transactionHistory) {

        return TransactionDto.builder()
                .date(transactionHistory.getDate())
                .amount(transactionHistory.getAmount())
                .initiator(transactionHistory.getInitiator())
                .from(transactionHistory.getFrom())
                .to(transactionHistory.getTo())
                .build();
    }
}
