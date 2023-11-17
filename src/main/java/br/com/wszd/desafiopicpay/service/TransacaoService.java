package br.com.wszd.desafiopicpay.service;

import br.com.wszd.desafiopicpay.domain.transacao.Transacao;
import br.com.wszd.desafiopicpay.domain.usuario.Usuario;
import br.com.wszd.desafiopicpay.dtos.TransacaoDTO;
import br.com.wszd.desafiopicpay.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private NotificacaoService notificacaoService;

    public Transacao criarTransacao(TransacaoDTO transacaoDTO) throws Exception {
        Usuario remetente = usuarioService.findUsuarioById(transacaoDTO.remetenteId());
        Usuario receptor = usuarioService.findUsuarioById(transacaoDTO.receptorId());

        usuarioService.validaTransacao(remetente, transacaoDTO.valor());

        boolean autorizado = autorizacaoService.autorizaTransacao(remetente, transacaoDTO.valor());

        if(!autorizado){
            throw new Exception("Transacao não atorizada");
        }

        Transacao transacao = new Transacao();
        transacao.setRemetente(remetente);
        transacao.setReceptor(receptor);
        transacao.setValor(transacaoDTO.valor());
        transacao.setTimestamp(LocalDateTime.now());

        remetente.setSaldo(remetente.getSaldo().subtract(transacaoDTO.valor()));
        receptor.setSaldo(receptor.getSaldo().add(transacaoDTO.valor()));

        transacao = repository.save(transacao);
        usuarioService.saveUsuario(remetente);
        usuarioService.saveUsuario(receptor);

        notificacaoService.enviaNotificacao(remetente, "Transacao enviada!");
        notificacaoService.enviaNotificacao(receptor, "Transacao recebida!");
        return transacao;
    }
}
