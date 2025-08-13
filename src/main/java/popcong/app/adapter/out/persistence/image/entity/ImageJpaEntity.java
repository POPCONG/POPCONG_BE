package popcong.app.adapter.out.persistence.image.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import popcong.app.domain.image.model.ImageableType;

import java.time.LocalDateTime;

@Entity
@Table(name = "IMAGE")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ImageJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId", nullable = false)
    private Long imageId;

    @Column(name = "imageUrl", nullable = false, length = 1000)
    private String imageUrl;

    @Column(name = "saveOrder", nullable = false)
    private Integer saveOrder;

    @Builder.Default
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "imageableId", nullable = false)
    private Long imageableId;

    @Enumerated(EnumType.STRING)
    @Column(name = "imageableType", nullable = false)
    private ImageableType imageableType;
}
