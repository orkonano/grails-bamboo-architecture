grails-bamboo-architecture
==========================

#Instalación
Agregar el plugin al proyecto
```groovy
compile ":grails-bamboo-architecture:0.1.2"
```

#Build

Para compilar el proyecto e intalarlo localmente se debe ejecutar

 ```grails
grails maven-install
```

Para publicarlo se deje ejecutar

```grails
grails publish-plugin --protocol=webdav
```

El repositorio default para la publicación es https://repository-orkoapp.forge.cloudbees.com/snapshot/

###**Atención**
Tener en cuenta que se tiene que tener configurado en .grails/setting.groovy
```groovy
grails.project.repos.cloudbees.url = "dav:https://repository-orkoapp.forge.cloudbees.com/snapshot/"
grails.project.repos.cloudbees.username = yourUsername
grails.project.repos.cloudbees.password = yourPass
```


#Test

El proyecto usa travis-ci como entorno de integración continua. https://travis-ci.org/orkonano/grails-bamboo-architecture.
Se ejecutan test unitarios.


#Cómo usarlo

## Domain
Existe una clase base *EntityBase*, la cual tiene un property *enabled* e implementado el método *beforeInsert()*, 
donde se pone siempre en true a el tributo *enabled*.
El dominio *EntityBase* se encuentra dentro de la carpeta src/grovvy. No la ponemos como artefacto de grails porque no queremos tener herencia en la base de datos.

## Service
La clase *BaseService* implementa los métodos básicos para guardar, deshabilitar y buscar objetos.


## Logging
Todas las clases artefacto tienen logging. Utilizando por ejemplo grails.app.services, loggea todos los service.
Igualmente, la clase BaseService, siempre va a ser extendida, por lo que el logging va a quedar relacionado con la clase padre
