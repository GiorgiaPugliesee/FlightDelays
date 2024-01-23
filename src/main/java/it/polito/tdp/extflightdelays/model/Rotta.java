package it.polito.tdp.extflightdelays.model;

import java.util.Objects;

public class Rotta {
	
	private Airport origin;
	private Airport destination;
	private int nFlights;
	
	public Rotta(Airport origin, Airport destination, int nFlights) {
		this.origin = origin;
		this.destination = destination;
		this.nFlights = nFlights;
	}
	
	public Airport getOrigin() {
		return origin;
	}
	
	public Airport getDestination() {
		return destination;
	}
	
	public int getnFlights() {
		return nFlights;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(destination, nFlights, origin);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rotta other = (Rotta) obj;
		return Objects.equals(destination, other.destination) && nFlights == other.nFlights
				&& Objects.equals(origin, other.origin);
	}
	
	@Override
	public String toString() {
		return "Rotta [origin=" + origin + ", destination=" + destination + ", nFlights=" + nFlights + "]";
	}
	
	

}
