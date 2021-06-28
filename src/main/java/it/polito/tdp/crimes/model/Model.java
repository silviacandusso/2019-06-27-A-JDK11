package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	EventsDao dao;
	Graph<String,DefaultWeightedEdge>grafo;
	List<Adiacenza> archi;
	List<String>vertici;
	//variabile per la ricorsione
	int pesoMin;
	List<String>result;
	
	public Model() {
		dao= new EventsDao();
	}

	public List<String> getAllCategories() {
		return dao.getAllCategories();
	}

	public List<Integer> getAllYears() {
		return dao.getAllYears();
	}

	public void creaGrafo(int anno, String categoria) {
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		archi= new ArrayList<Adiacenza>();
		vertici= new ArrayList<String>();
		// seleziono gli archi
		for(String v: dao.getVertici(anno, categoria)) {
		grafo.addVertex(v);
		vertici.add(v);
		}
		//seleziono archi
		for(Adiacenza a: dao.getAdiacenze(anno, categoria)) {
			if(a.getPeso()>0)
			Graphs.addEdgeWithVertices(grafo, a.getTipo1(), a.getTipo2(),a.getPeso());
			archi.add(a);
		}
		
		
	}
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Adiacenza> getArchiMax(){
		int pesoMax=0;
		List<Adiacenza> result= new ArrayList<Adiacenza>();
		for(Adiacenza a: archi ) {
			if(a.getPeso()==pesoMax) {
				result.add(a);
			}
			else if(a.getPeso()> pesoMax) {
				result.clear();
				result.add(a);
				pesoMax=a.getPeso();
			}
		}
		return result;
	}
	
	public List<String> percorsoMin(String v1,String v2){
		pesoMin=Integer.MAX_VALUE;
	
		List<String>parziale= new ArrayList<String>();
		parziale.add(v1);
		vertici.remove(v1);
		cerca(parziale,v2,vertici);
		if(pesoMin!=Integer.MAX_VALUE) {
			System.out.println("si");
		return result;
		}
		else
			return null;
	}
	
	private void cerca(List<String>parziale, String v2,List<String> vertici ) {
		//caso terminale
		//arrivo a v2
		String ultimo=parziale.get(parziale.size()-1);
		//devo controllare se ha passato tutti i vertici
		if(ultimo.equals(v2) && vertici.isEmpty()) {
				//soluzione valida, verifico se è a peso minimo
				if(getPeso(parziale)<pesoMin) {
					//è la migliore soluzione
					result= new ArrayList<String>(parziale);
					pesoMin=getPeso(parziale);
					return;
				}
		}
		
		//altrimenti devo cercare un altro vertice
		List<String> vicini=Graphs.neighborListOf(grafo, ultimo);
		if(vicini!=null) {
		for(String vertice: vicini) {
			if(!parziale.contains(vertice)) {
				parziale.add(vertice);
				vertici.remove(vertice);
				//ricorsione
				cerca(parziale,v2,vertici);
			//backtracking
			parziale.remove(vertice);
			vertici.add(vertice);
			}
		  }
		}
	}
	
	private int getPeso(List<String>parziale) {
		int peso=0;
		for(int i=1; i<parziale.size();i++) {
			peso+=grafo.getEdgeWeight(grafo.getEdge(parziale.get(i-1),parziale.get(i)));
		}
		
		return peso;
	}

	public int getPesoMin() {
		return pesoMin;
	}

	public List<String> getResult() {
		return result;
	}
	
	
}
