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
import modelo.AgenteTb;
import modelo.BitacoraTb;
import modelo.UsuarioTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class BitacoraTbFacade extends AbstractFacade<BitacoraTb> {

    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BitacoraTbFacade() {
        super(BitacoraTb.class);
    }

    public List<BitacoraTb> BitacoraOrdenFecha() {
        TypedQuery<BitacoraTb> query = em.createQuery("SELECT p FROM BitacoraTb p ORDER BY p.tFecha DESC", BitacoraTb.class);
        return query.getResultList();
    }
    public List<UsuarioTb> listaUsuarios() {
        TypedQuery<UsuarioTb> query = em.createQuery("SELECT p FROM UsuarioTb p ORDER BY p.cNombre", UsuarioTb.class) ;
        //uery query = em.createNativeQuery(Sentencia, AgenteTb.class);
        return query.getResultList();
    }
    public List<BitacoraTb> bitacoraUsuario(UsuarioTb u) {
        TypedQuery<BitacoraTb> query = em.createQuery("SELECT p FROM BitacoraTb p WHERE p.eIdusuario=:u ORDER BY p.tFecha DESC", BitacoraTb.class) ;
        query.setParameter("u", u);
        return query.getResultList();
    }
    public List<BitacoraTb> bitacoraFecha(Date f1, Date f2) {
        em.clear();
        TypedQuery<BitacoraTb> query = em.createQuery("SELECT p FROM BitacoraTb p WHERE p.tFecha BETWEEN :f1 and :f2 ORDER BY p.tFecha DESC", BitacoraTb.class);
        query.setParameter("f1", f1);
        query.setParameter("f2", f2);
        return query.getResultList();
    }
    public List<BitacoraTb> bitacoraFechaUsuario(UsuarioTb u, Date f1, Date f2) {
        em.clear();
        TypedQuery<BitacoraTb> query = em.createQuery("SELECT p FROM BitacoraTb p WHERE p.tFecha BETWEEN :f1 and :f2 AND p.eIdusuario=:u ORDER BY p.tFecha DESC", BitacoraTb.class);
        query.setParameter("u", u);
        query.setParameter("f1", f1);
        query.setParameter("f2", f2);
        return query.getResultList();
    }

}
