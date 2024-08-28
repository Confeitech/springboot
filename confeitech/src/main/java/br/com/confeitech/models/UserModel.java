package br.com.confeitech.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "senha",nullable = false)
    private String password;
    @Column(name = "telefone",nullable = false)
    private String phone;
    @Column(name = "dtNasc",nullable = false)
    private LocalDate birthDate;
    @Column(name = "ativo", nullable = false)
    private Boolean active;
}
