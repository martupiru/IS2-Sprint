package org.example;


import entity.Articulo;
import entity.Categoria;
import entity.Cliente;
import entity.Domicilio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistencePu");
        EntityManager em = emf.createEntityManager();

        try  {
            em.getTransaction().begin();
            Articulo articulo = new Articulo(2, "galleta", 123);
            Categoria categoria = new Categoria("Altos en grasa",new ArrayList<>());
            articulo.setCategorias(new ArrayList<>());
            articulo.getCategorias().add(categoria);
            categoria.getArticulos().add(articulo);

            em.persist(articulo);
            em.persist(categoria);
            em.flush();
            em.getTransaction().commit();
        }
        catch(Exception ex){
            em.getTransaction().rollback();
        }

        em.close();
        emf.close();
    }
}