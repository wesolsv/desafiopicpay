package br.com.wszd.desafiopicpay.service;

import br.com.wszd.desafiopicpay.domain.usuario.Usuario;
import br.com.wszd.desafiopicpay.domain.usuario.UsuarioTipo;
import br.com.wszd.desafiopicpay.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public void saveUsuario(Usuario usuario){
        repository.save(usuario);
    }

    public void validaTransacao(Usuario remetente, BigDecimal valorTransacao) throws Exception {

        if(remetente.getUsuarioTipo() == UsuarioTipo.LOJISTA){
            throw new Exception("Usuario Lojista não realiza transações!");
        }

        if(remetente.getSaldo().compareTo(valorTransacao) < 0){
            throw new Exception("O usuário não tem saldo suficiente para transação!");
        }
    }

    public Usuario findUsuarioById(Long idUsuario) throws Exception {
       return repository.findUsuarioById(idUsuario).orElseThrow(() -> new Exception("O usuario não foi encontrado!"));
    }
}
