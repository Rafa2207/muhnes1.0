/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    
    public UsuarioTb  usuarioByCorreo(String correo){
        Query query = em.createQuery("SELECT u FROM UsuarioTb u WHERE u.mEmail=:correo", UsuarioTb.class);
        query.setParameter("correo", correo);
        try {
            return (UsuarioTb) query.getSingleResult();
        } catch (NoResultException e) {
         return null;   
        }
    }
}
