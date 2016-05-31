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
import modelo.InstitucionTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class InstitucionTbFacade extends AbstractFacade<InstitucionTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstitucionTbFacade() {
        super(InstitucionTb.class);
    }
    
    public String nombreIns(Integer id){
        TypedQuery<InstitucionTb> query = em.createQuery("SELECT p FROM InstitucionTb p WHERE p.eIdinstitucion=:h", InstitucionTb.class);
       query.setParameter("h", id);
       return query.getSingleResult().getCNombre();
    }
    
    public InstitucionTb Institucion(Integer id){
        TypedQuery<InstitucionTb> query = em.createQuery("SELECT p FROM InstitucionTb p WHERE p.eIdinstitucion=:h", InstitucionTb.class);
       query.setParameter("h", id);
       return query.getSingleResult();
    }
    
    public List<InstitucionTb> institucionGeneral() {
        em.clear();
        TypedQuery<InstitucionTb> query = em.createQuery("SELECT p FROM InstitucionTb p ORDER BY p.cNombre ASC", InstitucionTb.class);
        return query.getResultList();
    }
    
}
