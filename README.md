# Calendar Week Widget 
Displays the current calendar week right in your taskbar - since Windows does not seem to be able to display it.
![image](https://user-images.githubusercontent.com/7944573/161097076-c4781040-c285-415d-9be3-072e6fa98f63.png)

## Installation 
1. Run maven package or download prebuilt jar
2. Create bat file under "shell:startup"
3. Add following line to the bat file:
```bat
start "CWW" javaw -jar C:\Path\To\cww-jar-with-dependencies.jar
```

## Parameters
You may add the parameter -c or --color to change the font color.
i.E.
```
java -jar cww.jar -c RED
```
Possible values can be found under https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
