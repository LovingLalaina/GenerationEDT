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
import com.eni.GenerationEDT.modele.Salle;

@RestController
@RequestMapping( "/api/salle" )
public class SalleControleur
{
	@PostMapping("/ajouter")
    public ResponseEntity<?> ajouter( @Validated @RequestBody final Salle salle )
	{
        try
        {
        	final Statement connexionBD = ConnexionBD.connecter();
        	final ResultSet resultatSELECT = connexionBD.executeQuery( "SELECT * FROM salle WHERE designation = '" + salle.getDesignation() + "'" );
        	if( resultatSELECT.next() )
        		return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Cette salle est déjà enregistrée, Veuillez entrer un autre" );
        	connexionBD.executeUpdate( "INSERT INTO salle ( designation , capacite ) VALUES ( '" + salle.getDesignation() + "' , '" + salle.getCapacite() + "' )" );

        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de l'ajout de la salle : " + erreur.getMessage() );
        }
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( "Salle ajoutée avec succès : " + salle.getDesignation() );
    }
	
	@GetMapping("/afficher")
    public ResponseEntity<?> afficher()
	{
        final List<Map<String, Object>> listeSalle = new ArrayList<>();
        
        try
        {
        	final ResultSet resultatSELECT = ConnexionBD.connecter().executeQuery( "SELECT * FROM salle" );
        	
            while( resultatSELECT.next() )
            {
                Map<String, Object> salleActuelle = new HashMap<>();
                salleActuelle.put( "id" , resultatSELECT.getInt( "id" ) );
                salleActuelle.put( "designation" , resultatSELECT.getString( "designation" ) );
                salleActuelle.put( "capacite" , resultatSELECT.getInt("capacite" ) );
                listeSalle.add( salleActuelle );
            }
        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de la récupération des salles : " + erreur.getMessage() );
        }

        return ResponseEntity.ok( listeSalle );
    }
	
	@PutMapping("/modifier/{id}")
    public ResponseEntity<?> modifier( @PathVariable final int id , @Validated @RequestBody final Salle nouvelleSalle )
	{
        try
        {
        	final Statement connexionBD = ConnexionBD.connecter();
        	final ResultSet resultatSELECT = connexionBD.executeQuery( "SELECT * FROM salle WHERE designation = '" + nouvelleSalle.getDesignation() + "' AND id !='" + id + "'" );
        	if( resultatSELECT.next() )
        		return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Cette salle est déjà enregistrée, Veuillez utiliser un autre nom" );

            if( connexionBD.executeUpdate( "UPDATE salle SET designation = '" + nouvelleSalle.getDesignation() + "' , capacite = '" + nouvelleSalle.getCapacite() + "' WHERE id = '" + id + "'" ) <= 0 )
            	return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Aucune salle trouvée avec l'ID : " + id );
            return ResponseEntity.ok( "Salle modifiée avec succès." );
        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de la modification de la salle : " + erreur.getMessage() );
        }
    }
	
	@DeleteMapping( "/supprimer/{id}" )
	public ResponseEntity<?> supprimer( @PathVariable final int id )
	{
		try
        {
			if( ConnexionBD.connecter().executeUpdate( "DELETE FROM salle WHERE id = '" + id + "'" ) <= 0 )
				return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Aucune salle trouvée avec l'ID : " + id );
            return ResponseEntity.ok( "Salle supprimée avec succès." );
        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de la suppression de la salle : " + erreur.getMessage() );
        }
	}
}
