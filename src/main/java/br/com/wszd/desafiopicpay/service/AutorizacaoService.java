package br.com.wszd.desafiopicpay.service;

import br.com.wszd.desafiopicpay.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AutorizacaoService {

    @Autowired
    private RestTemplate restTemplate;

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
