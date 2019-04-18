# VladimirCauchemar

## Projet d'info ASINSA2
=====================

### Objectif du projet : 

Modéliser simplement le comportement d'une onde sonore à travers un intrument à vent à l'aide d'une décomposition de Fourier du signal simplifié

Réalisé par : TOUT Iyad, BREMARD Alexandre, ZENG Chenyang, NEISS Emma


-------
CONTENU
-------

Notre projet est articulé autour de différents fichiers :

	- les README.md et readme.txt pour décrire le projet et son fonctionnement
	- le dossier src contenant le code source du projet (*.java)
	- le dossier Files contenant les fichiers auxiliaires utilisés par le programme
	- la bibliothèque à installer pour pouvoir utiliser le programme
	- le .gitignore qui sert dans la phase de développement
	- le compte-rendu du projet sous format docx et PDF

-------------
CONFIGURATION
-------------

Les étapes nécessaires au bon fonctionnement du projet sont les suivantes :

	- vérifiez que vous possédez un SDK suffisamment récent (Java 8 est suffisant)
	- décompressez tous les fichiers contenus dans l'archive du projet dans un même dossier
	- ajoutez la bibliothèque JFugue au projet : elle est présente dans l'archive jfugue-5.0.9.jar à la racine ou disponible ou à l'adresse suivante : http://www.jfugue.org/download.html (version utilisée : 5.0.9)
	- compilez et exécutez MainPrinc.java
	

**NB : Si un message d'erreur tel que celui qui suit apparaît dans la console lors de l'exécution, ce n'est pas un problème pour notre programme.**
`WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002. Windows RegCreateKeyEx(...) returned error code 5.`



-----------
UTILISATION
-----------

Afin de jouer un son avec votre clavier, vous pouvez utiliser les touches S,D,F puis J,K,L et M pour faire les notes de Do à Si.
Les touches noires du piano sont représentées par les touches E,R puis I,O et P.
Vous pouvez retrouver la correspondance des touches dans le fichier clavier.png (dans le dossier Files)
Vous pouvez également choisir la durée et la hauteur (octave) de la note jouée avec les sliders sur la droite de la fenêtre.
Les autres options (analyse du signal, changement d'instrument par exemple) se trouvent dans le menu supérieur.

