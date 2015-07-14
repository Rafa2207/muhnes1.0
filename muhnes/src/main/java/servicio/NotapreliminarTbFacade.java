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
import modelo.NotapreliminarTb;
import modelo.ProyectoTb;
import modelo.UsuarioTb;

/**
 *
 * @author Frank Martinez
 */
@Stateless
public class NotapreliminarTbFacade extends AbstractFacade<NotapreliminarTb> {

    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotapreliminarTbFacade() {
        super(NotapreliminarTb.class);
    }

    public List<NotapreliminarTb> buscarNotasAll() {

        TypedQuery<NotapreliminarTb> query = em.createQuery("SELECT p FROM NotapreliminarTb p ORDER BY p.fFecha DESC", NotapreliminarTb.class);
        return query.getResultList();
    }

    public List<NotapreliminarTb> buscarPorProyecto(ProyectoTb proyecto) {

        TypedQuery<NotapreliminarTb> query = em.createQuery("SELECT p FROM NotapreliminarTb p WHERE p.eIdproyecto=:proy ORDER BY p.fFecha DESC", NotapreliminarTb.class);
        query.setParameter("proy", proyecto);
        return query.getResultList();
    }

    public UsuarioTb UsuarioParaVista(int usuario) {
        TypedQuery<UsuarioTb> query = em.createQuery("SELECT p FROM UsuarioTb p WHERE p.eIdusuario=:usuario", UsuarioTb.class);
        query.setParameter("usuario", usuario);
        return query.getSingleResult();
    }

}
