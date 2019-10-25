package br.com.rmsystems.auditlog.resource;

import br.com.rmsystems.auditlog.model.mongo.Audit;
import br.com.rmsystems.auditlog.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class AuditResource {

    @Autowired
    AuditService auditService;

    @GetMapping("/audit/{userId}")
    List<Audit> retrieveActivityLog(@PathVariable("userId") final Long userId,
                                    @Valid @NotNull @RequestParam("page") int page,
                                    @Valid @NotNull @RequestParam("size") int size) {
        return auditService.findAuditByUser(userId, page, size);
    }

    @GetMapping("/audit")
    List<Audit> retriverAllActivityLog(@Valid @NotNull @RequestParam("page") int page,
                                       @Valid @NotNull @RequestParam("size") int size) {
        return auditService.listAllAudit(page, size);
    }
}
