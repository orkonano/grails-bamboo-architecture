grails-bamboo-architecture
==========================

#Instalación
Agregar el plugin al proyecto
```groovy
compile ":grails-bamboo-architecture:0.1.2"
```

#Build

Para compilar el proyecto e instalarlo localmente se debe ejecutar

 ```grails
grails maven-install
```

Para publicar un release se debe ejecutar

```grails
grails publish-plugin

```

Para publicar un snapshot se debe ejecutar

```grails
grails publish-plugin --repository=bambooRepoSnapshot

```

El repositorio default para la publicación es http://nexus-bambooarg.rhcloud.com/nexus/content/groups/public/


###**Atención**
Tener en cuenta que se tiene que tener configurado en .grails/setting.groovy
```groovy
grails.project.repos.default = "bambooRepo"
grails.project.repos.bambooRepo.url = "http://nexus-bambooarg.rhcloud.com/nexus/content/repositories/releases/"
grails.project.repos.bambooRepo.type = "maven"
grails.project.repos.bambooRepo.username = username (poner el username real)
grails.project.repos.bambooRepo.password = password (poner el password real)

grails.project.repos.bambooRepoSnapshot.url = "http://nexus-bambooarg.rhcloud.com/nexus/content/repositories/snapshots/"
grails.project.repos.bambooRepoSnapshot.type = "maven"
grails.project.repos.bambooRepoSnapshot.username = username
grails.project.repos.bambooRepoSnapshot.password = password



#Test

El proyecto usa travis-ci como entorno de integración continua. https://travis-ci.org/orkonano/grails-bamboo-architecture.
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
