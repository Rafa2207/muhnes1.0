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
import modelo.AgenteTaxonomiaTb;
import modelo.TaxonomiaTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class TaxonomiaTbFacade extends AbstractFacade<TaxonomiaTb> {

    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaxonomiaTbFacade() {
        super(TaxonomiaTb.class);
    }

    public int siguienteId() {
        Query query = em.createNativeQuery("SELECT last_value from secuencia_taxonomia_id");
        try {
            Long id = (Long) query.getSingleResult();
            return id.intValue() + 1;
        } catch (NoResultException nre) {
            return 0;
        }
    }

    public List<TaxonomiaTb> buscarTaxonomiaAsc(String Tipo) {

        TypedQuery<TaxonomiaTb> query = em.createQuery("SELECT t FROM TaxonomiaTb t WHERE t.cTipo=:h ORDER BY t.cNombre ASC ", TaxonomiaTb.class);
        query.setParameter("h", Tipo);
        return query.getResultList();
    }
    
    public List<TaxonomiaTb> buscarFamiliaAsc() {

        TypedQuery<TaxonomiaTb> query = em.createQuery("SELECT t FROM TaxonomiaTb t WHERE t.eRango=:h ORDER BY t.cNombre ASC ", TaxonomiaTb.class);
        query.setParameter("h", 1);
        return query.getResultList();
    }

    public List<TaxonomiaTb> buscarGeneroAsc(Integer genero) {
        Query query = em.createNativeQuery("SELECT * FROM taxonomia_tb WHERE e_rango = '2' and e_idniveltaxonomia = '" + genero + "' ORDER BY c_nombre ASC", TaxonomiaTb.class);
        return query.getResultList();
    }

    public List<TaxonomiaTb> buscarEspecieAsc(Integer especie) {
        Query query = em.createNativeQuery("SELECT * FROM taxonomia_tb WHERE e_rango = '3' and e_idniveltaxonomia = '" + especie + "' ORDER BY c_nombre ASC", TaxonomiaTb.class);
        return query.getResultList();
    }
    
    public List<TaxonomiaTb> buscarSubEspecieAsc(Integer subespecie) {
        Query query = em.createNativeQuery("SELECT * FROM taxonomia_tb WHERE e_rango = '4' and e_idniveltaxonomia = '" + subespecie + "' and c_tipo='Subespecie' ORDER BY c_nombre ASC", TaxonomiaTb.class);
        return query.getResultList();
    }

    public List<TaxonomiaTb> buscarVariedadAsc(Integer variedad) {
        Query query = em.createNativeQuery("SELECT * FROM taxonomia_tb WHERE e_rango = '4' and e_idniveltaxonomia = '" + variedad + "' and c_tipo='Variedad' ORDER BY c_nombre ASC", TaxonomiaTb.class);
        return query.getResultList();
    }
    
    public List<AgenteTaxonomiaTb> buscarEspecieSecuencia(Integer especie) {

        TypedQuery<AgenteTaxonomiaTb> query = em.createQuery("SELECT p FROM AgenteTaxonomiaTb p WHERE p.taxonomiaTb.eIdtaxonomia=:h ORDER BY p.eSecuecia ASC", AgenteTaxonomiaTb.class);
        query.setParameter("h", especie);
        return query.getResultList();
    }
}
