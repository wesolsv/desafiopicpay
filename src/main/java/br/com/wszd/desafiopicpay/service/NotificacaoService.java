package br.com.wszd.desafiopicpay.service;

import br.com.wszd.desafiopicpay.domain.usuario.Usuario;
import br.com.wszd.desafiopicpay.dtos.NotificacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificacaoService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RestTemplate restTemplate;

    public void enviaNotificacao(Usuario usuario, String mensagem) throws Exception {

        String email = usuario.getEmail();

        NotificacaoDTO notificacaoRequest = new NotificacaoDTO(email, mensagem);

        ResponseEntity<String> reponseNotificacao = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notificacaoRequest, String.class);

        if(!(reponseNotificacao.getStatusCode() == HttpStatus.OK)){
            System.out.println("Email não enviado");
            throw new Exception("Serviço de notificação down!");
        }
    }
}
