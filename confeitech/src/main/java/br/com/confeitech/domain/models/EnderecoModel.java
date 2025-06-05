package br.com.confeitech.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String cep;
    @Column(name = "logradouro", nullable = false)
    private String address;
    @Column(name = "bairro")
    private String neighborhood;
    @Column(nullable = false)
    private String uf;
}
