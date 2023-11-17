package br.com.wszd.desafiopicpay.service;

import br.com.wszd.desafiopicpay.domain.usuario.Usuario;
import br.com.wszd.desafiopicpay.domain.usuario.UsuarioTipo;
import br.com.wszd.desafiopicpay.dtos.TransacaoDTO;
import br.com.wszd.desafiopicpay.repository.TransacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransacaoServiceTest {

    @Mock
    private TransacaoRepository repository;

    @Mock
    private AutorizacaoService autorizacaoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private NotificacaoService notificacaoService;

    @Autowired
    @InjectMocks
    private TransacaoService service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Cria transação com sucesso")
    void criarTransacaoCaso1() throws Exception {
        Usuario remetente = new Usuario(1L,
                "Nome Completo",
                "123456",
                "nome@teste.com",
                "123",
                new BigDecimal(100),
                UsuarioTipo.PADRAO);

        Usuario receptor = new Usuario(2L,
                "Segundo Completo ",
                "654321",
                "segundo@teste.com",
                "123",
                new BigDecimal(150),
                UsuarioTipo.PADRAO);

        when(usuarioService.findUsuarioById(1L)).thenReturn(remetente);
        when(usuarioService.findUsuarioById(2L)).thenReturn(receptor);

        when(autorizacaoService.autorizaTransacao(any(), any())).thenReturn(true);

        TransacaoDTO transacaoDto = new TransacaoDTO(new BigDecimal(100), 1L, 2L);
        service.criarTransacao(transacaoDto);

        verify(repository, times(1)).save(any());

        remetente.setSaldo(new BigDecimal(0));
        verify(usuarioService, times(1)).saveUsuario(remetente);

        receptor.setSaldo(new BigDecimal(250));
        verify(usuarioService, times(1)).saveUsuario(receptor);

        verify(notificacaoService, times(1)).enviaNotificacao(remetente, "Transacao enviada!");
        verify(notificacaoService, times(1)).enviaNotificacao(receptor, "Transacao recebida!");

    }

    @Test
    @DisplayName("Não cria transação retorna exception")
    void criarTransacaoCaso2() throws Exception {

        Usuario remetente = new Usuario(1L,
                "Nome Completo",
                "123456",
                "nome@teste.com",
                "123",
                new BigDecimal(100),
                UsuarioTipo.PADRAO);

        Usuario receptor = new Usuario(2L,
                "Segundo Completo ",
                "654321",
                "segundo@teste.com",
                "123",
                new BigDecimal(150),
                UsuarioTipo.PADRAO);

        when(usuarioService.findUsuarioById(1L)).thenReturn(remetente);
        when(usuarioService.findUsuarioById(2L)).thenReturn(receptor);

        when(autorizacaoService.autorizaTransacao(any(), any())).thenReturn(false);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            TransacaoDTO transacaoDto = new TransacaoDTO(new BigDecimal(100), 1L, 2L);
            service.criarTransacao(transacaoDto);
        });

        Assertions.assertEquals("Transacao não atorizada", exception.getMessage());

    }
}