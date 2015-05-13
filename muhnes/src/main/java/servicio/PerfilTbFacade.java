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
import modelo.PerfilTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class PerfilTbFacade extends AbstractFacade<PerfilTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PerfilTbFacade() {
        super(PerfilTb.class);
    }
    
    public List<PerfilTb> buscarTodosAZ(){
        TypedQuery<PerfilTb> query = em.createQuery("SELECT p FROM PerfilTb p ORDER BY p.cNombre ASC", PerfilTb.class);
        return query.getResultList();
    }
    
    
}
