package ru.practicum.explorewithme.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Модель сущности посещения ресурса.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "hits")
public class Hit {
    /**
     * Идентификатор посещения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hit_id", nullable = false)
    private Long id;

    /**
     * Название сервиса.
     */
    @Column(name = "hit_app", nullable = false)
    private String app;

    /**
     * URI, по которому был выполнено посещение.
     */
    @Column(name = "hit_uri", nullable = false)
    private String uri;

    /**
     * IP пользователя, выполнившего посещение.
     */
    @Column(name = "hit_user_ip", nullable = false)
    private String ip;

    /**
     * Дата и время посещение.
     */
    @Column(name = "hit_created", nullable = false)
    private LocalDateTime created;
}
