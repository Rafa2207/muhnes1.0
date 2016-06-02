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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.AreaprotegidaTb;
import modelo.LocalidadTb;
import modelo.MunicipioTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class AreaprotegidaTbFacade extends AbstractFacade<AreaprotegidaTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AreaprotegidaTbFacade() {
        super(AreaprotegidaTb.class);
    }
    
    public List<AreaprotegidaTb> AreaProtegidaOrdenNombreAsc() {
        em.clear();
        TypedQuery<AreaprotegidaTb> query = em.createQuery("SELECT p FROM AreaprotegidaTb p ORDER BY p.cNombre ASC", AreaprotegidaTb.class);
        return query.getResultList();
    }
    
    public List<LocalidadTb> ListaDeLocalidadesPorArea(AreaprotegidaTb a) {
        em.clear();
        TypedQuery<LocalidadTb> query = em.createQuery("SELECT p FROM LocalidadTb p where p.eIdarea=:a ORDER BY p.cNombre ASC", LocalidadTb.class);
        query.setParameter("a", a);
        return query.getResultList();
    }
   
    public List<AreaprotegidaTb> AreaProtegidaPorMunicipio(MunicipioTb m){
        em.clear();
        TypedQuery<AreaprotegidaTb> query = em.createQuery("SELECT p FROM AreaprotegidaTb p where p.eIdmunicipio=:a ORDER BY p.cNombre ASC", AreaprotegidaTb.class);
        query.setParameter("a", m);
        return query.getResultList();
    }
    
}
