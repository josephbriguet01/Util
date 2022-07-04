/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util;



import com.jasonpercus.util.array.ExpandableArray;



/**
 * Correspond à une classe représentant des représentations abstraites de chemins, de fichiers et de répertoires.
 * @see java.io.File
 * @author JasonPercus
 * @version 1.1
 */
public class File extends java.io.File {
    
    
    
    // <editor-fold defaultstate="collapsed" desc="SERIAL_VERSION_UID">
    /**
     * Correspond au numéro de série qui identifie le type de dé/sérialization utilisé pour l'objet
     */
    private static final long serialVersionUID = 1L;
    // </editor-fold>

    
    
//CONSTRUCTORS
    /**
     * Crée une nouvelle instance de File en convertissant la chaîne de chemin d'accès donnée en un chemin d'accès abstrait
     * @param pathname Correspond à une chaîne de chemin
     */
    public File(String pathname) {
        super(pathname);
    }
    
    /**
     * Crée une nouvelle instance de File en convertissant le fichier donné en un chemin d'accès abstrait
     * @param file Correspond au fichier à convertir
     */
    public File(java.io.File file){
        super(path(file));
    }

    /**
     * Crée une nouvelle instance de File à partir d'une chaîne de chemin parent et d'une chaîne de chemin enfant
     * @param parent Correspond à la chaîne de chemin d'accès parent
     * @param child Correspond à la chaîne de chemin d'accès enfant
     */
    public File(String parent, String child) {
        super(parent, child);
    }

    /**
     * Crée une nouvelle instance de fichier à partir d'un chemin d'accès abstrait parent et d'une chaîne de chemin d'accès enfant
     * @param parent Correspond au chemin d'accès abstrait parent
     * @param child Correspond à la chaîne de chemin d'accès enfant
     */
    public File(java.io.File parent, String child) {
        super(parent, child);
    }

    /**
     * Crée une nouvelle instance de File en convertissant le fichier donné: URI en un chemin abstrait
     * @param uri Correspond à une URI hiérarchique absolu avec un schéma égal à "fichier", un composant de chemin non vide et des composants d'autorité, de requête et de fragment non définis
     */
    public File(java.net.URI uri) {
        super(uri);
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie la date de création du fichier
     * @return Retourne la date de création du fichier
     * @throws java.io.FileNotFoundException Si le fichier n'existe pas
     */
    public java.util.Date getCreationDate() throws java.io.FileNotFoundException {
        if(this.exists()){
            try {
                java.nio.file.attribute.FileTime creationTime = (java.nio.file.attribute.FileTime) java.nio.file.Files.getAttribute(toPath(), "creationTime");
                return new java.util.Date(creationTime.toMillis());
            } catch (java.io.IOException ex) {
                return null;
            }
        }else throw new java.io.FileNotFoundException(this+" not exists !");
    }
    
    /**
     * Renvoie l'extension du fichier
     * @return Retourne l'extension du fichier sans son point
     * @throws java.io.FileNotFoundException Si le fichier n'existe pas
     */
    public String getExtension() throws java.io.FileNotFoundException {
        return getExtension(this);
    }

    /**
     * Renvoie une liste de fichiers filtrés par leur extension
     * @param extensions Correspond à la liste des extensions qui seront autorisés à apparaître dans la liste filtré de sortie
     * @return Retourne une liste de fichiers filtrés par leur extension
     */
    public File[] listFilesByExtension(String...extensions) {
        java.io.File[] files = this.listFiles((java.io.File pathname) -> {
            File f = castTo(pathname);
            try {
                String ext = f.getExtension();
                
                if(extensions == null) return false;
                if(extensions.length == 0) return false;
                
                for(String extension : extensions){
                    if(extension != null && extension.equals(ext)){
                        return true;
                    }
                }
                return false;
                
            } catch (java.io.FileNotFoundException ex) {
                return false;
            }
        });
        File[] newFile = new File[files.length];
        for(int i=0;i<files.length;i++){
            newFile[i] = new File(files[i]);
        }
        sortByNameASC(newFile);
        return newFile;
    }

    /**
     * Renvoie une liste de noms de fichiers filtrés par leur extension
     * @param extensions Correspond à la liste des extensions qui seront autorisés à apparaître dans la liste filtré de sortie
     * @return Retourne une liste de noms de fichiers filtrés par leur extension
     */
    public String[] listByExtension(String...extensions) {
        File[] files = listFilesByExtension(extensions);
        if(files == null) return null;
        String[] strs = new String[files.length];
        for(int i=0;i<files.length;i++){
            strs[i] = files[i].getName();
        }
        return strs;
    }

    
    
//OVERRIDED
    /**
     * Returns the absolute form of this abstract pathname.  Equivalent to
     * <code>new&nbsp;File(this.{@link #getAbsolutePath})</code>.
     *
     * @return  The absolute abstract pathname denoting the same file or
     *          directory as this abstract pathname
     *
     * @throws  SecurityException
     *          If a required system property value cannot be accessed.
     *
     * @since 1.2
     */
    @Override
    public java.io.File getAbsoluteFile() {
        return castTo(super.getAbsoluteFile());
    }

    /**
     * Returns an array of abstract pathnames denoting the files and
     * directories in the directory denoted by this abstract pathname that
     * satisfy the specified filter.  The behavior of this method is the same
     * as that of the {@link #listFiles()} method, except that the pathnames in
     * the returned array must satisfy the filter.  If the given {@code filter}
     * is {@code null} then all pathnames are accepted.  Otherwise, a pathname
     * satisfies the filter if and only if the value {@code true} results when
     * the {@link java.io.FileFilter#accept FileFilter.accept(File)} method of the
     * filter is invoked on the pathname.
     *
     * @param  filter
     *         A file filter
     *
     * @return  An array of abstract pathnames denoting the files and
     *          directories in the directory denoted by this abstract pathname.
     *          The array will be empty if the directory is empty.  Returns
     *          {@code null} if this abstract pathname does not denote a
     *          directory, or if an I/O error occurs.
     *
     * @throws  SecurityException
     *          If a security manager exists and its {@link
     *          SecurityManager#checkRead(String)} method denies read access to
     *          the directory
     *
     * @since  1.2
     * @see java.nio.file.Files#newDirectoryStream(Path,java.nio.file.DirectoryStream.Filter)
     */
    @Override
    public java.io.File[] listFiles(java.io.FileFilter filter) {
        return castTo(super.listFiles(filter));
    }

    /**
     * Returns an array of abstract pathnames denoting the files and
     * directories in the directory denoted by this abstract pathname that
     * satisfy the specified filter.  The behavior of this method is the same
     * as that of the {@link #listFiles()} method, except that the pathnames in
     * the returned array must satisfy the filter.  If the given {@code filter}
     * is {@code null} then all pathnames are accepted.  Otherwise, a pathname
     * satisfies the filter if and only if the value {@code true} results when
     * the {@link java.io.FilenameFilter#accept
     * FilenameFilter.accept(File,&nbsp;String)} method of the filter is
     * invoked on this abstract pathname and the name of a file or directory in
     * the directory that it denotes.
     *
     * @param  filter
     *         A filename filter
     *
     * @return  An array of abstract pathnames denoting the files and
     *          directories in the directory denoted by this abstract pathname.
     *          The array will be empty if the directory is empty.  Returns
     *          {@code null} if this abstract pathname does not denote a
     *          directory, or if an I/O error occurs.
     *
     * @throws  SecurityException
     *          If a security manager exists and its {@link
     *          SecurityManager#checkRead(String)} method denies read access to
     *          the directory
     *
     * @since  1.2
     * @see java.nio.file.Files#newDirectoryStream(Path,String)
     */
    @Override
    public java.io.File[] listFiles(java.io.FilenameFilter filter) {
        return castTo(super.listFiles(filter));
    }

    /**
     * Returns an array of abstract pathnames denoting the files in the
     * directory denoted by this abstract pathname.
     *
     * <p> If this abstract pathname does not denote a directory, then this
     * method returns {@code null}.  Otherwise an array of {@code File} objects
     * is returned, one for each file or directory in the directory.  Pathnames
     * denoting the directory itself and the directory's parent directory are
     * not included in the result.  Each resulting abstract pathname is
     * constructed from this abstract pathname using the {@link java.io.File#File(java.io.File, java.lang.String) File(File,&nbsp;String)} constructor.  Therefore if this
     * pathname is absolute then each resulting pathname is absolute; if this
     * pathname is relative then each resulting pathname will be relative to
     * the same directory.
     *
     * <p> There is no guarantee that the name strings in the resulting array
     * will appear in any specific order; they are not, in particular,
     * guaranteed to appear in alphabetical order.
     *
     * <p> Note that the {@link java.nio.file.Files} class defines the {@link
     * java.nio.file.Files#newDirectoryStream(Path) newDirectoryStream} method
     * to open a directory and iterate over the names of the files in the
     * directory. This may use less resources when working with very large
     * directories.
     *
     * @return  An array of abstract pathnames denoting the files and
     *          directories in the directory denoted by this abstract pathname.
     *          The array will be empty if the directory is empty.  Returns
     *          {@code null} if this abstract pathname does not denote a
     *          directory, or if an I/O error occurs.
     *
     * @throws  SecurityException
     *          If a security manager exists and its {@link
     *          SecurityManager#checkRead(String)} method denies read access to
     *          the directory
     *
     * @since  1.2
     */
    @Override
    public java.io.File[] listFiles() {
        return castTo(super.listFiles());
    }

    /**
     * Returns the canonical form of this abstract pathname.  Equivalent to
     * <code>new&nbsp;File(this.{@link #getCanonicalPath})</code>.
     *
     * @return  The canonical pathname string denoting the same file or
     *          directory as this abstract pathname
     *
     * @throws  java.io.IOException
     *          If an I/O error occurs, which is possible because the
     *          construction of the canonical pathname may require
     *          filesystem queries
     *
     * @throws  SecurityException
     *          If a required system property value cannot be accessed, or
     *          if a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead}</code> method denies
     *          read access to the file
     *
     * @since 1.2
     * @see     java.nio.file.Path#toRealPath
     */
    @Override
    public java.io.File getCanonicalFile() throws java.io.IOException {
        return castTo(super.getCanonicalFile());
    }

    /**
     * Returns the abstract pathname of this abstract pathname's parent,
     * or <code>null</code> if this pathname does not name a parent
     * directory.
     *
     * <p> The <em>parent</em> of an abstract pathname consists of the
     * pathname's prefix, if any, and each name in the pathname's name
     * sequence except for the last.  If the name sequence is empty then
     * the pathname does not name a parent directory.
     *
     * @return  The abstract pathname of the parent directory named by this
     *          abstract pathname, or <code>null</code> if this pathname
     *          does not name a parent
     *
     * @since 1.2
     */
    @Override
    public java.io.File getParentFile() {
        return castTo(super.getParentFile());
    }

    /**
     * Returns the length of the file denoted by this abstract pathname.
     * The return value is unspecified if this pathname denotes a directory.
     *
     * <p> Where it is required to distinguish an I/O exception from the case
     * that {@code 0L} is returned, or where several attributes of the same file
     * are required at the same time, then the {@link
     * java.nio.file.Files#readAttributes(Path,Class,LinkOption[])
     * Files.readAttributes} method may be used.
     *
     * @return  The length, in bytes, of the file denoted by this abstract
     *          pathname, or <code>0L</code> if the file does not exist.  Some
     *          operating systems may return <code>0L</code> for pathnames
     *          denoting system-dependent entities such as devices or pipes.
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *          method denies read access to the file
     */
    @Override
    public long length() {
        if(isDirectory())
            return length(this);
        else
            return super.length();
    }
    
    
    
//METHODES PUBLICS STATICS
    /**
     * Converti un {@link java.io.File} en {@link File} qui est la nouvelle version
     * @param file Correspond au fichier à convertir
     * @return Retourne la nouvelle instance de fichier
     */
    public static File castTo(java.io.File file){
        try {
            return new File(file.getCanonicalFile().getAbsolutePath());
        } catch (java.io.IOException ex) {
            return new File(file.getAbsolutePath());
        }
    }
    
    /**
     * Converti un tableau de {@link java.io.File} en un tableau de {@link File} qui est la nouvelle version
     * @param files Correspond au tableau de fichiers à convertir
     * @return Retourne le nouveau tableau de fichiers (nouvelle version)
     */
    public static File[] castTo(java.io.File[] files){
        File[] fs = new File[files.length];
        for(int i=0;i<files.length;i++){
            fs[i] = castTo(files[i]);
        }
        return fs;
    }
    
    /**
     * Renvoie la taille d'un fichier/dossier
     * @param file Correspond au fichier ou dossier dont on cherche à récupérer la taille
     * @return Retourne la taille d'un fichier/dossier
     */
    public static long length(java.io.File file){
        if(file.isDirectory()){
            long length = 0;
            java.io.File[] children = file.listFiles();
            for(java.io.File child : children){
                length += length(child);
            }
            return length;
        }else{
            return file.length();
        }
    }
    
    /**
     * Renvoie l'extension du fichier
     * @param file Correspond au fichier dont on souhaite récupérer l'extension
     * @return Retourne l'extension du fichier sans son point
     * @throws java.io.FileNotFoundException Si le fichier n'existe pas
     */
    public static String getExtension(java.io.File file) throws java.io.FileNotFoundException {
        if (file.exists()){
            if (file.isDirectory()) {
                return "";
            } else {
                String fileName = file.getName();
                int i = fileName.lastIndexOf('.');
                if (i > -1 )
                    return fileName.substring(i).replace(".", "");
                else
                    return "";
            }
        }else throw new java.io.FileNotFoundException(file+" not exists !");
    }
    
    /**
     * Renvoie la liste des extensions de la liste de fichiers. Si un fichier n'existe pas, son extension sera considérée comme null
     * @param files Correspond à la liste de fichiers dont on veut récupérer la liste des extensions
     * @return Retourne la liste des extensions de la liste de fichiers
     */
    public static String[] getExtensions(java.io.File[] files){
        String[] extensions = new String[files.length];
        for(int i=0;i<files.length;i++){
            try {
                extensions[i] = getExtension(files[i]);
            } catch (java.io.FileNotFoundException ex) {
                extensions[i] = null;
            }
        }
        return extensions;
    }
    
    /**
     * Renvoie la liste des extensions utilisable de la liste de fichiers (sans doublons, ni de valeurs null, ni de valeurs vides)
     * @param files Correspond à la liste de fichiers dont on veut récupérer la liste des extensions
     * @return Retourne la liste des extensions de la liste de fichiers
     */
    public static String[] getUsableExtensions(java.io.File[] files){
        ExpandableArray<String> array = new ExpandableArray<>(String.class);
        for(java.io.File file : files){
            try {
                String extension = getExtension(file);
                if(!extension.isEmpty() && !array.contains(extension)) array.put(extension);
            } catch (java.io.FileNotFoundException ex) {}
        }
        return array.toArray();
    }
    
    /**
     * Trie le tableau de fichiers par leur nom (A-Z)
     * @param files Correspond à la liste des fichiers à trier
     */
    @SuppressWarnings("Convert2Lambda")
    public static void sortByNameASC(java.io.File[] files){
        java.util.Arrays.sort(files, new java.util.Comparator<java.io.File>() {
            @Override
            public int compare(java.io.File o1, java.io.File o2) {
                if(o1.exists() && o2.exists())
                    return o1.getName().compareTo(o2.getName());
                else if(o1.exists() && !o2.exists())
                    return -1;
                else if(!o1.exists() && o2.exists())
                    return 1;
                else return 0;
            }
        });
    }
    
    /**
     * Trie le tableau de fichiers par leur nom (Z-A)
     * @param files Correspond à la liste des fichiers à trier
     */
    @SuppressWarnings("Convert2Lambda")
    public static void sortByNameDESC(java.io.File[] files){
        sortByNameASC(files);
        for (int i = 0; i < files.length / 2; i++) {
            java.io.File temp = files[i];
            files[i] = files[files.length - 1 - i];
            files[files.length - 1 - i] = temp;
        }
    }
    
    /**
     * Trie le tableau de fichiers par leur nom (A-Z) en ignorant la casse
     * @param files Correspond à la liste des fichiers à trier
     */
    @SuppressWarnings("Convert2Lambda")
    public static void sortByNameIgnoreCaseASC(java.io.File[] files){
        java.util.Arrays.sort(files, new java.util.Comparator<java.io.File>() {
            @Override
            public int compare(java.io.File o1, java.io.File o2) {
                if(o1.exists() && o2.exists())
                    return o1.getName().compareToIgnoreCase(o2.getName());
                else if(o1.exists() && !o2.exists())
                    return -1;
                else if(!o1.exists() && o2.exists())
                    return 1;
                else return 0;
            }
        });
    }
    
    /**
     * Trie le tableau de fichiers par leur nom (Z-A) en ignorant la casse
     * @param files Correspond à la liste des fichiers
     */
    @SuppressWarnings("Convert2Lambda")
    public static void sortByNameIgnoreCaseDESC(java.io.File[] files){
        sortByNameIgnoreCaseASC(files);
        for (int i = 0; i < files.length / 2; i++) {
            java.io.File temp = files[i];
            files[i] = files[files.length - 1 - i];
            files[files.length - 1 - i] = temp;
        }
    }
    
    /**
     * Trie le tableau de fichiers par leur date de création (de la plus ancienne à la plus récente)
     * @param files Correspond à la liste des fichiers à trier
     */
    @SuppressWarnings("Convert2Lambda")
    public static void sortByCreationDateASC(java.io.File[] files){
        java.util.Arrays.sort(files, new java.util.Comparator<java.io.File>() {
            @Override
            public int compare(java.io.File o1, java.io.File o2) {
                long l1, l2;
                try {
                    if(o1.exists())
                        l1 = ((java.nio.file.attribute.FileTime) java.nio.file.Files.getAttribute(o1.toPath(), "creationTime")).toMillis();
                    else
                        l1 = Long.MAX_VALUE;
                } catch (java.io.IOException ex) {
                    l1 = Long.MAX_VALUE;
                }
                try {
                    if(o2.exists())
                        l2 = ((java.nio.file.attribute.FileTime) java.nio.file.Files.getAttribute(o2.toPath(), "creationTime")).toMillis();
                    else
                        l2 = Long.MAX_VALUE;
                } catch (java.io.IOException ex) {
                    l2 = Long.MAX_VALUE;
                }
                if(l1 < l2) return -1;
                else if(l1 > l2) return 1;
                else return 0;
            }
        });
    }
    
    /**
     * Trie le tableau de fichiers par leur date de création (de la plus récente à la plus ancienne)
     * @param files Correspond à la liste des fichiers à trier
     */
    public static void sortByCreationDateDESC(java.io.File[] files){
        sortByCreationDateASC(files);
        Arrays.invert(files);
    }
    
    /**
     * Trie le tableau de fichiers par leur dernière date de modification (de la plus ancienne à la plus récente)
     * @param files Correspond à la liste des fichiers à trier
     */
    @SuppressWarnings("Convert2Lambda")
    public static void sortByLastDateModifiedASC(java.io.File[] files){
        java.util.Arrays.sort(files, new java.util.Comparator<java.io.File>() {
            @Override
            public int compare(java.io.File o1, java.io.File o2) {
                long l1, l2;
                if(o1.exists())
                    l1 = o1.lastModified();
                else
                    l1 = Long.MAX_VALUE;
                if(o2.exists())
                    l2 = o2.lastModified();
                else
                    l2 = Long.MAX_VALUE;
                if(l1 < l2) return -1;
                else if(l1 > l2) return 1;
                else return 0;
            }
        });
    }
    
    /**
     * Trie le tableau de fichiers par leur dernière date de modification (de la plus récente à la plus ancienne)
     * @param files Correspond à la liste des fichiers à trier
     */
    @SuppressWarnings("Convert2Lambda")
    public static void sortByLastDateModifiedDESC(java.io.File[] files){
        sortByLastDateModifiedASC(files);
        Arrays.invert(files);
    }
    
    /**
     * Trie le tableau de fichiers par leur taille (de la plus petite à la plus grande)
     * @param files Correspond à la liste des fichiers à trier
     */
    @SuppressWarnings("Convert2Lambda")
    public static void sortByLengthASC(java.io.File[] files){
        java.util.Arrays.sort(files, new java.util.Comparator<java.io.File>() {
            @Override
            public int compare(java.io.File o1, java.io.File o2) {
                long l1 = Long.MAX_VALUE;
                long l2 = Long.MAX_VALUE;
                if(o1.exists())
                    l1 = o1.length();
                if(o2.exists())
                    l2 = o2.length();
                if(l1 < l2) return -1;
                else if(l1 > l2) return 1;
                else return 0;
            }
        });
    }
    
    /**
     * Trie le tableau de fichiers par leur taille (de la plus petite à la plus grande)
     * @param files Correspond à la liste des fichiers à trier
     */
    @SuppressWarnings("Convert2Lambda")
    public static void sortByLengthDESC(java.io.File[] files){
        sortByLengthASC(files);
        Arrays.invert(files);
    }
    
    
    
//METHODE PRIVATE STATIC
    /**
     * Renvoie le chemin du fichier
     * @param file Correspond au fichier dont on veut récupérer le chemin
     * @return Retourne le chemin du fichier
     */
    private static String path(java.io.File file){
        try {
            return file.getCanonicalFile().getAbsolutePath();
        } catch (java.io.IOException ex) {
            return file.getAbsolutePath();
        }
    }
    
    
    
}