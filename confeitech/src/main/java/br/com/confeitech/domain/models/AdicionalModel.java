package br.com.confeitech.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "adicionais")
@Getter
@Setter
public class AdicionalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private Double preco;

    @ManyToMany(mappedBy = "adicionais") // O lado inverso da relação
    private Set<CakeModel> bolos;

    @Column
    private Boolean active;

}