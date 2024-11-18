docker run -it -d --name web --privileged --network=host -v /opt/deploy/backup-server:/opt/deploy/backup-server --workdir=/opt/deploy/backup-server --restart=unless-stopped java:8 sh start.sh
