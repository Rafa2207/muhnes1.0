package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgentePerfilTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-17T08:48:34")
@StaticMetamodel(PerfilTb.class)
public class PerfilTb_ { 

    public static volatile SingularAttribute<PerfilTb, Integer> eIdperfil;
    public static volatile SingularAttribute<PerfilTb, String> cNombre;
    public static volatile SingularAttribute<PerfilTb, String> mDescripcion;
    public static volatile ListAttribute<PerfilTb, AgentePerfilTb> agentePerfilTbList;

}