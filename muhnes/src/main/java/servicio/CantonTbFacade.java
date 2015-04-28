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
import modelo.CantonTb;
import modelo.MunicipioTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class CantonTbFacade extends AbstractFacade<CantonTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CantonTbFacade() {
        super(CantonTb.class);
    }
    
    public List<CantonTb> buscarMuniAsc(MunicipioTb municipio){
        
        TypedQuery<CantonTb> query = em.createQuery("SELECT p FROM CantonTb p WHERE p.eIdmunicipio=:h ORDER BY p.cNombre ASC ", CantonTb.class);
       query.setParameter("h", municipio);
        return query.getResultList();
    }
    
}
