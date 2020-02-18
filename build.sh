rm -rf target
sbt universal:packageBin
cd  target/universal/
unzip etml-1.0-SNAPSHOT.zip
cd -
docker build -t etml-test .
docker tag  etml-test nivtraiana/etml-test
docker push nivtraiana/etml-test