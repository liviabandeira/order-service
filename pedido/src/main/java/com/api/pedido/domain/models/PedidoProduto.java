package com.api.pedido.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "pedido_produto")
public class PedidoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="idPedido")
    private Long idPedido;

    @Column(name="sku")
    private String sku;

    @Column(name="qtnd")
    private int qntd;

    @Column(name="preco")
    private double preco;

}
