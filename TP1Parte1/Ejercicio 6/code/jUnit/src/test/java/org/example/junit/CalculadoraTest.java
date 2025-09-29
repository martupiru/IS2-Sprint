package org.example.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CalculadoraTest {

    @BeforeAll
    public static void beforeAll(){
        System.out.println("Iniciando beforeALL");
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("Iniciando afterALL");
    }

    @AfterEach
    public void afterEach(){
        System.out.println("Iniciando afterEach");
    }

    @BeforeEach
    public void beforeEach(){
        System.out.println("Iniciando beforeEach");
    }


    @Test
    @Order(1)
    public void testSumar(){

        int a=2, b=9, c=5, resultado=0;

        Calculadora calc = new Calculadora();
        resultado = calc.Sumar(a,b);

        System.out.print("resultado suma  " + resultado);
    }

    @Test
    @Order(2)
    public void testDivision(){
        Double a=10.0, b= 2.0, esperado=5.0, resultado=0.0;

        Calculadora calc = new Calculadora();
        resultado = calc.Dividir(a,b);

        try{
            resultado = calc.Dividir(a,b);
        } catch(ArithmeticException e){
            e.getMessage();
        }
        assertEquals(esperado,resultado);

        System.out.print("resultado division " + resultado);
    }

    @Test
    @Disabled
    @Order(3)
    public void testDivisionAssertTrue(){
        Double a=10.0, b= 0.0, esperado=5.0, resultado=0.0;

        Calculadora calc = new Calculadora();
        resultado = calc.Dividir(a,b);

        try{
            resultado = calc.Dividir(a,b);
        } catch(ArithmeticException e){
            e.getMessage();
        }
        Assertions.assertTrue(esperado==resultado);

        System.out.print("resultado division " + resultado);
    }


    @Test
    @Order(4)
    public void testDivisionCheckException(){
        Double a=10.0, b= 0.0, esperado=5.0, resultado=0.0;

        Calculadora calc = new Calculadora();
        resultado = calc.Dividir(a,b);

        try{
            Exception ex = assertThrows(Exception.class, () ->{
                calc.Dividir(a,b);
            });
            assertEquals("Denominador inválido", ex.getMessage());
        } catch(ArithmeticException e){
            e.getMessage();
        }
        Assertions.assertTrue(esperado==resultado);

        System.out.print("resultado division " + resultado);
    }
}
