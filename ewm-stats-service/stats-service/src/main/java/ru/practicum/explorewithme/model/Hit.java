package ru.practicum.explorewithme.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
