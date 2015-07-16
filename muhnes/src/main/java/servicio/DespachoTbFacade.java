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
import modelo.DespachoTb;

/**
 *
 * @author Frank Martinez
 */
@Stateless
public class DespachoTbFacade extends AbstractFacade<DespachoTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DespachoTbFacade() {
        super(DespachoTb.class);
    }
    public int siguienteId(){
        Query query = em.createNativeQuery("SELECT last_value from secuencia_despacho_id");
        try{
            Long id =  (Long) query.getSingleResult();
            return id.intValue()+1;
        }
        catch(NoResultException nre){
            return 0;
        }
    }
}
