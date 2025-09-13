package org.example.junit;

public class Calculadora {

    public Integer Sumar (Integer a, Integer b){
        if (a==null || b==null){
            a=0;
            b=0;
        }
        return a+b;
    }

    public Double Dividir (Double numerador, Double denominador) throws ArithmeticException{
        if (denominador==null || denominador==0){
            throw new ArithmeticException("denominador inválido");
        }
        Double i = numerador/denominador;
        return i;
    }
}
