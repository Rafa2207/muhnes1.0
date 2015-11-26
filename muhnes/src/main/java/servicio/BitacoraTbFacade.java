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
import modelo.BitacoraTb;

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

}
