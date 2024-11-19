package br.com.confeitech.domain.models;

import br.com.confeitech.domain.enums.AndamentoEncomenda;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Table(name = "encomenda")
@Getter
@Setter
public class EncomendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double preco;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bolo_id", nullable = false)
    private CakeModel bolo;

    @Column
    private String adicionais;

    @Column(nullable = false)
    private AndamentoEncomenda andamento;

    @Column(nullable = false)
    private LocalDate dataCriacao;

    @Column(nullable = false)
    private LocalDate dataRetirada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrinho_id")
    private CarrinhoModel carrinho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

}
