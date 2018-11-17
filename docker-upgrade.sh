git clone https://github.com/castlemock/docker.git
cd docker
sh upgrade.sh $1
git add Dockerfile
git commit -m "Updated the Dockerfile to use Castle Mock version $1"
git push origin master