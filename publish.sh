#!/bin/bash

echo "instalando bamboo architecture"
grails clean && grails refresh-dependencies && grails maven-install

#grails publish-plugin 

