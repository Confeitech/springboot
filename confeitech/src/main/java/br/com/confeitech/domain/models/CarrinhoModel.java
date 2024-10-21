package br.com.confeitech.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "carrinho")
@Getter
@Setter
public class CarrinhoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double precoTotal;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    @Column(nullable = false)
    private LocalDate dataCompra;
    @Column(nullable = false)
    private LocalDateTime dataRetirada;
    @OneToMany(mappedBy = "carrinho", fetch = FetchType.LAZY)
    private Set<EncomendaModel> encomendas;
}
