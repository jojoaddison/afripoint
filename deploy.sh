#!/bin/bash

./mvnw -Pprod clean package -DskipTests

mv target/afripointserver.war target/afripointserver.jar

mv target/afripointserver.war.original target/afripointserver.war

scp target/afripointserver.war kojo@smartxms:/home/kojo/webroot/afripoint/.

rsync -azr webapp/* --exclude='data' kojo@smartxms:/home/kojo/webroot/afripoint/dev/.

#rsync -azr webapp/* --exclude='data' kojo@smartxms:/home/kojo/webroot/afripoint/www/.

# rsync -azr webapp/* --exclude='data' --exclude='app/admin/user-management/photos' kojo@smartxms:/home/kojo/webroot/afripoint/www/.

