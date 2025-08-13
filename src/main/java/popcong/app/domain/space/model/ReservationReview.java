package popcong.app.domain.space.model;

import java.time.LocalDateTime;

public record ReservationReview(
        Long reservationReviewId,
        Long reservationId,
        Double rating,
        String description,
        LocalDateTime createdAt
) {
}
