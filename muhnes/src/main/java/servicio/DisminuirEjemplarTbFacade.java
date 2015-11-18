/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.DisminuirEjemplarTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class DisminuirEjemplarTbFacade extends AbstractFacade<DisminuirEjemplarTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DisminuirEjemplarTbFacade() {
        super(DisminuirEjemplarTb.class);
    }
    
}
