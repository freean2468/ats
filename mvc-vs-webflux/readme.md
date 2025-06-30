# mvc

```sh
curl https://start.spring.io/starter.zip \
  -d dependencies=web \
  -d baseDir=mvc \
  -d packageName=com.example.mvc \
  -d name=MvcApplication \
  -d bootVersion=3.5.0 \
  -o mvc.zip
```

# webflux

```sh
curl https://start.spring.io/starter.zip \
  -d dependencies=webflux \
  -d baseDir=webflux \
  -d packageName=com.example.webflux \
  -d name=WebfluxApplication \
  -d bootVersion=3.5.0 \
  -o webflux.zip
```

# gradle build

```sh
./gradlew clean build
```

# docker build & run

```sh
docker compose up --build
docker compose up -d
docker compose logs -f
```
