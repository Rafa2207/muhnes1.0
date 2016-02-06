/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.AgenteIdentificaEjemplarTb;
import modelo.AgenteTb;
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

    public int siguienteId() {
        Query query = em.createNativeQuery("SELECT last_value from secuencia_ejemplar_id");
        try {
            Long id = (Long) query.getSingleResult();
            return id.intValue() + 1;
        } catch (NoResultException nre) {
            return 0;
        }
    }

    public String obtenerCorrelativo(String pre) {
        Query query = em.createNativeQuery("SELECT COUNT(c_codigoentrada) "
                + "FROM ejemplar_tb "
                + "WHERE SUBSTRING(c_codigoentrada,1,2)='" + pre + "'");
        query.setParameter("pre", pre);
        Long count = (Long) query.getSingleResult();
        return String.format("%d", count + 1);
    }

    public List<AgenteIdentificaEjemplarTb> ejemplarRecolector(Integer ejemplar, String recolector) {

        TypedQuery<AgenteIdentificaEjemplarTb> query = em.createQuery("SELECT p FROM AgenteIdentificaEjemplarTb p WHERE p.ejemplarTb.eIdejemplar=:h AND p.agenteIdentificaEjemplarTbPK.cTipo=:recol ORDER BY P.eSecuencia", AgenteIdentificaEjemplarTb.class);
        query.setParameter("h", ejemplar);
        query.setParameter("recol", recolector);
        return query.getResultList();
    }

    public List<AgenteIdentificaEjemplarTb> ejemplarIdentificador(Integer ejemplar, String identificador) {

        TypedQuery<AgenteIdentificaEjemplarTb> query = em.createQuery("SELECT p FROM AgenteIdentificaEjemplarTb p WHERE p.ejemplarTb.eIdejemplar=:h AND p.agenteIdentificaEjemplarTbPK.cTipo=:ident ORDER BY P.eSecuencia", AgenteIdentificaEjemplarTb.class);
        query.setParameter("h", ejemplar);
        query.setParameter("ident", identificador);
        return query.getResultList();
    }

    public List<EjemplarTb> EjemplarOrdenAsc() {
        TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p ORDER BY p.cCodigoentrada ASC", EjemplarTb.class);
        return query.getResultList();
    }

    public List<EjemplarTb> BuscarEjemplares(TaxonomiaTb tax) {
        TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p WHERE p.eIdtaxonomia=:t ORDER BY p.cCodigoentrada ASC", EjemplarTb.class);
        query.setParameter("t", tax);
        return query.getResultList();
    }

    public List<NombrecomunTb> nombresComunes(TaxonomiaTb tax) {
        TypedQuery<NombrecomunTb> query = em.createQuery("SELECT n FROM NombrecomunTb n WHERE n.eIdtaxonomia=:t", NombrecomunTb.class);
        query.setParameter("t", tax);
        return query.getResultList();
    }

    public List<EjemplarTb> ejemplarGeneral() {
        em.clear();
        TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p ORDER BY p.cCodigoentrada ASC", EjemplarTb.class);
        return query.getResultList();
    }

    public List<EjemplarTb> ejemplarGeneralRecoleccion(Date f1, Date f2) {
        em.clear();
        TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p WHERE p.fFechaInicioIdent BETWEEN :f1 and :f2 ORDER BY p.fFechaInicioIdent ASC", EjemplarTb.class);
        query.setParameter("f1", f1);
        query.setParameter("f2", f2);
        return query.getResultList();
    }

    public List<EjemplarTb> ejemplarGeneralIdentificacion(Date f1, Date f2) {
        em.clear();
        TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p WHERE p.fFechaFinIdent BETWEEN :f1 and :f2 ORDER BY p.fFechaFinIdent ASC", EjemplarTb.class);
        query.setParameter("f1", f1);
        query.setParameter("f2", f2);
        return query.getResultList();
    }

    public List<EjemplarTb> ejemplarGeneralEntrada(String c1, String c2) {
        em.clear();
        TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p WHERE p.cCodigoentrada BETWEEN :c1 and :c2 ORDER BY p.cCodigoentrada ASC", EjemplarTb.class);
        query.setParameter("c1", c1);
        query.setParameter("c2", c2);
        return query.getResultList();
    }

    public List<EjemplarTb> ejemplarGeneralResponsable(Integer r1) {
        em.clear();
        TypedQuery<EjemplarTb> query = em.createQuery("SELECT p FROM EjemplarTb p WHERE p.eResponsable=:r1 ORDER BY p.cCodigoentrada ASC", EjemplarTb.class);
        query.setParameter("r1", r1);
        return query.getResultList();
    }

    public List<AgenteTb> responsables() {
        String Sentencia = "SELECT a.e_idagente, a.c_nombre, a.c_apellido FROM agente_tb a INNER JOIN ejemplar_tb e on a.e_idagente=e.e_responsable";
        Query query = em.createNativeQuery(Sentencia, AgenteTb.class);
        return query.getResultList();
    }

    public String nombreResposable(Integer id) {
        TypedQuery<AgenteTb> query = em.createQuery("SELECT p FROM AgenteTb p WHERE p.eIdagente=:r1", AgenteTb.class);
        query.setParameter("r1", id);
        return query.getSingleResult().getCNombre() + " " + query.getSingleResult().getCApellido() + " (" + query.getSingleResult().getCIniciales() + ")";
    }

    public List<AgenteTb> identificadores() {
        String Sentencia = "SELECT DISTINCT a.e_idagente, a.c_nombre, a.c_apellido FROM agente_tb a INNER JOIN agente_identifica_ejemplar_tb ai on a.e_idagente=ai.e_idagente WHERE c_tipo='Identificador'";
        Query query = em.createNativeQuery(Sentencia, AgenteTb.class);
        return query.getResultList();
    }

    public List<EjemplarTb> ejemplarGeneralIdentificador(Integer id) {
        String Sentencia = "SELECT * FROM ejemplar_tb e INNER JOIN agente_identifica_ejemplar_tb ai on e.e_idejemplar=ai.e_idejemplar INNER JOIN agente_tb a ON ai.e_idagente=a.e_idagente where ai.e_idagente=" + id + "";
        Query query = em.createNativeQuery(Sentencia, EjemplarTb.class);
        return query.getResultList();
    }

    public List<AgenteTb> recolectores() {
        String Sentencia = "SELECT DISTINCT a.e_idagente, a.c_nombre, a.c_apellido FROM agente_tb a INNER JOIN agente_identifica_ejemplar_tb ai on a.e_idagente=ai.e_idagente WHERE c_tipo='Recolector'";
        Query query = em.createNativeQuery(Sentencia, AgenteTb.class);
        return query.getResultList();
    }

    public List<EjemplarTb> ejemplarGeneralRecolector(Integer id) {
        String Sentencia = "SELECT * FROM ejemplar_tb e INNER JOIN agente_identifica_ejemplar_tb ai on e.e_idejemplar=ai.e_idejemplar INNER JOIN agente_tb a ON ai.e_idagente=a.e_idagente where ai.e_idagente=" + id + "";
        Query query = em.createNativeQuery(Sentencia, EjemplarTb.class);
        return query.getResultList();
    }
}
