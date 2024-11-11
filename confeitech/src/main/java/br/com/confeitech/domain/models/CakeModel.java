package br.com.confeitech.domain.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "bolo")
@Getter
@Setter
public class CakeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @JsonIgnore // ignoramos no JSON pois não faz sentido retornar um vetor de bytes num JSON!
    @Column(length = 50 * 1024 * 1024) // 50 Mega Bytes
    private byte[] imagem;

    @Column(name = "nome", nullable = false)
    private String name;
    @Column(name = "peso", nullable = false)
    private Double weight;
    @Column(name = "preco", nullable = false)
    private Double price;
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "bolo_has_adicional", // Tabela de junção
            joinColumns = @JoinColumn(name = "bolo_id"),
            inverseJoinColumns = @JoinColumn(name = "adicional_id")
    )
    private Set<AdicionalModel> adicionais;

    @OneToMany(mappedBy = "bolo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EncomendaModel> encomendas;


    @Column(name = "ativo", nullable = false)
    private Boolean active;

}
