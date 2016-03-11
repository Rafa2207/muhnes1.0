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
import modelo.EjemplarParticipaExhibicionTb;
import modelo.ExhibicionTb;

@Stateless
public class EjemplarParticipaExhibicionTbFacade extends AbstractFacade<EjemplarParticipaExhibicionTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EjemplarParticipaExhibicionTbFacade() {
        super(EjemplarParticipaExhibicionTb.class);
    }
    
    public List<EjemplarParticipaExhibicionTb> exhibicionesAunNoRecibidos(ExhibicionTb exhibicion) {
        TypedQuery<EjemplarParticipaExhibicionTb> query = em.createQuery("SELECT p FROM EjemplarParticipaExhibicionTb p WHERE p.exhibicionTb=:e and p.eEstado=0", EjemplarParticipaExhibicionTb.class);
        query.setParameter("e", exhibicion);
        return query.getResultList();
    }
    
}
