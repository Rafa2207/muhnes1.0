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
import modelo.ActividadTb;
import modelo.ProyectoTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class ActividadTbFacade extends AbstractFacade<ActividadTb> {

    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActividadTbFacade() {
        super(ActividadTb.class);
    }

    public List<ActividadTb> buscarAsc(ProyectoTb proyectos) {
        TypedQuery<ActividadTb> query = em.createQuery("SELECT p FROM ActividadTb p WHERE p.eIdproyecto=:h ORDER BY p.mNombre ASC ", ActividadTb.class);
        query.setParameter("h", proyectos);
        return query.getResultList();
    }

    //Consulta para la agenda de las actividades
    public ActividadTb BuscarActividades(String titulo, Date fechaInicio, Date fechaFin) {
        TypedQuery<ActividadTb> query = em.createQuery("SELECT p FROM ActividadTb p WHERE p.mNombre=:t and p.fFecha=:fi and p.fFechafin=:ff", ActividadTb.class);
        query.setParameter("t", titulo);
        query.setParameter("fi", fechaInicio);
        query.setParameter("ff", fechaFin);
        return query.getSingleResult();
    }

    //Consulta para el control de los proyectos
    public ActividadTb BuscarActividadNoIniciada(String titulo, Date fechaInicio, Date fechaFin,ProyectoTb proy) {
        TypedQuery<ActividadTb> query = em.createQuery("SELECT p FROM ActividadTb p WHERE p.mNombre=:t and p.fFecha=:fi and p.fFechafin=:ff and p.eIdproyecto=:proyecto", ActividadTb.class);
        query.setParameter("t", titulo);
        query.setParameter("fi", fechaInicio);
        query.setParameter("ff", fechaFin);
        query.setParameter("proyecto", proy);
        return query.getSingleResult();
    }

    public ActividadTb BuscarActividadEnProceso(String titulo, Date fechaInicioReal,ProyectoTb proy) {
        TypedQuery<ActividadTb> query = em.createQuery("SELECT p FROM ActividadTb p WHERE p.mNombre=:t and p.fFechaInicioReal=:fi and p.eIdproyecto=:proyecto", ActividadTb.class);
        query.setParameter("t", titulo);
        query.setParameter("fi", fechaInicioReal);
        query.setParameter("proyecto", proy);
        return query.getSingleResult();
    }

    public ActividadTb BuscarActividadFinalizada(String titulo, Date fechaInicioReal, Date fechaFinReal, ProyectoTb proy) {
        TypedQuery<ActividadTb> query = em.createQuery("SELECT p FROM ActividadTb p WHERE p.mNombre=:t and p.fFechaInicioReal=:fi and p.fFechaFinReal=:ff and p.eIdproyecto=:proyecto", ActividadTb.class);
        query.setParameter("t", titulo);
        query.setParameter("fi", fechaInicioReal);
        query.setParameter("ff", fechaFinReal);
        query.setParameter("proyecto", proy);
        return query.getSingleResult();
    }
    
    public List<ActividadTb> ActividadNotificaciones(Date diaActual, Date Semana) {
        em.clear();
        TypedQuery<ActividadTb> query = em.createQuery("SELECT p FROM ActividadTb p where p.eEstado!=3 or p.fFechaFinReal between :d1 and :d2 order by p.fFechaFinReal ASC", ActividadTb.class);
        query.setParameter("d1", diaActual);
        query.setParameter("d2", Semana);
        return query.getResultList();
    }

}
