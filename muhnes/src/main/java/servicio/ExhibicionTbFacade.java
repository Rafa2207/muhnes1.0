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
import modelo.ExhibicionTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class ExhibicionTbFacade extends AbstractFacade<ExhibicionTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExhibicionTbFacade() {
        super(ExhibicionTb.class);
    }
    
    public List<ExhibicionTb> ExhibicionesNotificaciones(Date diaActual, Date Semana) {
        em.clear();
        TypedQuery<ExhibicionTb> query = em.createQuery("SELECT e FROM ExhibicionTb e where e.eEstado=0 or e.tFechaRecibido between :d1 and :d2 order by e.tFechaRecibido ASC", ExhibicionTb.class);
        query.setParameter("d1", diaActual);
        query.setParameter("d2", Semana);
        return query.getResultList();
    }
    public List<ExhibicionTb> ExhibicionGeneral() {
        em.clear();
        TypedQuery<ExhibicionTb> query = em.createQuery("SELECT e FROM ExhibicionTb e order by e.tFechaPrestamo DESC", ExhibicionTb.class);
        
        return query.getResultList();
    }
}
