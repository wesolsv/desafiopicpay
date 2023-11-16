package br.com.wszd.desafiopicpay.dtos;

import br.com.wszd.desafiopicpay.domain.usuario.UsuarioTipo;

import java.math.BigDecimal;

public record UsuarioDTO(String nomeCompleto, String documento, BigDecimal saldo, String email, String senha, UsuarioTipo tipo) {
}
