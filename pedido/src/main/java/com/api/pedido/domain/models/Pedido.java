package com.api.pedido.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @Column(name="nomeCliente")
    private String nomeCliente;

    @Column(name="telefone")
    private String telefone;

    @Column(name="valorProduto")
    private double valorProduto;

    @Column(name="valorDesconto")
    private double valorDesconto;

    @Column(name="valorTotal")
    private double valorTotal;

}
