#!/usr/bin/make -f

 
dev-start:
	echo "START FRONTEND"
	docker build -t amadeuscam/vue-perfumir . --no-cache
	docker run -it -p 3000:3000 --rm --name perfumir-app amadeuscam/vue-perfumir:latest

dev-build:
	docker build --platform linux/amd64 -t amadeuscam/perfumir-api .


