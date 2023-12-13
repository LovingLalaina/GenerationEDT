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
import com.eni.GenerationEDT.modele.Matiere;

@RestController
@RequestMapping( "/api/matiere" )
public class MatiereControleur
{
	@PostMapping("/ajouter")
    public ResponseEntity<?> ajouter( @Validated @RequestBody final Matiere matiere )
	{
        try
        {
        	final Statement connexionBD = ConnexionBD.connecter();
        	final ResultSet resultatSELECT = connexionBD.executeQuery( "SELECT * FROM matiere WHERE designation = '" + matiere.getDesignation() + "'" );
        	if( resultatSELECT.next() )
        		return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Cette matiere est déjà enregistrée, Veuillez entrer une autre" );
        	connexionBD.executeUpdate( "INSERT INTO matiere ( designation , id_professeur , id_classe , priorite ) VALUES ( '" + matiere.getDesignation() + "' , '" + matiere.getId_professeur() + "' , '" + matiere.getId_classe() + "' , '" + matiere.getPriorite() + "' )" );
        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de l'ajout de la matiere : " + erreur.getMessage() );
        }
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( "Matiere ajoutée avec succès" );
    }
	
	@GetMapping("/afficher")
    public ResponseEntity<?> afficher()
	{
        final List<Map<String, Object>> listeMatiere = new ArrayList<>();
        
        try
        {
        	final ResultSet resultatSELECT = ConnexionBD.connecter().executeQuery( "SELECT matiere.id AS id_matiere , matiere.designation AS designation_matiere , professeur.nom AS nom_professeur , classe.niveau AS niveau_classe , classe.parcours AS parcours_classe , classe.groupe AS groupe_classe , priorite FROM matiere JOIN professeur ON matiere.id_professeur = professeur.id JOIN classe ON matiere.id_classe = classe.id" );
        	
            while( resultatSELECT.next() )
            {
                Map<String, Object> matiereActuelle = new HashMap<>();
                matiereActuelle.put( "id" , resultatSELECT.getInt( "id_matiere" ) );
                matiereActuelle.put( "designation" , resultatSELECT.getString( "designation_matiere" ) );
                matiereActuelle.put( "nom_professeur" , resultatSELECT.getString( "nom_professeur" ) );
                matiereActuelle.put( "classe" , resultatSELECT.getString( "niveau_classe" ) + " " + resultatSELECT.getString( "parcours_classe" ) + " " + resultatSELECT.getString( "groupe_classe" ) );
                matiereActuelle.put( "priorite" , resultatSELECT.getInt( "priorite" ) );
                listeMatiere.add( matiereActuelle );
            }
        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de la récupération des matieres : " + erreur.getMessage() );
        }

        return ResponseEntity.ok( listeMatiere );
    }
	
	@PutMapping("/modifier/{id}")
    public ResponseEntity<?> modifier( @PathVariable final int id , @Validated @RequestBody final Matiere nouvelleMatiere )
	{
        try
        {
        	final Statement connexionBD = ConnexionBD.connecter();
        	final ResultSet resultatSELECT = connexionBD.executeQuery( "SELECT * FROM matiere WHERE designation = '" + nouvelleMatiere.getDesignation() + "' AND id !='" + id + "'" );
        	if( resultatSELECT.next() )
        		return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Cette matiere est déjà enregistrée, Veuillez changer le nom" );
        	
            if( connexionBD.executeUpdate( "UPDATE matiere SET designation = '" + nouvelleMatiere.getDesignation() + "' , id_classe = '" + nouvelleMatiere.getId_classe() + "' , priorite = '" + nouvelleMatiere.getPriorite() + "' WHERE id = '" + id + "'" ) <= 0 )
            	return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Aucune matiere trouvée avec l'ID : " + id );
            return ResponseEntity.ok( "Matiere modifiée avec succès." );
        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de la modification de la matiere : " + erreur.getMessage() );
        }
    }
	
	@DeleteMapping( "/supprimer/{id}" )
	public ResponseEntity<?> supprimer( @PathVariable final int id )
	{
		try
        {
			if( ConnexionBD.connecter().executeUpdate( "DELETE FROM matiere WHERE id = '" + id + "'" ) <= 0 )
				return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Aucune matiere trouvée avec l'ID : " + id );
            return ResponseEntity.ok( "Matiere supprimée avec succès." );
        }
        catch( Exception erreur )
        {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( "Une erreur s'est produite lors de la suppression de la matiere : " + erreur.getMessage() );
        }
	}
}
