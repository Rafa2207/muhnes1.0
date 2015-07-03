/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.UsuarioTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class UsuarioTbFacade extends AbstractFacade<UsuarioTb> {

    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioTbFacade() {
        super(UsuarioTb.class);
    }

    public UsuarioTb usuarioByCorreo(String correo) {
        Query query = em.createQuery("SELECT u FROM UsuarioTb u WHERE u.mEmail=:correo", UsuarioTb.class);
        query.setParameter("correo", correo);
        try {
            return (UsuarioTb) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public UsuarioTb BuscarUsuario(String usuario) {
        TypedQuery q = em.createNamedQuery("UsuarioTb.findByCNick", UsuarioTb.class);
        q.setParameter("cNick", usuario);
        try {
            return (UsuarioTb) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<UsuarioTb> buscarActivos() {

        TypedQuery<UsuarioTb> query = em.createQuery("SELECT p FROM UsuarioTb p WHERE p.bEstado = 'TRUE' ORDER BY p.cNombre ASC ", UsuarioTb.class);
        return query.getResultList();
    }

    public List<UsuarioTb> buscarInactivos() {

        TypedQuery<UsuarioTb> query = em.createQuery("SELECT p FROM UsuarioTb p WHERE p.bEstado = 'FALSE' ORDER BY p.cNombre ASC ", UsuarioTb.class);
        return query.getResultList();
    }
}
