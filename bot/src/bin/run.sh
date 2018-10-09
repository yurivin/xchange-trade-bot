#!/bin/bash
parent_path=$( cd "$(dirname "${BASH_SOURCE}")" ; pwd -P )
cd $parent_path/..
exec java -cp "$parent_path/../conf/:$parent_path/../lib/*" com.github.yurivin.bot.Application