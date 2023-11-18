package br.com.pcvj.awss3app.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@Entity
public class Ebook {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    private String title;

    private String author;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private FileReference cover;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private FileReference attachment;

    protected Ebook() {
    }

    public Ebook(UUID id, OffsetDateTime createdAt,
                 String title, String author,
                 FileReference cover, FileReference attachment) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(author);
        Objects.requireNonNull(cover);
        Objects.requireNonNull(attachment);

        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.author = author;
        this.cover = cover;
        this.attachment = attachment;
    }

    public void update(Ebook ebookUpdated) {
        Objects.requireNonNull(ebookUpdated);
        this.title = ebookUpdated.title;
        this.author = ebookUpdated.author;
        this.cover = ebookUpdated.cover;
        this.attachment = ebookUpdated.attachment;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Ebook ebook = (Ebook) o;
        return getId() != null && Objects.equals(getId(), ebook.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
