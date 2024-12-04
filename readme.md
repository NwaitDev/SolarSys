Lancer "jruby solarsys.rb" à la racine pour faire fonctionner l'application.

- JSON -> dossier "bodies"
- RUBY -> dossier "cinnamon"
- JAVA (version 14 ou plus à priori) -> dossier "vanilla"

Pour que l'application fonctionne, récupérer les données au préalable via l'API en ligne.
Se placer dans le dossier cinnamon puis executer :

```bash
jruby get_data_v2.rb
```

Ensuite, compiler les différentes classes du dossier vanilla. 
Se placer dans le dossier vanilla puis executer :

```bash
javac ./model/*.java
javac ./view/*.java
javac ./controller/*.java
```
L'application est prête. Se placer à la racine et la lancer avec :

```bash
jruby solarsys.rb
```
