
package servicio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
}
