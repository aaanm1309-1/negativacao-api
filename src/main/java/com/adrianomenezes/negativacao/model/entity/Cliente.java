package com.adrianomenezes.negativacao.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Cliente {

    @Id
    private int id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false)
    private Double divida;

    @Column(nullable = false)
    private Boolean negativar;

}
