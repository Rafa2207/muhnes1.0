/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.DonacionTb;
import modelo.EjemplarTb;
import modelo.InstitucionTb;
import modelo.TaxonomiaTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class DonacionTbFacade extends AbstractFacade<DonacionTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DonacionTbFacade() {
        super(DonacionTb.class);
    }
    
    public int siguienteId(){
        Query query = em.createNativeQuery("SELECT last_value from secuencia_donacion_id");
        try{
            Long id =  (Long) query.getSingleResult();
            return id.intValue()+1;
        }
        catch(NoResultException nre){
            return 0;
        }
    }
    
    public InstitucionTb BuscarInsitucion(int a) {
        TypedQuery<InstitucionTb> query = em.createQuery("SELECT p FROM InstitucionTb p WHERE p.eIdinstitucion=:t", InstitucionTb.class);
        query.setParameter("t", a);
        return query.getSingleResult();
    }
    
    public List<EjemplarTb> BuscarEjemplares(TaxonomiaTb tax) {
        TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p WHERE p.eIdtaxonomia=:t AND p.eCantDuplicado > 1 ORDER BY p.cCodigoentrada ASC", EjemplarTb.class);
        query.setParameter("t", tax);
        return query.getResultList();
    }
}
