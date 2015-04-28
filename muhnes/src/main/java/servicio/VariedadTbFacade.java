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
import modelo.VariedadTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class VariedadTbFacade extends AbstractFacade<VariedadTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VariedadTbFacade() {
        super(VariedadTb.class);
    }
    public List<VariedadTb> buscarSubEspecieAsc(Integer  subespecie){
        
        TypedQuery<VariedadTb> query = em.createQuery("SELECT p FROM VariedadTb p WHERE p.eIdsubespecie=:h ORDER BY p.cNombre ASC ", VariedadTb.class);
       query.setParameter("h", subespecie);
        return query.getResultList();
    }
    
}
