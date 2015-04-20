package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.BitacoraTb;
import modelo.ExhibicionTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-19T22:18:01")
@StaticMetamodel(UsuarioTb.class)
public class UsuarioTb_ { 

    public static volatile SingularAttribute<UsuarioTb, String> cTipo;
    public static volatile SingularAttribute<UsuarioTb, String> cNick;
    public static volatile ListAttribute<UsuarioTb, BitacoraTb> bitacoraTbList;
    public static volatile SingularAttribute<UsuarioTb, Integer> eIdusuario;
    public static volatile SingularAttribute<UsuarioTb, String> cTelefono;
    public static volatile SingularAttribute<UsuarioTb, String> cDui;
    public static volatile SingularAttribute<UsuarioTb, String> cNombre;
    public static volatile SingularAttribute<UsuarioTb, String> cPassword;
    public static volatile SingularAttribute<UsuarioTb, String> cApellido;
    public static volatile ListAttribute<UsuarioTb, ExhibicionTb> exhibicionTbList;

}