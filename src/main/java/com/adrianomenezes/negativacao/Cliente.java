package com.adrianomenezes.negativacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cliente {

    private int id;
    private String nome;
    private Double divida;
    private Boolean negativar;

}
