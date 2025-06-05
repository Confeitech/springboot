package br.com.confeitech.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
