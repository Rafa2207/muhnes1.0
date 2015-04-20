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
import modelo.DepartamentoTb;
import modelo.PaisTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class DepartamentoTbFacade extends AbstractFacade<DepartamentoTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartamentoTbFacade() {
        super(DepartamentoTb.class);
    }
    
    public List<DepartamentoTb> buscarPaisAsc(PaisTb  pais){
        
        TypedQuery<DepartamentoTb> query = em.createQuery("SELECT p FROM DepartamentoTb p WHERE p.eIdpais=:h ORDER BY p.cNombre ASC ", DepartamentoTb.class);
       query.setParameter("h", pais);
        return query.getResultList();
    }
    
}
