package br.edu.ifpe.afogados.desplugai.enums;

public enum PerfilGlobalEnum {
    ROLE_ADMIN("ADMIN", "Administrador do sistema, possui permissão para todas as funcionalidades"),
    ROLE_SECRETARIA("SECRETARIA", "Secretaria de Educação credenciada no sistema"),
    ROLE_PROFESSOR_PENDING("PROFESSOR_PENDING", "Professor recém cadastrado no sistema com inscrição não homologada"),
    ROLE_PROFESSOR("PROFESSOR", "Professor com inscrição homologada no sistema");

    private final String role;
    private final String descricao;

    PerfilGlobalEnum(String role, String descricao) {
        this.role = role;
        this.descricao = descricao;
    }

    public String getRole() {
        return role;
    }

    public String getDescricao() {
        return descricao;
    }

}
