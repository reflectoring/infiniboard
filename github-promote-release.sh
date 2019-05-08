#!/bin/bash
shopt -s extglob
#set -x

USERNAME=reflectoring
REPOSITORY=infiniboard
GITHUB_PUSH_USER=matthiasbalke

if [ $# -ne 1 ];
then
  echo "usage: github-promote-release.sh <RELEASE_VERSION>"
  exit 1;
fi

if [ -z "$GITHUB_TOKEN" ]; then
    echo "Environment variable GITHUB_TOKEN must be set. Aborting!"
    exit 2;
fi

TAG_VERSION=$1

command -v jq >/dev/null 2>&1 || { echo >&2 "\"jq\" is required but it's not installed.  Aborting."; exit 1; }
command -v github-release >/dev/null 2>&1 || {

  case "$OSTYPE" in
    darwin*)  OS_TYPE=darwin ;; 
    bsd*)     OS_TYPE=freebsd ;;
    linux*)   OS_TYPE=linux ;;
    msys*)    OS_TYPE=windows ;;
    *)        echo "\"github-release\" is not supported by your os type: $OSTYPE. Aborting."; exit 3 ;;
  esac

  echo >&2 "\"github-release\" is required but it's not installed.  Installing $OS_TYPE version ...";
  
  if [ ! -f ~/github-release.tar.bz2 ];
  then
    echo "downloading release: ~/github-release.tar.bz2 ..."
    curl -L -o ~/github-release.tar.bz2 https://github.com/aktau/github-release/releases/download/v0.7.2/$OS_TYPE-amd64-github-release.tar.bz2
  else
    echo "using preloaded release: ~/github-release.tar.bz2 ..."
  fi

  mkdir -p ~/bin
  tar --strip-components=3 -C ~/bin -jxf ~/github-release.tar.bz2
  echo ""

  command -v ~/bin/github-release >/dev/null 2>&1 || {
    echo "installation of github-release failed. Aborting!"
    exit 4;
  }
}

echo "adding github remote ..."
git remote add github https://$GITHUB_PUSH_USER:$GITHUB_TOKEN@github.com/$USERNAME/$REPOSITORY.git

echo "creating git tag '$TAG_VERSION' ..."
# create git tag
git tag -a $TAG_VERSION $CI_COMMIT_SHA -m "released version $TAG_VERSION"
echo ""

echo "pushing git tag $TAG_VERSION ..."
git push github $TAG_VERSION
echo ""

echo "creating github release draft $TAG_VERSION ..."
# create github release
~/bin/github-release release \
  --user $USERNAME \
  --repo $REPOSITORY \
  --tag "$TAG_VERSION" \
  --name "$TAG_VERSION" \
  --description "Released on $(date +%Y-%m-%d)" \
  --draft
echo ""

echo "uploading github release artifacts ..."
for artifact in {quartermaster/build/libs/quartermaster*.war,harvester/build/libs/harvester*.war}; do
    echo "> $(basename $artifact)"

    # upload artifacts
    ~/bin/github-release upload \
      --user $USERNAME \
      --repo $REPOSITORY \
      --tag "$TAG_VERSION" \
      --name "$(basename "$artifact")" \
      --file "$artifact"
done
echo ""

echo "publishing github release ..."
~/bin/github-release edit \
  --user $USERNAME \
  --repo $REPOSITORY \
  --tag "$TAG_VERSION" \
  --name "$TAG_VERSION"
echo ""
echo "successfully created $USERNAME/$REPOSITORY release $TAG_VERSION !"
