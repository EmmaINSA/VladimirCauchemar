# VladimirCauchemar
(Copie du README.md en format txt pour respecter la consigne donn�e en cours)

=====================
Projet d'info ASINSA2
=====================

Objectif du projet : 

Mod�liser simplement le comportement d'une onde sonore � travers un intrument � vent � l'aide d'une d�composition de Fourier du signal simplifi�

R�alis� par : TOUT Iyad, BREMARD Alexandre, ZENG Chenyang, NEISS Emma

-------------
CONFIGURATION
-------------

Les �tapes n�cessaires au bon fonctionnement du projet sont les suivantes :

	- v�rifiez que vous poss�dez un SDK suffisamment r�cent (Java 8 est suffisant)
	- installez la biblioth�que JFugue, disponible � l'adresse suivante : http://www.jfugue.org/download.html (version utilis�e : 5.0.9)
	- d�compressez tous les fichiers contenus dans l'archive du projet dans un m�me dossier : celui-ci contiendra normalement deux dossiers "Files" et "src" (ainsi que ce fichier)
	- compilez et ex�cutez MainPrinc.java
	

NB : Si un message d'erreur tel que celui qui suit appara�t dans la console lors de l'ex�cution, ce n'est pas un probl�me pour notre programme.
WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002. Windows RegCreateKeyEx(...) returned error code 5.



-----------
UTILISATION
-----------

Afin de jouer un son avec votre clavier, vous pouvez utiliser les touches S,D,F puis J,K,L et M pour faire les notes de Do � Si.
Les touches noires du piano sont repr�sent�es par les touches E,R puis I,O et P.
Vous pouvez retrouver la correspondance des touches dans le fichier clavier.png (dans Files)
Vous pouvez �galement choisir la dur�e et la hauteur (octave) de la note jou�e avec les sliders sur la droite de la fen�tre.
Les autres options (analyse du signal, changement d'instrument par exemple) se trouvent dans le menu sup�rieur.