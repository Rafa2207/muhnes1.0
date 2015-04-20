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
import modelo.DepartamentoTb;
import modelo.MunicipioTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class MunicipioTbFacade extends AbstractFacade<MunicipioTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MunicipioTbFacade() {
        super(MunicipioTb.class);
    }
    
    public List<MunicipioTb> buscarDeptoAsc(DepartamentoTb  departamento){
        
        TypedQuery<MunicipioTb> query = em.createQuery("SELECT p FROM MunicipioTb p WHERE p.eIddepto=:h ORDER BY p.cNombre ASC ", MunicipioTb.class);
       query.setParameter("h", departamento);
        return query.getResultList();
    }
}
