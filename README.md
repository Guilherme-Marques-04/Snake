<h1 align="center">:snake: Retro Snake</h1>

Rétro Snake est un jeu 2D. Le but du jeu est de manger des pommes pour faire grandir notre serpent. Il ne faut pas taper dans les murs ou dans soit même sinon le jeu s'arrête.

# Image

<p float="left">
  <img src="https://github.com/user-attachments/assets/d2dd0e00-8f50-49bd-9de9-28cadbc9b28d" width=30% height=30%>
  <img src="https://github.com/user-attachments/assets/9f7cb7e2-4b71-4030-8f80-7ef4b7b7451b" width=30% height=30%>
  <img src="https://github.com/user-attachments/assets/b7b54e69-b229-42b7-bf94-f57e029592a5" width=30% height=30%>
</p>

- Sur le première image c'est le menu de notre jeu
- Sur la deuxième image c'est le déroulement du jeu
- La troisème image montre la fin d'une partie

# Structure
```
📁 <root>
├── 📁 res             # Project ressources
│   └── 📁 lib         # contains all the libraries required to run the project
├── 📁 src             # Code of the project 
│   ├── 📄 Snake.scala # Primary Scala file. Use this to start the project
│   └── 📁 res         # Ressources called in code
└──     └── 📁 img     # Contains images use in the program
```

# Mode d'emploi

- Pour démarrer le jeu il faut cliquer sur le bouton start
- Pour resart il faut appuyer sur la touche R
- Pour quitter il faut soit cliquer sur le bouton quit du menu ou soit appuyer sur Q
- Le jeu se joue avec les flèches directionnelles

# Conception

Pour la conception de notre jeu Snake, nous avons implémenté les éléments principaux du jeu : le serpent et la pomme qui est générée aléatoirement sur la grille. Le programme prend en compte les inputs de l'utilisateur pour définir la direction dans laquelle le serpent doit se déplacer. À chaque itération, nous vérifions si le serpent a mangé la pomme ou s'il s'est heurté à un mur ou à lui-même. Le score est augmenté de 1 à chaque fois que le serpent mange une pomme. L'affichage graphique a été conçu pour mettre à jour l'écran à chaque itération. Nous avons ajouté un menu initial permettant de lancer la partie et une option pour restart.

