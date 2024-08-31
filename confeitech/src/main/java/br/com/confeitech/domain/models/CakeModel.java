package br.com.confeitech.domain.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bolo")
@Getter
@Setter
public class CakeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String name;
    @Column(name = "peso", nullable = false)
    private Double weight;
    @Column(name = "preco", nullable = false)
    private Double price;
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String description;
    @Column(name = "contemGluten", nullable = false)
    private Boolean containsGluten;
    @Column(name = "contemLactose", nullable = false)
    private Boolean containsLactose;
    @Column(name = "ativo", nullable = false)
    private Boolean active;

}
