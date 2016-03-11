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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        TypedQuery<ExhibicionTb> query = em.createQuery("SELECT e FROM ExhibicionTb e where e.eEstado=0 and e.eEstado=1 or e.fFechaRecibido between :d1 and :d2 order by e.fFechaRecibido ASC", ExhibicionTb.class);
        query.setParameter("d1", diaActual);
        query.setParameter("d2", Semana);
        return query.getResultList();
    }

    public List<ExhibicionTb> ExhibicionGeneral() {
        em.clear();
        TypedQuery<ExhibicionTb> query = em.createQuery("SELECT e FROM ExhibicionTb e order by e.fFechaPrestamo DESC", ExhibicionTb.class);
        return query.getResultList();
    }
    
    public List<ExhibicionTb> ExhibicionControl() {
        em.clear();
        TypedQuery<ExhibicionTb> query = em.createQuery("SELECT e FROM ExhibicionTb e WHERE e.eEstado!=2 order by e.fFechaPrestamo DESC", ExhibicionTb.class);
        return query.getResultList();
    }

    public int siguienteId() {
        Query query = em.createNativeQuery("SELECT last_value from secuencia_exhibicion_id");
        try {
            Long id = (Long) query.getSingleResult();
            return id.intValue() + 1;
        } catch (NoResultException nre) {
            return 0;
        }
    }
    
    public List<ExhibicionTb> ExhibicionesReporteAll(Date f1, Date f2) {
        em.clear();
        TypedQuery<ExhibicionTb> query = em.createQuery("SELECT e FROM ExhibicionTb e where e.fFechaPrestamo between :d1 and :d2 order by e.fFechaPrestamo DESC", ExhibicionTb.class);
        query.setParameter("d1", f1);
        query.setParameter("d2", f2);
        return query.getResultList();
    }
}
