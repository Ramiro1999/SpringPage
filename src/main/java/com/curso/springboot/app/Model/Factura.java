package com.curso.springboot.app.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String descripcion;
    private String observacion;

    @ManyToOne(fetch = FetchType.LAZY) //relacion bidireccional
    @JsonBackReference
    private Cliente cliente;



    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fecha;


    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL) //relacion unidireccional
    @JoinColumn(name = "factura_id")
    private List<ItemFactura> itemFacturas;

    @PrePersist
    public void prePersist(){
        fecha = new Date();
    }

    public Factura() {
        this.itemFacturas = new ArrayList<ItemFactura>();
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }



    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<ItemFactura> getItemFacturas() {
        return itemFacturas;
    }

    public void setItemFacturas(List<ItemFactura> itemFacturas) {
        this.itemFacturas = itemFacturas;
    }

    public void addItemFactura(ItemFactura itemFactura){
        this.itemFacturas.add(itemFactura);
    }


    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getTotal(){
        Double total = 0.0;
        int size = itemFacturas.size();

        for(int i=0; i<size; i++){
            total +=itemFacturas.get(i).calcularImporte();
        }
        return total;
    }
}
