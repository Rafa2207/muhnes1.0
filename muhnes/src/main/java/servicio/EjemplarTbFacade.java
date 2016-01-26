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
import modelo.NombrecomunTb;
import modelo.TaxonomiaTb;

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
    
    public List<EjemplarTb> EjemplarOrdenAsc(){
       TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p ORDER BY p.cCodigoentrada ASC", EjemplarTb.class);
        return query.getResultList();
    }
    public List<EjemplarTb> BuscarEjemplares(TaxonomiaTb tax) {
        TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p WHERE p.eIdtaxonomia=:t ORDER BY p.cCodigoentrada ASC", EjemplarTb.class);
        query.setParameter("t", tax);
        return query.getResultList();
    }
    public List<NombrecomunTb> nombresComunes(TaxonomiaTb tax){
        TypedQuery<NombrecomunTb> query = em.createQuery("SELECT n FROM NombrecomunTb n WHERE n.eIdtaxonomia=:t", NombrecomunTb.class);
        query.setParameter("t", tax);
        return query.getResultList();
    }
    public List<EjemplarTb> ejemplarGeneral() {
        em.clear();
        TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p ORDER BY p.cCodigoentrada DESC", EjemplarTb.class);
        return query.getResultList();
    }
}
