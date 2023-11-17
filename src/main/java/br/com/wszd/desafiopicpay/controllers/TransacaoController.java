package br.com.wszd.desafiopicpay.controllers;

import br.com.wszd.desafiopicpay.domain.transacao.Transacao;
import br.com.wszd.desafiopicpay.dtos.TransacaoDTO;
import br.com.wszd.desafiopicpay.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @PostMapping
    public ResponseEntity<Transacao> criarTransacao(@RequestBody TransacaoDTO transacao) throws Exception {

        Transacao nova = service.criarTransacao(transacao);

        return new ResponseEntity<>(nova, HttpStatus.OK);
    }
}
