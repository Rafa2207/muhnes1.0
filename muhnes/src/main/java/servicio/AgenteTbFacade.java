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
        String Sentencia = "select a.e_idagente, a.c_nombre, a.c_apellido, a.c_iniciales from agente_tb a inner join agente_perfil_tb ap on a.e_idagente=ap.e_idagente inner join perfil_tb p on ap.e_idperfil=p.e_idperfil where p.c_nombre='autor taxonomico'";
        Query query = em.createNativeQuery(Sentencia, AgenteTb.class);
        return query.getResultList();
    }
}
