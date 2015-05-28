/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.PresupuestoTb;
import modelo.ProyectoTb;
import modelo.UnidadesTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class PresupuestoTbFacade extends AbstractFacade<PresupuestoTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PresupuestoTbFacade() {
        super(PresupuestoTb.class);
    }
    
    public List<PresupuestoTb> buscarPresupuestoAsc(ProyectoTb proyecto) {
        
        TypedQuery<PresupuestoTb> query = em.createQuery("SELECT p FROM PresupuestoTb p WHERE p.eIdproyecto=:h ORDER BY p.mNombre ASC", PresupuestoTb.class);
       query.setParameter("h", proyecto);
        return query.getResultList();
         }
    public int siguienteId(){
        Query query = em.createNativeQuery("SELECT last_value from secuencia_presupuesto_id");
        try{
            Long id =  (Long) query.getSingleResult();
            return id.intValue()+1;
        }
        catch(NoResultException nre){
            return 0;
        }
    }
}
