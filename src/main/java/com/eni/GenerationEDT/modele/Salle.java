package com.eni.GenerationEDT.modele;

public class Salle
{
	private int id;
	
	private String designation;
	
	private int capacite;
	
	public int getId()
	{
		return id;
	}
	
	public void setId( final int  id )
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

	public int getCapacite()
	{
		return capacite;
	}

	public void setCapacite( final int capacite )
	{
		this.capacite = capacite;
	}
	
	
	
}