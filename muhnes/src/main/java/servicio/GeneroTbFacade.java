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
import modelo.FamiliaTb;
import modelo.GeneroTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class GeneroTbFacade extends AbstractFacade<GeneroTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GeneroTbFacade() {
        super(GeneroTb.class);
    }
    public List<GeneroTb> buscarFamiliaAsc(Integer  familias){
        
        TypedQuery<GeneroTb> query = em.createQuery("SELECT p FROM GeneroTb p WHERE p.eIdfamilia:h ORDER BY p.cNombre ASC ", GeneroTb.class);
       query.setParameter("h", familias);
        return query.getResultList();
    }
    
}
