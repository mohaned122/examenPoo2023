/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen_2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Hotel {
    private String nom;
    private HashSet<client> clients;
    private ArrayList<Chambre> chambres;
    private HashMap<client,Chambre> reservation;

    public Hotel(String nom) {
        this.nom = nom;
        this.clients = new HashSet<client>();
        this.chambres = new ArrayList<Chambre>();
        this.reservation = new HashMap<client,Chambre>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public boolean existeClient(client cl){
        return clients.contains(cl);
    }
    
    public boolean existeChambre(int numChambre){
        Iterator it = chambres.iterator();
        
        while(it.hasNext()){
            Chambre chambre = (Chambre) it.next();
            if(chambre.getNumChambre() == numChambre){
                return true;
            }
        }
        return false;
    }
    
    public boolean verifdispo(Chambre ch, int nblits){
        boolean ok = existeChambre(ch.getNumChambre());
        System.out.println("en traine de vrefie l'existance");
        if(!ok){
            return ok;
        }
        ok = ch.isEtat();
        if(ok){
            return ok;
        }
        ok = ch.getNbLit()>= nblits;
        if(!ok){
            System.out.println(ok);
            return ok;
        }
        return true;
    }
    
    public boolean Réserver(client cl, Chambre ch,int nbFamille) throws ChambreException{
        System.out.println("entraine de reserver");
        if(reservation.containsKey(cl)){
            System.out.println("reservation annuler");
            return false;
        }
        if(!verifdispo(ch,nbFamille)){
            throw new ChambreException("non disponible",ch);
        }
        reservation.put(cl, ch);
        ch.setEtat(true);
        return true;
    }
    
    public boolean annulerRéservation(client cl){
        if(reservation.containsKey(cl)){
            reservation.remove(cl);
            return true;
        }else{
            return false;
        }
    }
    
    public void ajouteChambre(Chambre chambre){
        this.chambres.add(chambre);
    }
    public boolean TransférerVers(client cl, Chambre ch1, Chambre ch2){
        if(!reservation.containsKey(cl)){
            return false;
        }
        
        if(!existeChambre(ch1.getNumChambre())){
            return false;
        }
        
        if(!verifdispo(ch2,0)){
            return false;
        }
        reservation.replace(cl, ch2);
        return true;
    }
    public void AfficherTout(){
        Set<client> keys = reservation.keySet();
        System.out.println("*****************nos reservation*********************");
        for(client c : keys){
            System.out.println("client:"+c.toString());
            System.out.println("chambre:"+reservation.get(c).toString());
        }
    }
    
    public Connection connexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connect");
            Connection cx = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam","root","root");
            return cx;
        } catch (ClassNotFoundException ex) {
            System.out.println("class not found");
            return null;
        } catch (SQLException ex) {
            System.out.println("sql exception");
            return null;
        }
    }
    
    public boolean create (Chambre ch){
        if(connexion() != null){
            try {
                String sql = "insert into chambre (numero_de_chambre,nombre_de_lits_disponibles,etat) values ( ?,?,?)";
                Connection cx = connexion();
                PreparedStatement pstm = cx.prepareStatement(sql);
                pstm.setInt(1, ch.getNumChambre());
                pstm.setInt(2, ch.getNbLit());
                pstm.setString(3, ch.getType());
                pstm.executeUpdate();
                return true;
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                return false;
            }
            
        }else{
            return false;
        }
    }
    
    public boolean update (Chambre ch){
        if(connexion() != null){
            try {
                String sql = "UPDATE chambre SET nombre_de_lits_disponibles = ?, etat = ? WHERE numero_de_chambre = ?";
                Connection cx = connexion();
                PreparedStatement pstm = cx.prepareStatement(sql);
                pstm.setInt(3, ch.getNumChambre());
                pstm.setInt(1, ch.getNbLit());
                pstm.setBoolean(2, ch.isEtat());
                pstm.executeUpdate();
                return true;
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                return false;
            }
            
        }else{
            return false;
        }
    }
    
    public void findbyNum(int numch){
        if(connexion() != null){
            try {
                String sql = "select * from  chambre where numero_de_chambre = ?";
                Connection cx = connexion();
                PreparedStatement pstm = cx.prepareStatement(sql);
                pstm.setInt(1, numch);
                ResultSet rs = pstm.executeQuery();
                while(rs.next()){
                    System.out.println("num:"+rs.getInt(1)+"|nombre lit:"+rs.getInt(2)+"|etat:"+rs.getString(3));
                }
            } catch (SQLException ex) {
                System.out.println("exception found");
            }
        }else{
            System.out.println("error connextion");
        }
    }
    public static void main(String arg[]){
        Hotel hotel = new Hotel("grand bleu");
        //int numChambre, int nbLit, String type, boolean etat
        hotel.create(new Chambre(3,4,"conforte",false));
        //boolean ok = hotel.update(new Chambre(1,7,"familiale",false));
        //System.out.println("modification : "+ok);
        hotel.ajouteChambre(new Chambre(1,4,"conforte",false));
        hotel.ajouteChambre(new Chambre(2,4,"conforte",false));
        hotel.ajouteChambre(new Chambre(3,4,"conforte",false));
        //public client(String nom, String prenom, String adress)
        client c1 = new client("mohanned","zayoud","mellita");
        client c2 = new client("mohamed","ben romdhanne","mellita");
        client c3 = new client("jamel","zayoud","mellita");
        
        try {
            hotel.Réserver(c3, hotel.chambres.getFirst(),2);
        } catch (ChambreException ex) {
            System.out.println(ex.toString());
        }
        System.out.println(hotel.reservation.toString());
        hotel.AfficherTout();
        System.out.println("************************************");
        hotel.findbyNum(2);
    }
}
