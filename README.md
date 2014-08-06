grails-bamboo-architecture
==========================

- Domain
Existe una clase base EntityBase, la cual tiene un property 'enable' e implementado el método 'beforeInsert()', donde se pone siempre en true a la property 'enable'
El dominio EntityBase se encuentra dentro de la carpeta src/grovvy. No la ponemos como artefacto de grails porque no queremos tener herencia en la base de datos

- Service
La clase BaseService implementa los métodos básicos para guardar, deshabilitar y buscar objetos.


- Repositorio maven
https://repository-maximicciullo.forge.cloudbees.com/release/

1) Para instalar el plugin en el entorno local, ejecutar
grails maven-install

2) Para subirlo al repositorio de plugins, ejecutar
grails publish-plugin --protocol=webdav

Atención:
Tener en cuenta que se tiene que tener configurado en .grails/setting.groovy
grails.project.repos.cloudbees.url = "dav:https://repository-orkoapp.forge.cloudbees.com/snapshot/"
grails.project.repos.cloudbees.username = yourUsername
grails.project.repos.cloudbees.password = yourPass
