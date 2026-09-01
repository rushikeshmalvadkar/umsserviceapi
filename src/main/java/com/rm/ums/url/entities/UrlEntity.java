package com.rm.ums.url.entities;

import com.rm.ums.common.entities.AbstractAuditEntity;
import com.rm.ums.common.entities.UrlStatusEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

import static com.rm.ums.url.enums.UrlStatusEnum.IN_ACTIVE;

@Entity
@Table(name = "urls")
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
public class UrlEntity extends AbstractAuditEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "slug", nullable = false)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_status_id", updatable = false, insertable = false)
    private UrlStatusEntity urlStatus;

    @Column(name = "url_status_id", nullable = false)
    private Long urlStatusId;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "expire_at")
    private LocalDate expireAt;


    public boolean isInActive() {
        return IN_ACTIVE.id().equals(urlStatus.getId());
    }

    public void setExpiryDates(LocalDate startAt, LocalDate expireAt){
        this.startAt = startAt;
        this.expireAt = expireAt;
    }

}
