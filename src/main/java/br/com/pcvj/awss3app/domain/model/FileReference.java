package br.com.pcvj.awss3app.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@Entity
public class FileReference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    private String fileName;

    private String contentType;

    private Long contentLength;

    @Builder.Default
    private boolean temp = true;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Getter
    @AllArgsConstructor
    public enum Type {
        DOCUMENT(false),
        IMAGE(true);

        private final boolean publicAccess;

    }

    protected FileReference() {
    }

    public FileReference(UUID id, OffsetDateTime createdAt,
                         String fileName, String contentType,
                         Long contentLength, boolean temp, Type type) {
        Objects.requireNonNull(fileName);
        Objects.requireNonNull(contentType);
        Objects.requireNonNull(contentLength);

        this.id = id;
        this.createdAt = createdAt;
        this.fileName = fileName;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.temp = temp;
        this.type = type;
    }

    public boolean isPublicAccessible() {
        return type.publicAccess;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public String getPath() {
        return this.id + "/" + this.fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileReference that = (FileReference) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
