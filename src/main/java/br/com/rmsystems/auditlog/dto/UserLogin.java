package br.com.rmsystems.auditlog.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLogin {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
