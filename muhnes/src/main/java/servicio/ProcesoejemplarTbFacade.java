/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import modelo.ProcesoejemplarTb;
import modelo.ProyectoTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class ProcesoejemplarTbFacade extends AbstractFacade<ProcesoejemplarTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProcesoejemplarTbFacade() {
        super(ProcesoejemplarTb.class);
    }
    
    public List<ProcesoejemplarTb> buscarProcesoAsc(ProyectoTb proyectos) {
        
        TypedQuery<ProcesoejemplarTb> query = em.createQuery("SELECT p FROM ProcesoejemplarTb p WHERE p.eIdproyecto=:h and p.cTipo='Secado' ORDER BY p.fFecha DESC", ProcesoejemplarTb.class);
       query.setParameter("h", proyectos);
        return query.getResultList();
         }
    
    public List<ProcesoejemplarTb> ProcesoNotificaciones(Date diaActual, Date Semana) {
        em.clear();
        TypedQuery<ProcesoejemplarTb> query = em.createQuery("SELECT p FROM ProcesoejemplarTb p where p.eEstado=0 or p.fFechafin between :d1 and :d2 order by p.fFechafin ASC", ProcesoejemplarTb.class);
        query.setParameter("d1", diaActual);
        query.setParameter("d2", Semana);
        return query.getResultList();
    }
    
}
