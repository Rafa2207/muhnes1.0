
package servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import modelo.AreaprotegidaTb;
import modelo.EjemplarTb;
import modelo.LocalidadTb;


@Stateless
public class LocalidadTbFacade extends AbstractFacade<LocalidadTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocalidadTbFacade() {
        super(LocalidadTb.class);
    }
    
    //Consulta general
    public List<LocalidadTb> LocalidadGeneral() {
        em.clear();
        TypedQuery<LocalidadTb> query = em.createQuery("SELECT p FROM LocalidadTb p ORDER BY P.cNombre ASC", LocalidadTb.class);
        return query.getResultList();
    }
    
    //consulta de los ejemplares que tienen esas localidades
    public List<EjemplarTb> EjemplaresPorLocalidad(LocalidadTb l) {
        em.clear();
        TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p where p.eIdlocalidad=:l ORDER BY p.cCodigoentrada ASC", EjemplarTb.class);
        query.setParameter("l", l);
        return query.getResultList();
    }
    
}
