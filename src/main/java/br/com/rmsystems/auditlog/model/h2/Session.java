package br.com.rmsystems.auditlog.model.h2;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "SPRING_SESSION")
public class Session {

    @Id
    @Column(name="PRIMARY_ID")
    private String primaryId;

    @Column(name="SESSION_ID")
    private String sessionId;

    @Column(name="CREATION_TIME")
    private Long creationTime;

    @Column(name="LAST_ACCESS_TIME")
    private Long lastAccessTime;

    @Column(name="MAX_INACTIVE_INTERVAL")
    private Long maxInactiveInterval;

    @Column(name="EXPIRY_TIME")
    private Long expiryTime;

    @Column(name="PRINCIPAL_NAME")
    private String principalName;

    @OneToMany(mappedBy = "session", cascade=CascadeType.ALL)
    private List<SessionAttributes> attributes;

}
