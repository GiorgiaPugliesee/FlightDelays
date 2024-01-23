package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private ExtFlightDelaysDAO dao;
	private Map<Integer, Airport> idMap;
	private List<Airport> allAirports;
	
	public Model() {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao = new ExtFlightDelaysDAO();
		this.idMap = new HashMap<>();
		allAirports = this.dao.loadAllAirports();
	}
	

	public void creaGrafo(int x) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		if(idMap.isEmpty()) {
			for(Airport a : allAirports) {
				this.idMap.put(a.getId(), a);
			}
		}
		
		Graphs.addAllVertices(this.grafo, this.dao.loadAllNodes(x, idMap));
		
		List<Rotta> edges = this.dao.loadAllEdges(idMap);
		
		for(Rotta r : edges) {
			Airport origin = r.getOrigin();
			Airport destination = r.getDestination();
			int n = r.getnFlights();
			
			if(grafo.vertexSet().contains(origin) && grafo.vertexSet().contains(destination)) {
				DefaultWeightedEdge edge = this.grafo.addEdge(origin, destination);
				if(edge != null) {
					double weight = this.grafo.getEdgeWeight(edge);
					weight += n;
					this.grafo.setEdgeWeight(origin, destination, weight);
				} else {
					this.grafo.addEdge(origin, destination);
					this.grafo.setEdgeWeight(origin, destination, n);
				}
			}
		}
		System.out.println("Ci sono " + grafo.vertexSet().size() + " vertici.");
		System.out.println("Ci sono " + grafo.edgeSet().size() + " archi.");

	}
	
	public List<Airport> trovaPercorso(Airport origin, Airport destination) {
		List<Airport> percorso = new ArrayList<>();
	 	BreadthFirstIterator<Airport,DefaultWeightedEdge> it = new BreadthFirstIterator<>(this.grafo, origin);
	 	Boolean trovato = false;
	 	
	 	//visito il grafo fino alla fine o fino a che non trovo la destinazione
	 	while(it.hasNext() & !trovato) {
	 		Airport visitato = it.next();
	 		if(visitato.equals(destination))
	 			trovato = true;
	 	}
	 
	 
	 	/* se ho trovato la destinazione, costruisco il percorso risalendo l'albero di visita in senso
	 	 * opposto, ovvero partendo dalla destinazione fino ad arrivare all'origine, ed aggiiungo gli aeroporti
	 	 * ad ogni step IN TESTA alla lista
	 	 * se non ho trovato la destinazione, restituisco null.
	 	 */
	 	if(trovato) {
	 		percorso.add(destination);
	 		Airport step = it.getParent(destination);
	 		while (!step.equals(origin)) {
	 			percorso.add(0,step);
	 			step = it.getParent(step);
	 		}
		 
		 percorso.add(0,origin);
		 return percorso;
	 	} else {
	 		return null;
	 	}
	}
	
}



















