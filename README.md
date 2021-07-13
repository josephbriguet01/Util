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
# 5. **Network**
# 6. **ExpandableArray**
# 7. **Code**
# 8. **Key**
# 9. **MouseArea**
# 10. **Process**
   ## 10.1. Process OS
   ## 10.2. Process java
# 11. **Thread**
# 12. **File**
# 13. **LoaderPlugin**
# 14. **OS**
# 15. **OTC**
# 16. **Serializer**
# 17. **Strings**
# 18. **WinUser**