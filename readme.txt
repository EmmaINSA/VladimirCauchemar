# VladimirCauchemar

## Projet d'info ASINSA2
=====================

### Objectif du projet : 

Mod�liser simplement le comportement d'une onde sonore � travers un intrument � vent � l'aide d'une d�composition de Fourier du signal simplifi�

R�alis� par : TOUT Iyad, BREMARD Alexandre, ZENG Chenyang, NEISS Emma


-------
CONTENU
-------

Notre projet est articul� autour de diff�rents fichiers :

	- les README.md et readme.txt pour d�crire le projet et son fonctionnement
	- le dossier src contenant le code source du projet (*.java)
	- le dossier Files contenant les fichiers auxiliaires utilis�s par le programme
	- la biblioth�que � installer pour pouvoir utiliser le programme
	- le .gitignore qui sert dans la phase de d�veloppement
	- le compte-rendu du projet sous format docx et PDF

-------------
CONFIGURATION
-------------

Les �tapes n�cessaires au bon fonctionnement du projet sont les suivantes :

	- v�rifiez que vous poss�dez un SDK suffisamment r�cent (Java 8 est suffisant)
	- d�compressez tous les fichiers contenus dans l'archive du projet dans un m�me dossier
	- ajoutez la biblioth�que JFugue au projet : elle est pr�sente dans l'archive jfugue-5.0.9.jar � la racine ou disponible ou � l'adresse suivante : http://www.jfugue.org/download.html (version utilis�e : 5.0.9)
	- compilez et ex�cutez MainPrinc.java
	

**NB : Si un message d'erreur tel que celui qui suit appara�t dans la console lors de l'ex�cution, ce n'est pas un probl�me pour notre programme.**
`WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002. Windows RegCreateKeyEx(...) returned error code 5.`



-----------
UTILISATION
-----------

Afin de jouer un son avec votre clavier, vous pouvez utiliser les touches S,D,F puis J,K,L et M pour faire les notes de Do � Si.
Les touches noires du piano sont repr�sent�es par les touches E,R puis I,O et P.
Vous pouvez retrouver la correspondance des touches dans le fichier clavier.png (dans le dossier Files)
Vous pouvez �galement choisir la dur�e et la hauteur (octave) de la note jou�e avec les sliders sur la droite de la fen�tre.
Les autres options (analyse du signal, changement d'instrument par exemple) se trouvent dans le menu sup�rieur.

