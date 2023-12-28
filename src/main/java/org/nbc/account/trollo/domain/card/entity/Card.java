package org.nbc.account.trollo.domain.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nbc.account.trollo.domain.section.entity.Section;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TB_CARD")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    private String color;

    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @Builder
    public Card(final String title, final String content,
        final Section section) {
        this.title = title;
        this.content = content;
        this.section = section;
    }

    public void update(String title, String content, String color, LocalDateTime deadline) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.deadline = deadline;
    }
}
