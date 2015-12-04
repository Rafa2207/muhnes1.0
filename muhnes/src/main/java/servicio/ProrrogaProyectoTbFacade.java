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
import modelo.ProrrogaProyectoTb;
import modelo.ProyectoTb;

/**
 *
 * @author Endy
 */
@Stateless
public class ProrrogaProyectoTbFacade extends AbstractFacade<ProrrogaProyectoTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProrrogaProyectoTbFacade() {
        super(ProrrogaProyectoTb.class);
    }
    
    public List<ProrrogaProyectoTb> buscarProrroga(ProyectoTb proyectos) {
        TypedQuery<ProrrogaProyectoTb> query = em.createQuery("SELECT p FROM ProrrogaProyectoTb p WHERE p.eIdproyecto=:h order by p.eNumprorroga", ProrrogaProyectoTb.class);
        query.setParameter("h", proyectos);
        return query.getResultList();
    }
}
