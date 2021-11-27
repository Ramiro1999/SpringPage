package com.curso.springboot.app.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "item_facturas")
public class ItemFactura {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;


    @ManyToOne(fetch = FetchType.LAZY) //unidireccional
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Producto producto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public float calcularImporte(){
        return (float) (cantidad.longValue() * producto.getPrecio());
    }

}
