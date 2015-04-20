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
import modelo.PresupuestoTb;
import modelo.ProyectoTb;

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
    
    public List<PresupuestoTb> buscarPresupuestoAsc(ProyectoTb proyectos) {
        
        TypedQuery<PresupuestoTb> query = em.createQuery("SELECT p FROM PresupuestoTb p WHERE p.eIdproyecto=:h ORDER BY p.cNombre ASC ", PresupuestoTb.class);
       query.setParameter("h", proyectos);
        return query.getResultList();
         }
}
