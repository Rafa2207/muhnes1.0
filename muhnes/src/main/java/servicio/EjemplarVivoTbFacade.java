/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import modelo.EjemplarVivoTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class EjemplarVivoTbFacade extends AbstractFacade<EjemplarVivoTb> {

    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EjemplarVivoTbFacade() {
        super(EjemplarVivoTb.class);
    }

    public List<EjemplarVivoTb> buscarEjemplarConExistencia() {
        TypedQuery<EjemplarVivoTb> query = em.createQuery("SELECT p FROM EjemplarVivoTb p WHERE p.eCantidad>0 ORDER BY p.cNombre ASC", EjemplarVivoTb.class);
        return query.getResultList();
    }

    public List<EjemplarVivoTb> buscarEjemplarSinExistencia() {
        TypedQuery<EjemplarVivoTb> query = em.createQuery("SELECT p FROM EjemplarVivoTb p WHERE p.eCantidad=0 ORDER BY p.cNombre ASC", EjemplarVivoTb.class);
        return query.getResultList();
    }
}
