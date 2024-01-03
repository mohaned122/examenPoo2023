/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen_2023;

/**
 *
 * @author user
 */
public class Chambre {
    //numéro de chambre, nombre de lits disponibles de type entier et de son état
    private int numChambre;
    private int nbLit;
    private String type;
    private boolean etat;

    public Chambre(int numChambre, int nbLit, String type, boolean etat) {
        this.numChambre = numChambre;
        this.nbLit = nbLit;
        this.type = type;
        this.etat = etat;
    }

    public int getNumChambre() {
        return numChambre;
    }

    public void setNumChambre(int numChambre) {
        this.numChambre = numChambre;
    }

    public int getNbLit() {
        return nbLit;
    }

    public void setNbLit(int nbLit) {
        this.nbLit = nbLit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Chambre{" + "numChambre=" + numChambre + ", nbLit=" + nbLit + ", type=" + type + ", etat=" + etat + '}';
    }
    
}
