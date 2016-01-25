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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.ProyectoTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class ProyectoTbFacade extends AbstractFacade<ProyectoTb> {

    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProyectoTbFacade() {
        super(ProyectoTb.class);
    }

    //Consulta para la agenda de los proyectos
    public ProyectoTb BuscarProyecto(String titulo, Date fechaInicio, Date fechaFin) {
        TypedQuery<ProyectoTb> query = em.createQuery("SELECT p FROM ProyectoTb p WHERE p.mNombre=:t and p.fFechaInicio=:fi and p.fFechaFin=:ff", ProyectoTb.class);
        query.setParameter("t", titulo);
        query.setParameter("fi", fechaInicio);
        query.setParameter("ff", fechaFin);
        return query.getSingleResult();
    }

    //Consulta general
    public List<ProyectoTb> ProyectoGeneral() {
        em.clear();
        TypedQuery<ProyectoTb> query = em.createQuery("SELECT p FROM ProyectoTb p ORDER BY p.fFechaFin DESC", ProyectoTb.class);
        return query.getResultList();
    }

    //consulta proyectos entre fechas 
    public List<ProyectoTb> ProyectoReportesEntreFechas(Date f1, Date f2) {
        em.clear();
        TypedQuery<ProyectoTb> query = em.createQuery("SELECT p FROM ProyectoTb p WHERE p.fFechaInicio between :f1 and :f2 order by p.fFechaInicio DESC", ProyectoTb.class);
        query.setParameter("f1", f1);
        query.setParameter("f2", f2);
        return query.getResultList();
    }

    //Consulta para notificaciones
    public List<ProyectoTb> ProyectoNotificaciones(Date diaActual, Date Semana) {
        em.clear();
        TypedQuery<ProyectoTb> query = em.createQuery("SELECT p FROM ProyectoTb p where p.eEstado!=2 or p.fFechaFin between :d1 and :d2 order by p.fFechaFin ASC", ProyectoTb.class);
        query.setParameter("d1", diaActual);
        query.setParameter("d2", Semana);
        return query.getResultList();
    }

    public List<ProyectoTb> ProyectoGeneralNotificacion() {
        em.clear();
        TypedQuery<ProyectoTb> query = em.createQuery("SELECT p FROM ProyectoTb p where p.eEstado!=2 ORDER BY p.fFechaFin ASC", ProyectoTb.class);
        return query.getResultList();
    }

}
