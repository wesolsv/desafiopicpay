package br.com.wszd.desafiopicpay.repository;

import br.com.wszd.desafiopicpay.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByDocumento(String document);

    Optional<Usuario> findUsuarioById(Long id);
}
