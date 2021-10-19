package com.curso.springboot.app.Model;


import javax.persistence.*;

@Entity
@Table(name = "item_facturas")
public class ItemFactura {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;


    @ManyToOne(fetch = FetchType.LAZY) //unidireccional
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


    public double calcularImporte(){
        return cantidad.longValue() * producto.getPrecio();
    }

}
