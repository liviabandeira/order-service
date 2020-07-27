package com.api.produto.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    @Column(name="descricao")
    @NotBlank(message = "Campo Descrição deve ser informado")
    @NotNull(message = "Campo Descricão deve ser informado")
    private String descricao;

    @Column(name="sku", unique = true)
    @NotNull(message = "Campo SKU deve ser informado")
    private String sku;

    @Column(name="peso")
    private double peso;

    @Column(name="altura")
    private double altura;

    @Column(name="largura")
    private double largura;

    @Column(name="profundidade")
    private double profundidade;

    @Column(name="fabricante")
    private String fabricante;

    @NotNull(message = "Campo Preco deve ser informado")
    @Column(name="preco")
    private double preco;

    @NotNull(message = "Campo Quantidade deve ser informado")
    @Column(name="quantidade")
    @ColumnDefault("1")
    private int quantidade;

}
