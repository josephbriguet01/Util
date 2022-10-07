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
 * Cette classe représente la même chose qu'un java.sql.ResultSet, mais à la différence, c'est que lorsque la connexion est fermée, les résultats dans QueryResult restent vivant.
 * Elle permet également de transformer chaque ligne d'un résultat en objet java par l'intermédiaire de l'annotation @Column
 * @author JasonPercus
 * @version 1.0
 */
public class QueryResult {

    
    
//ATTRIBUTS
    /**
     * Correspond à la liste des noms de colonnes dans les résultats
     */
    private final String[] labels;
    
    /**
     * Correspond aux nouveau résultats sauvegardés
     */
    private final java.util.List<Object[]> table;
    
    /**
     * Correspond au curseur qui point un élément dans les résultats obtenus
     */
    private int cursor;
    
    /**
     * Détermine si les caractères unicodes doivent être utilisé
     */
    private boolean useUnicode;
    
    
    
//CONSTRUCTOR
    /**
     * Crée un objet ResultSelect
     * @param rs Correspond aux résultats récupérés de la base
     * @throws java.sql.SQLException Lève une exception si l'extraction des données est impossible
     */
    public QueryResult(java.sql.ResultSet rs) throws java.sql.SQLException {
        this.table = new java.util.ArrayList<>();
        this.cursor = -1;
        java.sql.ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        labels = new String[columnsNumber];
        for(int i=0;i<columnsNumber;i++){
            labels[i] = rsmd.getColumnLabel(i+1);
        }
        while (rs.next()) {
            Object[] line = new Object[columnsNumber];
            for(int i=0;i<columnsNumber;i++){
                line[i] = rs.getObject(i+1);
            }
            table.add(line);
        }
        rs.close();
    }
    
    
    
//METHODE PUBLIC
    /**
     * Positionne le curseur sur le suivant élément des résultats
     * @return Retourne true si le suivant élément existe bien
     */
    public boolean next(){
        cursor++;
        if(cursor >= table.size()){
            cursor = -1;
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Détermine si oui ou non les valeurs qui seront récupérés des cellules du tableau sont en unicode
     * @return Retourne true si elle le sont, sinon false
     */
    public boolean useUnicode(){
        return this.useUnicode;
    }
    
    /**
     * Modifie si oui ou non les valeurs qui seront récupérés des cellules du tableau doivent être en unicode
     * @param useUnicode Correspond à true si on souhaite qu'elles le soient, sinon false
     */
    public void useUnicode(boolean useUnicode){
        this.useUnicode = useUnicode;
    }
    
    /**
     * Renvoie les noms de colonnes
     * @return Retourne les noms de colonnes
     */
    public String[] getColumnNames(){
        return this.labels;
    }
    
    /**
     * Renvoie une liste d'objets créé à partir d'un select d'une table
     * @param <O> Correspond au type d'objets à renvoyer
     * @param c Correspond à la classe des objets que l'on veut obtenir
     * @return Retourne la liste des objets créé à partir d'un select d'une table
     */
    public <O> java.util.List<O> getList(Class c){
        java.util.List<O>       ret   = new java.util.ArrayList<>();
        java.util.List<String>  names = new java.util.ArrayList<>();
        java.lang.reflect.Field[] fields = c.getDeclaredFields();
        
        for(java.lang.reflect.Field field : fields){
            java.lang.annotation.Annotation annotation = field.getAnnotation(Column.class);
            if(annotation != null){
                Column column = (Column) annotation;
                String  name = column.name();
                names.add(name);
            }
        }
        
        while(next()){
            try {
                boolean hasOperated = false;
                O o = (O) c.newInstance();
                int sizeTable = table.get(cursor).length;
                for(int i=0;i<sizeTable;i++){
                    String nameColumn = labels[i];
                    int indexof = names.indexOf(nameColumn);
                    if(indexof > -1){
                        caster(o, i, nameColumn);
                        hasOperated = true;
                    }
                }
                if(hasOperated)
                    ret.add(o);
            } catch (InstantiationException | IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(QueryResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        return ret;
    }
    
    /**
     * Renvoie l'objet créé à la nième place à partir d'un select d'une table
     * @param <O> Correspond au type de l'objet à renvoyer
     * @param c Correspond à la classe de l'objet que l'on veut obtenir
     * @param index Correspond à la position de l'objet à récupérer dans le résultat
     * @return Retourne l'objet créé à la nième place à partir d'un select d'une table
     */
    public <O> O get(Class c, int index){
        java.util.List<O> list = getList(c);
        if(list == null || list.isEmpty() || index < 0) return null;
        return list.get(index);
    }
    
    /**
     * Renvoie le premier objet créé à partir d'un select d'une table
     * @param <O> Correspond au type de l'objet à renvoyer
     * @param c Correspond à la classe de l'objet que l'on veut obtenir
     * @return Retourne le premier objet créé à partir d'un select d'une table
     */
    public <O> O getFirst(Class c){
        return get(c, 0);
    }
    
    /**
     * Renvoie le dernier objet créé à partir d'un select d'une table
     * @param <O> Correspond au type de l'objet à renvoyer
     * @param c Correspond à la classe de l'objet que l'on veut obtenir
     * @return Retourne le dernier objet créé à partir d'un select d'une table
     */
    public <O> O getLast(Class c){
        return get(c, size()-1);
    }
    
    /**
     * Renvoie l'objet créé à partir d'un select d'une table
     * @param <O> Correspond au type de l'objet à renvoyer
     * @param c Correspond à la classe de l'objet que l'on veut obtenir
     * @return Retourne l'objet créé à partir d'un select d'une table
     */
    public <O> O get(Class c){
        return getFirst(c);
    }
    
    /**
     * Renvoie le nombre de colonnes du résultat
     * @return Retourne le nombre de colonnes du résultat
     */
    public int length(){
        return labels.length;
    }
    
    /**
     * Renvoie le nombre de lignes du résultat
     * @return Retourne le nombre de lignes du résultat
     */
    public int size(){
        return table.size();
    }
    
    /**
     * Renvoie le nombre de colonnes du résultat
     * @return Retourne le nombre de colonnes du résultat
     */
    public int countCols(){
        return length();
    }
    
    /**
     * Renvoie le nombre de lignes du résultat
     * @return Retourne le nombre de lignes du résultat
     */
    public int countLines(){
        return size();
    }
    
    
    
//METHODES SQL java.sql.ResultSet
    /**
     * Renvoie une référence à un tableau sql
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne une référence à un tableau sql
     */
    public java.sql.Array getArray(String columnLabel){
        return (java.sql.Array) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie une référence à un tableau sql
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne une référence à un tableau sql
     */
    public java.sql.Array getArray(int columnIndex){
        return (java.sql.Array) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un flux de caractères ASCII
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un flux de caractères ASCII
     */
    public java.io.InputStream getAsciiStream(String columnLabel){
        return (java.io.InputStream) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un flux de caractères ASCII
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un flux de caractères ASCII
     */
    public java.io.InputStream getAsciiStream(int columnIndex){
        return (java.io.InputStream) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un grand nombre
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un grand nombre
     */
    public java.math.BigDecimal getBigDecimal(String columnLabel){
        return (java.math.BigDecimal) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un grand nombre
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un grand nombre
     */
    public java.math.BigDecimal getBigDecimal(int columnIndex){
        return (java.math.BigDecimal) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un flux binaire
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un flux binaire
     */
    public java.io.InputStream getBinaryStream(String columnLabel){
        return (java.io.InputStream) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un flux binaire
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un flux binaire
     */
    public java.io.InputStream getBinaryStream(int columnIndex){
        return (java.io.InputStream) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un blob sql
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un blob sql
     */
    public java.sql.Blob getBlob(String columnLabel){
        return (java.sql.Blob) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un blob sql
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un blob sql
     */
    public java.sql.Blob getBlob(int columnIndex){
        return (java.sql.Blob) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un boolean
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un boolean
     */
    public boolean getBoolean(String columnLabel){
        Object o = table.get(getCursor())[searchIndexColumn(columnLabel)];
        if(o instanceof Boolean) return (boolean)o;
        else if(o instanceof Integer) return (((int)o) == 1);
        else return (((long)o) == 1);
    }
    
    /**
     * Renvoie un boolean
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un boolean
     */
    public boolean getBoolean(int columnIndex){
        Object o = table.get(getCursor())[columnIndex-1];
        if(o instanceof Boolean) return (boolean)o;
        else if(o instanceof Integer) return (((int)o) == 1);
        else return (((long)o) == 1);
    }
    
    /**
     * Renvoie un byte
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un byte
     */
    public byte getByte(String columnLabel){
        return (byte) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un byte
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un byte
     */
    public byte getByte(int columnIndex){
        return (byte) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un tableau de bytes
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un tableau de bytes
     */
    public byte[] getBytes(String columnLabel){
        return (byte[]) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un tableau de bytes
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un tableau de bytes
     */
    public byte[] getBytes(int columnIndex){
        return (byte[]) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un flux de caractères
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un flux de caractères
     */
    public java.io.Reader getCharacterStream(String columnLabel){
        return (java.io.Reader) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un flux de caractères
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un flux de caractères
     */
    public java.io.Reader getCharacterStream(int columnIndex){
        return (java.io.Reader) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un clob sql
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un clob sql
     */
    public java.sql.Clob getClob(String columnLabel){
        return (java.sql.Clob) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un clob sql
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un clob sql
     */
    public java.sql.Clob getClob(int columnIndex){
        return (java.sql.Clob) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie une date sql
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne une date sql
     */
    public java.sql.Date getDate(String columnLabel){
        return (java.sql.Date) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie une date sql
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne une date sql
     */
    public java.sql.Date getDate(int columnIndex){
        return (java.sql.Date) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un double
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un double
     */
    public double getDouble(String columnLabel){
        return (double) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un double
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un double
     */
    public double getDouble(int columnIndex){
        return (double) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un float
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un float
     */
    public float getFloat(String columnLabel){
        return (float) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un float
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un float
     */
    public float getFloat(int columnIndex){
        return (float) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un entier
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un entier
     */
    public int getInt(String columnLabel){
        return (int) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un entier
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un entier
     */
    public int getInt(int columnIndex){
        return (int) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un long
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un long
     */
    public long getLong(String columnLabel){
        return (long) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un long
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un long
     */
    public long getLong(int columnIndex){
        return (long) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un flux de caractères
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un flux de caractères
     */
    public java.io.Reader getNCharacterStream(String columnLabel){
        return (java.io.Reader) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un flux de caractères
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un flux de caractères
     */
    public java.io.Reader getNCharacterStream(int columnIndex){
        return (java.io.Reader) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un clob sql
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un clob sql
     */
    public java.sql.NClob getNClob(String columnLabel){
        return (java.sql.NClob) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un clob sql
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un clob sql
     */
    public java.sql.NClob getNClob(int columnIndex){
        return (java.sql.NClob) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie une chaine de caractères
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne une chaine de caractères
     */
    public String getNString(String columnLabel){
        return (String) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie une chaine de caractères
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne une chaine de caractères
     */
    public String getNString(int columnIndex){
        return (String) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un objet
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un objet
     */
    public Object getObject(String columnLabel){
        return table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un objet
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un objet
     */
    public Object getObject(int columnIndex){
        return table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie une référence sql
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne une référence sql
     */
    public java.sql.Ref getRef(String columnLabel){
        return (java.sql.Ref) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie une référence sql
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne une référence sql
     */
    public java.sql.Ref getRef(int columnIndex){
        return (java.sql.Ref) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un numéro d'id de ligne
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un numéro d'id de ligne
     */
    public java.sql.RowId getRowId(String columnLabel){
        return (java.sql.RowId) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un numéro d'id de ligne
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un numéro d'id de ligne
     */
    public java.sql.RowId getRowId(int columnIndex){
        return (java.sql.RowId) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un xml
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un xml
     */
    public java.sql.SQLXML getSQLXML(String columnLabel){
        return (java.sql.SQLXML) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un xml
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un xml
     */
    public java.sql.SQLXML getSQLXML(int columnIndex){
        return (java.sql.SQLXML) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie un short
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne un short
     */
    public short getShort(String columnLabel){
        return (short) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie un short
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne un short
     */
    public short getShort(int columnIndex){
        return (short) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie une chaîne de caractères
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne une chaîne de caractères
     */
    public String getString(String columnLabel){
        return (String) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie une chaîne de caractères
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne une chaîne de caractères
     */
    public String getString(int columnIndex){
        return (String) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie une heure sql
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne une heure sql
     */
    public java.sql.Time getTime(String columnLabel){
        return (java.sql.Time) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie une heure sql
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne une heure sql
     */
    public java.sql.Time getTime(int columnIndex){
        return (java.sql.Time) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie une date et une heure sql
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne une date et une heure sql
     */
    public java.sql.Timestamp getTimestamp(String columnLabel){
        return (java.sql.Timestamp) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie une date et une heure sql
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne une date et une heure sql
     */
    public java.sql.Timestamp getTimestamp(int columnIndex){
        return (java.sql.Timestamp) table.get(getCursor())[columnIndex-1];
    }
    
    /**
     * Renvoie une url
     * @param columnLabel Correspond au nom de la colonne sql visée
     * @return Retourne une url
     */
    public java.net.URL getURL(String columnLabel){
        return (java.net.URL) table.get(getCursor())[searchIndexColumn(columnLabel)];
    }
    
    /**
     * Renvoie une url
     * @param columnIndex Correspond au numéro de la colonne sql visée
     * @return Retourne une url
     */
    public java.net.URL getURL(int columnIndex){
        return (java.net.URL) table.get(getCursor())[columnIndex-1];
    }
    
    
    
//METHODE PRIVATE
    /**
     * Recherche la position d'une colonne
     * @param columnLabel Correspond au nom de la colonne
     * @return Retourne la position de la colonne ou -1 si celle-ci n'a pas été trouvée
     */
    private int searchIndexColumn(String columnLabel){
        for(int i=0;i<labels.length;i++){
            if(labels[i].equals(columnLabel))
                return i;
        }
        return -1;
    }
    
    /**
     * Renvoie la position du cursor. Si celui-ci est à -1 alors qu'il y a des données, c'est que l'utilisateur n'a pas fait de next(), certainement car il n'y a qu'un seul résultat.
     * @return Retourne la position du cursor
     */
    private int getCursor(){
        int c = cursor;
        if(cursor == -1 && !table.isEmpty()) c = 0;
        return c;
    }
    
    /**
     * Cast un objet selon le type d'un attribut(le indexColumn) de O et initialise ce cast dans l'objet O
     * @param <O> Correspond au type d'objet créé et modifié qui sera renvoyé
     * @param line Correspond à l'objet fraichement créé dont ont cherche à initialiser les attributs
     * @param indexColumn Correspond au numéro de colonne de l'objet que l'on va caster
     * @param columnName Correspond au nom de la colonne de l'objet que l'on va caster
     */
    private <O> void caster(O line, int indexColumn, String columnName){
        java.lang.reflect.Field[] fields = line.getClass().getDeclaredFields();
        
        for(java.lang.reflect.Field field : fields){
            java.lang.annotation.Annotation annotation = field.getAnnotation(Column.class);
            if(annotation != null){
                Column column = (Column) annotation;
                String name   = column.name();
                if(name.equals(columnName)){
                    field.setAccessible(true);
                    try {
                        if(field.getType().isAssignableFrom(Byte.class)){
                            field.setByte(line, getByte(indexColumn+1));
                        } else if(field.getType().isAssignableFrom(Short.class)){
                            field.setShort(line, getShort(indexColumn+1));
                        } else if(field.getType().isAssignableFrom(Integer.class)){
                            field.setInt(line, getInt(indexColumn+1));
                        } else if(field.getType().isAssignableFrom(Long.class)){
                            field.setLong(line, getLong(indexColumn+1));
                        } else if(field.getType().isAssignableFrom(Float.class)){
                            field.setFloat(line, getFloat(indexColumn+1));
                        } else if(field.getType().isAssignableFrom(Double.class)){
                            field.setDouble(line, getDouble(indexColumn+1));
                        } else if(field.getType().isAssignableFrom(Boolean.class)){
                            field.setBoolean(line, getBoolean(indexColumn+1));
                        } else if(field.getType().isAssignableFrom(java.math.BigDecimal.class)){
                            field.set(line, getBigDecimal(indexColumn+1));
                        } else if(field.getType().isAssignableFrom(byte[].class)){
                            field.set(line, getBytes(indexColumn+1));
                        } else if(field.getType().isAssignableFrom(java.util.Date.class)){
                            try{
                                java.sql.Date date = getDate(indexColumn+1);
                                if(date != null)
                                    field.set(line, new java.util.Date(date.getTime()));
                                else
                                    field.set(line, null);
                            }catch(java.lang.ClassCastException e){
                                java.sql.Timestamp timestamp = getTimestamp(indexColumn+1);
                                if(timestamp != null)
                                    field.set(line, new java.util.Date(timestamp.getTime()));
                                else
                                    field.set(line, null);
                            }
                        } else if(field.getType().isAssignableFrom(java.sql.Date.class)){
                            field.set(line, getDate(indexColumn+1));
                        } else if(field.getType().isAssignableFrom(String.class)){
                            if(useUnicode)
                                field.set(line, getNString(indexColumn+1));
                            else
                                field.set(line, getString(indexColumn+1));
                        } else if(field.getType().isAssignableFrom(java.net.URL.class)){
                            field.set(line, getURL(indexColumn+1));
                        } else {
                            field.set(line, getObject(indexColumn+1));
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(QueryResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    
    
}