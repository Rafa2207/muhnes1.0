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
import modelo.DespachoTb;
import modelo.MaterialTb;
import modelo.ProyectoTb;

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
    public List<MaterialTb> materialDisponible(){
        Query query = em.createQuery("SELECT d FROM MaterialTb d WHERE d.dCantidad != 0");
        try{
            List<MaterialTb> material = query.getResultList();
            return material;
        }
        catch(NoResultException nre){
            return null;
        }
    }
    
    public List<ProyectoTb> proyectosDisponibles(){
        TypedQuery<ProyectoTb> query = em.createQuery("SELECT p FROM ProyectoTb p WHERE p.eEstado=0 OR p.eEstado=1 ORDER BY p.mNombre ASC", ProyectoTb.class);
        return query.getResultList();
    }
    
    public List<DespachoTb> ordenarDespachoFecha(){
        TypedQuery<DespachoTb> query = em.createQuery("SELECT d FROM DespachoTb d ORDER BY d.fFecha DESC", DespachoTb.class);
        return query.getResultList();
    }
}
