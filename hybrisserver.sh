#!/bin/sh

DIR=`dirname $0`
cd ${DIR}/suite/hybris/bin/platform
exec "./hybrisserver.sh"
