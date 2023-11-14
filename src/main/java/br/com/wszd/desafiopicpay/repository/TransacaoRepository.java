package br.com.wszd.desafiopicpay.repository;

import br.com.wszd.desafiopicpay.domain.transacao.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
