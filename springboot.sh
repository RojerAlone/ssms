#!/bin/sh
start()
{
    nohup java -jar $1 > STDOUT &
}

stop()
{
    PID=$(ps -ef | grep $1 | grep -v grep | awk '{ print $2 }')
    if [ -z "$PID" ]
    then
        echo "$1 is already stopped"
    else
        echo $PID
        kill $PID
    fi
}
command=$1
arg=$2
if [[ $command == "start" ]]
then
    start $arg
else
    if [[ $command == "stop" ]]
    then
        stop $arg
    else
        echo "error command"
    fi
fi
