package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.EjemplarTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-20T12:05:34")
@StaticMetamodel(GeneroTb.class)
public class GeneroTb_ { 

    public static volatile SingularAttribute<GeneroTb, Integer> eIdgenero;
    public static volatile ListAttribute<GeneroTb, EjemplarTb> ejemplarTbList;
    public static volatile SingularAttribute<GeneroTb, String> cNombre;
    public static volatile SingularAttribute<GeneroTb, Integer> eIdfamilia;

}