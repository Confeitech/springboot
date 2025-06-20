package br.com.confeitech.domain.models;

import br.com.confeitech.domain.enums.AndamentoEncomenda;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Table(name = "encomenda")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EncomendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double preco;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String observacoes;

    @Column(nullable = false)
    private Double peso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bolo_id", nullable = false)
    private CakeModel bolo;

    @Column
    private String adicionais;

    @Enumerated(EnumType.STRING)
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
