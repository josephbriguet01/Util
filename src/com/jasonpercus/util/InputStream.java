/*
 * Copyright (C) JasonPercus Systems, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by JasonPercus, 05/2021
 */
package com.jasonpercus.util;



/**
 * Cette classe permet de recevoir des évênements lors de la lecture d'un InputStream
 * @see java.io.InputStream
 * @see InputStreamListener
 * @author JasonPercus
 * @version 1.0
 */
public class InputStream extends java.io.InputStream implements Comparable<InputStream>{

    
    
//ATTRIBUT STATIC
    /**
     * Correspond au compteur d'id
     */
    private static int cpt = 0;
    
    
    
//ATTRIBUTS
    /**
     * Correspond à l'id de l'InputStream (utilisé pour {@link #equals(java.lang.Object) equals()})
     */
    private int id;
    
    /**
     * Correspond à l'InputStream qui sera lu et analysé
     */
    private java.io.InputStream stream;
    
    /**
     * Correspond au listener qui sera informé des changements d'états de l'InputStream
     */
    private InputStreamListener listener;
    
    /**
     * Détermine si la lecture des données a commancé
     */
    private boolean started;
    
    /**
     * Détermine si la lecture des données est terminée
     */
    private boolean finished;

    
    
//CONSTRUCTORS
    /**
     * Constructeur par défaut (ne pas utiliser)
     */
    private InputStream(){
        
    }
    
    /**
     * Crée un InputStream
     * @param stream Correspond à l'InputStream qui sera lu
     * @param listener Correspond au listener qui sera informé des changements d'états de l'InputStream
     */
    public InputStream(java.io.InputStream stream, InputStreamListener listener) {
        this.id = cpt++;
        this.stream = stream;
        this.listener = listener;
        this.started = false;
        this.finished = false;
    }
    
    
    
//METHODES PUBLICS
    /**
     * Renvoie si oui ou non, des données sont encore disponible
     * @return Retourne true si oui, sinon false
     */
    public boolean isAlive(){
        return !finished;
    }
    
    /**
     * Reads the next byte of data from the input stream. The value byte is returned as an int in the range 0 to 255. If no byte is available because the end of the stream has been reached, the value -1 is returned. This method blocks until input data is available, the end of the stream is detected, or an exception is thrown. A subclass must provide an implementation of this method.
     * @return the next byte of data, or -1 if the end of the stream is reached.
     * @throws java.io.IOException if an I/O error occurs.
     */
    @Override
    public int read() throws java.io.IOException {
        if(!started){
            started = true;
            if(listener != null)
                listener.started();
        }
        int entier = stream.read();
        if(entier<=-1){
            if(!finished){
                finished = true;
                if(listener != null)
                    listener.finished();
            }
        }
        return entier;
    }

    /**
     * Returns an estimate of the number of bytes that can be read (or skipped over) from this input stream without blocking by the next invocation of a method for this input stream. The next invocation might be the same thread or another thread. A single read or skip of this many bytes will not block, but may read or skip fewer bytes. Note that while some implementations of InputStream will return the total number of bytes in the stream, many will not. It is never correct to use the return value of this method to allocate a buffer intended to hold all data in this stream. A subclass' implementation of this method may choose to throw an IOException if this input stream has been closed by invoking the close() method. The available method for class InputStream always returns 0. This method should be overridden by subclasses.
     * @return an estimate of the number of bytes that can be read (or skipped over) from this input stream without blocking or 0 when it reaches the end of the input stream.
     * @throws java.io.IOException If an I/O error occurs.
     */
    @Override
    public int available() throws java.io.IOException {
        int i = stream.available();
        return i;
    }

    /**
     * Closes this input stream and releases any system resources associated with the stream. The close method of InputStream does nothing.
     * @throws java.io.IOException If an I/O error occurs.
     */
    @Override
    public void close() throws java.io.IOException {
        if (!finished) {
            finished = true;
            if (listener != null)
                listener.finished();
        }
        stream.close();
        super.close();
    }

    /**
     * Tests if this input stream supports the mark and reset methods. Whether or not mark and reset are supported is an invariant property of a particular input stream instance. The markSupported method of InputStream returns false.
     * @return If this stream instance supports the mark and reset methods; false otherwise.
     * @see #mark(int) mark(int)
     * @see #reset() reset()
     */
    @Override
    public boolean markSupported() {
        boolean b = stream.markSupported();
        return b;
    }

    /**
     * Repositions this stream to the position at the time the mark method was last called on this input stream. The general contract of reset is: If the method markSupported returns true, then: If the method mark has not been called since the stream was created, or the number of bytes read from the stream since mark was last called is larger than the argument to mark at that last call, then an IOException might be thrown. If such an IOException is not thrown, then the stream is reset to a state such that all the bytes read since the most recent call to mark (or since the start of the file, if mark has not been called) will be resupplied to subsequent callers of the read method, followed by any bytes that otherwise would have been the next input data as of the time of the call to reset. If the method markSupported returns false, then: The call to reset may throw an IOException. If an IOException is not thrown, then the stream is reset to a fixed state that depends on the particular type of the input stream and how it was created. The bytes that will be supplied to subsequent callers of the read method depend on the particular type of the input stream. The method reset for class InputStream does nothing except throw an IOException.
     * @throws java.io.IOException If this stream has not been marked or if the mark has been invalidated.
     * @see #mark(int) mark(int)
     * @see java.io.IOException IOException
     */
    @Override
    public synchronized void reset() throws java.io.IOException {
        stream.reset();
        super.reset();
    }

    /**
     * Marks the current position in this input stream. A subsequent call to the reset method repositions this stream at the last marked position so that subsequent reads re-read the same bytes. The readlimit arguments tells this input stream to allow that many bytes to be read before the mark position gets invalidated. The general contract of mark is that, if the method markSupported returns true, the stream somehow remembers all the bytes read after the call to mark and stands ready to supply those same bytes again if and whenever the method reset is called. However, the stream is not required to remember any data at all if more than readlimit bytes are read from the stream before reset is called. Marking a closed stream should not have any effect on the stream. The mark method of InputStream does nothing.
     * @param readlimit the maximum limit of bytes that can be read before the mark position becomes invalid.
     * @see #reset() reset()
     */
    @Override
    public synchronized void mark(int readlimit) {
        stream.mark(readlimit);
        super.mark(readlimit);
    }

    /**
     * Skips over and discards n bytes of data from this input stream. The skip method may, for a variety of reasons, end up skipping over some smaller number of bytes, possibly 0. This may result from any of a number of conditions; reaching end of file before n bytes have been skipped is only one possibility. The actual number of bytes skipped is returned. If n is negative, no bytes are skipped. The skip method of this class creates a byte array and then repeatedly reads into it until n bytes have been read or the end of the stream has been reached. Subclasses are encouraged to provide a more efficient implementation of this method. For instance, the implementation may depend on the ability to seek.
     * @param n the number of bytes to be skipped.
     * @return the actual number of bytes skipped.
     * @throws java.io.IOException If the stream does not support seek, or if some other I/O error occurs.
     */
    @Override
    public long skip(long n) throws java.io.IOException {
        long l = stream.skip(n);
        return l;
    }

    /**
     * Reads up to len bytes of data from the input stream into an array of bytes. An attempt is made to read as many as len bytes, but a smaller number may be read. The number of bytes actually read is returned as an integer. This method blocks until input data is available, end of file is detected, or an exception is thrown. If len is zero, then no bytes are read and 0 is returned; otherwise, there is an attempt to read at least one byte. If no byte is available because the stream is at end of file, the value -1 is returned; otherwise, at least one byte is read and stored into b. The first byte read is stored into element b[off], the next one into b[off+1], and so on. The number of bytes read is, at most, equal to len. Let k be the number of bytes actually read; these bytes will be stored in elements b[off] through b[off+k-1], leaving elements b[off+k] through b[off+len-1] unaffected. In every case, elements b[0] through b[off] and elements b[off+len] through b[b.length-1] are unaffected. The read(b, off, len) method for class InputStream simply calls the method read() repeatedly. If the first such call results in an IOException, that exception is returned from the call to the read(b, off, len) method. If any subsequent call to read() results in a IOException, the exception is caught and treated as if it were end of file; the bytes read up to that point are stored into b and the number of bytes read before the exception occurred is returned. The default implementation of this method blocks until the requested amount of input data len has been read, end of file is detected, or an exception is thrown. Subclasses are encouraged to provide a more efficient implementation of this method.
     * @param b the buffer into which the data is read.
     * @param off the start offset in array b at which the data is written.
     * @param len the maximum number of bytes to read.
     * @return the total number of bytes read into the buffer, or -1 if there is no more data because the end of the stream has been reached.
     * @throws java.io.IOException If the first byte cannot be read for any reason other than end of file, or if the input stream has been closed, or if some other I/O error occurs.
     * @throws java.lang.NullPointerException If b is null.
     * @throws java.lang.IndexOutOfBoundsException If off is negative, len is negative, or len is greater than b.length - off
     * @see #read() read()
     */
    @Override
    public int read(byte[] b, int off, int len) throws java.io.IOException {
        if(!started){
            started = true;
            if(listener != null)
                listener.started();
        }
        int i = stream.read(b, off, len);
        if(i<=-1){
            if(!finished){
                finished = true;
                if(listener != null)
                    listener.finished();
            }
        }
        return i;
    }

    /**
     * Reads some number of bytes from the input stream and stores them into the buffer array b. The number of bytes actually read is returned as an integer. This method blocks until input data is available, end of file is detected, or an exception is thrown. If the length of b is zero, then no bytes are read and 0 is returned; otherwise, there is an attempt to read at least one byte. If no byte is available because the stream is at the end of the file, the value -1 is returned; otherwise, at least one byte is read and stored into b. The first byte read is stored into element b[0], the next one into b[1], and so on. The number of bytes read is, at most, equal to the length of b. Let k be the number of bytes actually read; these bytes will be stored in elements b[0] through b[k-1], leaving elements b[k] through b[b.length-1] unaffected. The read(b) method for class InputStream has the same effect as: read(b, 0, b.length)
     * @param b the buffer into which the data is read.
     * @return the total number of bytes read into the buffer, or -1 if there is no more data because the end of the stream has been reached.
     * @throws java.io.IOException If the first byte cannot be read for any reason other than the end of the file, if the input stream has been closed, or if some other I/O error occurs.
     * @throws java.lang.NullPointerException If b is null.
     * @see #read(byte[], int, int) read(byte[], int, int)
     */
    @Override
    public int read(byte[] b) throws java.io.IOException {
        if(!started){
            started = true;
            if(listener != null)
                listener.started();
        }
        int i = stream.read(b);
        if(i<=-1){
            if(!finished){
                finished = true;
                if(listener != null)
                    listener.finished();
            }
        }
        return i;
    }

    /**
     * Renvoie le hashCode de l'InputStream
     * @return Retourne le hashCode de l'InputStream
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id;
        return hash;
    }

    /**
     * Détermine si deux InputStream sont identiques (par leur id unique)
     * @param obj Correspond au second InputStream à comparer au courant
     * @return Retourne true, s'ils sont identiques, sinon false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InputStream other = (InputStream) obj;
        return this.id == other.id;
    }

    /**
     * Affiche un InputStream sous la forme d'une chaîne de caractères
     * @return Retourne un InputStream sous la forme d'une chaîne de caractères
     */
    @Override
    public String toString() {
        return stream.toString();
    }

    /**
     * Compare deux InputStream (par leur id unique)
     * @param o Correspond au second InputStream à comparer au courant
     * @return Retourne le résultat de la comparaison
     */
    @Override
    public int compareTo(InputStream o) {
        if(id < o.id) return -1;
        else if(id > o.id) return 1;
        else return 0;
    }
    
    
    
}