package br.com.rmsystems.auditlog.model.mongo;

import br.com.rmsystems.auditlog.dto.JSession;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Audit implements Serializable {

    @Id
    private ObjectId _id;
    private Date actionDate;
    private JSession session;
    private String actionUrl;
    private Map header;
    private Map body;
    private Map response;

    private String get_id() {
        return _id.toHexString();
    }

}
