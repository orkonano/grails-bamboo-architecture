[![Build Status](https://api.travis-ci.org/BambooArg/grails-bamboo-architecture.svg?branch=master)](https://api.travis-ci.org/BambooArg/grails-bamboo-architecture.svg?branch=master)

grails-bamboo-architecture
==========================

#Instalación
Agregar el plugin al proyecto
```groovy
compile ":grails-bamboo-architecture:0.1.2"
```

#Build

Para compilar el proyecto e instalarlo localmente se debe ejecutar

 ```script
 
gradle install

```

Para publicar un release se debe ejecutar

```script

gradle publish

```

El repositorio default para la publicación es http://nexus-bambooarg.rhcloud.com/nexus/content/groups/public/


###**Atención**
Tener en cuenta que se tiene que tener configurado las variables de entorno para poder publicar
```script
BAMBOO_REPOSITORY_USERNAME
BAMBOO_REPOSITORY_PASSWORD

```

o las propiedades del proyecto
```script
bambooRepositoryUsername
bambooRepositoryPassword

```


#Test

El proyecto usa travis-ci como entorno de integración continua. https://travis-ci.org/BambooArg/grails-bamboo-architecture.
Se ejecutan test unitarios.


#Cómo usarlo

## Domain
Existe una clase base *EntityBase*, la cual tiene un property *enabled* e implementado el método *beforeInsert()*, 
donde se pone siempre en true a el atributo *enabled*.
El dominio *EntityBase* se encuentra dentro de la carpeta src/grovvy. No la ponemos como artefacto de grails porque no queremos tener herencia en la base de datos.

## Service
La clase *BaseService* implementa los métodos básicos para guardar, deshabilitar y buscar objetos.


## Logging
Todas las clases artefacto tienen logging. Utilizando por ejemplo grails.app.services, loggea todos los service.
Igualmente, la clase BaseService, siempre va a ser extendida, por lo que el logging va a quedar relacionado con la clase padre
