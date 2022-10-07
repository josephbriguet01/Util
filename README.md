Copyright © JasonPercus Systems, Inc - All Rights Reserved
# **Introduction**

Cette librairie apporte une multitude de fonctions utiles permettant tout et n’importe quoi. Vous souhaitez faire des requêtes vers une base MySQL ? Charger des plugins ? Gèrer des threads ? Alors cette librairie est faite pour vous. De plus elle intègre en son sein 4 projets connues. À savoir :

- gson-2.8.2
- javaparser-core-3.22.1
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

# 2. **MySQL**
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

# 3. **ExpandableArray**
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

# 4. **Code**
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

# 5. **Key**

   ## 5.1. Classe Code

Cette classe permet de représenter une touche de clavier. Chaque touche de clavier est représenté avec Java par en entier. Cette classe n'est pas instanciable, elle permet seulement de faire le lien entre un entier et le nom d'une touche
```java
//Affiche le nom d'une touche de clavier
System.out.println(Code.name(65)); //A

//Et inversement affiche le code d'une touche de clavier
System.out.println(Code.code("A")); //65
```

   ## 5.2. Classe Combination

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

   ## 5.3. Classe CombinationCapture

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

# 6. **MouseArea**

   ## 6.1. Enumération ModeArea

Cette énumération énumère deux modes. Le mode additif et le mode soustractif.
- Le mode additif: permet d'ajouter une zone qui sera contrôlée. Autrement dit lorsque la souris passera sur la zone en question, il se passera quelque chose.
- Le mode soustractif: permet de supprimer une zone qui est contrôlée. Autrement dit lorsque la souris passera sur la zone en question, il ne se passera plus rien.

Ces deux modes doivent être ajoutés au besoin, à une zone pour définir une zone de contrôle.

   ## 6.2. Classe Zone

Cette classe représente un ```java.awt.Graphics``` invisible qui servira comme zone de contrôle lorsque la souris passera dessus. Donc quasiment tout ce qui peut être fait avec un objet ```Graphics``` peut être fait avec un objet ```Zone```.

> Pour plus de précision lire la javadoc associée avec cette classe

# 7. **NumberConverter**
Cette classe permet de convertir des nombres entiers en un tableau de bytes et vice-versa. Exemple:
```java
//Conversion d'un entier en un tableau de byte[]
int i = 15;
byte[] array = NumberConverter.intTobytes(i);

//Conversion d'un tableau de byte[] en un entier
int j = NumberConverter.bytesToInt(array);
```
> Bien sûr, il est possible de réaliser les mêmes opérations avec des ```long``` et des ```short```.

# 8. **Process**
   ## 8.1. Process OS
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

   ## 8.2. Process java
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

# 9. **Thread**
***Cette section n'est pas détaillée pour le moment. Veuillez consulter la javadoc qui explique comment utiliser les différentes classes et interface***
Voici un tableau récapitulatif du fonctionnement des classes et interfaces principales:

| Nom            |    Type   | Description sommaire                                                                                                   |
|----------------|:---------:|------------------------------------------------------------------------------------------------------------------------|
| CodeProcessing |   class   | Permet de lancer plusieurs threads en même temps et attend leurs résultats respectifs                                  |
| During         |   class   | Est équivalent à l'interface Runnable, mais renvoie un résultat. Elle est couplé avec CodeProcessing                   |
| OnCodeProcess  | interface | Cette interface permet d'être au courant de chaque changement d'état d'un objet CodeProcessing                         |
| TimedResult    |   class   | Execute un bout de code en un temps imparti. Si celui-ci dépasse le temps indiqué une valeur par défaut est envoyée    |
| CodeTimeResult | interface | Cette interface permet de définir le bout de code a être réalisé en un temps imparti. Elle est couplé avec TimedResult |
| ThreadPool     |   class   | Voir section 11.1 pour plus d'informations                                                                             |

   ## 9.1. ThreadPool
Cette classe permet de lancer plusieurs actions sur un pool de threads. Lorsqu'un thread est disponible, il se charge d'exécuter une tâche contenu dans la file d'attente des actions à exécuter. Une fois que le thread a terminé sa tâche il en prend une nouvelle et ainsi de suite. Si le pool contient n threads, alors ces threads vont se répartir la charge des tâches à réaliser.

```java
//Crée un pool de 4 threads
ThreadPool pool = new ThreadPool(4);

//Ajoute une tâche a exécuter. Elle sera prise dès que possible par l'un des 4 threads du pool
pool.execute(new Runnable(){...});

//Ajoute encore une tâche a exécuter. Elle sera prise dès que possible par l'un des 4 threads du pool. 
//Et ainsi de suite...
pool.execute(new Runnable(){...});

//Ferme le pool. Si des tâches sont en cours, alors le système attend que les tâches sont terminées. 
//En revanche les tâches qui n'ont pas encore été exécutés ne seront pas lancé
pool.close();

//Ferme le pool de force. Si des tâches sont en cours, celle-ci seront fermée de force.
pool.closeNow();
```

##### Monitor
Il est possible de joindre un objet ```Monitor``` à un pool. Cela permet entre autre d'être mis au courant des changements d'état des threads ainsi que des tâches en cours à tout moment.

```java
ThreadPool.Monitor monitor = new ThreadPool.Monitor(){
    
    public void started(){
        //Lorsque le pool démarre (Remarque: une première tâche doit être donnée pour lancer le pool)
    }
    
    public void update(Runnable runnable, 
                        StatusThread status, 
                        int poolSize, 
                        int corePoolSize, 
                        int activeCount, 
                        long completedTaskCount, 
                        long taskCount){
        //Est appelée a chaque changement d'état d'un thread
        
        //Si la tâche runnable vient d'être prise en charge par un thread du pool
        if(status == ThreadPool.StatusThread.Playing){
            
        }
        
        //Si la tâche runnable est terminée. Le thread qui l'exécutait va pouvoir prendre une nouvelle tâche
        if(status == ThreadPool.StatusThread.STOPPED){
            
        }
    }
    
    public void stopped(){
        //Lorsque le pool est arrêté
    }
    
};
```

Pour attribuer un ```Monitor``` à un ```ThreadPool```:
```java
pool.setMonitor(monitor);
```

# 10. **File**
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

# 11. **LoaderPlugin**
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

# 12. **Locker**
Cette classe permet de bloquer un bout de code. Par exemple il peut servir à attendre un résultat avant de reprendre l'exécution du programme.

```java
//Imaginons que l'on veut exécuter une addition par un thread et récupérer le résultat après l'exécution du thread

//Création des valeurs à additionner
int a = 15;
int b = 37;

//Crée un objet Runnable
RunnableWithResult runnable = new RunnableWithResult(a, b){
    
    public void run(){
        //Récupère les paramètres a et b
        int a = (int) getParams()[0];
        int b = (int) getParams()[1];
        
        //Additionne les 2 nombres
        int c = a + b;
        
        //Renvoie le résultat
        returnResult(c);
    }
    
};

//Crée et exécute le thread
new Thread(runnable).start();

//Imaginons que nous avons ici un mécanisme qui attend que le thread se termine avant de récupérer le résultat
>...wait...<

//Récupère le résultat de l'exécution du thread. Evidemment il faut attendre que celui-ci soit terminé
int result = (int) runnable.getResult();

//Affiche le résultat
System.out.println(String.format("Résultat: %d + %d = %d", a, b, result)); //Résulat 15 + 37 = 52
```

> Comment faire pour que l'instruction >...wait...< attende la fin de l'exécution du thread avant de passer à la récupération du résultat ?

C'est tout simple il suffit d'utiliser un objet ```Locker```. Il existe 2 façons d'utiliser un Locker:
 * Par un objet ```Locker``` dédié
 * Par une chaîne de caractère qui servira d'id
 
### Locker dédié
```java
//On crée un locker
Locker locker = new Locker();
```
Il suffit de remplacer ```>...wait...<``` (l'exemple précédemment utilisé) par:
```java
//Bloque l'exécution du programme. 
//Ainsi le thread restera sur cette instruction jusqu'à ce que locker soit débloqué
locker.lock();
```
Pour débloquer le ```Locker``` dans notre exemple, il nous faut le résultat de l'addition:
```java
//Crée un objet Runnable
RunnableWithResult runnable = new RunnableWithResult(a, b){
    
    public void run(){
        //Récupère les paramètres a et b
        int a = (int) getParams()[0];
        int b = (int) getParams()[1];
        
        //Additionne les 2 nombres
        int c = a + b;
        
        //Renvoie le résultat
        returnResult(c);
        
        //Comme nous possédons notre résultat, alors on débloque le Locker
        locker.unlock();
    }
    
};
```

### Par un ID
C'est presque plus simple d'utiliser un ```Locker``` par son ID. Il suffit d'utiliser la méthode ```Locker.createAndLock([id])``` à la place de ```locker.lock()```. Ainsi il n'y a pas besoin de créer de locker, car la méthode se charge de créer un ```Locker``` pour cet ID. De même qu'il suffit d'utiliser la méthode ```Locker.unlockAndDestroy([id])``` à la place de ```locker.unlock()```. Voici à quoi ressemblerait l'exmple une fois modifié:
```java
//Imaginons que l'on veut exécuter une addition par un thread et récupérer le résultat après l'exécution du thread

//Création des valeurs à additionner
int a = 15;
int b = 37;

//Crée un objet Runnable
RunnableWithResult runnable = new RunnableWithResult(a, b){
    
    public void run(){
        //Récupère les paramètres a et b
        int a = (int) getParams()[0];
        int b = (int) getParams()[1];
        
        //Additionne les 2 nombres
        int c = a + b;
        
        //Renvoie le résultat
        returnResult(c);
        
        //Débloque la méthode createAndLock("MonID")
        Locker.unlockAndDestroy("MonID");
    }
    
};

//Crée et exécute le thread
new Thread(runnable).start();

//On attend le résultat de l'opération
Locker.createAndLock("MonID");

//Récupère le résultat de l'exécution du thread. Evidemment il faut attendre que celui-ci soit terminé
int result = (int) runnable.getResult();

//Affiche le résultat
System.out.println(String.format("Résultat: %d + %d = %d", a, b, result)); //Résulat 15 + 37 = 52
```

# 13. **OS**
Cette classe permet de déterminer le système d'exploitation où le programme est exécuté. Voici un exemple:
```java
//Affiche si le système qui exécute le programme est un système Windows 10
System.out.println(OS.IS_WINDOWS_10); //true

//Affiche si le système qui exécute le programme est un système linux
System.out.println(OS.IS_LINUX); //false

//...
```

# 14. **OTC**
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

# 15. **RunnableWithResult**
Cette classe abstraite est une interface ```Runnable``` classique. Elle ajoute cependant deux fonctionnalitées qui peuvent s'avérer pratiques.
 * Transmettre des paramètres, de manière à ce que la méthode ```run()``` puisse y accéder
 * Faire retourner un résultat à la méthode ```run()``` grâce à la méthode ```returnResult(Object result)``` qui bien sûr doit être utilisé en toute fin de la méthode ```run()```

Voici un exemple simplifié:
```java
//Imaginons que l'on veut exécuter une addition par un thread et récupérer le résultat après l'exécution du thread

//Création des valeurs à additionner
int a = 15;
int b = 37;

//Crée un objet Runnable
RunnableWithResult runnable = new RunnableWithResult(a, b){
    
    public void run(){
        //Récupère les paramètres a et b
        int a = (int) getParams()[0];
        int b = (int) getParams()[1];
        
        //Additionne les 2 nombres
        int c = a + b;
        
        //Renvoie le résultat
        returnResult(c);
    }
    
};

//Crée et exécute le thread
new Thread(runnable).start();

//Imaginons que nous avons ici un mécanisme qui attend que le thread se termine avant de récupérer le résultat
...wait...

//Récupère le résultat de l'exécution du thread. Evidemment il faut attendre que celui-ci soit terminé
int result = (int) runnable.getResult();

//Affiche le résultat
System.out.println(String.format("Résultat: %d + %d = %d", a, b, result)); //Résulat 15 + 37 = 52
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

# 19. **WinBatteryInfo**
> Attention : la classe ```WinBatteryInfo``` ne fonctionne que sur les systèmes windows

La classe ```WinBatteryInfo``` permet de récupérer l'état de la batterie du système windows... Voici un exemple ultra simplifié de ce que la classe peut faire:
```java
//Récupère les infos de la batterie
WinBatteryInfo infos = WinBatteryInfo.get();

//Affiche les infos de la batterie
System.out.println(infos);
```

> Pour plus d'information, veuillez consulter la javadoc de cette classe

# 20. **com.jasonpercus.util.desktop.Screen**
Il est possible d'obtenir la liste des écrans connectés, leurs tailles et leurs positions les uns par rapport aux autres.
```java
// Renvoie la liste des écrans connectés
Screen[] screens = Screen.getScreens();

// Parcourt la liste des écrans
for(Screen screen : screens){
    
    // Détermine s'il s'agit de l'écran principal
    boolean isPrimary = screen.isPrimary();
    
    // Renvoie la taille et la position d'un Desktop sur cet écran. 
    // C'est à dire en tenant compte de la barre des tâches...
    Desktop desktop = screen.getDesktop();
    
    // ...
}
```

# 21. **com.jasonpercus.util.desktop.Desktop**
Il est possible d'obtenir la liste des bureaux des écrans connectés, leurs tailles et leurs positions les uns par rapport aux autres.
```java
// Renvoie la liste des bureaux des écrans connectés
Desktop[] desktops = Desktop.getDesktops();

// Parcourt la liste des bureaux
for(Desktop desktop : desktops){
    
    // Détermine s'il s'agit du bureau principal
    boolean isPrimary = desktop.isPrimary();
    
    // Renvoie la taille et la position de l'écran qui possède ce bureau
    Screen screen = desktop.getScreen();
    
    // ...
}
```

# 22. **Clipboard**
Cette classe permet de gérer les chaînes "String" dans le presse papier.
Ainsi on peut:
### 1) Copier du texte dans le presse papier
```java
Clipboard.copy("Ma chaîne de caractères à copier");
```

### 2) Récupérer du texte du presse papier
```java
// Récupère le texte du Clipboard
String toGet = Clipboard.get();

// S'il n'est pas null, alors...
if (toGet != null) {

    // On peut l'utiliser ici sans aucun problème
    System.out.println(toGet);
    
} else {

    // Sinon il y une erreur ou un problème
    System.err.println("Rien n'est contenu dans le presse papier ou il ne s'agit pas d'un texte !");
    
}
```

### 3) Coller du texte venant du presse papier
Pour ce dernier exemple, on part du principe que le carret de texte apparait dans un composant texte.
```java
try{
    Clipboard.paste();
}catch(AWTException ex){
    System.err.println("Il y a une erreur lors du collage");
}
```

# 23. **ArrayQueue & ArrayQueueDetector**
### ArrayQueue
Lorsque l'on souhaite ajouter des données dans une file de taille fixe et les consumer au fur et à mesure de leur ajout, alors l'ArrayQueue répond à ce besoin. Imaginons une file pouvant contenir 3 objets maximum.
File = [rien][rien][rien]
 * Ajoutons l'élément "Lundi":
*File =* **["Lundi"]**[rien][rien]
 * Puis l'élément "Mardi":
*File =* **["Lundi"]["Mardi"]**[rien]
 * Puis l'élément "Mercredi":
*File =* **["Lundi"]["Mardi"]["Mercredi"]**

La file est maintenant pleine.
 * Mais nous voulons ajouter l'élément "Jeudi"
*File =* **["Mardi"]["Mercredi"]*****["Jeudi"]***

> On constate que l'élément **"Lundi"** a été supprimé, puis tous les éléments restant décallés vers la gauche, puis **"Jeudi"** a été ajouté à la fin.

Maintenant voyons avec le code ce que cela donne:
```java
// Création d'une file de 3 cases de chaînes de caractères
ArrayQueue<String> queue = new ArrayQueue<>(3);

// Ajoute quelques éléments
queue.put("Lundi");    // la méthode renvoie null car aucune ancienne valeur n'a été trouvé
queue.put("Mardi");    // la méthode renvoie null car aucune ancienne valeur n'a été trouvé
queue.put("Mercredi"); // la méthode renvoie null car aucune ancienne valeur n'a été trouvé
queue.put("Jeudi");    // la méthode renvoie "Lundi" car il s'agit de la valeur qui a été éjecté de la file

// Récupérons une valeur
System.out.println(queue.get(1)); // Affichera "Mercredi"

// Affiche la file
System.out.println(queue); // Affichera "[Mardi, Mercredi, Jeudi]"
```

### ArrayQueueDetector
Supposons maintenant que nous souhaitons savoir lorsque la file contient dans cet ordre les éléments "Mardi", "Mercredi", "Jeudi". Alors il faut utiliser ArrayQueueDetector.
Reprenons le même exemple ci-dessus:
```java
// Créons un détecteur. Celui-ci affichera les séquences trouvés dans la file
ArrayQueueDetector.IDetector<String> detector = new ArrayQueueDetector.IDetector<String>(){
    @Override
    public void sequenceDetected(String[] sequence) {
        System.out.println(Arrays.toString(sequence));
    }
};

// Création d'une file de 3 cases de chaînes de caractères. On donne également le détecteur
ArrayQueueDetector<String> queue = new ArrayQueueDetector<>(3, detector);

// Maintenant il faut donner la séquence recherchée
// Ce qui veut dire qu'à chaque fois que la file possèdera exactement dans cet ordre le modèle alors, 
// un évènement sera créé dans l'objet "detector" (voir plus haut)
queue.addModelToDetect(new String[]{"Mardi", "Mercredi", "Jeudi"});

// Ajoute quelques éléments
queue.put("Lundi");    // Le détecteur ne réagit pas
queue.put("Mardi");    // Le détecteur ne réagit pas
queue.put("Mercredi"); // Le détecteur ne réagit pas
queue.put("Jeudi");    // Le détecteur réagit car la file contient "Mardi", "Mercredi", "Jeudi"
queue.put("Vendredi"); // Le détecteur ne réagit pas car la file contient "Mercredi", "Jeudi", "Vendredi"

// Récupérons une valeur
System.out.println(queue.get(1)); // Affichera "Jeudi"

// Affiche la file
System.out.println(queue); // Affichera "[Mercredi, Jeudi, Vendredi]"
```

> Il est possible d'ajouter plusieurs modèles à un ```ArrayQueueDetector``` avec la méthode ```addModelToDetect```.

> Il est possible de supprimer un modèle avec la méthode ```removeModelToDetect```.

# 24. **Utilisation de la librairie**
La librairie ```Util-*.*.jar``` fait référence à plusieurs autres projets (cf: Introduction). Ces sous projets sont contenus dans le fichier. En revanche le fichier ```Util-*.*-without-dependencies.jar``` ne les contient pas. Pensez donc à les ajouter dans le classpath si vous utilisez cette version.

# 25. **Licence**
Le projet est sous licence "GNU General Public License v3.0"

## Accès au projet GitHub => [ici](https://github.com/josephbriguet01/Util "Accès au projet Git Util")