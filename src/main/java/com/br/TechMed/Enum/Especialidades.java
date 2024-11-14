package com.br.TechMed.Enum;

/**
 * Enumeração que representa as especialidades médicas.
 */
public enum Especialidades {
    CARDIOLOGISTA,
    DERMATOLOGISTA,
    ENDOCRINOLOGISTA,
    GINECOLOGISTA,
    NEUROLOGISTA,
    OFTALMOLOGISTA,
    ORTOPEDISTA,
    OTORRINOLARINGOLOGISTA,
    PEDIATRA,
    PSIQUIATRA,
    UROLOGISTA;

    /**
     * Retorna a descrição da especialidade médica.
     *
     * @param especialidade a especialidade médica
     * @return a descrição da especialidade médica
     */
    public static String getDescricao(Especialidades especialidade) {
        switch (especialidade) {
            case CARDIOLOGISTA:
                return "Especialista em doenças do coração e do sistema cardiovascular.";
            case DERMATOLOGISTA:
                return "Especialista em doenças da pele.";
            case ENDOCRINOLOGISTA:
                return "Especialista em doenças hormonais.";
            case GINECOLOGISTA:
                return "Especialista em saúde feminina.";
            case NEUROLOGISTA:
                return "Especialista em doenças do sistema nervoso.";
            case OFTALMOLOGISTA:
                return "Especialista em doenças dos olhos.";
            case ORTOPEDISTA:
                return "Especialista em doenças do sistema musculoesquelético.";
            case OTORRINOLARINGOLOGISTA:
                return "Especialista em doenças do ouvido, nariz e garganta.";
            case PEDIATRA:
                return "Especialista em saúde infantil.";
            case PSIQUIATRA:
                return "Especialista em doenças mentais.";
            case UROLOGISTA:
                return "Especialista em doenças do sistema urinário.";
            default:
                return "Descrição não disponível.";
        }
    }

    /**
     * Converte uma string para uma instância da enumeração, ignorando maiúsculas e minúsculas.
     *
     * @param nomeEspecialidade o nome da especialidade
     * @return a instância da enumeração correspondente
     */
    public static Especialidades fromStringIgnoreCase(String nomeEspecialidade) {
        for (Especialidades especialidade : Especialidades.values()) {
            if (especialidade.name().equalsIgnoreCase(nomeEspecialidade)) {
                return especialidade;
            }
        }
        throw new IllegalArgumentException("Especialidade não encontrada: " + nomeEspecialidade);
    }
}