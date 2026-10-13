package br.edu.ifpe.afogados.desplugai.enums;

public enum PerfilGlobalEnum {
    ROLE_ADMIN(1, "ADMIN", "Administrador do sistema, possui permissão para todas as funcionalidades"),
    ROLE_SECRETARIA(2, "SECRETARIA", "Secretaria de Educação credenciada no sistema"),
    ROLE_PROFESSOR_PENDING(3, "PROFESSOR_PENDING", "Professor recém cadastrado no sistema com inscrição não homologada"),
    ROLE_PROFESSOR(4, "PROFESSOR", "Professor com inscrição homologada no sistema");

    private final Integer id;
    private final String role;
    private final String descricao;

    PerfilGlobalEnum(Integer id, String role, String descricao) {
        this.role = role;
        this.descricao = descricao;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getDescricao() {
        return descricao;
    }

}
