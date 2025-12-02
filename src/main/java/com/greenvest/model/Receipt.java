package com.greenvest.model;

import com.greenvest.common.Preconditions;
import java.time.LocalDateTime;

/**
 * Receipt generated after a successful trade.
 * Uses immutability and preconditions (SOLID).
 */
public class Receipt {

    private final String id;
    private final String tradeId;
    private final LocalDateTime generatedAt;

    /**
     * @pre tradeId != null && !tradeId.isBlank()
     * @pre generatedAt != null
     */
    public Receipt(String id, String tradeId, LocalDateTime generatedAt) {
        Preconditions.requireNonNull(id, "receipt id must not be null");
        Preconditions.require(!tradeId.isBlank(), "trade id must not be blank");
        Preconditions.requireNonNull(generatedAt, "generatedAt must not be null");

        this.id = id;
        this.tradeId = tradeId;
        this.generatedAt = generatedAt;
    }

    public String getId() {
        return id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    @Override
    public String toString() {
        return """
                Receipt:
                -----------------
                Receipt ID: %s
                Trade ID: %s
                Generated On: %s
                """.formatted(id, tradeId, generatedAt);
    }
}
