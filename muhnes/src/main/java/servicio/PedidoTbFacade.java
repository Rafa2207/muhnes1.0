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
import modelo.MaterialPedidoTb;
import modelo.PedidoTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class PedidoTbFacade extends AbstractFacade<PedidoTb> {

    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedidoTbFacade() {
        super(PedidoTb.class);
    }

    public int siguienteId() {
        Query query = em.createNativeQuery("SELECT last_value from secuencia_pedido_id");
        try {
            Long id = (Long) query.getSingleResult();
            return id.intValue() + 1;
        } catch (NoResultException nre) {
            return 0;
        }
    }

    public List<MaterialPedidoTb> pedido(Integer pedido) {
        TypedQuery<MaterialPedidoTb> query = em.createQuery("SELECT p FROM MaterialPedidoTb p WHERE p.pedidoTb.eIdpedido=:h", MaterialPedidoTb.class);
        query.setParameter("h", pedido);
        return query.getResultList();
    }
}
