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
    
}
