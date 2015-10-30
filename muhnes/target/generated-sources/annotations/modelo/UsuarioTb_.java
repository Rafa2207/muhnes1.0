package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ExhibicionTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:44:40")
@StaticMetamodel(UsuarioTb.class)
public class UsuarioTb_ { 

    public static volatile SingularAttribute<UsuarioTb, String> cTipo;
    public static volatile SingularAttribute<UsuarioTb, Boolean> bEstado;
    public static volatile SingularAttribute<UsuarioTb, String> cNick;
    public static volatile SingularAttribute<UsuarioTb, Integer> eIdusuario;
    public static volatile SingularAttribute<UsuarioTb, String> mPassword;
    public static volatile SingularAttribute<UsuarioTb, String> cDui;
    public static volatile SingularAttribute<UsuarioTb, String> cNombre;
    public static volatile SingularAttribute<UsuarioTb, String> cApellido;
    public static volatile SingularAttribute<UsuarioTb, Integer> eIdagente;
    public static volatile ListAttribute<UsuarioTb, ExhibicionTb> exhibicionTbList;
    public static volatile SingularAttribute<UsuarioTb, String> mEmail;

}