package br.com.rmsystems.auditlog.model.h2;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "SPRING_SESSION_ATTRIBUTES")
public class SessionAttributes {

    @EmbeddedId
    private SpringSessionAttributesId springSessionAttributesId;

    @Column(name = "ATTRIBUTE_BYTES")
    private String attributeJSON;

    @ManyToOne
    @JoinColumn(name="SESSION_PRIMARY_ID", insertable = false, updatable = false)
    private Session session;
}
