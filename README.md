<details><summary>Запуск</summary>
Для того, чтобы запустить необходимо проделать следующие шаги, установите [Git Bash](https://git-scm.com/)

1. Склонируйте репозиторий

```shell
git clone https://github.com/maksim25y/TranslateTask.git
```

2. Скачайте и установите Docker

Скачать и найти инструкцию по установке вы можете на официальном сайте [Docker](https://www.docker.com)

3. Запустите приложение в Docker

Для этого запустите Docker, откройте терминал и перейдите в папку репозитория

```shell
cd TranslateTask
```
Далее введите команду

```shell
docker-compose up --build
```
Готово! Сервер запущен.
Чтобы зайти на сайт перейдите по ссылке: localhost:8080/translate

Чтобы остановить работу контейнеров, в терминале, откуда вы запускали docker-compose нажмите Ctrl+C (Control + C для Mac)
</details>
