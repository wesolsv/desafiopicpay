package br.com.wszd.desafiopicpay.dtos;

import java.math.BigDecimal;

public record TransacaoDTO(BigDecimal valor, Long remetenteId, Long receptorId) {
}
