package br.edu.ifpe.afogados.desplugai.security;

import br.edu.ifpe.afogados.desplugai.enums.PerfilGlobalEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PerfisPermitidos {

    PerfilGlobalEnum[] value();
}
