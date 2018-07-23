#!/usr/bin/env bash
docker run -v "$PWD":/code -p 8080:8080 spring-boot-web-service