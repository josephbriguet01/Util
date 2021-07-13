﻿Copyright © JasonPercus Systems, Inc - All Rights Reserved
# **Introduction**

Cette librairie apporte une multitude de fonctions utiles permettant tout et n’importe quoi. Vous souhaitez faire des requêtes vers une base MySQL ? Dé/Chiffrer du texte ? Ou encore connaitre le nom des cartes réseaux d’un PC ? Alors cette librairie est faite pour vous. De plus elle intègre en son sein 5 projets connues. À savoir :

- gson-2.8.2
- javaparser-core-3.22.1
- mysql-connector-java-5.1.23-bin
- jna-5.6.0
- jna-platform-5.6.0

D’ailleurs les projets ci-dessus sont notamment très utilisés par les diverses classes du projet Util.

# 1. **Annotations processeurs**
Les annotations processeurs sont des annotations qui seront analysées par le compilateur lorsqu'il sera en train de compiler un bout de code annoté.
## 1.1. Comment déclarer les annotations sur Netbeans

Pour que les annotations processeurs soient prises en compte, il faut:

- Aller dans les propriétés du projet Netbeans,
- Puis dans la catégorie compilation,
- Activer l’option « Enable Annotation Processing »,
- Et « Enable Annotation Processing in Editor »,
- Puis « OK ».

A partir de ce moment lorsqu’une bibliothèque ou projet sera ajouté au classpath du projet, les annotations de ceux-là seront automatiquement prises en compte.

> *Remarque : Ce tuto a été réalisé sur la version 8.1 (Build 201511021428) Francophone de Netbeans. Ce qui signifie qu'avec une version différente, il peut y avoir des différences.*

## 1.2. Comment déclarer les annotations sur AndroidStudio

Pour que les annotations processeurs soient prises en compte, il faut:

**Premièrement**
- Lorsque l'on choisit le projet à ouvrir (tous les projets doivent être fermés), aller dans « Configure » (en bas à droite),
- Puis « Settings »,
- Sélectionner la catégorie « Build, Execution, Deployment »,
- Puis la sous-catégorie « Compiler »,
- Et enfin « Annotation Processors »,
- Activer l'option « Enable annotation processing »,
- Puis « OK ». C'est seulement à partir de ce moment que vous pouvez crééer ou ouvrir un projet Android.

**Deuxièmement**
- Aller dans le menu « File » puis « Project Structure... » (Ctrl+Alt+Maj+S),
- Puis dans la catégorie « Dependencies »,
- Sélectionner « app » dans la section « Modules »,
- Puis ajouter une dépendance en prenant en compte que dans la « Step 2. » de l'ajout, ```implementation``` doit être remplacé par ```annotationProcessor```,
- Puis « OK », « Apply » et terminer avec « OK ».

A partir de ce moment le projet android reconnaitra seulement les annotations processeurs de la nouvelle dépendence.
> *Attention: Ce n'est pas parce que les annotations processeurs sont reconnues que le code de la dépendence est reconnue. Pour ce faire recommencer le tuto, mais en ajoutant ```implementation``` au lieur de ```annotationProcessor```.*

> *Remarque : Ce tuto a été réalisé sur la version 4.2.1 (Build #AI-202.7660.26.42.7351085) d'Android Studio. Ce qui signifie qu'avec une version différente, il peut y avoir des différences.*

## 1.3. @Builder

L'annotation ```@Builder``` d'une classe, à pour fonction de créer à la volée (lors de la compilation), une nouvelle classe permettant de créer et modifier facilement la classe annotée. Prenons un exemple:

```java
@Builder
public class Personnage {
    
    private String nom;
    private String prenom;
    private int age;

    public Personnage() {
    
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
}
```
> Il s'agit ci-dessus de la classe annotée

Nous aimerions à présent créer un ```Personnage```, et le modifier sur la même ligne à partir de ses setters. Avec quelque chose qui ressemble à ça
```java
Personnage p = new PersonnageBuilder().setPrenom("Martin").setNom("Durant").setAge(27).build();
```
Malheureusement, nous n'avons pas créé encore de classe ```PersonnageBuilder```. D'où l'intérêt de l'annotation ```@Builder```. Celle-ci va la crééer automatiquement à la compilation du projet. Elle se trouvera dans le même package que la classe ```Personnage```. Voici à quoi elle peut ressembler:
```java
public class PersonnageBuilder {

    private final Personnage obj = new Personnage();

    public Personnage build() {
        return obj;
    }

    public PersonnageBuilder setNom(String nom) {
        obj.setNom(nom);
        return this;
    }

    public PersonnageBuilder setPrenom(String prenom) {
        obj.setPrenom(prenom);
        return this;
    }

    public PersonnageBuilder setAge(int age) {
        obj.setAge(age);
        return this;
    }
    
}
```
Il existe plusieurs façon d'utiliser l'annotation ```@Builder```. Tout d'abord, elle ne fonctionne que sur des classes qui ne sont pas `abstract`, `private` et `strictfp`. Voici ses 3 modes d'utilisations:
- ```@Builder(mode = Builder.Mode.IF_NOT_EXISTS)``` *(par défaut)* Cela peut s'écrire également ```@Builder```. Ce mode signifie qu'une fois la classe à générer existe, aucune modification ne sera effectué aux compilations suivantes.
- ```@Builder(mode = Builder.Mode.ALWAYS_OVERWRITE)```. Ce mode signifie qu'à chaque compilation, la classe sera regénérée.
- ```@Builder(mode = Builder.Mode.DELETE)```. Ce mode signifie qu'à la prochaine compilation la classe générée sera supprimée.
## 1.4. @Showing

L'annotation ```@Showing``` a pour fonction de restructurer correctement le code. Sa fonction ne sert qu'à une meilleure visibilité possible du code. Cette annotation fonctionne pour les classes, les interfaces, les énumérations et les interfaces d'annotation.

Il existe plusieurs façon de l'utiliser:

- ```@Showing(blocs = Showing.Bloc.COLLAPSE)```, lorsque l'on veut que le code soit représenté par plusieurs blocs repliables,
- ```@Showing(blocs = Showing.Bloc.NO_COLLAPSE)``` *(par défaut)*, lorsque l'on veut que le code soit représenté par plusieurs blocs délimités par une ligne de commentaire,
- ```@Showing(state = Showing.State.COLLAPSED)``` *(par défaut)*, lorsque l'on veut que les blocs repliables se replient d'eux même,
- ```@Showing(state = Showing.State.UNCOLLAPSED)```, lorsque l'on veut que les blocs repliables ne se replient pas d'eux même,
- ```@Showing(javadoc = Showing.Javadoc.WARNING)``` *(par défaut)*, lorsque l'on veut que le compilateur affiche les attributs, méthodes... qui n'ont pas de documentation
- ```@Showing(javadoc = Showing.Javadoc.NO_WARNING)```, lorsque l'on veut que le compilateur n'affiche pas les attributs, méthodes... qui n'ont pas de documentation
> Note: Il est possible de combiner plusieurs modes entre-eux. Ex: ```@Showing(blocs = Showing.Bloc.COLLAPSE, state = Showing.State.UNCOLLAPSED, javadoc = Showing.Javadoc.NO_WARNING)```

# 2. **Dé/Chiffrement**
La librairie Util permet entre-autre de dé/chiffrer des textes/tableau de bytes.
## 2.1. Base
Cette classe n'a pas de réelle fonction pour le développeur lambda. Elle est la classe mère de la classe ```Base64```. Mais elle peut servir pour créer d'autre base comme par exemple la base 32. Exemple, pour créer une base 32:
```java
Base base32 = new Base(32);
```
## 2.2. Base64
Cette classe est très utilisée pour convertir un tableau de bytes en texte visible par l'utilisateur et vice-versa. Voici un exemple de son utilisation:
```java
//Création d'un objet Base64
Base64 base64 = new Base64();

//Il s'agit de mon texte à chiffrer en base64
byte[] array = "Mon texte converti en bytes".getBytes();

//Chiffrement & déchiffrement
String encrypted = base64.toString(array);
byte[] decrypted = base64.toBytes(encrypted);

//Affichages des résultats
System.out.println("Encrypted: " + encrypted);
System.out.println("Decrypted: " + new String(decrypted));
```
Nous obtenons ce résultat
```
Encrypted: TW9uIHRleHRlIGNvbnZlcnRpIGVuIGJ5dGVz
Decrypted: Mon texte converti en bytes
```
Il existe plusieurs types de chiffrement Base64:
- ```BASE```: Lorsque l'on souhaite dé/chiffrer de manière classique (sur une ligne et sans espace)
- ```WITH_LINE_BREAK```: Lorsque l'on souhaite dé/chiffrer sur plusieurs lignes et sans espace
- ```WITH_SPACE```: Lorsque l'on souhaite dé/chiffrer sur une seule ligne mais avec des espaces
- ```WITH_LINE_BREAK_AND_SPACE```: Lorsque l'on souhaite dé/chiffrer sur plusieurs lignes et avec des espaces
> Attention: Ce système Base64 n'est pas compatibles avec les systèmes existants sur le marché. Autrement dit, vous ne pouvez pas chiffrer avec la classe ```java.util.Base64``` et déchiffrer avec la classe ```com.jasonpercus.encryption.base64.Base64``` et inversement.

## 2.3. RSA
Cette classe sert pour chiffrer du texte ou tableau de bytes en tableau chiffré de bytes et inversement déchiffrer un tableau de bytes en texte. Elle s'appuie sur l'algorithme existant RSA. Voici un exemple de son utilisation:
```java
//Création d'un objet RSA
Cipher rsa = new RSA();

//Création d'une clée publique et une clée privée
Key publicKey  = rsa.generatePublicKey();
Key privateKey = rsa.generatePrivateKey();

//Il s'agit de mon texte à chiffrer avec RSA
byte[] toEncrypt =  "Mon texte converti en bytes".getBytes();

//Chiffrement & déchiffrement
byte[] encrypted = rsa.encrypt(publicKey, toEncrypt);
byte[] decrypted = rsa.decrypt(privateKey, encrypted);

//Affichages des résultats
System.out.println("Encrypted: " + new String(encrypted));
System.out.println("Decrypted: " + new String(decrypted));
```
Nous obtenons ce résultat
```
Encrypted: �3�d��t0��%�V�춠�:G�D#��Q�m�����o��b��QEZ37U q�ݨ3
Decrypted: Mon texte converti en bytes
```

> Attention: Ce système RSA n'est pas compatibles avec les systèmes existants sur le marché. Autrement dit, vous ne pouvez pas chiffrer avec la classe ```java.util.RSA``` et déchiffrer avec la classe ```com.jasonpercus.encryption.rsa.RSA``` et inversement.

## 2.4. AES
Cette classe sert pour chiffrer du texte ou tableau de bytes en tableau chiffré de bytes et inversement déchiffrer un tableau de bytes en texte. Elle s'appuie sur l'algorithme existant AES. Voici un exemple de son utilisation:
```java
//Création d'un objet AES
Cipher aes = new AES();

//Création d'une clée
Key key = aes.generateKey();

//Il s'agit de mon texte à chiffrer avec AES
byte[] toEncrypt =  "Mon texte converti en bytes".getBytes();

//Chiffrement & déchiffrement
byte[] encrypted = aes.encrypt(key, toEncrypt);
byte[] decrypted = aes.decrypt(key, encrypted);

//Affichages des résultats
System.out.println("Encrypted: " + new String(encrypted));
System.out.println("Decrypted: " + new String(decrypted));
```
Nous obtenons ce résultat
```
Encrypted: ��d�8X-��]���e������daR
Decrypted: Mon texte converti en bytes
```

> Attention: Ce système AES n'est pas compatibles avec les systèmes existants sur le marché. Autrement dit, vous ne pouvez pas chiffrer avec la classe ```java.util.AES``` et déchiffrer avec la classe ```com.jasonpercus.encryption.aes.AES``` et inversement.

## 2.5. JPS (Jason Percus Security)
Cette classe sert pour chiffrer du texte ou tableau de bytes en tableau chiffré de bytes et inversement déchiffrer un tableau de bytes en texte. Elle s'appuie sur un nouvel algorithme JPS. Voici un exemple de son utilisation:
```java
//Création d'un objet JPS
Cipher jps = new JPS();

//Création d'une clée
Key key = jps.generateKey();

//Il s'agit de mon texte à chiffrer avec JPS
byte[] toEncrypt =  "Mon texte converti en bytes".getBytes();

//Chiffrement & déchiffrement
byte[] encrypted = jps.encrypt(key, toEncrypt);
byte[] decrypted = jps.decrypt(key, encrypted);

//Affichages des résultats
System.out.println("Encrypted: " + new String(encrypted));
System.out.println("Decrypted: " + new String(decrypted));
```
Nous obtenons ce résultat
```
Encrypted:  ��w�
��]�M��#���P�_�����,3�	�:�eή$
Decrypted: Mon texte converti en bytes
```

# 3. **JSON**
JSON est une classe qui se sert de Gson pour pouvoir transformer un objet en chaîne de caractère JSON et inversement. 

#### Serialisation d'un objet en Json & Deserialisation d'une chaîne JSON en objet
```java
//Partons du principe que nous avons un tableau de 3 objets Voiture.
Voiture[] voitures = ... //initialisation d'un tableau de voitures

//Serialise la dernière voiture du tableau
String json = JSON.serialize(voitures[2]);

//Affiche la chaîne de caractères au format json de la voiture
System.out.println(json);

//Déserialise une chaîne json en objet Voiture
JSON<Voiture> voitureJSON = JSON.deserialize(Voiture.class, json);

//Récupère la voiture déserialisée -> On vient de récupérer un objet à partir d'un texte json
Voiture voiture = voitureJSON.getObj();

//Serialise le tableau complet de voitures
json = JSON.serialize(voitures);

//Affiche la chaîne de caractères au format json du tableau de voitures
System.out.println(json);

//Déserialise une chaîne json en liste de Voitures
voitureJSON = JSON.deserialize(Voiture.class, json);

//Récupère la liste de voitures déserialisée -> on vient de récupérer une liste à partir d'un texte json
List<Voiture> listVoitures = voitureJSON.getList();
```

> Attention: On peut constater que la déserialisation se fait de manière identique pour un objet ou une liste. Cela est dû au fait, que la méthode `JSON.deserialize()` s'occupe automatiquement de distinguer les listes ou les objets bruts. Donc dans les deux cas, la méthode renvoie un objet `JSON<ObjetADeserialiser>`.

"Alors comment savoir si l'objet contient une liste et non un objet brut et vice-versa ?"

-> C'est grâce à ses méthodes

Voici un exemple. Partons du principe que nous avons désérialisé une liste appelée `JSON<Voiture> list` et un objet appelé `JSON<Voiture> obj`
```java
//Commençons le test avec l'objet
boolean obj_isObject = obj.isObject();
boolean obj_isList = obj.isList();
System.out.println(obj_isObject + " - " + obj_isList); // Résultat: "true - false"

//Faisons maintenant le test avec la liste
boolean list_isObject = list.isObject();
boolean list_isList = list.isList();
System.out.println(list_isObject + " - " + list_isList); // Résultat: "false - true"

//Lorsque l'on utilise le switch case, il est possible d'utiliser cette structure (exemple sur l'objet. Cela fonctionne de la même manière pour la liste)
switch(obj.getType()){
    case "OBJECT":
        Voiture voiture = obj.getObj();
        break;
    case "LIST":
        List<Voiture> voitures = obj.getList();
        break;
}
```

> Remarque: Un tableau d'objet serialisé puis redéserialisé ne redonne pas un tableau, mais une liste. Une liste d'objet serialisé puis redéserialisé ne donne pas un tableau, mais une liste.

# 4. **MySQL**
Cette classe permet d'intéragir avec une base de données MySQL.
```java
try {
    // Création et connexion MySQL
    MySQL mysql = MySQL.getInstance("mon login", "mon mot de passe", "192.168.1.1", 3306, "ma base");
    mysql.connect();
    
    // Ou pour simplifier
    mysql = MySQL.getInstanceConnected("mon login", "mon mot de passe", "192.168.1.1", 3306, "ma base");
    
    // Fermer une connexion MySQL
    mysql.close();
} catch (SQLException ex) {
    // Si la connexion ne se fait pas
    Logger.getLogger(MonProjet.class.getName()).log(Level.SEVERE, null, ex);
}
```
Pour intéragir avec la base de données, il faut déterminer si l'on souhaite communiquer avec une ou plusieurs tables
```java
// Intéragir avec une seule table
mysql.table.....

// Intéragir avec plusieurs table en même temps
mysql.tables.....
```
Ensuite il faut déterminer si l'on souhaite obtenir/envoyer une ou plusieurs données
```java
// Pour une donnée
mysql.table.data.....
mysql.tables.data.....

// Pour plusieurs données
mysql.table.datas.....
mysql.tables.datas.....
```
Voici une liste de quelques méthodes très utile:
* ```mysql.table.create("CREATE TABLE...");``` ou ```mysql.table.add("CREATE TABLE...");``` -> crée une nouvelle table dans la BDD
* ```mysql.table.alter("ALTER TABLE...");``` ou ```mysql.table.set("ALTER TABLE...");``` -> modifie la structure d'une table de la BDD
* ```mysql.table.drop("DROP TABLE...");``` ou ```mysql.table.delete("DROP TABLE...");``` -> supprime une table de la BDD
* ```mysql.table.exists("MaTable");``` -> renvoie ```true``` si la table "MaTable" existe dans la BDD, sinon ```false```
* ```mysql.table.getColumnNames("MaTable");``` -> renvoie la liste des intitulés de colonnes d'une table dans la BDD
* ```mysql.tables.count();``` -> renvoie le nombre de tables dans la BDD
* ```mysql.tables.getList();``` -> renvoie la liste des noms de tables dans la BDD
* ```mysql.table.data.insertInto("INSERT INTO...");``` ou ```mysql.table.data.add("INSERT INTO...");``` -> insère une ou plusieurs entrées dans la BDD
* ```mysql.table.data.update("UPDATE...");``` ou ```mysql.table.data.set("UPDATE...");``` -> Modifie une ou des entrées d'une table de la BDD
* ```mysql.table.data.delete("DELETE FROM...");``` -> Supprime une ou des entrées d'une table de la BDD
* ```mysql.table.data.select("SELECT * FROM...");``` ou ```mysql.table.data.get("SELECT * FROM...");``` -> Renvoie les résultat d'une requête SELECT
* ```mysql.table.datas.getList(Personnage.class, "SELECT * FROM...");``` -> renvoie la liste des personnages d'une requête SELECT
* ```mysql.table.datas.getFirst(Personnage.class, "SELECT * FROM...");``` -> renvoie le premier personnage d'une requête SELECT
* ```mysql.table.datas.getLast(Personnage.class, "SELECT * FROM...");``` -> renvoie le dernier personnage d'une requête SELECT
 
Pour pouvoir faire le lien direct entre un objet (ex: ```Personnage```) et une entrée récupérée de la BDD se fait par l'annotation ```@Column```. Voici un exemple d'une classe qui utilise cette annotation:
```java
public class Personnage {
    
    @Column(name = "NOM") //Ici le lien se fera avec la colone "NOM" de la table "Personnage"
    private String nom;

    @Column(name = "PRENOM") //Ici le lien se fera avec la colone "PRENOM" de la table "Personnage"
    private String prenom;

    @Column(name = "AGE") //Ici le lien se fera avec la colone "AGE" de la table "Personnage"
    private int age;
    
}
```
> Attention: il ne faut pas lier une annotation ```@Column``` avec un objet autre qu'un type primitif.

# 5. **Network**
Ce projet permet de récupérer le nom des cartes réseaux, le nom des interfaces, l'adresse MAC, les IP... il se sert de jna-5.6.0, jna-platform-5.6.0.

### Explications sur les classes et interfaces
##### CharHexadecimal
La classe `CharHexadecimal.java` permet de créer un caractère hexadécimal.
On peut lui donner sa version en caractère hexadécimal et également en valeur décimal. 
Voici les correspondances des valeurs décimales et hexadécimales:
`0` *(décimal)* -> `0` *(hexadécimal)*, 
`1` *(décimal)* -> `1` *(hexadécimal)*, 
`2` *(décimal)* -> `2` *(hexadécimal)*, 
`3` *(décimal)* -> `3` *(hexadécimal)*, 
`4` *(décimal)* -> `4` *(hexadécimal)*, 
`5` *(décimal)* -> `5` *(hexadécimal)*, 
`6` *(décimal)* -> `6` *(hexadécimal)*, 
`7` *(décimal)* -> `7` *(hexadécimal)*, 
`8` *(décimal)* -> `8` *(hexadécimal)*, 
`9` *(décimal)* -> `9` *(hexadécimal)*, 
`10` *(décimal)* -> `A` *(hexadécimal)*, 
`11` *(décimal)* -> `B` *(hexadécimal)*, 
`12` *(décimal)* -> `C` *(hexadécimal)*, 
`13` *(décimal)* -> `D` *(hexadécimal)*, 
`14` *(décimal)* -> `E` *(hexadécimal)*, 
`15` *(décimal)* -> `F` *(hexadécimal)*
Voici comment on crée un objet `CharHexadecimal`:
```java
// Création d'un objet CharHexadecimal à partir d'une valeur décimale
CharHexadecimal hex1 = new CharHexadecimal(12);

// Création d'un objet CharHexadecimal à partir d'un caractère hexadécimal
CharHexadecimal hex2 = new CharHexadecimal('c');

// On teste que ces deux objets sont identiques
System.out.println("Test [" + hex1 + " = " + hex2 + " ?] -> "+hex1.equals(hex2));
```
Ceci nous affiche comme résultat
```
Test [c = c ?] -> true
```
>Si on entre dans le constructeur une valeure inférieure à `0` et supérieure à `15`, l'exception `InvalidNumberException` est levée.
>Si on entre dans le constructeur le caractère `'g'`, l'exception `InvalidCharacterException` est levée.

##### ChainHexadecimal
La classe `ChainHexadecimal.java` permet de créer des chaînes hexadécimales de caractères.
On peut créer un objet de ce type à partir d'une chaîne String contenant que des caractères hexadécimaux: exemple `47ab53fc`. Ou à partir d'une liste de `CharHexadecimal`: exemple `List<CharHexadecimal> lch = ...`

Voici comment on crée un objet `ChainHexadecimal`:
```java
// Création d'un objet ChainHexadecimal à partir d'une chaîne String.
ChainHexadecimal sHex1 = new ChainHexadecimal("47ab53fc");

// Création d'une liste de CharHexadecimal
List<CharHexadecimal> lHex = new ArrayList<>();
lHex.add(new CharHexadecimal('8'));
lHex.add(new CharHexadecimal('7'));
lHex.add(new CharHexadecimal('a'));
lHex.add(new CharHexadecimal('b'));
lHex.add(new CharHexadecimal('5'));
lHex.add(new CharHexadecimal('3'));
lHex.add(new CharHexadecimal('f'));
lHex.add(new CharHexadecimal('c'));

// Création d'un objet ChainHexadecimal à partir d'une liste de CharHexadecimal
ChainHexadecimal sHex2 = new ChainHexadecimal(lHex);

// On teste que ces deux objets sont identiques
System.out.println("Test [" + sHex1 + " = " + sHex2 + " ?] -> "+sHex1.equals(sHex2));
```
Ceci nous affiche comme résultat
```
Test [47ab53fc = 87ab53fc ?] -> false
```
> Les mêmes exceptions que pour `CharHexadecimal` sont levées si la chaîne est incorrecte...

> Remarque: `ChainHexadecimal` implémente `CharSequence`

##### IPv4
La classe `IPv4.java` permet de créer des adresses IP de type 4.
On peut créer un objet de ce type à partir d'une chaîne String: exemple `"192.168.1.1"`. Ou à partir d'un tableau de bytes: exemple `{-64, -88, 1, 1}`.

Voici comment on crée un objet IPv4:
```java
// Création d'un objet IPv4 par défaut.
IPv4 ip0 = new IPv4();

// Création d'un objet IPv4 à partir d'une chaîne String.
IPv4 ip1 = new IPv4("192.168.1.1");

// Création d'un tableau de bytes
byte[] array = {-64, -88, 1, 1};

// Création d'un objet IPv4 à partir d'un tableau de bytes
IPv4 ip2 = new IPv4(array);

// On teste que deux adresses IP sont identiques
System.out.println("Test [" + ip0 + " = " + ip1 + " ?] -> "+ip0.equals(ip1));

// On teste que ces deux adresses IP sont identiques
System.out.println("Test [" + ip1 + " = " + ip2 + " ?] -> "+ip1.equals(ip2));

// On récupère et on affiche l'ip publique du réseau local
System.out.println("IP Publique: "+IPv4.getPublicIP());
```
Ceci nous affiche comme résultat
```
Test [0.0.0.0 = 192.168.1.1 ?] -> false
Test [192.168.1.1 = 192.168.1.1 ?] -> true
IP Publique: 87.198.63.149
```
> On peut également récupérer l'adresse IPv4 principale de l'équipement qui exécute la méthode `IPv4.getMainLocalIPv4()`. Cette méthode est pratique lorsque l'on possède plusieurs IP statiques et que l'on veut connaître la principale.

##### MaskIPv4
La classe `MaskIPv4.java` permet de créer des masques IP de type 4. Cette classe hérite de `IPv4.java`. Elle possède donc des méthodes supplémentaires:
 - public static String getSimplifiedNorm(MaskIPv4 ipv4): Permet de transformer un masque de ce type `255.255.0.0` au type `/16`.
 - public static MaskIPv4 getFullNorm(String simplifiedNorm): Permet de transformer un masque de ce type `/16` au type `255.255.0.0`.

> Attention: A la création d'un masque une vérification supplémentaire est réalisée. En effet certains masques sont impossibles à créer. Exemple: `255.255.192.128`. cette création lèvera l'exception `InvalidAddressException`.

> On peut également récupérer le masque principale de l'équipement qui exécute la méthode `IPv4.getMainLocalMask()`. Cette méthode est pratique lorsque l'on possède plusieurs IP statiques et que l'on veut connaître le masque principal.

##### MACAddress
La classe `MACAddress.java` permet de créer et gérer des adresses MAC.
On peut créer un objet de ce type à partir d’une chaîne String: exemple "88-51-FC-55-30-A4". Ou à partir d’un tableau de bytes: exemple {-120, 81, -4, 85, 48, -92}.

Voici comment on crée un objet MACAddress:
```java
// Création d'un objet MACAddress par défaut.
MACAddress mac0 = new MACAddress();

// Création d'un objet MACAddress à partir d'une chaîne String.
MACAddress mac1 = new MACAddress("88-51-FC-55-30-A4");

// Création d'un tableau de bytes
byte[] array = {-120, 81, -4, 85, 48, -92};

// Création d'un objet MACAddress à partir d'un tableau de bytes
MACAddress mac2 = new MACAddress(array);

// On teste que deux adresses MAC sont identiques
System.out.println("Test [" + mac0 + " = " + mac1 + " ?] -> "+mac0.equals(mac1));

// On teste que ces deux adresses MAC sont identiques
System.out.println("Test [" + mac1 + " = " + mac2 + " ?] -> "+mac1.equals(mac2));
```
Ceci nous affiche comme résultat
```
Test [00-00-00-00-00-00 = 88-51-FC-55-30-A4 ?] -> false
Test [88-51-FC-55-30-A4 = 88-51-FC-55-30-A4 ?] -> true
```

> Une mauvaise création d'adresse MAC entraine la levée de l'exception `InvalidAddressException`.

> On peut également récupérer l'adresse MAC principale de l'équipement qui exécute la méthode `IPv4.getMainLocalMACAddress()`. Cette méthode est pratique lorsque l'on possède plusieurs IP statiques et que l'on veut connaître l'adresse MAC principale de l'équipement.

##### IPCard
La classe `IPCard.java` permet d'associer une adresse IP avec un masque et une adresse de broadcast.

Voici comment on crée un objet IPCard:
```java
// Création d'une adresse IP
IPv4 ip = new IPv4("192.168.1.1");

// Création d'un masque
MaskIPv4 mask = MaskIPv4.getFullNorm("/24");

// Création d'une adresse de broadcast
BroadcastIPv4 broadcast = new BroadcastIPv4("192.168.1.255");

// Création d'un objet IPCard.
IPCard ipCard = new IPCard(ip, mask, broadcast);

// Affichage de l'objet IPCard
System.out.println(ipCard);
```
Ceci nous affiche comme résultat
```
IPCard(ip: 192.168.1.1, mask: 255.255.255.0, broadcast: 192.168.1.255)
```

##### NetworkCard
La classe `NetworkCard.java` permet de lier une adresse MAC avec le nom de la carte réseau, le nom de l'interface réseau, le numéro de la carte et les `IPCards` de la carte réseau.

Voici comment on crée un objet NetworkCard:
```java
// Création d'un objet MACAddress à partir d'une chaîne String.
MACAddress mac = new MACAddress("88-51-FC-55-30-A4");

// Création d'une IPCard
IPCard ipc1 = new IPCard(new IPv4("192.168.1.1"), MaskIPv4.getFullNorm("/24"), new BroadcastIPv4("192.168.1.255"));

// Création d'une autre IPCard
IPCard ipc2 = new IPCard(new IPv4("192.168.0.36"), MaskIPv4.getFullNorm("/24"), new BroadcastIPv4("192.168.0.255"));

// Création d'une liste d'IPCard
List<IPCard> lipc = new ArrayList<>();
lipc.add(ipc1);
lipc.add(ipc2);

// Création d'un objet NetworkCard
NetworkCard nc = new NetworkCard(mac, "Realtek PCIe GBE Family Controller", "eth1", 1, lipc);

// Affichage de l'objet NetworkCard
System.out.println(nc);
```
Ceci nous affiche comme résultat
```
NetworkCard(@MAC: 88-51-fc-55-30-a4, name: Realtek PCIe GBE Family Controller, nameEth: eth1, index: 1, ipCards: [IPCard(ip: 192.168.1.1, mask: 255.255.255.0, broadcast: 192.168.1.255), IPCard(ip: 192.168.0.36, mask: 255.255.255.0, broadcast: 192.168.0.255)])
```

##### Network
La classe `Network.java` permet de récupérer la liste des cartes réseaux d'un système d'exploitation...
Elle ne contient qu'un seul constructeur: `public Network(){}`.

Voici comment on crée un objet Network:
```java
// Création d'un objet Network par défaut.
Network net = new Network();

// Récupère une liste de NetworkCard
java.util.List<NetworkCard> list = net.getHardwareList();

// Affiche chaque NetworkCard
for(NetworkCard nc : list)
    System.out.println(nc);

// On crée un masque de réseau
MaskIPv4 mask = Mask.getFullNorm("/24");

// On récupère l'adresse IP d'un réseau avec l'adresse IP d'un équipement ainsi que le masque de réseau
IPv4 ipNetwork = Network.getIPNetwork(new IPv4("192.168.0.35"), mask);

// On récupère l'adresse de broadcast d'un réseau avec l'adresse IP d'un réseau aunsi que son masque
BroadcastIPv4 ipBroadcast = Network.getIPBroadcast(ipNetwork, mask);

// On récupère un pool d'adresse utilisable comprise dans le réseau 192.168.0.0
PoolIPv4 pool = Network.getPoolIpv4(ipNetwork, mask);

// On récupère le nombre d'adresses utilisables (sans l'adresse du réseau et de broadcast)
long count = pool.count();

// On affiche toute les adresses IP utilisable sur le réseau 192.168.0.0/24
for(long l=0; l<count; l++)
    System.out.println(pool.next());
```
Ceci nous affiche comme résultat
```
NetworkCard(@MAC: null, name: WAN Miniport (SSTP), nameEth: net0, index: 2, ipCards: [])
NetworkCard(@MAC: null, name: WAN Miniport (L2TP), nameEth: net1, index: 3, ipCards: [])
NetworkCard(@MAC: null, name: WAN Miniport (PPTP), nameEth: net2, index: 4, ipCards: [])
NetworkCard(@MAC: null, name: WAN Miniport (PPPOE), nameEth: ppp0, index: 5, ipCards: [])
NetworkCard(@MAC: null, name: WAN Miniport (IPv6), nameEth: eth0, index: 6, ipCards: [])
NetworkCard(@MAC: null, name: WAN Miniport (Network Monitor), nameEth: eth1, index: 7, ipCards: [])
NetworkCard(@MAC: null, name: WAN Miniport (IP), nameEth: eth2, index: 8, ipCards: [])
NetworkCard(@MAC: null, name: RAS Async Adapter, nameEth: ppp1, index: 9, ipCards: [])
NetworkCard(@MAC: null, name: WAN Miniport (IKEv2), nameEth: net3, index: 10, ipCards: [])
NetworkCard(@MAC: 88-51-fb-55-30-a4, name: Realtek PCIe GBE Family Controller, nameEth: eth3, index: 11, ipCards: [IPCard(ip: 192.168.201.35, mask: 255.255.255.0, broadcast: 192.168.201.255), IPCard(ip: 192.168.31.48, mask: 255.255.255.0, broadcast: 192.168.31.255), IPCard(ip: 192.168.0.36, mask: 255.255.255.0, broadcast: 192.168.0.255)])
...
192.168.0.1
192.168.0.2
192.168.0.3
192.168.0.4
192.168.0.5
...
192.168.0.251
192.168.0.252
192.168.0.253
192.168.0.254
```
Il est possible de parcourir un pool dans le sens inverse pour cela il faut placer le curseur à la fin du pool avec la méthode `setCursorEnd()` *(l'inverse c'est: `setCursorBeginning()`)* puis d'utiliser la méthode `previous()` au lieu de `next()`.
##### Scanner
La classe `Scanner.java` permet de récupérer la liste des équipements réseaux d'un réseau LAN.
```java
// Crée un objet scanner
Scanner scanner = new Scanner();

// On vérifie que le port 80 d'un PC est ouvert
boolean open = scanner.portIsOpen(new IPv4("192.168.0.1"), 80);

// On scanne toutes les ip du réseau LAN
ResultScan rs = scanner.listLanHardware(new IPv4("192.168.0.0"));

// On scanne toutes les ip inactives du réseau LAN
rs = scanner.listLanHardware(new IPv4("192.168.0.0"), "/n");

// On scanne toutes les ip actives du réseau LAN
rs = scanner.listLanHardware(new IPv4("192.168.0.0"), "/a");

// On scanne toutes les ports de 0 à 1024 d'un équipement
ResultScan rsp = scanner.listPort(new IPv4("192.168.0.0"));

// On scanne toutes les ports de 100 à 512 d'un équipement
rsp = scanner.listPort(new IPv4("192.168.0.1"), "/m 100", "/M 512");

// On scanne toutes les ports inactifs de 100 à 512 d'un équipement
rsp = scanner.listPort(new IPv4("192.168.0.1"), "/m 100", "/M 512", "/n");

// On scanne toutes les ports actifs de 100 à 512 d'un équipement
rsp = scanner.listPort(new IPv4("192.168.0.1"), "/m 100", "/M 512", "/a");

// On scanne toutes les ports TCP actifs de 100 à 512 d'un équipement
rsp = scanner.listPort(new IPv4("192.168.0.1"), "/m 100", "/M 512", "/a", "/tcp");

// On scanne toutes les ports UDP inactifs de 100 à 512 d'un équipement
rsp = scanner.listPort(new IPv4("192.168.0.1"), "/m 100", "/M 512", "/n", "/udp");

// Affiche tous les équipements réseaux du LAN
for(int i=0;i<rs.getEquipments().length;i++)
    System.out.println(rs.getEquipments()[i]);

// Affiche tous les ports d'un équipement réseau ainsi que leurs noms
for(int i=0;i<rsp.getEquipmentPorts().length;i++)
    System.out.println(rsp.getEquipmentPorts()[i] + " - nom: " + rsp.getEquipmentPorts()[i].getName());
    
// Affiche un port TCP non utilisé
System.out.println(scanner.getFreePortTCP());

// Affiche un port UDP non utilisé
System.out.println(scanner.getFreePortUDP());
```

# 6. **ExpandableArray**
Les classes ExpandableArray représentent des tableaux qui peuvent s'étendre ce qui n'est pas le cas d'un tableau classique. Pourquoi utiliser un ExpandableArray lorsqu'il existe la classe ```java.util.List``` ? Pour répondre à cette question, voici un tableau de comparaison entre un tableau d'entier ```int[]```, une liste d'entier ```java.util.List<Integer>``` et un ExpandableArray d'entier ```com.jasonpercus.util.array.ExpandableArray_int``` lorsqu'il y a énormément d'éléments dans chacun d'eux:

|                         | Vitesse processeur | Elargissement |
|-------------------------|:------------------:|:-------------:|
| int[]                   | Excellente         | Impossible    |
| java.util.List<Integer> | Mauvaise           | Oui           |
| ExpandableArray_int     | Bonne              | Oui           |

Et lorsqu'il y a peu d'éléments:

|                         | Vitesse processeur | Elargissement |
|-------------------------|:------------------:|:-------------:|
| int[]                   | Excellente         | Impossible    |
| java.util.List<Integer> | Bonne              | Oui           |
| ExpandableArray_int     | Moyenne            | Oui           |

En fonction des tableaux si dessus on peut conclure:

**Lorsque l'on connait la taille d'une collection d'entiers et que celle-ci ne bougera pas:**
> Il est préférable d'utiliser un tableau ```int[]```

**Lorsque l'on veut une collection d'entiers pouvant s'étendre, il y a deux choix. Si la collection doit contenir peu d'élément:**
> Il est préférable d'utiliser une ```java.util.List<Integer>```

**Et Lorsque la collection doit contenir beaucoup d'élément:**
> Il est préférable d'utiliser un ExpandableArray ```ExpandableArray_int```

Ce qui est valable pour les entiers est également valable pour tout les types primitifs et également les objets. Voici le nom des classes associées à chaque type:

| Tableau      | Equivalent ExpandableArray  |
|--------------|-----------------------------|
| byte[]       | ExpandableArray_byte        |
| short[]      | ExpandableArray_short       |
| int[]        | ExpandableArray_int         |
| long[]       | ExpandableArray_long        |
| float[]      | ExpandableArray_float       |
| double[]     | ExpandableArray_double      |
| char[]       | ExpandableArray_char        |
| boolean[]    | ExpandableArray_boolean     |
| Object[]     | ExpandableArray             |
| Personnage[] | ExpandableArray<Personnage> |
| Voiture[]    | ExpandableArray<Voiture>    |
| ...          | ...                         |

##### Création d'un tableau pouvant s'étendre
```java
//Création d'un tableau de personnages pouvant s'étendre
ExpandableArray<Personnage> personnages = new ExpandableArray<>(Personnage.class);

//Création d'un tableau d'entiers pouvant s'étendre
ExpandableArray_int entiers = new ExpandableArray_int();
```

##### Ajouter un élément
```java
entiers.set(0, 15); //équivalent de array[0] = 15
entiers.set(1, 16); //équivalent de array[1] = 16
entiers.set(2, 17); //équivalent de array[2] = 17
entiers.put(18); //équivalent de array[3] = 18
entiers.put(19); //équivalent de array[4] = 19
```

##### Modifier un élément
```java
entiers.set(1, 106); //équivalent de array[1] = 106
```

##### Vider le tableau
```java
entiers.clear();
```

##### Trier le tableau
```java
entiers.sort();
```

##### Vérifier l'existance d'un élément
```java
System.out.println(entiers.contains(16)); //true
System.out.println(entiers.contains(20)); //false
```

##### Rechercher l'index d'un élément
```java
System.out.println(entiers.indexOf(17)); //2
System.out.println(entiers.indexOf(20)); //-1
```

##### Renvoyer un élément du tableau
```java
System.out.println(entiers.get(3)); //18
```

##### Effacer un élément
```java
entiers.remove(3); //Passe la valeur 18 à 0
```

# 7. **Code**
La classe ```com.jasonpercus.util.FileCode``` est la classe fille de ```com.jasonpercus.util.File``` qui elle même est la classe fille de ```java.io.File```. La classe ```FileCode``` permet de charger un fichier *.java et de l'analyser, le modifier... Exemple:
```java
try {
    //Analyse le code
    code.analyse();
    
    //Parcours le nombre de documents compris dans le fichier Personnage.java
    for(int i=0;i<code.countDocuments();i++){
        
        //Récupérer un document (classe, interface, énumération, annotation)
        Type_code type = code.getDocuments(i);
        
        //S'il s'agit d'une classe
        if(type instanceof Class_type){
            
            //Cast le document en classe
            Class_type classe = (Class_type) type;
            
            //Correspond à la liste des constructeurs
            java.util.List<Constructor_element> listeConstructeurs = classe.getConstructors();
            
            //Correspond à la liste des méthodes
            java.util.List<Method_element> listeMethodes = classe.getMethods();
            
        }
        
        //S'il s'agit d'une interface
        if(type instanceof Interface_type){
            ...
        }
        
        //S'il s'agit d'une énumération
        if(type instanceof Enum_type){
            ...
        }
        
        //S'il s'agit d'une interface d'annotation
        if(type instanceof Annotation_type){
            ...
        }
        
    }

} catch (FileNotFoundException ex) {
    //Le fichier n'est pas analysable, car il n'existe pas
} catch (SyntaxCodeException ex) {
    //Le fichier n'est pas analysable, car il n'a pas été écrit correctement
}
```

# 8. **Key**
   ## 8.1. Classe ```Code```
Cette classe permet de représenter une touche de clavier. Chaque touche de clavier est représenté avec Java par en entier. Cette classe n'est pas instanciable, elle permet seulement de faire le lien entre un entier et le nom d'une touche
```java
//Affiche le nom d'une touche de clavier
System.out.println(Code.name(65)); //A

//Et inversement affiche le code d'une touche de clavier
System.out.println(Code.code("A")); //65
```
   
   ## 8.2. Classe ```Combination```
Cette classe permet de représenter une combinaison de touches (exemple: ```Ctrl + Alt + Suppr```).
```java
//Correspond à la touche Suppr.
int keyCtrl = Code.code("DELETE");

//Correspond une combination (code de la touche, windows, meta, Ctrl, Shift, Alt)
Combination combination = new Combination(keyCtrl, false, false, true, false, true);

//Affiche une combinaison
System.out.println(combination); //Ctrl + Alt + DELETE

//Execute la combinaison avec le robot Java
combination.execute();
```
   
   ## 8.3. Classe ```CombinationCapture```
Cette classe permet de récupérer une combinaison de touches pressées par l'utilisateur. Voici un exemple sur la récupération d'une combinaison de touche à partir d'un ```JTextField```.
```java
//Correspond à l'évènement de capture
CombinationCaptureListener listener = new CombinationCaptureListener() {
    @Override
    public void combinationPressed(Combination combination, KeyEvent evt) {
        System.out.println(combination); //Ctrl + Alt + DELETE
    }
};

//Crée un objet de Capture
CombinationCapture capture = new CombinationCapture(monJTextField, listener);
```
> Si on appuie sur les touches Ctrl + Alt + Suppr à partir du ```JTextField```, le listener affiche Ctrl + Alt + DELETE

# 9. **MouseArea**
   ## 9.1. Enumération ```ModeArea```

Cette énumération énumère deux modes. Le mode additif et le mode soustractif.
- Le mode additif: permet d'ajouter une zone qui sera contrôlée. Autrement dit lorsque la souris passera sur la zone en question, il se passera quelque chose.
- Le mode soustractif: permet de supprimer une zone qui est contrôlée. Autrement dit lorsque la souris passera sur la zone en question, il ne se passera plus rien.

Ces deux modes doivent être ajoutés au besoin, à une zone pour définir une zone de contrôle.
   ## 9.2. Classe ```Zone```

Cette classe représente un ```java.awt.Graphics``` invisible qui servira comme zone de contrôle lorsque la souris passera dessus. Donc quasiment tout ce qui peut être fait avec un objet ```Graphics``` peut être fait avec un objet ```Zone```.

> Pour plus de précision lire la javadoc associée avec cette classe

# 10. **Process**
   ## 10.1. Process OS
La classe ```com.jasonpercus.util.process.Process``` représente la même chose qu'un objet ```java.lang.Process``` avec en plus la possibilité d'obtenir le pid du processus, de le tuer. Voici un exemple d'utilisation:
```java
Process process = new Process("Outlook.exe", "/safe");
process.start(new IProcessListener() {
    @Override
    public <R> R started(Process process, String[] args, long pid, InputStream messageStream, InputStream errorStream) {
        //Le processus vient de démarrer
        
        System.out.println(process.getPid()); //Affiche le PID
    }

    @Override
    public void stopped(Process process, String[] args) {
        //Le processus est stoppé
    }

    @Override
    public void killed(Process process, String[] args) {
        //Le processus est tué
    }
});

//Tue le processus
process.kill();
```

   ## 10.2. Process java
Ce projet permet de lancer un processus Java à partir du processus courant et de communiquer avec lui tout le long du cycle de vie d'un des deux processus. Le lancement et la communication se fait avec l'aide de la classe ```InterPipe```. Voici un exemple simplifié d'utilisation:
##### Pour le processus père
```java
//Crée et lance un processus fils
InterPipe.createProcessJava("UnNomUniqueDeProcessusFils", "ProcessusFils.jar");

//Ecoute le processus fils
InterPipe.in.read(new IReceivedForParents() {
    @Override
    public void received(String id, Object object, boolean error) {
        //Lorsque le processus père reçoit un objet du processus fils
        //error = détermine s'il s'agit d'un objet envoyé sur le bus d'erreur
    }

    @Override
    public Serializable receivedSync(String id, Object object, boolean error) {
        //Lorsque le processus père reçoit un objet du processus fils et doit renvoyer une réponse immédiate
        //error = détermine s'il s'agit d'un objet envoyé sur le bus d'erreur
        
        if(object.toString().equals("Quelle heure est-il ?"))
            return "Il est 16:57"
            
        //S'il n'y a pas de réponse envisagée
        return null;
    }
});

//Envoie un message de bienvenue au processus fils
InterPipe.out.print("UnNomUniqueDeProcessusFils", "Bonjour mon fils !");

//Pose une question au fils et affiche la réponse
System.out.println(InterPipe.getSync("UnNomUniqueDeProcessusFils", "As-tu fais tes devoirs ?")); //true
```
##### Pour le processus fils
```java
//Ecoute le processus père
InterPipe.in.read(new IReceivedForChildren() {
    @Override
    public void received(Object object) {
        //Lorsque le processus fils reçoit un objet du processus père
    }

    @Override
    public Serializable receivedSync(Object object) {
        //Lorsque le processus fils reçoit un objet du processus père et doit renvoyer une réponse immédiate
        
        if(object.toString().equals("As-tu fais tes devoirs ?"))
            return true;
            
        //S'il n'y a pas de réponse envisagée
        return null;
    }
});

//Envoie un message de bienvenue au processus père
InterPipe.out.print("Bonjour papa !");

//Envoie un autre message au processus père
System.out.println("Je suis heureux d'être ton fils.");

//Envoie une erreur au processus père
InterPipe.err.print("Je bug !");

//Envoie une autre erreur au processus père
System.err.println("Je bug encore !");

//Pose une question au père et affiche la réponse
System.out.println(InterPipe.getSync("Quelle heure est-il ?", false)); //Il est 16:57
```

# 11. **Thread**
***Cette section n'est pas détaillée pour le moment. Veuillez consulter la javadoc qui explique comment utiliser les différentes classes et interface***
Voici un tableau récapitulatif du fonctionnement des classes et interfaces principales:

| Nom            |    Type   | Description sommaire                                                                                                   |
|----------------|:---------:|------------------------------------------------------------------------------------------------------------------------|
| CodeProcessing |   class   | Permet de lancer plusieurs threads en même temps et attend leurs résultats respectifs                                  |
| During         |   class   | Est équivalent à l'interface Runnable, mais renvoie un résultat. Elle est couplé avec CodeProcessing                   |
| OnCodeProcess  | interface | Cette interface permet d'être au courant de chaque changement d'état d'un objet CodeProcessing                         |
| TimedResult    |   class   | Execute un bout de code en un temps imparti. Si celui-ci dépasse le temps indiqué une valeur par défaut est envoyée    |
| CodeTimeResult | interface | Cette interface permet de définir le bout de code a être réalisé en un temps imparti. Elle est couplé avec TimedResult |

# 12. **File**
Cette classe étend l'ancienne classe ```java.io.File```. Elle apporte quelques méthodes pratiques qui n'existaient pas dans l'ancienne classe. Exemple:
```java
//Crée un fichier
File file = new File("MonFichier.txt");

//Affiche la date de création du fichier
System.out.println(file.getCreationDate());

//Affiche l'extension du fichier
System.out.println(file.getExtension()); //txt

//...
```

# 13. **LoaderPlugin**
Cette classe permet de charger des plugins dans un programme. Un plugin est représenté et compilé en fichiers `jar`. Mais il peut être renommé avec une autre extension si on ne veut pas l'associer à Java *(voir l'exemple plus bas)*. Pour pouvoir rendre un plugin fonctionnel, il faut comprendre le principe d'un plugin. 

Tout d'abord on ne connait pas les classes du plugin. C'est embêtant, car on ne peut pas créer d'objet si on ne connait pas les classes du plugin. C'est pourquoi on fait en sorte que les classes des plugins implémentent une interface du projet ou qu'elles étendent une classe bien connue du projet. Ainsi ces interfaces ou classes font le lien entre le projet et le plugin.

### Plugins et modèles
##### Modèles
On va partir sur le modèle suivant: On a une interface `Name`. Son travail sera de permettre aux classes qui l'implémente de donner un nom. Voici son code:

```java
public interface Name {
    
    /**
     * Renvoie un nom
     */
    public String getName();
    
}
```

On a une classe abstraite `Vehicule` qui implémente `Name`. Voici son code:

```java
public abstract class Vehicule implements Name {
    
    /**
     * Renvoie le nombre de roues du véhicule
     */
    public abstract int countWheels();
    
}
```

On a une classe abstraite `Personnage` qui implémente `Name`. Voici son code:

```java
public abstract class Personnage implements Name {
    
    /**
     * Renvoie l'age du personnage
     */
    public abstract int getOld();
    
}
```

> On a donc une classe `Personnage` qui fournira un nom et un age, et une classe `Vehicule` qui fournira un nom et un nombre de roues.

Nous avons vu les 3 classes du projet. Maintenant créons des plugins...

##### Plugins
Comme expliqué plus haut, une classe d'un plugin doit implémenter une interface ou étendre une classe déjà existante dans le projet. 

**Plugin n°1**
Il faut tout d'abord créer un nouveau projet. Puis copier du projet principale les classes/interfaces qui vont être utilisées: `Name`, `Vehicule` et `Personnage` dans le plugin

Créons ensuite la classe `Peugeot`:

```java
public class Peugeot extends Vehicule {
    
    /**
     * Renvoie un nom
     */
    public String getName(){
        return "Peugeot";
    }
    
    /**
     * Renvoie le nombre de roues du véhicule
     */
    public int countWheels(){
        return 4;
    }
    
}
```

Puis on compile le code. Si on n'avait pas copié les 3 classes, il y aurait eu des erreurs de compilation. 

Vous vous dites certainement que cela n'est pas très avantageux en terme de poid de plugin, puisque celui-ci est plus lourd que le projet principal. En réalité, ON AVAIT besoin des 3 classes principales du projet pour la compilation et pour obtenir un `.jar`. ON POURRA par la suite supprimer les 3 classes du fichier `.jar`, pour qu'il ne reste que la classe `Peugeot`.

**Plugin n°2**
Créons un deuxième plugin (donc un nouveau projet avec la copie des 3 classes principales). Et ajoutons la classe Plombier:

```java
public class Plombier extends Personnage {
    
    /**
     * Renvoie un nom
     */
    public String getName(){
        return "Martin";
    }
    
    /**
     * Renvoie l'age du personnage
     */
    public int getOld(){
        return 21;
    }
    
}
```

Puis on compile le code, pour créer le plugin.

> On a donc un projet avec deux plugins que l'on place dans un dossier appelé `plugins`. Il faut maintenant charger les plugins dans le projet principal

> Pour rendre notre projet plus beau, renommons les extensions des plugins de `.jar` à `.plg`.

##### Chargement des plugins

```java
// On veut charger nos plugins aux démarrages et donc créer un Plombier et une Peugeot. 
// Or le projet principal ne connait pas ces deux classes puisqu'elles appartiennent à leur plugin respectif.
public static void main(String[] args){
    
    // On crée un objet File représentant le dossier où se trouve les plugins
    File plugins = new File("plugins");
    
    // On crée un objet LoaderPlugin qui va charger tous les plugins avec l'extension *.*
    LoaderPlugin lp = new LoaderPlugin(plugins);
    
    // On crée un objet LoaderPlugin qui va charger tous les plugins avec l'extension *.plg
    lp = new LoaderPlugin(plugins, "plg");
    
    // On récupère le nombre de plugins chargés (=2)
    int countPlugins = lp.size();
    
    
    
    // Comme on peut charger plusieurs plugins en même temps, un curseur va pointer le plugin que l'on veut interroger
    lp.setPosition(0); // écoute le plugin 0
    
    // On peut récupérer le fichier qui fait office de plugin 0
    File plugin0 = lp.getFilePlugin();
    
    // On récupère des objets instanciés du plugin Peugeot.plg (puisqu'il s'agit du 1er plugin)
    Object[] obj0 = lp.getObject(Vehicule.class); // On ne peut pas mettre Peugeot.class puisque l'on ne connait pas la classe
    
    //On parcourt la liste des objets Véhicule que l'on vient de recevoir
    for(int i = 0; i < obj0.length; i++){
        
        // On caste l'objet en véhicule puisque l'on sait que le tableau contient que des véhicules (et parce que l'on connait la classe véhicule)
        Vehicule v = (Vehicule) obj0[i];
        
        // Affiche le véhicule
        System.out.println(v.getName() + ": " + v.countWheels() + ")";
    }
    
    
    
    // Ecoute le plugin 1
    lp.setPosition(1);
    
    // On peut récupérer le fichier qui fait office de plugin 1
    File plugin1 = lp.getFilePlugin();
    
    // On récupère des objets instanciés du plugin Plombier.plg (puisqu'il s'agit du 2ème plugin)
    Object[] obj1 = lp.getObject(Personnage.class);
    
    //On parcourt la liste des objets Personnage que l'on vient de recevoir
    for(int i = 0; i < obj1.length; i++){
        
        // On caste l'objet en personnage puisque l'on sait que le tableau contient que des personnages
        Personnage p = (Personnage) obj1[i];
        
        // Affiche le personnage p
        System.out.println(p.getName() + ": " + p.getOld() + ")";
    }
}
```

Nous obtenons le résultat de console suivant
```
Peugeot: 4
Martin: 21
```

##### Quelques subtilités

Si on modifiait la ligne suivante:
```java
Object[] obj1 = lp.getObject(Personnage.class);
```
en:
```java
Object[] obj1 = lp.getObject(Vehicule.class);
```
Une erreur survient car le système ne peut pas caster un `Plombier` en `Vehicule`.

Si on souhaite afficher tous les noms des objets de tous les plugins, voici comment on procède:
```java
// On crée un objet LoaderPlugin qui va charger tous les plugins avec l'extension *.plg
LoaderPlugin lp = new LoaderPlugin(new File("plugins"), "plg");

// On récupère le nombre de plugins chargés (=2)
int countPlugins = lp.size();

// On parcourt le nombre de plugin
for(int i = 0; i < countPlugins; i++){
    
    //On positionne le curseur
    lp.setPosition(i);
    
    // On peut récupérer le fichier qui fait office du plugin pointé en i
    File plugin = lp.getFilePlugin();
    
    // On récupère des objets instanciés du plugin pointé en i
    Object[] obj = lp.getObject(Name.class);
    
    // On parcourt les objets instanciés du plugin pointé en i
    for(int j = 0; j < obj.length; j++){
        
        // On caste l'objet en Name puisqu'on récupère des objets Name
        Name n = (Name) obj[j];
        
        // Affiche le nom de l'objet
        System.out.println(n.getName());
    }
}
```

On obtient le résultat suivant:
```
Peugeot
Martin
```

On ne peut pas remplacer la ligne:
```java
Object[] obj = lp.getObject(Name.class);
```
par:
```java
Object[] obj = lp.getObject(Personnage.class, Vehicule.class);
```
...dans l'espoir d'obtenir le même résultat. En effet, aucun résultat ne serait trouvé, car le système ne trouve aucune classe dans les plugins, faisant partie et de Personnage.class et de Vehicule.class.

> Conclusion: Pour créer un plugin, il faut penser avant tout à bien fournir l'interface ou classe de référence du projet principale pour que les plugins puissent avoir un réel potentiel.

# 14. **OS**
Cette classe permet de déterminer le système d'exploitation où le programme est exécuté. Voici un exemple:
```java
//Affiche si le système qui exécute le programme est un système Windows 10
System.out.println(OS.IS_WINDOWS_10); //true

//Affiche si le système qui exécute le programme est un système linux
System.out.println(OS.IS_LINUX); //false

//...
```

# 15. **OTC**
Il arrive parfois utile de déterminer le temps que met un bout de code à s'exécuter. C'est possible grâce à la classe ```OTC```. Il existe deux manières pour celà.
### Premier exemple
```java
//Récupère la durée d'un bout de code
long duree = OTC.duration(new Runnable() {
    @Override
    public void run() {
        //Le code à exécuter
    }
});

//Affiche la durée
System.out.println("Le code a mis " + duree + " millisecondes à s'exécuter !"); //Le code a mis 3 millisecondes à s'exécuter !
```
### Deuxième exemple
```java
//Pose un tag de départ pour un bout de code
OTC.start("IdUnique");

    /*
    ICI le code qui s'exécute
    */

//Pose un tag de fin pour un bout de code et renvoie la durée
long duree = OTC.stop("IdUnique");

//Affiche la durée
System.out.println("Le code a mis " + duree + " millisecondes à s'exécuter !"); //Le code a mis 3 millisecondes à s'exécuter !
```

# 16. **Serializer**
La classe ```Serializer``` permet de transformer un objet ```Serializable``` en tableau de byte[] et inversement. Voici un exemple:
```java
//Crée un objet
Personnage personne = new Personnage("Durant", "Martin", 27);

//L'objet Personnage est maintenant serialisé en un tableau de byte[]
byte[] datas = Serializer.getData(personne);

//Le tableau de byte[] est maintenant déserialisé en un objet Personnage
Personnage personneDeserialisée = (Personnage) Serializer.getObject(datas);
```

# 17. **Strings**
La classe ```Strings``` apporte quelques méthodes utiles permettant de manipuler des chaînes de caractères. Voici un exemple:
```java
//Génère une chaîne de caractères avec seulement des caractères numériques
String str = Strings.generateLowerDigit(20);

//Génère une chaîne de caractères avec seulement des caractères alphabétiques minuscules
str = Strings.generateUpper(20);

//Génère une chaîne de caractères avec seulement des caractères alphabétiques minuscules
str = Strings.generateLower(20);

//Affiche la chaîne générée
System.out.println(str); //ljjiqwtoygxysgmrujkg
```
# 18. **WinUser**
> Attention : la classe ```WinUser``` ne fonctionne que sur les systèmes windows

La classe ```WinUser``` permet de manipuler des fenêtres autres que java, obtenir des informations d'affichages, des informations systèmes (comme le nombre de bouton contenu sur la souris)... Voici un exemple ultra simplifié de ce que la classe peut faire:
```java
//Liste la liste des fenêtres windows ouvertes
WinUser.Window[] fenêtres = WinUser.listOpenedWindows();

//Affiche la liste des fenêtres
for(WinUser.Window fenêtre : fenêtres)
    System.out.println(fenêtre.getName());

//...
```

> Pour plus d'information, veuillez consulter la javadoc de cette classe

# 19. **Utilisation de la librairie**
La librairie ```Util-*.*.jar``` fait référence à plusieurs autres projets (cf: Introduction). Ces sous projets sont contenus dans le fichier. En revanche le fichier ```Util-*.*-without-dependencies.jar``` ne les contient pas. Pensez donc à les ajouter dans le classpath si vous utilisez cette version.

# 20. **Licence**
Le projet est sous licence "GNU General Public License v3.0"

## Accès au projet GitHub => [ici](https://github.com/josephbriguet01/Util "Accès au projet Git Util")