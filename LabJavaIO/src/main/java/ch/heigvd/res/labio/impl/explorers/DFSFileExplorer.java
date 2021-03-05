package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    //on récupère la liste des fichiers et répertoire
    File[] listOfFileAndDir = rootDirectory.listFiles();
    if (listOfFileAndDir == null) //si le repertoire est vide, on quitte
      return;

    for(File file : listOfFileAndDir){
      if(file.isDirectory())
        explore(file, vistor); //si on a un repetoire, on le visite
      else
        vistor.visit(file); //si on a un fichier, on l'envoie au visitor
    }
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
