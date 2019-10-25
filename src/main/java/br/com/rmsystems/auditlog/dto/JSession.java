package br.com.rmsystems.auditlog.dto;

import lombok.*;

import java.io.Serializable;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JSession implements Serializable {

    private String sessaoId;
    private String login;
    private Long userId;
}
