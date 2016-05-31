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
import modelo.ActividadTb;
import modelo.AgenteTb;

/**
 *
 * @author Rafa
 */
@Stateless
public class AgenteTbFacade extends AbstractFacade<AgenteTb> {
    @PersistenceContext(unitName = "muhnes_muhnes_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgenteTbFacade() {
        super(AgenteTb.class);
    }
    public int siguienteId(){
        Query query = em.createNativeQuery("SELECT last_value from secuencia_agente_id");
        try{
            Long id =  (Long) query.getSingleResult();
            return id.intValue()+1;
        }
        catch(NoResultException nre){
            return 0;
        }
    }
    
    public List<AgenteTb> agentesAutores(){
        String Sentencia = "SELECT a.e_idagente, a.c_nombre, a.c_apellido, a.c_iniciales from agente_tb a INNER JOIN agente_perfil_tb ap ON a.e_idagente=ap.e_idagente INNER JOIN perfil_tb p ON ap.e_idperfil=p.e_idperfil WHERE p.c_nombre='Autor taxonomico'";
        Query query = em.createNativeQuery(Sentencia, AgenteTb.class);
        return query.getResultList();
    }
    
    public List<AgenteTb> agentesRecolectores(){
        String Sentencia = "SELECT a.e_idagente, a.c_nombre, a.c_apellido, a.c_iniciales from agente_tb a INNER JOIN agente_perfil_tb ap ON a.e_idagente=ap.e_idagente INNER JOIN perfil_tb p ON ap.e_idperfil=p.e_idperfil WHERE p.c_nombre='Recolector'";
        Query query = em.createNativeQuery(Sentencia, AgenteTb.class);
        return query.getResultList();
    }
    
    public List<AgenteTb> agentesIdentificadores(){
        String Sentencia = "SELECT a.e_idagente, a.c_nombre, a.c_apellido, a.c_iniciales from agente_tb a INNER JOIN agente_perfil_tb ap ON a.e_idagente=ap.e_idagente INNER JOIN perfil_tb p ON ap.e_idperfil=p.e_idperfil WHERE p.c_nombre='Identificador'";
        Query query = em.createNativeQuery(Sentencia, AgenteTb.class);
        return query.getResultList();
    }
    
    public AgenteTb agentePorId(int agente) {
        em.clear();
        TypedQuery<AgenteTb> query = em.createQuery("SELECT p FROM AgenteTb p WHERE p.eIdagente=:a", AgenteTb.class);
        query.setParameter("a", agente);
        return query.getSingleResult();
    }
    
    public List<AgenteTb> agenteCustodio() {
       String Sentencia = "SELECT a.e_idagente, a.c_nombre, a.c_apellido, a.c_iniciales from agente_tb a INNER JOIN agente_perfil_tb ap ON a.e_idagente=ap.e_idagente INNER JOIN perfil_tb p ON ap.e_idperfil=p.e_idperfil WHERE p.c_nombre='Custodio'";
        Query query = em.createNativeQuery(Sentencia, AgenteTb.class);
        return query.getResultList();
    }
    
    public List<AgenteTb> agentesGeneral() {
        TypedQuery<AgenteTb> query = em.createQuery("SELECT p FROM AgenteTb p ORDER BY p.cApellido ASC", AgenteTb.class);
        return query.getResultList();
    }
}
