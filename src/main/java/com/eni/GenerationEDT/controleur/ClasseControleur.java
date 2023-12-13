package com.eni.GenerationEDT.controleur;

import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eni.GenerationEDT.modele.ConnexionBD;
import com.eni.GenerationEDT.modele.Classe;

@RestController
@RequestMapping( "/api/classe" )
public class ClasseControleur
{
	@PostMapping("/ajouter")
    public ResponseEntity<?> ajouter( @Validated @RequestBody final Classe classe )
	{
        try
        {
        	final Statement connexionBD = ConnexionBD.connecter();
        	final ResultSet resultatSELECT = connexionBD.executeQuery( "SELECT * FROM classe WHERE niveau = '" + classe.getNiveau() + "' AND parcours = '" + classe.getParcours() + "' AND groupe = " + classe.getGroupe() + "" );
        	if( resultatSELECT.next() )
        		return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Cette classe est déjà enregistrée, Veuillez entrer une autre" );
        	connexionBD.executeUpdate( "INSERT INTO classe ( niveau , parcours , groupe , nombre_eleves ) VALUES ( '" + classe.getNiveau() + "' , '" + classe.getParcours() + "' , '" + classe.getGroupe() + "' , '" + classe.getNombre_eleves() + "' )" );

        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de l'ajout de la classe : " + erreur.getMessage() );
        }
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( "Classe ajoutée avec succès" );
    }
	
	@GetMapping("/afficher")
    public ResponseEntity<?> afficher()
	{
        final List<Map<String, Object>> listeClasse = new ArrayList<>();
        
        try
        {
        	final ResultSet resultatSELECT = ConnexionBD.connecter().executeQuery( "SELECT * FROM classe" );
        	
            while( resultatSELECT.next() )
            {
                Map<String, Object> classeActuelle = new HashMap<>();
                classeActuelle.put( "id" , resultatSELECT.getInt( "id" ) );
                classeActuelle.put( "niveau" , resultatSELECT.getString( "niveau" ) );
                classeActuelle.put( "parcours" , resultatSELECT.getString( "parcours" ) );
                classeActuelle.put( "groupe" , resultatSELECT.getInt( "groupe" ) );
                classeActuelle.put( "nombre_eleves" , resultatSELECT.getInt( "nombre_eleves" ) );
                listeClasse.add( classeActuelle );
            }
        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de la récupération des classes : " + erreur.getMessage() );
        }

        return ResponseEntity.ok( listeClasse );
    }
	
	@PutMapping("/modifier/{id}")
    public ResponseEntity<?> modifier( @PathVariable final int id , @Validated @RequestBody final Classe nouvelleClasse )
	{
        try
        {
        	final Statement connexionBD = ConnexionBD.connecter();
        	final ResultSet resultatSELECT = connexionBD.executeQuery( "SELECT * FROM classe WHERE niveau = '" + nouvelleClasse.getNiveau() + "' AND parcours = '" + nouvelleClasse.getParcours() + "' AND groupe = '" + nouvelleClasse.getGroupe() + "'  AND id != '" + id + "'" );
        	if( resultatSELECT.next() )
        		return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Cette classe est déjà enregistrée, Veuillez changer les informations" );
        	
            if( connexionBD.executeUpdate( "UPDATE classe SET niveau = '" + nouvelleClasse.getNiveau() + "' , parcours = '" + nouvelleClasse.getParcours() + "' , groupe = '" + nouvelleClasse.getGroupe() + "' , nombre_eleves = '" + nouvelleClasse.getNombre_eleves() + "' WHERE id = '" + id + "'" ) <= 0 )
            	return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Aucune classe trouvée avec l'ID : " + id );
            return ResponseEntity.ok( "Classe modifiée avec succès." );
        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de la modification de la classe : " + erreur.getMessage() );
        }
    }
	
	@DeleteMapping( "/supprimer/{id}" )
	public ResponseEntity<?> supprimer( @PathVariable final int id )
	{
		try
        {
			if( ConnexionBD.connecter().executeUpdate( "DELETE FROM classe WHERE id = '" + id + "'" ) <= 0 )
				return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Aucune classe trouvée avec l'ID : " + id );
            return ResponseEntity.ok( "Classe supprimée avec succès." );
        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de la suppression de la classe : " + erreur.getMessage() );
        }
	}
}
