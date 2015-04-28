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
import modelo.EspecieTb;
import modelo.SubespecieTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class SubespecieTbFacade extends AbstractFacade<SubespecieTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubespecieTbFacade() {
        super(SubespecieTb.class);
    }
    public List<SubespecieTb> buscarEspecieAsc(Integer  especie){
        
        TypedQuery<SubespecieTb> query = em.createQuery("SELECT p FROM SubespecieTb p WHERE p.eIdespecie=:h ORDER BY p.cNombre ASC ", SubespecieTb.class);
       query.setParameter("h", especie);
        return query.getResultList();
    }
    
}
