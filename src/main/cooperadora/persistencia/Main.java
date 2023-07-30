package main.cooperadora.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        Persona yo = new Persona("Javier", 28);
        guardar(yo);

        List<Persona> personas = encontrarPersonas();
        for (Persona persona : personas) {
            System.out.println(persona);
        }
//		Persona per = encontrarPersonaPorId(1L);
//		System.out.println(per);
//		actualizarPersonaPorId(1L);
//		eliminarPersonaPorId(1L);
    }

    private static void guardar(Persona persona) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadCooperadora");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(persona);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static List<Persona> encontrarPersonas() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadCooperadora");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Persona> p = em.createQuery("select p from Persona p where p.edad > :e ", Persona.class).setParameter("e", 18).getResultList();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    private static Persona encontrarPersonaPorId(Long id) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadCooperadora");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Persona p = em.find(Persona.class, id);
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    private static void actualizarPersonaPorId(Long id) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadCooperadora");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Persona p = em.find(Persona.class, id);
            em.detach(p);
            p.setEdad(31);
            em.merge(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void eliminarPersonaPorId(Long id) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadCooperadora");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Persona p = em.find(Persona.class, id);
            em.remove(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
