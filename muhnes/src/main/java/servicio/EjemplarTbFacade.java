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
import modelo.AgenteIdentificaEjemplarTb;
import modelo.EjemplarTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class EjemplarTbFacade extends AbstractFacade<EjemplarTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EjemplarTbFacade() {
        super(EjemplarTb.class);
    }
    public int siguienteId(){
        Query query = em.createNativeQuery("SELECT last_value from secuencia_ejemplar_id");
        try{
            Long id =  (Long) query.getSingleResult();
            return id.intValue()+1;
        }
        catch(NoResultException nre){
            return 0;
        }
    }
    public String obtenerCorrelativo(String pre){
        Query query = em.createNativeQuery("SELECT COUNT(c_codigoentrada) "
                + "FROM ejemplar_tb "
                + "WHERE SUBSTRING(c_codigoentrada,1,2)='"+pre+"'");
        query.setParameter("pre", pre);
        Long count = (Long) query.getSingleResult();
        return String.format("%d", count+1);
    }
    
    public List<AgenteIdentificaEjemplarTb> ejemplarRecolector(Integer  ejemplar, String recolector){
        
        TypedQuery<AgenteIdentificaEjemplarTb> query = em.createQuery("SELECT p FROM AgenteIdentificaEjemplarTb p WHERE p.ejemplarTb.eIdejemplar=:h AND p.agenteIdentificaEjemplarTbPK.cTipo=:recol ORDER BY P.eSecuencia", AgenteIdentificaEjemplarTb.class);
       query.setParameter("h", ejemplar);
       query.setParameter("recol", recolector);
        return query.getResultList();
    }
    public List<AgenteIdentificaEjemplarTb> ejemplarIdentificador(Integer  ejemplar, String identificador){
        
        TypedQuery<AgenteIdentificaEjemplarTb> query = em.createQuery("SELECT p FROM AgenteIdentificaEjemplarTb p WHERE p.ejemplarTb.eIdejemplar=:h AND p.agenteIdentificaEjemplarTbPK.cTipo=:ident ORDER BY P.eSecuencia", AgenteIdentificaEjemplarTb.class);
       query.setParameter("h", ejemplar);
       query.setParameter("ident", identificador);
        return query.getResultList();
    }
}
