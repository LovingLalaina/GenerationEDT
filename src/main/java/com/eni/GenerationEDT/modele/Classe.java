package com.eni.GenerationEDT.modele;

public class Classe
{
	private int id;
	private String niveau;
	private String parcours;
	private int groupe;
	private int nombre_eleves;
	
	public int getId()
	{
		return id;
	}
	
	public void setId( final int id )
	{
		this.id = id;
	}
	
	public String getNiveau()
	{
		return niveau;
	}
	
	public void setNiveau( final String niveau )
	{
		this.niveau = niveau;
	}
	
	public String getParcours()
	{
		return parcours;
	}
	
	public void setParcours( final String parcours )
	{
		this.parcours = parcours;
	}
	
	public int getGroupe()
	{
		return groupe;
	}
	public void setGroupe( final int groupe )
	{
		this.groupe = groupe;
	}

	public int getNombre_eleves() {
		return nombre_eleves;
	}

	public void setNombre_eleves(int nombre_eleves) {
		this.nombre_eleves = nombre_eleves;
	}
}