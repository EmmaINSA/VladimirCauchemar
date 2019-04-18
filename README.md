# VladimirCauchemar

=====================
Projet d'info ASINSA2
=====================

Objectif du projet : 

Modéliser simplement le comportement d'une onde sonore à travers un intrument à vent à l'aide d'une décomposition de Fourier du signal simplifié

-------------
CONFIGURATION
-------------

Les étapes nécessaires au bon fonctionnement du projet sont les suivantes :

	- vérifiez que vous possédez un SDK suffisamment récent (Java 8 est suffisant)
	- installez la bibliothèque JFugue, disponible à l'adresse suivante : http://www.jfugue.org/download.html (version utilisée : 5.0.9)
	- décompressez tous les fichiers contenus dans l'archive du projet dans un même dossier : celui-ci contiendra normalement deux dossiers "Files" et "src" (ainsi que ce fichier)
	- compilez et exécutez MainPrinc.java
	

NB : Si un message d'erreur tel que celui qui suit apparaît dans la console lors de l'exécution, ce n'est pas un problème pour notre programme.
WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002. Windows RegCreateKeyEx(...) returned error code 5.



-----------
UTILISATION
-----------

Afin de jouer un son avec votre clavier, vous pouvez utiliser les touches S,D,F puis J,K,L et M pour faire les notes de Do à Si.
Les touches noires du piano sont représentées par les touches E,R puis I,O et P.
Vous pouvez choisir la durée et la hauteur (octave) de la note jouée avec les sliders sur la droite de la fenêtre.
Les options pour l'analyse du signal ou pour changer d'instrument se trouvent dans le menu supérieur.