/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen_2023;

/**
 *
 * @author user
 */
public class ChambreException extends Exception {
    private String msg;
    private Chambre chambre;
    
    public ChambreException(String msg,Chambre chambre){
        this.chambre = chambre;
        this.msg = msg;
    }
    
    public int getNumChambre(){
        return 0;
    }
    
    @Override
    public String toString(){
        return "nombre de lits insuffisants dans le chambre"+getNumChambre();
    }
}