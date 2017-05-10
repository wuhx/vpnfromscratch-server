#!/bin/bash
BIN_DIR=$(dirname $0)
sbtver=0.13.11
sbtjar=$BIN_DIR/sbt-launch.jar
# sbtsha128=b407b2a76ad72165f806ac7e7ea09132b951ef53

sbtrepo=http://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch

if [ ! -f $sbtjar ]; then
  echo "downloading $sbtjar" 1>&2
  sbtUrl=$sbtrepo/$sbtver/$(basename $sbtjar)
	echo "downloading..." $sbtUrl
	curl $sbtUrl -Lo $sbtjar
  # if ! curl --silent --fail --remote-name $sbtrepo/$sbtver/$sbtjar; then
# 		echo "download sbt-launch.jar fail"
#     exit 1
#   fi
fi

# checksum=`openssl dgst -sha1 $sbtjar | awk '{ print $2 }'`
# if [ "$checksum" != $sbtsha128 ]; then
#   echo "bad $sbtjar.  delete $sbtjar and run $0 again."
#   exit 1
# fi

[ -f ~/.sbtconfig ] && . ~/.sbtconfig

#SBT_OPTS=$SBT_OPTS:"-DsocksProxyHost=localhost \ -DsocksProxyPort=1080"

#-DsocksProxyHost=localhost      \
#-DsocksProxyPort=1080           \

java -ea                          \
  -Dsbt.ivy.home=/ivy2              \
  $SBT_OPTS                       \
  $JAVA_OPTS                      \
  -Djava.net.preferIPv4Stack=true \
  -XX:+AggressiveOpts             \
  -XX:+UseParNewGC                \
  -XX:+UseConcMarkSweepGC         \
  -XX:+CMSParallelRemarkEnabled   \
  -XX:+CMSClassUnloadingEnabled   \
  -XX:ReservedCodeCacheSize=128m  \
  -XX:SurvivorRatio=128           \
  -XX:MaxTenuringThreshold=0      \
  -server                         \
  -jar $sbtjar "$@"
