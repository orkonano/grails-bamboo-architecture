#!/bin/bash

echo "instalando bamboo architecture"
grails clean && grails refresh-dependencies && grails publish-plugin

#grails publish-plugin 

