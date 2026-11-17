package br.edu.ifpe.afogados.desplugai.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonPropertyOrder({
        "status",
        "path",
        "timestamp",
        "message",
        "errors"
})
@Getter
@Setter
@NoArgsConstructor
public class ApiErrorDTO {

    private Integer status;
    private String path;
    private Date timestamp;
    private String message;
    private List<String> errors = new ArrayList<>();
}
