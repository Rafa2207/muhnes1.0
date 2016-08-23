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
import modelo.MaterialTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class MaterialTbFacade extends AbstractFacade<MaterialTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaterialTbFacade() {
        super(MaterialTb.class);
    }
    
    public String obtenerCorrelativo(String pre){
        Query query = em.createNativeQuery("SELECT COUNT(m_codigobarras) "
                + "FROM material_tb "
                + "WHERE SUBSTRING(m_codigobarras,1,3)='"+pre+"'");
        query.setParameter("pre", pre);
        Long count = (Long) query.getSingleResult();
        return String.format("%03d", count+1);
    }
     public List<MaterialTb> buscarTodosAZ(){
        TypedQuery<MaterialTb> query = em.createQuery("SELECT p FROM MaterialTb p ORDER BY p.cNombre ASC", MaterialTb.class);
        return query.getResultList();
    }
     
     public List<MaterialTb> ordenarMenosStock(){
        TypedQuery<MaterialTb> query = em.createQuery("SELECT p FROM MaterialTb p ORDER BY p.dCantidad ASC", MaterialTb.class);
        return query.getResultList();
    }
     public MaterialTb obtenerPorCodigo(String codigo){
        TypedQuery<MaterialTb> query = em.createQuery("SELECT m FROM MaterialTb m WHERE m.mCodigobarras LIKE :codigo",MaterialTb.class);
        query.setParameter("codigo", codigo);
        try{
            return query.getSingleResult();
        }
        catch(NoResultException nre){
            return null;
        }
    }
}
