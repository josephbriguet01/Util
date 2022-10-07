/*
 * Copyright (C) BRIGUET Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Briguet, 05/2020
 */
package com.jasonpercus.util.mysql;



/**
 * Cette classe permet de créer une connexion MySQL puis d'envoyer des requêtes
 * @author JasonPercus
 * @version 1.1
 */
public class MySQL {
    
    
    
//ATTRIBUTS
    /**
     * Correspond à une connexion MySQL
     */
    private java.sql.Connection conn = null;
    
    /**
     * Correspond à l'utilisateur de la base MySQL
     */
    private final String username;
    
    /**
     * Correspond au mot de passe de la base MySQL
     */
    private final String password;
    
    /**
     * Correspond à l'adresse IP de la base MySQL
     */
    private final String ip;
    
    /**
     * Correspond au port de la base MySQL
     */
    private final int port;
    
    /**
     * Correspond au nom de la base MySQL
     */
    private final String base;
    
    /**
     * Correspond à la classe qui va gérer la donnée d'une table
     */
    private final Data data;
    
    /**
     * Correspond à la classe qui va gérer les données d'une ou plusieurs tables
     */
    private final Datas datas;
    
    /**
     * Correspond à la classe qui va gérer une table
     */
    public final Table table;
    
    /**
     * Correspond à la classe qui va gérer les tables
     */
    public final Tables tables;
    
    /**
     * Détermine si lorsque le serveur attend une requête depuis trop longtemps (pour éviter le message "The last packet successfully received from the server was 52 541 782 milliseconds ago"), la connexion est raffraichit
     */
    private boolean autoreconnect;
    
    /**
     * Détermine si la connexion utilisera l'unicode
     */
    private boolean useunicode;
    
    

    
    
//CONSTRUCTOR
    /**
     * Crée un objet MySQL
     * @param username Correspond à l'utilisateur de la base MySQL
     * @param password Correspond au mot de passe de la base MySQL
     * @param ip Correspond à l'adresse IP de la base MySQL
     * @param port Correspond au port de la base MySQL
     * @param base Correspond au nom de la base MySQL
     */
    protected MySQL(String username, String password, String ip, int port, String base) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.port = port;
        this.base = base;
        this.data = new Data();
        this.datas = new Datas();
        this.table = new Table();
        this.tables = new Tables();
        this.autoreconnect = true;
        this.useunicode = true;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie la connexion MySQL
     * @return Retourne la connexion MySQL
     */
    public java.sql.Connection getConnection(){
        return this.conn;
    }
    
    /**
     * Lance la connexion MySQL
     * @throws java.sql.SQLException Lève cette exception si la connexion ne peut être réalisée
     */
    public void connect() throws java.sql.SQLException {
        if(autoreconnect)
            this.conn = (java.sql.Connection) java.sql.DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+base+"?autoReconnect=true&useUnicode=yes", username, password);
        else{
            if(useunicode)
                this.conn = (java.sql.Connection) java.sql.DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+base+"?useUnicode=yes", username, password);
            else
                this.conn = (java.sql.Connection) java.sql.DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+base, username, password);
        }
    }
    
    /**
     * Ferme une connexion MySQL
     * @throws java.sql.SQLException Lève cette exception si la connexion ne peut être fermée
     */
    public void close() throws java.sql.SQLException {
        this.conn.close();
    }
    
    /**
     * Modifie si la connexion peut se raffraichir après une longue durée d'inactivité (cela évite le message "The last packet successfully received from the server was 52 541 782 milliseconds ago...")
     * Remarque: Cette méthode n'a un effet que si elle est utilisé AVANT la méthode connect()
     * @param autoreconnect Détermine si la connexion peut se raffraichir ou pas
     */
    public void autoreconnect(boolean autoreconnect) {
        this.autoreconnect = autoreconnect;
    }

    /**
     * Modifie si la connexion utilisera l'unicode
     * Remarque: Cette méthode n'a un effet que si elle est utilisé AVANT la méthode connect()
     * @param useunicode Détermine si la connexion utilisera l'unicode ou pas
     */
    public void useunicode(boolean useunicode) {
        this.useunicode = useunicode;
    }
    
    /**
     * Renvoie le nom de la base
     * @return Retourne le nom de la base
     */
    public String getBase(){
        return this.base;
    }
    
    /**
     * Recherche des objets ayant pour @PrimaryKey l'objet reference (l'attribut recherché doit avoir obligatoirement une annotation PrimaryKey)
     * @param <O> Correspond au type d'objet contenu dans la liste de recherche
     * @param listToAnalyse Correspond à la liste de recherche
     * @param reference Correspond à la valeur @PrimaryKey qui sera recherché dans tout objet de la liste sur chaque attribut contenant l'annotation
     * @return Retourne la liste des objets contenant un attribut annoté PrimaryKey ayant la même valeur que reference
     */
    public <O> java.util.List<O> searchs(java.util.List<O> listToAnalyse, Object reference) {
        java.util.List<O> result = new java.util.ArrayList<>();
        for(Object o : listToAnalyse){
            java.lang.reflect.Field[] fields = o.getClass().getDeclaredFields();
            for(java.lang.reflect.Field field : fields){
                java.lang.annotation.Annotation annotation = field.getAnnotation(PrimaryKey.class);
                if(annotation != null){
                    field.setAccessible(true);
                    try {
                        if((field.get(o) == null && reference == null) || (field.get(o) != null && reference != null && field.get(o).equals(reference))){
                            result.add((O) o);
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(MySQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * Recherche un objet ayant pour @PrimaryKey l'objet reference (l'attribut recherché doit avoir obligatoirement une annotation PrimaryKey)
     * @param <O> Correspond au type d'objet contenu dans la liste de recherche
     * @param listToAnalyse Correspond à la liste de recherche
     * @param reference Correspond à la valeur @PrimaryKey qui sera recherché dans tout objet de la liste sur chaque attribut contenant l'annotation
     * @return Retourne l'objet contenant un attribut annoté PrimaryKey ayant la même valeur que reference
     */
    public <O> O search(java.util.List<O> listToAnalyse, Object reference) {
        for(Object o : listToAnalyse){
            java.lang.reflect.Field[] fields = o.getClass().getDeclaredFields();
            for(java.lang.reflect.Field field : fields){
                java.lang.annotation.Annotation annotation = field.getAnnotation(PrimaryKey.class);
                if(annotation != null){
                    field.setAccessible(true);
                    try {
                        if((field.get(o) == null && reference == null) || (field.get(o) != null && reference != null && field.get(o).equals(reference))){
                            return (O) o;
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(MySQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Crée une requête préparée
     * @param request Correspond à la requête SQL
     * @return Retourne la requête préparée
     * @throws java.sql.SQLException Lève cette exception si une erreur SQL survient
     */
    public java.sql.PreparedStatement prepareStatement(String request) throws java.sql.SQLException {
        return this.conn.prepareStatement(request);
    }
    
    /**
     * Renvoie un récupérateur de résultats
     * @return Retourne un récupérateur de résultats
     * @throws java.sql.SQLException Lève une exception si le récupérateur ne peut être créé
     */
    public java.sql.Statement createStatement() throws java.sql.SQLException {
        return this.conn.createStatement();
    }
    
    
    
//PUBLICS PUBLICS STATICS
    /**
     * Crée une connexion MySQL et la connecte à la base
     * @param login Correspond à l'utilisateur de la base MySQL
     * @param password Correspond au mot de passe de la base MySQL
     * @param ip Correspond à l'adresse IP de la base MySQL
     * @param port Correspond au port de la base MySQL
     * @param base Correspond au nom de la base MySQL
     * @return Retourne une connexion MySQL connectée à la base
     * @throws java.sql.SQLException Lève une exception si la création de la connexion ou la connexion MySQL n'a pu aboutir
     */
    public static MySQL getInstanceConnected(String login, String password, String ip, int port, String base) throws java.sql.SQLException{
        MySQL mysql = MySQL.getInstance(login, password, ip, port, base);
        mysql.connect();
        return mysql;
    }
    
    /**
     * Crée une connexion MySQL et la connecte à la base (port=3306)
     * @param login Correspond à l'utilisateur de la base MySQL
     * @param password Correspond au mot de passe de la base MySQL
     * @param ip Correspond à l'adresse IP de la base MySQL
     * @param base Correspond au nom de la base MySQL
     * @return Retourne une connexion MySQL connectée à la base
     * @throws java.sql.SQLException Lève une exception si la création de la connexion ou la connexion MySQL n'a pu aboutir
     */
    public static MySQL getInstanceConnected(String login, String password, String ip, String base) throws java.sql.SQLException{
        MySQL mysql = MySQL.getInstance(login, password, ip, base);
        mysql.connect();
        return mysql;
    }
    
    /**
     * Crée une connexion MySQL et la connecte à la base (ip=localhost)
     * @param login Correspond à l'utilisateur de la base MySQL
     * @param password Correspond au mot de passe de la base MySQL
     * @param port Correspond au port de la base MySQL
     * @param base Correspond au nom de la base MySQL
     * @return Retourne une connexion MySQL connectée à la base
     * @throws java.sql.SQLException Lève une exception si la création de la connexion ou la connexion MySQL n'a pu aboutir
     */
    public static MySQL getInstanceConnected(String login, String password, int port, String base) throws java.sql.SQLException{
        MySQL mysql = MySQL.getInstance(login, password, "127.0.0.1", port, base);
        mysql.connect();
        return mysql;
    }
    
    /**
     * Crée une connexion MySQL et la connecte à la base (ip=localhost, port=3306)
     * @param login Correspond à l'utilisateur de la base MySQL
     * @param password Correspond au mot de passe de la base MySQL
     * @param base Correspond au nom de la base MySQL
     * @return Retourne une connexion MySQL connectée à la base
     * @throws java.sql.SQLException Lève une exception si la création de la connexion ou la connexion MySQL n'a pu aboutir
     */
    public static MySQL getInstanceConnected(String login, String password, String base) throws java.sql.SQLException{
        MySQL mysql = MySQL.getInstance(login, password, "127.0.0.1", 3306, base);
        mysql.connect();
        return mysql;
    }
    
    /**
     * Crée une connexion MySQL
     * @param login Correspond à l'utilisateur de la base MySQL
     * @param password Correspond au mot de passe de la base MySQL
     * @param ip Correspond à l'adresse IP de la base MySQL
     * @param port Correspond au port de la base MySQL
     * @param base Correspond au nom de la base MySQL
     * @return Retourne une connexion MySQL
     * @throws java.sql.SQLException Lève une exception si la création de la connexion MySQL n'a pu aboutir
     */
    public static MySQL getInstance(String login, String password, String ip, int port, String base) throws java.sql.SQLException{
        return new MySQL(login, password, ip, port, base);
    }
    
    /**
     * Crée une connexion MySQL (port=3306)
     * @param login Correspond à l'utilisateur de la base MySQL
     * @param password Correspond au mot de passe de la base MySQL
     * @param ip Correspond à l'adresse IP de la base MySQL
     * @param base Correspond au nom de la base MySQL
     * @return Retourne une connexion MySQL
     * @throws java.sql.SQLException Lève une exception si la création de la connexion MySQL n'a pu aboutir
     */
    public static MySQL getInstance(String login, String password, String ip, String base) throws java.sql.SQLException{
        return new MySQL(login, password, ip, 3306, base);
    }
    
    /**
     * Crée une connexion MySQL (ip=localhost)
     * @param login Correspond à l'utilisateur de la base MySQL
     * @param password Correspond au mot de passe de la base MySQL
     * @param port Correspond au port de la base MySQL
     * @param base Correspond au nom de la base MySQL
     * @return Retourne une connexion MySQL
     * @throws java.sql.SQLException Lève une exception si la création de la connexion MySQL n'a pu aboutir
     */
    public static MySQL getInstance(String login, String password, int port, String base) throws java.sql.SQLException{
        return new MySQL(login, password, "127.0.0.1", port, base);
    }
    
    /**
     * Crée une connexion MySQL (ip=localhost, port=3306)
     * @param login Correspond à l'utilisateur de la base MySQL
     * @param password Correspond au mot de passe de la base MySQL
     * @param base Correspond au nom de la base MySQL
     * @return Retourne une connexion MySQL
     * @throws java.sql.SQLException Lève une exception si la création de la connexion MySQL n'a pu aboutir
     */
    public static MySQL getInstance(String login, String password, String base) throws java.sql.SQLException{
        return new MySQL(login, password, "127.0.0.1", 3306, base);
    }
    
    
    
//CLASS
    /**
     * Cette classe contient toutes les méthodes permettant de gérer une table de la base
     * @author JasonPercus
     * @version 1.0
     */
    public class Table {

    
        
    //ATTRIBUTS
        /**
         * Correspond à la classe qui va gérer la donnée d'une table
         */
        public final Data data;

        /**
         * Correspond à la classe qui va gérer les données d'une ou plusieurs tables
         */
        public final Datas datas;

        
        
    //CONSTRUCTOR
        /**
         * Crée un objet Table par défaut
         */
        private Table() {
            data  = MySQL.this.data;
            datas = MySQL.this.datas;
        }
        
        
        
    //METHODES PUBLICS
        /**
         * Renvoie true si la table existe, sinon false
         * @param tableName Correspond au nom de la table que l'on souhaite tester
         * @return Retourne true si la table existe, sinon false
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public boolean exists(String tableName) throws java.sql.SQLException {
            return tables.exists(tableName);
        }

        /**
         * Crée une table dans la base ( = create(String request) )
         * @param request Correspond à la requête de création de la table
         * @throws java.sql.SQLException Lève une exception si la création ne peut être réalisée
         */
        public void add(String request) throws java.sql.SQLException {
            create(request);
        }

        /**
         * Crée une table dans la base
         * @param request Correspond à la requête de création de la table
         * @throws java.sql.SQLException Lève une exception si la création ne peut être réalisée
         */
        public void create(String request) throws java.sql.SQLException {
            prepareStatement(request).execute();
        }
        
        /**
         * Modifie une table existante dans la base ( = alter(String request) )
         * @param request Correspond à la requête de modification de la table
         * @return Retourne le nombre de table modifiées
         * @throws java.sql.SQLException Lève une exception si la modification ne peut être réalisée
         */
        public int set(String request) throws java.sql.SQLException {
            return alter(request);
        }

        /**
         * Modifie une table existante dans la base
         * @param request Correspond à la requête de modification de la table
         * @return Retourne le nombre de table modifiées
         * @throws java.sql.SQLException Lève une exception si la modification ne peut être réalisée
         */
        public int alter(String request) throws java.sql.SQLException {
            return prepareStatement(request).executeUpdate();
        }
        
        /**
         * Supprime une table de la base ( = drop(String request) )
         * @param request Correspond à la requête de suppression de la table
         * @return Retourne true si la table a été supprimé, sinon false
         * @throws java.sql.SQLException Lève une exception si la suppression ne peut être réalisée
         */
        public boolean delete(String request) throws java.sql.SQLException{
            return drop(request);
        }

        /**
         * Supprime une table de la base
         * @param request Correspond à la requête de suppression de la table
         * @return Retourne true si la table a été supprimé, sinon false
         * @throws java.sql.SQLException Lève une exception si la suppression ne peut être réalisée
         */
        public boolean drop(String request) throws java.sql.SQLException {
            return prepareStatement(request).execute();
        }
        
        /**
         * Renvoie les noms de colonnes
         * @param tableName Correspond au nom de la table dont on cherche les noms de colonnes
         * @return Retourne les noms de colonnes
         * @throws java.sql.SQLException Lève une exception si la suppression ne peut être réalisée
         */
        public String[] getColumnNames(String tableName) throws java.sql.SQLException {
            return datas.select(String.format("SELECT * FROM `%s`.`%s` LIMIT 1;", base, tableName)).getColumnNames();
        }
        
        
        
    }
    
    /**
     * Cette classe contient toutes les méthodes permettant de gérer les tables de la base
     * @author JasonPercus
     * @version 1.0
     */
    public class Tables {

    
        
    //ATTRIBUTS
        /**
         * Correspond à la classe qui va gérer la donnée d'une table
         */
        public final Data data;

        /**
         * Correspond à la classe qui va gérer les données d'une ou plusieurs tables
         */
        public final Datas datas;

        
        
    //CONSTRUCTOR
        /**
         * Crée un objet Tables par défaut
         */
        private Tables() {
            data  = MySQL.this.data;
            datas = MySQL.this.datas;
        }
        
        
        
    //METHODES PUBLICS
        /**
         * Renvoie true si la table existe, sinon false
         * @param tableName Correspond au nom de la table que l'on souhaite tester
         * @return Retourne true si la table existe, sinon false
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public boolean exists(String tableName) throws java.sql.SQLException {
            String request = String.format("SELECT COUNT(*) AS EXIST FROM information_schema.tables WHERE table_schema='%s' AND table_name='%s';", base, tableName);
            QueryResult results = get(request);
            while (results.next())
                return results.getBoolean("EXIST");
            return false;
        }

        /**
         * Renvoie la liste des tables de la base
         * @return Retourne la liste des tables de la base
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public java.util.List<String> getList() throws java.sql.SQLException {
            java.util.List<String> tables = new java.util.ArrayList<>();
            String request = String.format("SHOW TABLES FROM `%s`;", base);
            QueryResult results = get(request);
            while (results.next())
                tables.add(results.getString(0));
            return tables;
        }

        /**
         * Renvoie le nombre de tables comprises dans la base
         * @return Retourne le nombre de tables comprises dans la base
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public int count() throws java.sql.SQLException {
            return getList().size();
        }
        
        /**
         * Renvoie le résultat de la requête ( = select(String request) )
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne le résultat de la requête
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public QueryResult get(String request) throws java.sql.SQLException {
            return datas.select(request);
        }
        
        /**
         * Renvoie le résultat de la requête
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne le résultat de la requête
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public QueryResult select(String request) throws java.sql.SQLException {
            return datas.select(request);
        }
        
        
        
    }
    
    /**
     * Cette classe contient toutes les méthodes permettant de gérer une donné d'une table
     * @author JasonPercus
     * @version 1.0
     */
    public class Data {

        
        
    //CONSTRUCTOR
        /**
         * Crée un objet Data par défaut
         */
        private Data() {}
        
        
        
    //METHODES PUBLICS
        /**
         * Ajoute une entrée dans une table ( = insertInto(String request) )
         * @param request Correspond à la requête d'insertion d'une entrée dans la table
         * @return Retourne true si l'entrée a bien été ajouté
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public boolean add(String request) throws java.sql.SQLException {
            return insertInto(request);
        }
        
        /**
         * Ajoute une entrée dans une table
         * @param request Correspond à la requête d'insertion d'une entrée dans la table
         * @return Retourne true si l'entrée a bien été ajouté
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public boolean insertInto(String request) throws java.sql.SQLException {
            return prepareStatement(request).execute();
        }
        
        /**
         * Modifie une entrée dans une table ( = update(String request) )
         * @param request Correspond à la requête de mise à jour d'une entrée dans la table
         * @return Retourne le nombre d'entrées modifiées
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public int set(String request) throws java.sql.SQLException {
            return datas.update(request);
        }
        
        /**
         * Modifie une entrée dans une table
         * @param request Correspond à la requête de mise à jour d'une entrée dans la table
         * @return Retourne le nombre d'entrées modifiées
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public int update(String request) throws java.sql.SQLException {
            return datas.update(request);
        }
        
        /**
         * Supprime une entrée dans une table
         * @param request Correspond à la requête de suppression d'une entrée dans la table
         * @return Retourne le nombre d'entrées supprimée
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public int delete(String request) throws java.sql.SQLException {
            return datas.delete(request);
        }
        
        /**
         * Renvoie le résultat de la requête ( = select(String request) )
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne le résultat de la requête
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public QueryResult get(String request) throws java.sql.SQLException {
            return datas.select(request);
        }
        
        /**
         * Renvoie le résultat de la requête
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne le résultat de la requête
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public QueryResult select(String request) throws java.sql.SQLException {
            return datas.select(request);
        }
        
        /**
         * Renvoie l'objet créé à partir d'un select d'une table
         * @param <O> Correspond au type de l'objet à renvoyer
         * @param c Correspond à la classe de l'objet que l'on veut obtenir
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne l'objet créé à partir d'un select d'une table
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public <O> O get(Class c, String request) throws java.sql.SQLException {
            return select(request).get(c);
        }
        
        /**
         * Renvoie l'objet créé à la nième place à partir d'un select d'une table
         * @param <O> Correspond au type de l'objet à renvoyer
         * @param c Correspond à la classe de l'objet que l'on veut obtenir
         * @param request Correspond à la requête de sélection de la table
         * @param index Correspond à la position de l'objet à récupérer dans le résultat
         * @return Retourne l'objet créé à la nième place à partir d'un select d'une table
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public <O> O get(Class c, String request, int index) throws java.sql.SQLException {
            return datas.get(c, request, index);
        }
        
        /**
         * Renvoie le premier objet créé à partir d'un select d'une table
         * @param <O> Correspond au type de l'objet à renvoyer
         * @param c Correspond à la classe de l'objet que l'on veut obtenir
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne le premier objet créé à partir d'un select d'une table
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public <O> O getFirst(Class c, String request) throws java.sql.SQLException {
            return datas.getFirst(c, request);
        }
        
        /**
         * Renvoie le dernier objet créé à partir d'un select d'une table
         * @param <O> Correspond au type de l'objet à renvoyer
         * @param c Correspond à la classe de l'objet que l'on veut obtenir
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne le dernier objet créé à partir d'un select d'une table
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public <O> O getLast(Class c, String request) throws java.sql.SQLException {
            return datas.getLast(c, request);
        }
        
        
        
    }
    
    /**
     * Cette classe contient toutes les méthodes permettant de gérer les donnée d'une table
     * @author JasonPercus
     * @version 1.0
     */
    public class Datas {

        
        
    //CONSTRUCTOR
        /**
         * Crée un objet Datas par défaut
         */
        private Datas() {}
        
        
        
    //METHODES PUBLICS
        /**
         * Modifie une entrée dans une table ( = update(String request) )
         * @param request Correspond à la requête de mise à jour d'une entrée dans la table
         * @return Retourne le nombre d'entrées modifiées
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public int set(String request) throws java.sql.SQLException {
            return update(request);
        }
        
        /**
         * Modifie une entrée dans une table
         * @param request Correspond à la requête de mise à jour d'une entrée dans la table
         * @return Retourne le nombre d'entrées modifiées
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public int update(String request) throws java.sql.SQLException {
            return prepareStatement(request).executeUpdate();
        }
        
        /**
         * Supprime une entrée dans une table
         * @param request Correspond à la requête de suppression d'une entrée dans la table
         * @return Retourne le nombre d'entrées supprimée
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public int delete(String request) throws java.sql.SQLException {
            return prepareStatement(request).executeUpdate();
        }
        
        /**
         * Renvoie le résultat de la requête ( = select(String request) )
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne le résultat de la requête
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public QueryResult get(String request) throws java.sql.SQLException {
            return select(request);
        }
        
        /**
         * Renvoie le résultat de la requête
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne le résultat de la requête
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public QueryResult select(String request) throws java.sql.SQLException {
            return new QueryResult(createStatement().executeQuery(request));
        }
        
        /**
         * Renvoie une liste d'objets créé à partir d'un select d'une table
         * @param <O> Correspond au type d'objets à renvoyer
         * @param c Correspond à la classe des objets que l'on veut obtenir
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne la liste des objets créé à partir d'un select d'une table
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public <O> java.util.List<O> getList(Class c, String request) throws java.sql.SQLException {
            return select(request).getList(c);
        }
        
        /**
         * Renvoie l'objet créé à la nième place à partir d'un select d'une table
         * @param <O> Correspond au type de l'objet à renvoyer
         * @param c Correspond à la classe de l'objet que l'on veut obtenir
         * @param request Correspond à la requête de sélection de la table
         * @param index Correspond à la position de l'objet à récupérer dans le résultat
         * @return Retourne l'objet créé à la nième place à partir d'un select d'une table
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public <O> O get(Class c, String request, int index) throws java.sql.SQLException {
            return select(request).get(c, index);
        }
        
        /**
         * Renvoie le premier objet créé à partir d'un select d'une table
         * @param <O> Correspond au type de l'objet à renvoyer
         * @param c Correspond à la classe de l'objet que l'on veut obtenir
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne le premier objet créé à partir d'un select d'une table
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public <O> O getFirst(Class c, String request) throws java.sql.SQLException {
            return select(request).getFirst(c);
        }
        
        /**
         * Renvoie le dernier objet créé à partir d'un select d'une table
         * @param <O> Correspond au type de l'objet à renvoyer
         * @param c Correspond à la classe de l'objet que l'on veut obtenir
         * @param request Correspond à la requête de sélection de la table
         * @return Retourne le dernier objet créé à partir d'un select d'une table
         * @throws java.sql.SQLException Lève une exception si le résultat de la demande ne peut être obtenu
         */
        public <O> O getLast(Class c, String request) throws java.sql.SQLException {
            return select(request).getLast(c);
        }
        
        
        
    }
    
    
    
}