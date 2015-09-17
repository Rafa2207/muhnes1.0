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
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.PaisTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class PaisTbFacade extends AbstractFacade<PaisTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PaisTbFacade() {
        super(PaisTb.class);
    }
    public List<PaisTb> idiomas(){
        List<PaisTb> idioma;
        String Sentencia = "SELECT DISTINCT p.cIdioma FROM PaisTb p";
        Query query = em.createQuery(Sentencia, PaisTb.class);
        idioma = query.getResultList();
        /*CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PaisTb> q = cb.createQuery(PaisTb.class);
        Root<PaisTb> c = q.from(PaisTb.class);
        q.select(c);*/
        return idioma;
    }
}
