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
import modelo.UnidadesTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class UnidadesTbFacade extends AbstractFacade<UnidadesTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UnidadesTbFacade() {
        super(UnidadesTb.class);
    }
    public List<UnidadesTb> obtenerUnidades(String proyecto) {
        
        TypedQuery<UnidadesTb> query = em.createQuery("SELECT p FROM UnidadesTb p WHERE p.cTipo=:h ORDER BY p.cNombre ASC", UnidadesTb.class);
       query.setParameter("h", proyecto);
        return query.getResultList();
         }
}
