# ChatApplication

C'est une application qui sert a faire le clavardage entre des users en temps reel, ca pourra fonctionner par groupe ou juste un Chat 1 on 1. C'est la version ditribue, il n'y a pas le server centralise.
Fonctionne sur locale ou plusieurs differentes machines.

## Getting Started

Lancer Graphique.java sur Eclipse (ChatApplication/Interface/Graphique.java)

### Prerequisites

Il n'y a rien de special a installer


## Running the tests

Quand on lance le programme, juste entrer 
	votre pseudo, 
	votre mot de passe, 
	votre port qui va etre utilise (On recommande Le num de port>1024).  
Et puis presse le bouton de se connecter


Sur la page de clavadage: 
Il y aura une liste des users connecte sur notre reseau, vous pourriez cliquer l'un des users et entrer le message que vous voulez envoyer, et puis presse le bouton ENVOYER.
	
Groupe Chat: il faut selectionner au moins deux users en ligne (Press Ctrl+ the users choisi), apres c'est le meme morphologie que Chat 1 on 1.
	
### Break down into end to end tests

Tout fonctionne normallement with quelaues WARNING


## Deployment

TCP et UDP, on a deux tests independent

MainTest.java     (dans package Application)

La premiere partie sert a communiquer avec TCP (Pour le moment on les a mis  en commentaire)

Deuxieme partie sert a tester le multicast et broadcast avec UDP

Tout marche tres bien 


## Built With

Java.net
Java.Swing

## Versioning

1.0 Beta

## Authors

* **Yuheng JIN** - *Initial work* - [YuhengJIN](https://github.com/YuhengJin)
* **Mohammad Amine** - *Initial work* - [aminea7](https://github.com/aminea7)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc

