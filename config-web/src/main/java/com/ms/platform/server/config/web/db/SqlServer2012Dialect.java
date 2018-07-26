package com.ms.platform.server.config.web.db;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class SqlServer2012Dialect extends SQLServer2012Dialect {

    public SqlServer2012Dialect() {   
        super();
        //MSSQL
        registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NCLOB, StandardBasicTypes.CLOB.getName());
        
//        registerHibernateType(Types.NCHAR, StandardBasicTypes.CHARACTER.getName()); 
        registerHibernateType(Types.NCHAR, 1, StandardBasicTypes.CHARACTER.getName()); 
        registerHibernateType(Types.NCHAR, 255, StandardBasicTypes.STRING.getName()); 
//        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.TEXT.getName()); 
        
//        registerColumnType( Types.NCLOB, "nvarchar(MAX)" );
//        registerColumnType( Types.LONGNVARCHAR, "nvarchar(MAX)" );
//        registerColumnType( Types.NVARCHAR, "nvarchar(MAX)" );
//        registerColumnType( Types.NVARCHAR, 4000, "nvarchar($1)" );
    }  
}
