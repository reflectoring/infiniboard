#!/bin/bash
#set -x

USERNAME=reflectoring
REPOSITORY=infiniboard

if [ $# -ne 2 ];
then
  echo "usage: github-promote-release.sh <CIRCLE_CI_BUILD_NUMBER> <RELEASE_VERSION>"
  exit 1;
fi

BUILD_NUMBER=$1
TAG_VERSION=$2

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

  command -v github-release >/dev/null 2>&1 || {
    echo "adding ~/bin to PATH using ~/.bashrc..."
    echo export PATH=~/bin:"$PATH" >> ~/.bashrc 
    source ~/.bashrc
    echo ""
  }
}

CIRCLE_BUILD_URL=https://circleci.com/api/v1.1/project/github/$USERNAME/$REPOSITORY/$BUILD_NUMBER
CIRCLE_ARTIFACTS_URL=$CIRCLE_BUILD_URL/artifacts

# get VCS revision
echo "fetching git hash for build #$BUILD_NUMBER"
JSON=$(curl -s $CIRCLE_BUILD_URL)
COMMIT_HASH=$(echo $JSON | jq -r '.vcs_revision')
echo "> $COMMIT_HASH"
echo ""

echo "fetching artifacts for build #$BUILD_NUMBER"
JSON_ARTIFACTS=$(curl -s $CIRCLE_ARTIFACTS_URL)

# create download folder
DOWNLOAD_DIR=`pwd`/build/download
rm -rf $DOWNLOAD_DIR
mkdir -p $DOWNLOAD_DIR
cd $DOWNLOAD_DIR

# download matching artifacts
echo "downloading artifacts ..."
# TODO extract download file function
echo $JSON_ARTIFACTS | jq '.[].url' | while read artifact; do
    if [[ $artifact == *"war"* ]]
    then
        ARTIFACT=$(echo $artifact | cut -d '"' -f 2)
        echo "> $ARTIFACT"
        curl -s -L -O $(echo $ARTIFACT)
    fi
done
echo ""

echo "creating git tag '$TAG_VERSION' ..."
# create git tag
git tag -a $TAG_VERSION $COMMIT_HASH -m "released version $TAG_VERSION"
echo ""

echo "pushing git tag $TAG_VERSION ..."
git push origin $TAG_VERSION
echo ""

echo "creating github release draft $TAG_VERSION ..."
# create github release
github-release release \
  --user $USERNAME \
  --repo $REPOSITORY \
  --tag "$TAG_VERSION" \
  --name "$TAG_VERSION" \
  --description "Released on $(date +%Y-%m-%d)" \
  --draft
echo ""

echo "uploading github release artifacts ..."
for artifact in $DOWNLOAD_DIR/*.*; do
    echo "> $(basename $artifact)"

    # upload artifacts
    github-release upload \
      --user $USERNAME \
      --repo $REPOSITORY \
      --tag "$TAG_VERSION" \
      --name "$(basename "$artifact")" \
      --file "$artifact"
done
echo ""

echo "publishing github release ..."
github-release edit \
  --user $USERNAME \
  --repo $REPOSITORY \
  --tag "$TAG_VERSION" \
  --name "$TAG_VERSION"
echo ""
echo "successfully created $USERNAME/$REPOSITORY release $TAG_VERSION !"
