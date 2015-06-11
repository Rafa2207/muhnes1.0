package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.EjemplarTb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-11T11:55:26")
@StaticMetamodel(EspecieTb.class)
public class EspecieTb_ { 

    public static volatile SingularAttribute<EspecieTb, Integer> eIdgenero;
    public static volatile ListAttribute<EspecieTb, EjemplarTb> ejemplarTbList;
    public static volatile SingularAttribute<EspecieTb, String> cNombre;
    public static volatile SingularAttribute<EspecieTb, Integer> eIdespecie;
    public static volatile SingularAttribute<EspecieTb, String> cEstado;

}