/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.AgenteEspecieTb;
import modelo.EspecieTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class EspecieTbFacade extends AbstractFacade<EspecieTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EspecieTbFacade() {
        super(EspecieTb.class);
    }
    public int siguienteId(){
        Query query = em.createNativeQuery("SELECT last_value from secuencia_especie_id");
        try{
            Long id =  (Long) query.getSingleResult();
            return id.intValue()+1;
        }
        catch(NoResultException nre){
            return 0;
        }
    }
        
    public List<EspecieTb> buscarGeneroAsc(Integer  genero){
        
        TypedQuery<EspecieTb> query = em.createQuery("SELECT p FROM EspecieTb p WHERE p.eIdgenero=:h ORDER BY p.cNombre ASC ", EspecieTb.class);
       query.setParameter("h", genero);
        return query.getResultList();
    }
    public List<AgenteEspecieTb> buscarEspecieSecuencia(Integer  especie){
        
        TypedQuery<AgenteEspecieTb> query = em.createQuery("SELECT p FROM AgenteEspecieTb p WHERE p.especieTb.eIdespecie=:h ORDER BY p.eSecuencia ASC ", AgenteEspecieTb.class);
       query.setParameter("h", especie);
        return query.getResultList();
    }
}
