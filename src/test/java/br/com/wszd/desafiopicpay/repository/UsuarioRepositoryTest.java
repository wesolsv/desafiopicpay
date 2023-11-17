package br.com.wszd.desafiopicpay.repository;

import br.com.wszd.desafiopicpay.domain.usuario.Usuario;
import br.com.wszd.desafiopicpay.domain.usuario.UsuarioTipo;
import br.com.wszd.desafiopicpay.dtos.UsuarioDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Usuario existe")
    void findUsuarioByDocumentoSucesso() {
        UsuarioDTO infos = new UsuarioDTO("Nome completo",
                "123456789",
                new BigDecimal(100),
                "nome@gmail.com",
                "12345", UsuarioTipo.PADRAO);

        novoUsuario(infos);

        Optional<Usuario> retorno = repository.findUsuarioByDocumento(infos.documento());

        assertThat(retorno.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Usuario inexistente")
    void findUsuarioByDocumentoErro() {
        String documento = "123456789";

        Optional<Usuario> retorno = repository.findUsuarioByDocumento(documento);

        assertThat(retorno.isEmpty()).isTrue();
    }


    private Usuario novoUsuario(UsuarioDTO infos){
        Usuario novoUsuario = new Usuario(infos);
        this.entityManager.persist(novoUsuario);
        return novoUsuario;
    }
}