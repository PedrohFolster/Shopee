package com.projeto.shopee.util;

import com.projeto.shopee.dto.EnderecoDTO;
import java.time.LocalDate;
import java.time.Period;

public class ValidationUtils {

    public static boolean isValidCpf(String cpf) {
        // Verificar se o CPF contém apenas dígitos
        if (!cpf.matches("\\d{11}")) {
            return false;
        }

        // Verificar se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcular os dígitos verificadores
        int[] pesos = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * pesos[i];
        }
        int primeiroDigitoVerificador = 11 - (soma % 11);
        if (primeiroDigitoVerificador >= 10) {
            primeiroDigitoVerificador = 0;
        }

        pesos = new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * pesos[i];
        }
        int segundoDigitoVerificador = 11 - (soma % 11);
        if (segundoDigitoVerificador >= 10) {
            segundoDigitoVerificador = 0;
        }

        // Verificar se os dígitos verificadores são iguais aos informados
        return cpf.charAt(9) - '0' == primeiroDigitoVerificador && cpf.charAt(10) - '0' == segundoDigitoVerificador;
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    public static boolean isOlderThan12Years(LocalDate birthDate) {
        if (birthDate == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthDate, today);
        return age.getYears() >= 12;
    }

    public static boolean isValidEmail(String email) {
        // Verificar se o e-mail contém "@" e "."
        return email != null && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }

    public static boolean isValidTelefone(String telefone) {
        // Verificar se o telefone contém exatamente 11 dígitos
        return telefone != null && telefone.matches("\\d{11}");
    }

    public static boolean isValidNomeLoja(String nome) {
        return nome != null && nome.trim().length() >= 2;
    }

    public static boolean isValidCategoriaLoja(Long categoriaLojaId) {
        return categoriaLojaId != null;
    }

    public static boolean isValidNomeCompleto(String nome) {
        return nome != null && !nome.isEmpty() && nome.split(" ").length >= 2;
    }

    public static boolean isValidDataNascimento(LocalDate dataNascimento) {
        return dataNascimento != null && isOlderThan12Years(dataNascimento);
    }

    public static boolean isValidEndereco(EnderecoDTO enderecoDTO) {
        if (enderecoDTO == null) return false;
        return isValidCep(enderecoDTO.getCep()) &&
               isValidString(enderecoDTO.getRua()) &&
               isValidString(enderecoDTO.getNumero()) &&
               isValidString(enderecoDTO.getCidade()) &&
               isValidString(enderecoDTO.getEstado()) &&
               isValidString(enderecoDTO.getPais());
    }

    private static boolean isValidCep(String cep) {
        return cep != null && cep.length() == 8;
    }

    private static boolean isValidString(String value) {
        return value != null && !value.isEmpty();
    }
}