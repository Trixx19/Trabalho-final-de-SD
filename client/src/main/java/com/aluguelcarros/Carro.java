package com.aluguelcarros;

public class Carro {
    private Integer id;
    private String descricao;
    private String placa;
    private String modelo;
    private Integer ano;
    private Integer valorDiaria;
    private Boolean ocupado;

    Carro(String descricao, String placa, String modelo, Integer ano, Integer valorDiaria){
        this.id = -1;
        this.descricao = descricao;
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.valorDiaria = valorDiaria;
        this.ocupado = false;
    }

    public String getModeloPlaca(){
        return "Modelo " + this.modelo + " - Placa " + this.placa;
    }

    public Integer get(){
        return this.id;
    }

    public String getdescricao(){
        return this.descricao;
    }

    public Integer getAno(){
        return this.ano;
    }

    public Integer getValorDiaria(){
        return this.valorDiaria;
    }

    public Boolean ocupado(){
        return this.ocupado;
    }

}