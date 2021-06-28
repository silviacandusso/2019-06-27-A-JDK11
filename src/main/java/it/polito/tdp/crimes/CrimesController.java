/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Adiacenza> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer anno=this.boxAnno.getValue();
    	String categoria= this.boxCategoria.getValue();
    	if(anno==null || categoria ==null) {
    		txtResult.appendText("Selezionare un anno e una categoria di crimine");
    		return;
    	}
    	txtResult.appendText("Crea grafo...\n");
    	this.model.creaGrafo(anno,categoria);
    	txtResult.appendText("Lista degli archi con peso massimo: \n");
    	for(Adiacenza a: model.getArchiMax()) {
    		txtResult.appendText(a.getTipo1()+" "+a.getTipo2()+" peso "+a.getPeso());
    	}
    	
    	this.btnPercorso.setDisable(false);
    	this.boxArco.getItems().clear();
    	this.boxArco.getItems().addAll(model.getArchiMax());
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	Adiacenza arco=this.boxArco.getValue();
    	if(arco==null) {
    		txtResult.appendText("Selezionare un arco");
    		return;
    	}
    	txtResult.appendText("Calcola percorso...\n");
    	String v1=arco.getTipo1();
    	String v2= arco.getTipo2();
    	model.percorsoMin(v1, v2);
    	txtResult.appendText("Percorso da "+v1+" a "+v2+" :\n");
    	if(model.getResult()!=null) {
    	for(String v: model.getResult()) {
    		txtResult.appendText(v+"\n");
    	}
    	txtResult.appendText("Con peso minimo : "+model.getPesoMin());
    	}else {
    		txtResult.appendText("Non esiste un percorso");
    	}
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxCategoria.getItems().addAll(model.getAllCategories());
    	this.boxAnno.getItems().addAll(model.getAllYears());
    	this.btnPercorso.setDisable(true);
    	
    }
}
