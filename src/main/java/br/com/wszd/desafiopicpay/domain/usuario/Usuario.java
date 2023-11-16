package br.com.wszd.desafiopicpay.domain.usuario;

import br.com.wszd.desafiopicpay.dtos.UsuarioDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "usuarios")
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    @Column(unique = true)
    private String documento;
    @Column(unique = true)
    private String email;
    private String senha;
    private BigDecimal saldo;
    @Enumerated(EnumType.STRING)
    private UsuarioTipo usuarioTipo;

    public Usuario(UsuarioDTO data) {
        this.nomeCompleto = data.nomeCompleto();
        this.documento = data.documento();
        this.email = data.email();
        this.senha = data.senha();
        this.saldo = data.saldo();
        this.usuarioTipo = data.tipo();
    }
}
