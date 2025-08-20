package org.example;


import entity.Cliente;
import entity.Domicilio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistencePu");
        EntityManager em = emf.createEntityManager();

        try  {
            em.getTransaction().begin();
            Domicilio domicilio = new Domicilio(false,1,"hola");
            em.persist(domicilio);
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