package br.com.wszd.desafiopicpay.service;

import br.com.wszd.desafiopicpay.domain.transacao.Transacao;
import br.com.wszd.desafiopicpay.domain.usuario.Usuario;
import br.com.wszd.desafiopicpay.dtos.TransacaoDTO;
import br.com.wszd.desafiopicpay.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    private UsuarioService usuarioService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificacaoService notificacaoService;

    public Transacao createTransaction(TransacaoDTO transacaoDTO) throws Exception {
        Usuario remetente = usuarioService.findUsuarioById(transacaoDTO.remetenteId());
        Usuario receptor = usuarioService.findUsuarioById(transacaoDTO.receptorId());

        usuarioService.validaTransacao(remetente, transacaoDTO.valor());

        boolean autorizado = autorizaTransacao(remetente, transacaoDTO.valor());

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

    public boolean autorizaTransacao(Usuario remetente, BigDecimal valor){
       ResponseEntity<Map> response = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

       if(response.getStatusCode().isSameCodeAs(HttpStatus.OK)){
           String message = (String) response.getBody().get("message");

           return "Autorizado".equalsIgnoreCase(message);
       }else{
           return false;
       }
    }
}
