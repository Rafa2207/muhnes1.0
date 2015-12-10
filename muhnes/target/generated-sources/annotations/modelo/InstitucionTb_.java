package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgenteTb;
import modelo.PaisTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-10T10:12:17")
@StaticMetamodel(InstitucionTb.class)
public class InstitucionTb_ { 

    public static volatile SingularAttribute<InstitucionTb, Boolean> bEstado;
    public static volatile SingularAttribute<InstitucionTb, String> cAcronimo;
    public static volatile ListAttribute<InstitucionTb, AgenteTb> agenteTbList;
    public static volatile SingularAttribute<InstitucionTb, Integer> eIdinstitucion;
    public static volatile SingularAttribute<InstitucionTb, String> cTelefono;
    public static volatile SingularAttribute<InstitucionTb, String> cNombre;
    public static volatile SingularAttribute<InstitucionTb, Integer> ePostal;
    public static volatile SingularAttribute<InstitucionTb, String> mDireccion;
    public static volatile SingularAttribute<InstitucionTb, String> cUrl;
    public static volatile SingularAttribute<InstitucionTb, String> cFax;
    public static volatile SingularAttribute<InstitucionTb, PaisTb> eIdpais;

}