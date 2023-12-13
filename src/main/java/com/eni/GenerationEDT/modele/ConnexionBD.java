
package com.eni.GenerationEDT.modele;

import java.sql.DriverManager;
import java.sql.Statement;

public abstract class ConnexionBD
{
    public static Statement connecter()
    {
        try
        {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            return DriverManager.getConnection( "jdbc:mysql://localhost:3306/generation_edt" , "root" , "" ).createStatement();
        }
        catch( Exception erreur )
        {
        	System.out.println( "Erreur lors de l'accès à la base de données : " + erreur );
        	return null;
        }
    }
}











