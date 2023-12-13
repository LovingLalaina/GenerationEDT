package com.eni.GenerationEDT.modele;

public class Matiere
{
	private int id;
	private String designation;
	private int id_professeur;
	private int id_classe;
	private int priorite;
	
	public int getId()
	{
		return id;
	}
	public void setId( final int id )
	{
		this.id = id;
	}
	public String getDesignation()
	{
		return designation;
	}
	public void setDesignation( final String designation )
	{
		this.designation = designation;
	}
	public int getId_professeur()
	{
		return id_professeur;
	}
	public void setId_professeur( final int id_professeur )
	{
		this.id_professeur = id_professeur;
	}
	public int getId_classe()
	{
		return id_classe;
	}
	public void setId_classe( final int id_classe )
	{
		this.id_classe = id_classe;
	}
	public int getPriorite()
	{
		return priorite;
	}
	public void setPriorite( final int priorite )
	{
		this.priorite = priorite;
	}
}